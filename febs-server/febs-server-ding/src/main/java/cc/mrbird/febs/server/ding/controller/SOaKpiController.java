package cc.mrbird.febs.server.ding.controller;


import cc.mrbird.febs.common.core.entity.ding.SOaKpi;
import cc.mrbird.febs.server.ding.service.ISOaKpiService;
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
 * @date 2020-12-26 14:58:08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sOaKpi")
@RequiredArgsConstructor
public class SOaKpiController {

    private final ISOaKpiService sOaKpiService;

    @GetMapping
    @PreAuthorize("hasAuthority('sOaKpi:list')")
    public FebsResponse getAllSOaKpis(SOaKpi sOaKpi) {
        return new FebsResponse().data(sOaKpiService.findSOaKpis(sOaKpi));
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sOaKpi:list')")
    public FebsResponse sOaKpiList(QueryRequest request, SOaKpi sOaKpi) {
        Map<String, Object> dataTable = FebsUtil.getDataTable(this.sOaKpiService.findSOaKpis(request, sOaKpi));
        return new FebsResponse().data(dataTable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sOaKpi:add')")
    public void addSOaKpi(@Valid SOaKpi sOaKpi) throws FebsException {
        try {
            this.sOaKpiService.createSOaKpi(sOaKpi);
        } catch (Exception e) {
            String message = "新增SOaKpi失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sOaKpi:delete')")
    public void deleteSOaKpi(SOaKpi sOaKpi) throws FebsException {
        try {
            this.sOaKpiService.deleteSOaKpi(sOaKpi);
        } catch (Exception e) {
            String message = "删除SOaKpi失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('sOaKpi:update')")
    public void updateSOaKpi(SOaKpi sOaKpi) throws FebsException {
        try {
            this.sOaKpiService.updateSOaKpi(sOaKpi);
        } catch (Exception e) {
            String message = "修改SOaKpi失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
