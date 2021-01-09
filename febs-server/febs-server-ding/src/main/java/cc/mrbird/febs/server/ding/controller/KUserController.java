package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.constant.PageConstant;
import cc.mrbird.febs.common.core.entity.ding.KUser;
import cc.mrbird.febs.common.core.entity.ding.PClass;
import cc.mrbird.febs.common.core.entity.ding.PClassDetailAll;
import cc.mrbird.febs.common.core.exception.FebsException;
import cc.mrbird.febs.common.core.utils.DateUtil;
import cc.mrbird.febs.common.core.utils.FebsUtil;
import cc.mrbird.febs.server.ding.annotation.ControllerEndpoint;
import cc.mrbird.febs.server.ding.controller.req.DeptReq;
import cc.mrbird.febs.server.ding.controller.req.KUserReq;
import cc.mrbird.febs.server.ding.controller.req.PClassReq;
import cc.mrbird.febs.server.ding.service.IKKaoqinService;
import cc.mrbird.febs.server.ding.service.IKUserService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.taobao.api.ApiException;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
@Slf4j
@Validated
@RestController
@RequestMapping("kUser")
@RequiredArgsConstructor
public class KUserController {

    private final IKUserService kUserService;

    /**
     * 员工搜索  相同工号的去重复
     *
     * @param kUser
     * @return
     */
    @GetMapping
    public FebsResponse getAllKUser(KUser kUser) {
        return new FebsResponse().data(this.kUserService.findKUsers(kUser));
    }

    @GetMapping("likeUser")
    public FebsResponse deptAndUser(String name, Integer leaveType) {
        return new FebsResponse().data(this.kUserService.findLikeUser(name, leaveType));
    }

    @GetMapping("deptAndUser")
    public FebsResponse deptAndUser(DeptReq deptReq) {
        return new FebsResponse().data(this.kUserService.findDeptAndUser(deptReq));
    }

    @PostMapping("list")
    public FebsResponse getAllKUsers(Authentication authentication, QueryRequest request, KUserReq req) {
        boolean bool = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("kUser:moreDetail"));
        req.setAdmin(bool);
        Map<String, Object> dataTable = FebsUtil.getDataTable(this.kUserService.findKUsers(request, req));
        return new FebsResponse().data(dataTable);
    }

    @GetMapping("listOption")
    public FebsResponse getAllKUsers(Authentication authentication) throws ApiException {
        boolean bool = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("kUser:moreDetail"));
        if (bool) {
            return new FebsResponse().data(this.kUserService.findKUserOption());
        } else {
            return new FebsResponse().data(null);
        }

    }

    @PostMapping
    @PreAuthorize("hasAuthority('kUser:add')")
    public void addKUser(@Valid KUser kUser) throws FebsException {
        try {
            this.kUserService.createKUser(kUser);
        } catch (Exception e) {
            String message = "新增KUser失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('kUser:delete')")
    public void deleteKUser(KUser kUser) throws FebsException {
        try {
            this.kUserService.deleteKUser(kUser);
        } catch (Exception e) {
            String message = "删除KUser失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('kUser:update')")
    public FebsResponse updateKUser(KUser kUser) throws FebsException {
        try {
            return new FebsResponse().data(this.kUserService.updateKUserDingApi(kUser));
        } catch (Exception e) {
            String message = "修改KUser失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @PreAuthorize("hasAuthority('kUser:export')")
    @ControllerEndpoint(operation = "导出用户数据", exceptionMessage = "导出Excel失败")
    public void export(Authentication authentication, QueryRequest queryRequest, KUserReq req, HttpServletResponse response) throws IOException {
        boolean bool = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("kUser:moreDetail"));
        req.setAdmin(bool);
        queryRequest.setAll(true);
        List<KUser> users = this.kUserService.findKUsers(queryRequest, req).getRecords();
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), KUser.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet("全部数据").doWrite(users);
    }

    @PostMapping("excelToOther")
    @ControllerEndpoint(operation = "excelToOther", exceptionMessage = "导出excelToOther失败")
    public void excelToUser(Authentication authentication, QueryRequest queryRequest, KUserReq req, HttpServletResponse response) throws IOException {
        boolean bool = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("kUser:moreDetail"));
        req.setAdmin(bool);
        queryRequest.setAll(true);
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("static/人员信息新增和减少.xlsx");
        List<KUser> list = this.kUserService.findKUsers(queryRequest, req).getRecords();
        List<Map<String, String>> mapList = new ArrayList<>();
        for (KUser kUser : list) {
            Map<String, String> map = new HashMap<>();
            map.put("deptName", kUser.getDeptName());
            map.put("name", kUser.getName());
            map.put("certNoType", "居民身份证");
            map.put("certNo", kUser.getCertNo());
            map.put("country", "中国");
            map.put("sexType", kUser.getSexType());
            map.put("birthTime", kUser.getBirthTime() != null ? DateUtil.getDateFormat(kUser.getBirthTime(), "yyyy-MM-dd") : null);
            map.put("type1", kUser.getLastWorkDay() == null ? "正常" : "不正常");
            map.put("type2", "雇员");
            map.put("phone", kUser.getPhone());
            map.put("hiredDate", kUser.getHiredDate() != null ? DateUtil.getDateFormat(kUser.getHiredDate(), "yyyy-MM-dd") : null);
            map.put("lastWorkDay", kUser.getLastWorkDay() != null ? DateUtil.getDateFormat(kUser.getLastWorkDay(), "yyyy-MM-dd") : null);
            mapList.add(map);
        }
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(resourceAsStream).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            excelWriter.fill(new FillWrapper("list", mapList), writeSheet);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }
}
