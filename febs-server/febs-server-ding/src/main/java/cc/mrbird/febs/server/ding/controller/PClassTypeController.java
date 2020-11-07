package cc.mrbird.febs.server.ding.controller;


import cc.mrbird.febs.common.core.entity.ding.PClassType;
import cc.mrbird.febs.server.ding.service.IPClassTypeService;
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
 * @date 2020-11-06 08:20:43
 */
@Slf4j
@Validated
@RestController
@RequestMapping("pClassType")
@RequiredArgsConstructor
public class PClassTypeController {

    private final IPClassTypeService pClassTypeService;

    @GetMapping
    @PreAuthorize("hasAuthority('pClassType:list')")
    public FebsResponse getAllPClassTypes(PClassType pClassType) {
        return new FebsResponse().data(pClassTypeService.findPClassTypes(pClassType));
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('pClassType:list')")
    public FebsResponse pClassTypeList(QueryRequest request, PClassType pClassType) {
        Map<String, Object> dataTable = FebsUtil.getDataTable(this.pClassTypeService.findPClassTypes(request, pClassType));
        return new FebsResponse().data(dataTable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('pClassType:add')")
    public void addPClassType(@Valid PClassType pClassType) throws FebsException {
        try {
            this.pClassTypeService.createPClassType(pClassType);
        } catch (Exception e) {
            String message = "新增PClassType失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('pClassType:delete')")
    public void deletePClassType(PClassType pClassType) throws FebsException {
        try {
            this.pClassTypeService.deletePClassType(pClassType);
        } catch (Exception e) {
            String message = "删除PClassType失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('pClassType:update')")
    public void updatePClassType(PClassType pClassType) throws FebsException {
        try {
            this.pClassTypeService.updatePClassType(pClassType);
        } catch (Exception e) {
            String message = "修改PClassType失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
