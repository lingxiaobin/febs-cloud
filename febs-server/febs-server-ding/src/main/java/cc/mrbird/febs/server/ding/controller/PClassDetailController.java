package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.common.core.entity.ding.PClassDetail;
import cc.mrbird.febs.server.ding.controller.excel.PClassDetailTo1;
import cc.mrbird.febs.server.ding.controller.excel.PClassDetailTo1Listener;
import cc.mrbird.febs.server.ding.controller.excel.PClassDetailTo2;
import cc.mrbird.febs.server.ding.controller.excel.PClassDetailTo2Listener;
import cc.mrbird.febs.server.ding.service.IPClassDetailService;
import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.exception.FebsException;
import cc.mrbird.febs.common.core.utils.FebsUtil;
import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

/**
 *  Controller
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
    public FebsResponse pClassDetailList(QueryRequest request, PClassDetail pClassDetail) {
        Map<String, Object> dataTable = FebsUtil.getDataTable(this.pClassDetailService.findPClassDetails(request, pClassDetail));
        return new FebsResponse().data(dataTable);
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
    @PreAuthorize("hasAuthority('pClassDetail:delete')")
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
    @PreAuthorize("hasAuthority('pClassDetail:update')")
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
    public String upload1(MultipartFile file,@PathVariable Integer classId) throws IOException {
        EasyExcel.read(file.getInputStream(), PClassDetailTo1.class, new PClassDetailTo1Listener(pClassDetailService,classId)).sheet().doRead();
        return "success";
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
    public String upload2(MultipartFile file,@PathVariable Integer classId) throws IOException {
        EasyExcel.read(file.getInputStream(), PClassDetailTo2.class, new PClassDetailTo2Listener(pClassDetailService,classId)).sheet().doRead();
        return "success";
    }
}
