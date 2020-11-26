package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.KUser;
import cc.mrbird.febs.common.core.entity.ding.SSalarySetting;
import cc.mrbird.febs.common.core.exception.FebsException;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumReq;
import cc.mrbird.febs.server.ding.controller.req.SSalarySettingReq;
import cc.mrbird.febs.server.ding.service.ISKaoqinSumService;
import cc.mrbird.febs.server.ding.service.ISSalarySettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class SalaryController {

    private final ISKaoqinSumService isKaoqinSumService;
    private final ISSalarySettingService isSalarySettingService;

    @GetMapping("setting")
    public FebsResponse getSalarySetting(SSalarySettingReq sSalarySetting) {
        SSalarySetting sSalarySetting1 = isSalarySettingService.findSSalarySetting(sSalarySetting);
        return new FebsResponse().data(sSalarySetting1);
    }

    @PutMapping("setting")
    public FebsResponse updateKUser(SSalarySettingReq sSalarySetting) throws FebsException {
        try {
            return new FebsResponse().data(isSalarySettingService.updateSSalarySetting(sSalarySetting));

        } catch (Exception e) {
            String message = "修改Setting失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


}
