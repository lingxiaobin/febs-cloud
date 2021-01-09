package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.common.core.entity.ding.SEat;
import cc.mrbird.febs.server.ding.controller.req.SOaKpiReq;
import cc.mrbird.febs.server.ding.service.ISEatService;
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
import java.util.Map;

/**
 *  Controller
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
}
