package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.common.core.entity.ding.SAll;
import cc.mrbird.febs.server.ding.controller.req.BaseReq;
import cc.mrbird.febs.server.ding.controller.req.FlushReq;
import cc.mrbird.febs.server.ding.service.ISAllService;
import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.exception.FebsException;
import cc.mrbird.febs.common.core.utils.FebsUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.text.ParseException;
import java.util.Map;

/**
 *  Controller
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
    public FebsResponse sAllList(QueryRequest request,  BaseReq req) {
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
}
