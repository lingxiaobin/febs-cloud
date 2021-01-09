package cc.mrbird.febs.server.ding.controller;


import cc.mrbird.febs.common.core.entity.ding.SOaTeam;
import cc.mrbird.febs.server.ding.controller.req.SOaKpiReq;
import cc.mrbird.febs.server.ding.service.ISOaTeamService;
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
 * @date 2021-01-02 11:43:09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sOaTeam")
@RequiredArgsConstructor
public class SOaTeamController {

    private final ISOaTeamService sOaTeamService;

    @GetMapping
    @PreAuthorize("hasAuthority('sOaTeam:list')")
    public FebsResponse getAllSOaTeams(SOaTeam sOaTeam) {
        return new FebsResponse().data(sOaTeamService.findSOaTeams(sOaTeam));
    }

    @PostMapping("list")
//    @PreAuthorize("hasAuthority('sOaTeam:list')")
    public FebsResponse sOaTeamList(QueryRequest request, SOaKpiReq sOaKpiReq) {
        return new FebsResponse().data(this.sOaTeamService.findSOaTeams(request, sOaKpiReq));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sOaTeam:add')")
    public void addSOaTeam(@Valid SOaTeam sOaTeam) throws FebsException {
        try {
            this.sOaTeamService.createSOaTeam(sOaTeam);
        } catch (Exception e) {
            String message = "新增SOaTeam失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sOaTeam:delete')")
    public void deleteSOaTeam(SOaTeam sOaTeam) throws FebsException {
        try {
            this.sOaTeamService.deleteSOaTeam(sOaTeam);
        } catch (Exception e) {
            String message = "删除SOaTeam失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('sOaTeam:update')")
    public void updateSOaTeam(SOaTeam sOaTeam) throws FebsException {
        try {
            this.sOaTeamService.updateSOaTeam(sOaTeam);
        } catch (Exception e) {
            String message = "修改SOaTeam失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
