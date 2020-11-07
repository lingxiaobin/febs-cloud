package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.constant.PageConstant;
import cc.mrbird.febs.common.core.entity.ding.EWiplineUser;
import cc.mrbird.febs.common.core.entity.ding.Sale;
import cc.mrbird.febs.common.core.utils.DateUtil;
import cc.mrbird.febs.common.core.utils.FebsUtil;
import cc.mrbird.febs.server.ding.controller.req.WiplineReq;
import cc.mrbird.febs.server.ding.controller.vo.SaleDateValueVo;
import cc.mrbird.febs.server.ding.mapper.SaleMapper;
import cc.mrbird.febs.server.ding.mapper.WiplineMapper;
import cc.mrbird.febs.server.ding.service.ISaleService;
import cc.mrbird.febs.server.ding.service.IWiplineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class WiplineServiceImpl implements IWiplineService {

    private final WiplineMapper wiplineMapper;

    @Override
    public Map<String, Object> selectWipline(WiplineReq wiplineReq) {
        EWiplineUser eWiplineUser = wiplineMapper.selectWiplineUser(FebsUtil.getCurrentUsername());
        Map<String, String> parMap = new HashMap<>();
        parMap.put("wip_user_name",eWiplineUser.getWipUserName());
        if (StringUtils.isNotEmpty(wiplineReq.getParam1())){
            parMap.put("param1",wiplineReq.getParam1());
        }
        parMap.put("start_date",wiplineReq.getTimeRange()[0]);
        parMap.put("end_date",wiplineReq.getTimeRange()[1]);
        List<Map<String, Object>> list = wiplineMapper.selectWipline(parMap);

        Map<String, Object> data = new HashMap<>(2);
        data.put(PageConstant.ROWS,list);
        data.put(PageConstant.TOTAL, list.size());
        return data;
    }


}
