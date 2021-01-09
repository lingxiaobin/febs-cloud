package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.server.ding.common.constant.ConstantSalary;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumReq;
import cc.mrbird.febs.server.ding.controller.req.FlushReq;
import cc.mrbird.febs.server.ding.mapper.SSalaryMapper;
import cc.mrbird.febs.server.ding.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;

/**
 * Service实现
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SSalaryServiceImpl implements ISSalaryService {


    private final SSalaryMapper sSalaryMapper;
    private final ISSalarySettingService isSalarySettingService;
    private final ISKaoqinSumService isKaoqinSumDService;
    private final ISOaAwardService isOaAwardService;
    private final ISOaKpiService isOaKpiService;
    private final ISOaTeamService isOaTeamService;
    private final ISEatService isEatService;


    public void flush(FlushReq flushReq) throws ParseException {
        if (flushReq.getIsDiffPayPlace()) {
            for (String payPlace : flushReq.getPayPlaces()) {
                isSalarySettingService.updateSSalarySetting(flushReq.getWorkDate(), flushReq.getDataType(), "flush", payPlace, 1);
            }
        } else {
            isSalarySettingService.updateSSalarySetting(flushReq.getWorkDate(), flushReq.getDataType(), "flush", "全部", 1);
        }
        if (flushReq.getDataType().equals(ConstantSalary.KAO_QIN)) {
            isKaoqinSumDService.flush(flushReq);
        } else if (flushReq.getDataType().equals(ConstantSalary.OA_AWARD)) {
            isOaAwardService.flush(flushReq);
        }else if (flushReq.getDataType().equals(ConstantSalary.OA_KPI)) {
            isOaKpiService.flush(flushReq);
        }else if (flushReq.getDataType().equals(ConstantSalary.OA_TEAM)) {
            isOaTeamService.flush(flushReq);
        }else if (flushReq.getDataType().equals(ConstantSalary.EAT)) {
            isEatService.flush(flushReq);
        }

        if (flushReq.getIsDiffPayPlace()) {
            for (String payPlace : flushReq.getPayPlaces()) {
                isSalarySettingService.updateSSalarySetting(flushReq.getWorkDate(), flushReq.getDataType(), "flush", payPlace, 0);
            }
        } else {
            isSalarySettingService.updateSSalarySetting(flushReq.getWorkDate(), flushReq.getDataType(), "flush", "全部", 0);
        }
    }

    @Override
    public Object selectFlushState(String type, SKaoqinSumReq sKaoqinSumReq) {
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("workDate", sKaoqinSumReq.getWorkDate());
        parMap.put("payPlaces", sKaoqinSumReq.getPayPlaces());
        parMap.put("isUpdate", 0);
        if (type.equals(ConstantSalary.KAO_QIN)) {
            parMap.put("table1", "s_day_detail_sum");
        } else if (type.equals(ConstantSalary.OA_AWARD)) {
            parMap.put("table1", "s_oa_award");
        }else if (type.equals(ConstantSalary.OA_KPI)) {
            parMap.put("table1", "s_oa_kpi");
        }else if (type.equals(ConstantSalary.OA_TEAM)) {
            parMap.put("table1", "s_oa_team");
        }else if (type.equals(ConstantSalary.EAT)) {
            parMap.put("table1", "s_eat");
        }
        Object resMap = null;
        if (sKaoqinSumReq.getIsDiffPayPlace()) {
            resMap = selectFlushState(sKaoqinSumReq, parMap);
        } else {
            resMap = selectFlushStateByAll(parMap);
        }
        return resMap;
    }


    private Map<String, Object> selectFlushState(SKaoqinSumReq sKaoqinSumReq, Map<String, Object> parMap) {
        List<Map<String, Object>> resList = sSalaryMapper.selectFlushState(parMap);
        Map<String, Object> resMap = new HashMap<>();
        for (String payPlace : sKaoqinSumReq.getPayPlaces()) {
            resMap.put(payPlace, new int[4]);
        }
        for (Map<String, Object> map : resList) {
            int[] ints = (int[]) resMap.get(map.get("pay_place"));
            Integer isUpdate = (Integer) map.get("is_update");
            Integer counts = Integer.parseInt(map.get("counts").toString());
            if (isUpdate == 0) {
                ints[0] = counts;
            } else if (isUpdate == 1) {
                ints[1] = counts;
            }
            if (isUpdate == -1) {
                ints[2] = counts;
            }
            ints[3] = ints[0]+ints[1]+ints[2];
        }
        return resMap;
    }

    private int[] selectFlushStateByAll(Map<String, Object> parMap) {
        List<Map<String, Object>> resList = sSalaryMapper.selectFlushStateByAll(parMap);
        int[] ints = new int[4];
        for (Map<String, Object> map : resList) {
            Integer isUpdate = (Integer) map.get("is_update");
            Integer counts = Integer.parseInt(map.get("counts").toString());
            if (isUpdate == 0) {
                ints[0] = counts;
            } else if (isUpdate == 1) {
                ints[1] = counts;
            }
            if (isUpdate == -1) {
                ints[2] = counts;
            }
            ints[3] = ints[0]+ints[1]+ints[2];
        }
        return ints;
    }

}
