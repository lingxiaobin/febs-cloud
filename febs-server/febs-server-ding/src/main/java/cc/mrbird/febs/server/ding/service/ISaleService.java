package cc.mrbird.febs.server.ding.service;

import cc.mrbird.febs.server.ding.controller.vo.SaleDateValueVo;

import java.util.List;

public interface ISaleService {
    List<SaleDateValueVo> selectCountDay7();
    List<SaleDateValueVo> selectCountDayByAbbrname();

    List<SaleDateValueVo> selectCountBySalesname();
}
