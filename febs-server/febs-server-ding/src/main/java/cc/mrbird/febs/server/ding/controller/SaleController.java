package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.server.ding.controller.req.MyReq;
import cc.mrbird.febs.server.ding.controller.vo.SaleDateValueVo;
import cc.mrbird.febs.server.ding.service.IKKaoqinService;
import cc.mrbird.febs.server.ding.service.ISaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("sale")
@RequiredArgsConstructor
public class SaleController {

    private final ISaleService saleService;

    /**
     * 查询最近7天销售额
     *
     * @param
     * @return
     */
    @GetMapping
    @ResponseBody
    public FebsResponse selectCountDay7( QueryRequest request)   {
        List<SaleDateValueVo> saleList = saleService.selectCountDay7();
        return new FebsResponse().data(saleList);
    }

    @GetMapping("bySalesName")
    @ResponseBody
    public FebsResponse selectCountBySalesname( QueryRequest request)   {
        List<SaleDateValueVo> saleList = saleService.selectCountBySalesname();
        return new FebsResponse().data(saleList);
    }

    @GetMapping("byAbbrname")
    @ResponseBody
    public FebsResponse selectCountDayByAbbrname( QueryRequest request)   {
        List<SaleDateValueVo> saleList = saleService.selectCountDayByAbbrname();
        return new FebsResponse().data(saleList);
    }
}
