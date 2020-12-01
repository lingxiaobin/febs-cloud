package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumReq;
import cc.mrbird.febs.server.ding.service.ISOaAwardService;
import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.exception.FebsException;
import cc.mrbird.febs.common.core.utils.FebsUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import cc.mrbird.febs.common.core.entity.ding.SOaAward;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.Map;

/**
 *  Controller
 *
 * @author MrBird
 * @date 2020-12-01 18:07:41
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sOaAward")
@RequiredArgsConstructor
public class SOaAwardController {

    private final ISOaAwardService sOaAwardService;

    //同步最新的汇总信息
    @GetMapping("flush")
    public FebsResponse flush(SKaoqinSumReq sKaoqinSumReq) throws ParseException {
        sOaAwardService.flushOaAward(sKaoqinSumReq);
        return new FebsResponse().data("");
    }
    @GetMapping
    @PreAuthorize("hasAuthority('sOaAward:list')")
    public FebsResponse getAllSOaAwards(SOaAward sOaAward) {
        return new FebsResponse().data(sOaAwardService.findSOaAwards(sOaAward));
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sOaAward:list')")
    public FebsResponse sOaAwardList(QueryRequest request, SOaAward sOaAward) {
        Map<String, Object> dataTable = FebsUtil.getDataTable(this.sOaAwardService.findSOaAwards(request, sOaAward));
        return new FebsResponse().data(dataTable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sOaAward:add')")
    public void addSOaAward(@Valid SOaAward sOaAward) throws FebsException {
        try {
            this.sOaAwardService.createSOaAward(sOaAward);
        } catch (Exception e) {
            String message = "新增SOaAward失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sOaAward:delete')")
    public void deleteSOaAward(SOaAward sOaAward) throws FebsException {
        try {
            this.sOaAwardService.deleteSOaAward(sOaAward);
        } catch (Exception e) {
            String message = "删除SOaAward失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('sOaAward:update')")
    public void updateSOaAward(SOaAward sOaAward) throws FebsException {
        try {
            this.sOaAwardService.updateSOaAward(sOaAward);
        } catch (Exception e) {
            String message = "修改SOaAward失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
