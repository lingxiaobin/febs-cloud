package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.common.core.entity.ding.SEat;
import cc.mrbird.febs.server.ding.controller.excel.common.UploadExcelRes;
import cc.mrbird.febs.server.ding.controller.excel.redis.ExcelRedis;
import cc.mrbird.febs.server.ding.controller.excel.salary.SEatExcel;
import cc.mrbird.febs.server.ding.controller.excel.salary.SEatToListener;
import cc.mrbird.febs.server.ding.controller.req.SOaKpiReq;
import cc.mrbird.febs.server.ding.service.ISEatService;
import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.exception.FebsException;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Controller
 *
 * @author MrBird
 * @date 2021-01-04 08:39:20
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sEat")
@RequiredArgsConstructor
public class SEatController {

    private final ISEatService sEatService;
    private final ExcelRedis<SEatExcel> excelRedis;

    @GetMapping
    @PreAuthorize("hasAuthority('sEat:list')")
    public FebsResponse getAllSEats(SEat sEat) {
        return new FebsResponse().data(sEatService.findSEats(sEat));
    }

    @PostMapping("list")
//    @PreAuthorize("hasAuthority('sEat:list')")
    public FebsResponse sEatList(QueryRequest request, SOaKpiReq sOaKpiReq) {
        return new FebsResponse().data(this.sEatService.findSEats(request, sOaKpiReq));
    }

    @PostMapping("findDayNums")
    public FebsResponse findDayNums(SOaKpiReq sOaKpiReq) {
        return new FebsResponse().data(this.sEatService.findSSalaryDayNums(sOaKpiReq));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sEat:add')")
    public void addSEat(@Valid SEat sEat) throws FebsException {
        try {
            this.sEatService.createSEat(sEat);
        } catch (Exception e) {
            String message = "新增SEat失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sEat:delete')")
    public void deleteSEat(SEat sEat) throws FebsException {
        try {
            this.sEatService.deleteSEat(sEat);
        } catch (Exception e) {
            String message = "删除SEat失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('sEat:update')")
    public void updateSEat(SEat sEat) throws FebsException {
        try {
            this.sEatService.updateSEat(sEat);
        } catch (Exception e) {
            String message = "修改SEat失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping("updateDayNums")
    public void updateDayNums(String workDate,String dayNumsVal) throws FebsException {
        try {
            this.sEatService.updateDayNums(workDate,dayNumsVal);
        } catch (Exception e) {
            String message = "修改SEat失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @PostMapping("upload")
    @ResponseBody
    public UploadExcelRes upload1(MultipartFile file, String workDate) throws IOException {
        SEatToListener sEatToListener = new SEatToListener(sEatService,excelRedis, workDate);
        EasyExcel.read(file.getInputStream(), SEatExcel.class, sEatToListener).sheet().doRead();
        return sEatToListener.getUploadExcelRes();
    }

    @PostMapping("download")
    @ResponseBody
    public void download(HttpServletResponse response, String id) throws IOException {
        List<SEatExcel> list = excelRedis.setErrorDate(id);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), SEatExcel.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet("全部数据").doWrite(list);
    }
}
