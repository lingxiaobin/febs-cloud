package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.KUser;
import cc.mrbird.febs.common.core.exception.FebsException;
import cc.mrbird.febs.common.core.utils.FebsUtil;
import cc.mrbird.febs.server.ding.service.IKUserService;
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
 * @date 2020-09-07 11:15:39
 */
@Slf4j
@Validated
@RestController
@RequestMapping("kUser")
@RequiredArgsConstructor
public class KUserController {

    private final IKUserService kUserService;

    @GetMapping
    @PreAuthorize("hasAuthority('kUser:list')")
    public FebsResponse getAllKUsers(KUser kUser) {
        return new FebsResponse().data(kUserService.findKUsers(kUser));
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('kUser:list')")
    public FebsResponse kUserList(QueryRequest request, KUser kUser) {
        Map<String, Object> dataTable = FebsUtil.getDataTable(this.kUserService.findKUsers(request, kUser));
        return new FebsResponse().data(dataTable);
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
    public void updateKUser(KUser kUser) throws FebsException {
        try {
            this.kUserService.updateKUser(kUser);
        } catch (Exception e) {
            String message = "修改KUser失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
