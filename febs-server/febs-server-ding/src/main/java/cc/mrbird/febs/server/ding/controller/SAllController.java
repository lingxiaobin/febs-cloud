package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.common.core.entity.constant.PageConstant;
import cc.mrbird.febs.common.core.entity.ding.KUser;
import cc.mrbird.febs.common.core.entity.ding.SAll;
import cc.mrbird.febs.server.ding.annotation.ControllerEndpoint;
import cc.mrbird.febs.server.ding.controller.req.BaseReq;
import cc.mrbird.febs.server.ding.controller.req.FlushReq;
import cc.mrbird.febs.server.ding.controller.req.SAllReq;
import cc.mrbird.febs.server.ding.service.ISAllService;
import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.exception.FebsException;
import cc.mrbird.febs.common.core.utils.FebsUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Controller
 *
 * @author MrBird
 * @date 2020-12-04 14:33:28
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sAll")
@RequiredArgsConstructor
public class SAllController {

    private final ISAllService sAllService;

    @GetMapping
    @PreAuthorize("hasAuthority('sAll:list')")
    public FebsResponse getAllSAlls(SAll sAll) {
        return new FebsResponse().data(sAllService.findSAlls(sAll));
    }

    @PostMapping("list")
    public FebsResponse sAllList(QueryRequest request, SAllReq req) {
        return new FebsResponse().data(this.sAllService.findSAlls(request, req));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sAll:add')")
    public void addSAll(@Valid SAll sAll) throws FebsException {
        try {
            this.sAllService.createSAll(sAll);
        } catch (Exception e) {
            String message = "新增SAll失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sAll:delete')")
    public void deleteSAll(SAll sAll) throws FebsException {
        try {
            this.sAllService.deleteSAll(sAll);
        } catch (Exception e) {
            String message = "删除SAll失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('sAll:update')")
    public void updateSAll(SAll sAll) throws FebsException {
        try {
            this.sAllService.updateSAll(sAll);
        } catch (Exception e) {
            String message = "修改SAll失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("aync")
    public FebsResponse ayncDate(FlushReq flushReq) throws ParseException {
        return new FebsResponse().data(sAllService.ayncDate(flushReq));
    }

    @PostMapping("excel")
    @ControllerEndpoint(operation = "导出薪资数据", exceptionMessage = "导出Excel失败")
    public void export(QueryRequest request, SAllReq req, HttpServletResponse response) throws Exception {

//        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding("utf-8");
//        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
//        String fileName = URLEncoder.encode("测试", "UTF-8");
//        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
//        EasyExcel.write(response.getOutputStream(), SAll.class)
//                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
//                .sheet("全部数据").doWrite(sAlls);

        String fileName = "test2.zip";
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"));
        //按某个条件分组
        Map<String, List<SAll>> map = new HashMap();
        String[] payPlaces = {"惠东特创", "深圳特创", "淮安特创"};
        String[] payComputeTypes = {"月薪", "时薪", "财务"};
        for (String payPlace : payPlaces) {
            for (String payComputeType : payComputeTypes) {
                req.setPayPlaces(new String[]{payPlace});
                req.setPayComputeTypes(new String[]{payComputeType});
                List<SAll> sAlls = (List<SAll>) this.sAllService.findSAlls(request, req).get(PageConstant.ROWS);
                map.put(payPlace+"-" + payComputeType + ".xls", sAlls);
            }
        }
        req.setPayPlaces(payPlaces);
        req.setPayComputeTypes(payComputeTypes);
        List<SAll> sAlls = (List<SAll>) this.sAllService.findSAlls(request, req).get(PageConstant.ROWS);
        map.put("全部"  + ".xls", sAlls);
        exportExcel(response, map);
    }

    private void exportExcel(HttpServletResponse response, Map<String, List<SAll>> map) throws Exception {
        ServletOutputStream outputStream = response.getOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        try {
            for (Map.Entry<String, List<SAll>> entry : map.entrySet()) {
                String k = entry.getKey();
                List<SAll> value = entry.getValue();
                //构建一个excel对象,这里注意type要是xls不能是xlsx,否则下面的写入后流会关闭,导致报错
                ExcelWriter excelWriter = EasyExcel.write().excelType(ExcelTypeEnum.XLS).build();
                //构建一个sheet页
                WriteSheet writeSheet = EasyExcel.writerSheet("薪资单").build();
                //构建excel表头信息
                WriteTable writeTable0 = EasyExcel.writerTable(0).head(SAll.class).needHead(Boolean.TRUE).build();
                //将表头和数据写入表格
                excelWriter.write(value, writeSheet, writeTable0);

                //创建压缩文件
                ZipEntry zipEntry = new ZipEntry(k);
                zipOutputStream.putNextEntry(zipEntry);
                Workbook workbook = excelWriter.writeContext().writeWorkbookHolder().getWorkbook();
                //将excel对象以流的形式写入压缩流
                workbook.write(zipOutputStream);
            }
            zipOutputStream.flush();
        } catch (Exception e) {
            log.error("导XXX失败,原因" + e.getMessage());
            log.error(e.getMessage(), e);
            //抛出异常结束程序
            throw new Exception("数据导出接口异常", e);
        } finally {
            //关闭数据流，注意关闭的顺序
            zipOutputStream.close();
            outputStream.close();
        }
    }


}

