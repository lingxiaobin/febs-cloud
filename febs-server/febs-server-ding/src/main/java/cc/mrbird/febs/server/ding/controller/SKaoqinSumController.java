package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.KUser;
import cc.mrbird.febs.common.core.entity.ding.PClassDetail;
import cc.mrbird.febs.server.ding.controller.req.MyReq;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumReq;
import cc.mrbird.febs.server.ding.controller.vo.DeptVo;
import cc.mrbird.febs.server.ding.service.IKKaoqinService;
import cc.mrbird.febs.server.ding.service.ISKaoqinSumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

/**
 * Controller
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
@Slf4j
@Validated
@RestController
@RequestMapping("SKaoqinSum")
@RequiredArgsConstructor
public class SKaoqinSumController {

    private final ISKaoqinSumService isKaoqinSumService;

    //同步最新的汇总信息
    @GetMapping("flush")
    public FebsResponse flush(SKaoqinSumReq sKaoqinSumReq) throws ParseException {
        isKaoqinSumService.flush(sKaoqinSumReq);
        return new FebsResponse().data("");
    }

    //查询汇总信息
    @PostMapping
    public FebsResponse getAllPClassDetails(QueryRequest request, SKaoqinSumReq sKaoqinSumReq) {
        return new FebsResponse().data(isKaoqinSumService.findSKaoqinSums(request, sKaoqinSumReq));
    }
    //解锁锁定汇总信息


    //修改汇总信息


    //批量添加,删除汇总信息
}
