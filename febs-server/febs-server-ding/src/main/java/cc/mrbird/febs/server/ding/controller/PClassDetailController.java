package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.common.core.entity.constant.PageConstant;
import cc.mrbird.febs.common.core.entity.ding.PClassDetail;
import cc.mrbird.febs.common.core.entity.ding.PClassDetailAll;
import cc.mrbird.febs.common.core.utils.DateUtil;
import cc.mrbird.febs.server.ding.annotation.ControllerEndpoint;
import cc.mrbird.febs.server.ding.controller.excel.plainclass.PClassDetailTo1;
import cc.mrbird.febs.server.ding.controller.excel.plainclass.PClassDetailTo1Listener;
import cc.mrbird.febs.server.ding.controller.excel.plainclass.PClassDetailTo2;
import cc.mrbird.febs.server.ding.controller.excel.plainclass.PClassDetailTo2Listener;
import cc.mrbird.febs.server.ding.controller.req.PClassReq;
import cc.mrbird.febs.server.ding.service.IPClassDetailService;
import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.exception.FebsException;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ClassUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author MrBird
 * @date 2020-11-06 08:37:21
 */
@Slf4j
@Validated
@RestController
@RequestMapping("pClassDetail")
@RequiredArgsConstructor
public class PClassDetailController {

    private final IPClassDetailService pClassDetailService;

    @GetMapping
    @PreAuthorize("hasAuthority('pClassDetail:list')")
    public FebsResponse getAllPClassDetails(PClassDetail pClassDetail) {
        return new FebsResponse().data(pClassDetailService.findPClassDetails(pClassDetail));
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('pClassDetail:list')")
    public FebsResponse pClassDetailList(QueryRequest request, PClassReq pClassReq) {
        Map<String, Object> dataTable = this.pClassDetailService.findPClassDetails(request, pClassReq);
        return new FebsResponse().data(dataTable);
    }

    /**
     * 课程的导入情况 导入舒汇总
     *
     * @param request
     * @param pClassReq
     * @return
     */
    @GetMapping("listStateIn")
    @PreAuthorize("hasAuthority('pClassDetail:list')")
    public FebsResponse selectPClassDetailStateIn(QueryRequest request, PClassReq pClassReq) {
        return new FebsResponse().data(this.pClassDetailService.selectPClassDetailStateIn(pClassReq));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('pClassDetail:add')")
    public void addPClassDetail(@Valid PClassDetail pClassDetail) throws FebsException {
        try {
            this.pClassDetailService.createPClassDetail(pClassDetail);
        } catch (Exception e) {
            String message = "新增PClassDetail失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('pClass:update')")
    public void deletePClassDetail(PClassDetail pClassDetail) throws FebsException {
        try {
            this.pClassDetailService.deletePClassDetail(pClassDetail);
        } catch (Exception e) {
            String message = "删除PClassDetail失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('pClass:update')")
    public void updatePClassDetail(PClassDetail pClassDetail) throws FebsException {
        try {
            this.pClassDetailService.updatePClassDetail(pClassDetail);
        } catch (Exception e) {
            String message = "修改PClassDetail失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 文件上传
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link }
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link }
     * <p>
     * 3. 直接读即可
     */
    @PostMapping("upload1/{classId}")
    @ResponseBody
    public Integer upload1(MultipartFile file, @PathVariable Integer classId) throws IOException {
        PClassDetailTo1Listener classDetailTo1Listener = new PClassDetailTo1Listener(pClassDetailService, classId);
        EasyExcel.read(file.getInputStream(), PClassDetailTo1.class, classDetailTo1Listener).sheet().doRead();
        return  classDetailTo1Listener.sumALl;
    }

    /**
     * 文件上传
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link }
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link }
     * <p>
     * 3. 直接读即可
     */
    @PostMapping("upload2/{classId}")
    @ResponseBody
    public Integer upload2(MultipartFile file, @PathVariable Integer classId) throws IOException {
        PClassDetailTo2Listener classDetailTo1Listener = new PClassDetailTo2Listener(pClassDetailService, classId);
        EasyExcel.read(file.getInputStream(), PClassDetailTo2.class,classDetailTo1Listener ).sheet().doRead();
        return classDetailTo1Listener.sumALl;
    }

    @PostMapping("excelToProject")
    @ControllerEndpoint(operation = "导出excelToProject数据", exceptionMessage = "导出excelToProject Excel失败")
    public void excelToProject(QueryRequest queryRequest, PClassReq pClassReq, HttpServletResponse response) throws IOException {
        String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        staticPath = staticPath + File.separator + "培训签到表.xlsx";
        log.info("目录路径:"+staticPath);
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("static/培训签到表.xlsx");
        queryRequest.setAll(true);
        pClassReq.setSelOne(true);
        List<PClassDetailAll> list = (List<PClassDetailAll>) this.pClassDetailService.findPClassDetails(queryRequest, pClassReq).get(PageConstant.ROWS);
        PClassDetailAll data = list.get(0);

        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(resourceAsStream).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            excelWriter.fill(new FillWrapper("list", list), writeSheet);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("trainTime", DateUtil.getDateFormat(data.getTrainStartTime(), "MM-dd HH:mm") + "-" + DateUtil.getDateFormat(data.getTrainEndTime(), "MM-dd HH:mm"));
            map.put("trainLong", (data.getTrainEndTime().getTime() - data.getTrainStartTime().getTime()) / 3600000);
            map.put("trainName", data.getTrainNameStr());
            map.put("trainSite", data.getTrainSite());
            map.put("className", data.getClassName());
            map.put("expectNum", list.size());
            long signInCount = list.stream().filter(d -> d.getSignInTime() != null).count();
            long fractionCount = list.stream().filter(d -> d.getFraction() != null).count();
            map.put("signInCount", signInCount);
            map.put("fractionCount", fractionCount);
            map.put("giveLessons", data.getGiveLessons());
            map.put("examine", data.getExamine());
            map.put("train", data.getTrain());
            excelWriter.fill(map, writeSheet);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    @PostMapping("excelToUser")
    @ControllerEndpoint(operation = "导出excelToUser数据", exceptionMessage = "导出excelToUser Excel失败")
    public void excelToUser(QueryRequest queryRequest, PClassReq pClassReq, HttpServletResponse response) throws IOException {
        //此处getResourceAsStream 用于获取服务器打包后的Excel模板文件流;
        //如果采用getPath方法获取文件地址本地ieda环境可以获取到，上传到服务器后会失效。采用流可以都生效，具体原因暂未仔细查看。有兴趣的童鞋可以自己去尝试！
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("static/员工个人培训记录表.xlsx");
        queryRequest.setAll(true);
        pClassReq.setSelOne(true);
        List<PClassDetailAll> list = (List<PClassDetailAll>) this.pClassDetailService.findPClassDetails(queryRequest, pClassReq).get(PageConstant.ROWS);
        PClassDetailAll data = list.get(0);
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(resourceAsStream).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            excelWriter.fill(new FillWrapper("list", list), writeSheet);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userName", data.getUserName());
            map.put("jobnumber", data.getJobnumber());
            map.put("position", data.getPosition());
            map.put("deptName", data.getDeptName());
            excelWriter.fill(map, writeSheet);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }
}
