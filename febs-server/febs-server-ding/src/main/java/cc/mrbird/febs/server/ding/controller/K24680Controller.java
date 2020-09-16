package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.K24680;
import cc.mrbird.febs.server.ding.annotation.ControllerEndpoint;
import cc.mrbird.febs.server.ding.service.IKKaoqinService;
import cc.mrbird.febs.server.ding.service.IKUserService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 *  Controller
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
@Slf4j
@Validated
@RestController
@RequestMapping("k24680")
@RequiredArgsConstructor
public class K24680Controller {

    private final IKUserService kUserService;

    @GetMapping
    @PreAuthorize("hasAuthority('k24680:list')")
    public FebsResponse getAllK24680(QueryRequest queryRequest,K24680 k24680) {
        return new FebsResponse().data(kUserService.findK24680(queryRequest,k24680));
    }


    @PostMapping("excel")
    @PreAuthorize("hasAuthority('k24680:export')")
    @ControllerEndpoint(operation = "导出用户数据", exceptionMessage = "导出Excel失败")
    public void export(QueryRequest queryRequest, K24680 k24680, HttpServletResponse response) throws IOException {
        List<K24680> k24680List= kUserService.findK24680(queryRequest,k24680);
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), K24680.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet("全部数据").doWrite(k24680List);
    }
}
