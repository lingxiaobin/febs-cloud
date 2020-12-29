package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.KUser;
import cc.mrbird.febs.common.core.entity.ding.SSalarySetting;
import cc.mrbird.febs.common.core.exception.FebsException;
import cc.mrbird.febs.server.ding.controller.req.FlushReq;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumReq;
import cc.mrbird.febs.server.ding.controller.req.SSalarySettingReq;
import cc.mrbird.febs.server.ding.service.ISKaoqinSumService;
import cc.mrbird.febs.server.ding.service.ISSalaryService;
import cc.mrbird.febs.server.ding.service.ISSalarySettingService;
import cc.mrbird.febs.server.ding.service.ISaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.text.ParseException;

/**
 * Controller
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
@Slf4j
@Validated
@RestController
@RequestMapping("salary")
@RequiredArgsConstructor
public class SSalaryController {

    private final ISSalaryService iSaleService;

    private final ISKaoqinSumService isKaoqinSumService;
    private final ISSalarySettingService isSalarySettingService;

    @GetMapping("setting")
    public FebsResponse getSalarySetting(SSalarySettingReq sSalarySetting) {
        return new FebsResponse().data(isSalarySettingService.findSSalarySetting(sSalarySetting));
    }

    @PutMapping("setting")
    public FebsResponse updateKUser(SSalarySettingReq sSalarySetting) throws FebsException {
        try {
            sSalarySetting.setValue(sSalarySetting.getValue() == 0 ? 1 : 0);
            return new FebsResponse().data(isSalarySettingService.updateSSalarySetting(sSalarySetting));
        } catch (Exception e) {
            String message = "修改Setting失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    //刷新数据源
    @PostMapping("flush")
    public FebsResponse flush(FlushReq flushReq) throws ParseException {
        iSaleService.flush(flushReq);
        return new FebsResponse();
    }

    //查询最新的更新状态
    @PostMapping("flushStateNum/{type}")
    public FebsResponse selectFlushState(@PathVariable String type, SKaoqinSumReq sKaoqinSumReq) throws ParseException {
        return new FebsResponse().data(iSaleService.selectFlushState(type,sKaoqinSumReq));
    }

}
