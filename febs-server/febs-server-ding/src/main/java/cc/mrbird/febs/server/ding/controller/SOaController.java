package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.SDayDetailSum;
import cc.mrbird.febs.common.core.exception.FebsException;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumEnitReq;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumReq;
import cc.mrbird.febs.server.ding.service.ISKaoqinSumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * Controller   品质奖罚
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
@Slf4j
@Validated
@RestController
@RequestMapping("SOa")
@RequiredArgsConstructor
public class SOaController {

    private final ISKaoqinSumService isKaoqinSumService;

    //同步最新的汇总信息
/*    @GetMapping("flush")
    public FebsResponse flush(SKaoqinSumReq sKaoqinSumReq) throws ParseException {
        isKaoqinSumService.flush(sKaoqinSumReq);
        return new FebsResponse().data("");
    }*/

    //查询汇总信息
    @PostMapping
    public FebsResponse findSKaoqinSums(QueryRequest request, SKaoqinSumReq sKaoqinSumReq) {
        return new FebsResponse().data(isKaoqinSumService.findSKaoqinSums(request, sKaoqinSumReq));
    }
    //解锁锁定汇总信息


    //修改汇总信息
    @PutMapping
    public FebsResponse updateSKaoqinSum(SKaoqinSumEnitReq enitReq, SDayDetailSum sDayDetailSum) throws FebsException {
        try {
            return new FebsResponse().data(this.isKaoqinSumService.updateSKaoqinSum(enitReq,sDayDetailSum));
        } catch (Exception e) {
            String message = "SKaoqinSum";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    //查询所属权限的无需打卡组成员
    @GetMapping("noKaoqinUser")
    public FebsResponse noKaoqinUser(SKaoqinSumReq sKaoqinSumReq) throws ParseException {
            return new FebsResponse().data(isKaoqinSumService.noKaoqinUser(sKaoqinSumReq));
    }


    //批量添加,删除汇总信息
}
