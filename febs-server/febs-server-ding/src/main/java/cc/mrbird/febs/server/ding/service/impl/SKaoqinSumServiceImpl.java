package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.constant.PageConstant;
import cc.mrbird.febs.common.core.entity.ding.*;
import cc.mrbird.febs.common.core.utils.DateUtil;
import cc.mrbird.febs.server.ding.common.constant.ConstantSalary;
import cc.mrbird.febs.server.ding.controller.req.FlushReq;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumEnitReq;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumReq;
import cc.mrbird.febs.server.ding.controller.req.SSalarySettingReq;
import cc.mrbird.febs.server.ding.controller.vo.DeptVo;
import cc.mrbird.febs.server.ding.mapper.SKaoqinSumMapper;
import cc.mrbird.febs.server.ding.service.ISKaoqinSumService;
import cc.mrbird.febs.server.ding.service.ISSalarySettingService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class SKaoqinSumServiceImpl extends ServiceImpl<SKaoqinSumMapper, SDayDetailSum> implements ISKaoqinSumService {

    private final SKaoqinSumMapper sKaoqinSumMapper;

    private final ISSalarySettingService isSalarySettingService;

    public boolean flush(FlushReq flushReq)  {
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("workDate", flushReq.getWorkDate());
        parMap.put("payPlaces", flushReq.getPayPlaces());
        List<Map<String, Object>> isUpdateList = sKaoqinSumMapper.selectIsUpdate(parMap);
        Set<String> updateSet01 = new HashSet<>();
        Set<String> updateSet_1 = new HashSet<>();
        for (Map<String, Object> map : isUpdateList) {
            String userId = (String) map.get("userid");
            Integer isUpdate = (Integer) map.get("is_update");
            if (isUpdate == -1) {
                updateSet_1.add(userId);
            } else{
                updateSet01.add(userId);
            }
        }
        List<SDayDetailSum> addList=new ArrayList<>();
        List<SDayDetailSum> updateList=new ArrayList<>();

        List<SDayDetailSum> kDayDetailsSumList = sKaoqinSumMapper.findKDayDetailsSumByFlush(parMap);
        for (SDayDetailSum sDayDetailSum : kDayDetailsSumList) {
            sDayDetailSum.setId(flushReq.getWorkDate() + sDayDetailSum.getUserid());  //id
            if (updateSet01.contains(sDayDetailSum.getUserid())){
                sDayDetailSum.setIsUpdate(1);
                updateList.add(sDayDetailSum);
            }else if (updateSet_1.contains(sDayDetailSum.getUserid())){
            }else {
                sDayDetailSum.setIsUpdate(0);
                addList.add(sDayDetailSum);
            }
        }
        this.saveBatch(addList);
        if (updateList.size()>0){
            this.updateBatchById(updateList);
        }
        return true;
    }


    @Override
    public Map<String, Object> findSKaoqinSums(QueryRequest request, SKaoqinSumReq req) {
        Map<String, Object> parMap = new HashMap<>();
        JSONArray jsonArray = JSON.parseArray(req.getDeptsAndUsers());
        if (jsonArray.size() > 0) {
            List<String> deptIds = new ArrayList<>();
            List<String> userIds = new ArrayList<>();
            for (Object o : jsonArray) {
                JSONObject oo = JSON.parseObject(o.toString());
                if (oo.getIntValue("type") == 1) {
                    deptIds.add(oo.getString("id"));
                } else {
                    userIds.add(oo.getString("id"));
                }

            }
            parMap.put("deptIds", deptIds);
            parMap.put("userIds", userIds);
        }

        parMap.put("workDate", req.getWorkDate());
        if (req.getPayPlaces().length > 0) {
            parMap.put("payPlaces", req.getPayPlaces());
        }
        if (req.getPayComputeTypes()!=null && req.getPayComputeTypes().length > 0) {
            parMap.put("payComputeTypes", req.getPayComputeTypes());
        }
        if (!request.isAll()) {
            parMap.put("pageNum", (request.getPageNum() - 1) * request.getPageSize());
            parMap.put("size", request.getPageSize());
        }
        parMap.put("isAll", request.isAll());
        List<SDayDetailSum> kDayDetailsSum = sKaoqinSumMapper.findKDayDetailsSum(parMap);
        Long aLong = 0L;
        if (!request.isAll()) {
            aLong = sKaoqinSumMapper.countKDayDetailsSum(parMap);
        }
        Map<String, Object> data = new HashMap<>(2);
        data.put(PageConstant.ROWS, kDayDetailsSum);
        data.put(PageConstant.TOTAL, aLong);

        return data;
    }

    @Override
    public int updateSKaoqinSum(SKaoqinSumEnitReq enitReq, SDayDetailSum sDayDetailSum) throws ParseException {
        String workDate = sDayDetailSum.getId().substring(0, 7);
        SDayDetailSum sDayDetailSumNew = new SDayDetailSum();
        sDayDetailSumNew.setWorkDate(DateUtil.getDateParse(workDate, "yyyy-MM"));
        sDayDetailSumNew.setGoWorkDayBai(sDayDetailSum.getGoWorkDayBai());  //基本
        sDayDetailSumNew.setGoWorkDayYe(sDayDetailSum.getGoWorkDayYe());
        sDayDetailSumNew.setGoWorkDayYebu(sDayDetailSum.getGoWorkDayYebu());
        sDayDetailSumNew.setGongshiBaseChidao(sDayDetailSum.getGongshiBaseChidao());
        sDayDetailSumNew.setGongshiBaseZaotui(sDayDetailSum.getGongshiBaseZaotui());
        sDayDetailSumNew.setGongshiBaseKuang(sDayDetailSum.getGongshiBaseKuang());
        sDayDetailSumNew.setBukaNum(sDayDetailSum.getBukaNum());
        sDayDetailSumNew.setGoWorkDay2(sDayDetailSum.getGoWorkDay2());     // 排班-平时,周末
        sDayDetailSumNew.setGongshiBase2(sDayDetailSum.getGongshiBase2());
        sDayDetailSumNew.setJiaban2(sDayDetailSum.getJiaban2());
        sDayDetailSumNew.setGoWorkDayWeekend2(sDayDetailSum.getGoWorkDayWeekend2());
        sDayDetailSumNew.setJiabanWeekend2(sDayDetailSum.getJiabanWeekend2());
        sDayDetailSumNew.setGoWorkDayHolidayBase(sDayDetailSum.getGoWorkDayHolidayBase());  // 排班-节假日
        sDayDetailSumNew.setJiabanHolidayBase(sDayDetailSum.getJiabanHolidayBase());
        sDayDetailSumNew.setGoWorkDayHoliday2(sDayDetailSum.getGoWorkDayHoliday2());
        sDayDetailSumNew.setJiabanHoliday2(sDayDetailSum.getJiabanHoliday2());
        sDayDetailSumNew.setProcessBaseJiaban(sDayDetailSum.getProcessBaseJiaban());  //加班审批
        sDayDetailSumNew.setProcessWeekendJiaban(sDayDetailSum.getProcessWeekendJiaban());
        sDayDetailSumNew.setProcessHolidayJiaban(sDayDetailSum.getProcessHolidayJiaban());
        sDayDetailSumNew.setFormNianjia(sDayDetailSum.getFormNianjia());   // 请假
        sDayDetailSumNew.setFormShijia(sDayDetailSum.getFormShijia());
        sDayDetailSumNew.setFormBingjia(sDayDetailSum.getFormBingjia());
        sDayDetailSumNew.setFormTiaoxiu(sDayDetailSum.getFormTiaoxiu());
        sDayDetailSumNew.setFormChanjianjia(sDayDetailSum.getFormChanjianjia());
        sDayDetailSumNew.setFormPeichanjia(sDayDetailSum.getFormPeichanjia());
        sDayDetailSumNew.setFormChanjia(sDayDetailSum.getFormChanjia());
        sDayDetailSumNew.setFormHunjia(sDayDetailSum.getFormHunjia());
        sDayDetailSumNew.setFormLijia(sDayDetailSum.getFormLijia());
        sDayDetailSumNew.setFormSangjia(sDayDetailSum.getFormSangjia());
        sDayDetailSumNew.setFormBurujia(sDayDetailSum.getFormBurujia());
        sDayDetailSumNew.setIsUpdate(-1);

        JSONArray jsonArray = JSON.parseArray(enitReq.getDeptsAndUsers());
        if (jsonArray.size() > 0) {
            for (Object o : jsonArray) {
                JSONObject oo = JSON.parseObject(o.toString());
                sDayDetailSumNew.setUserid(oo.getString("id"));
                sDayDetailSumNew.setId(workDate + sDayDetailSumNew.getUserid());  //id
                sDayDetailSumNew.setIsUpdate(-1);
                this.saveOrUpdate(sDayDetailSumNew);
                /*Integer isUpdateNum = sKaoqinSumMapper.selectIsUpdateById(sDayDetailSumNew.getId());
                if (isUpdateNum != null) {
                    sDayDetailSumNew.setIsUpdate(-1);
                    this.updateById(sDayDetailSumNew);
                } else {
                    sDayDetailSumNew.setIsUpdate(0);
                    this.save(sDayDetailSumNew);
                }*/
            }
        }
        return 0;
    }

    @Override
    public List<String> noKaoqinUser(SKaoqinSumReq sKaoqinSumReq) {
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("workDate", sKaoqinSumReq.getWorkDate());
        parMap.put("payPlaces", sKaoqinSumReq.getPayPlaces());
        List<Map<String, Object>> noKaoqinUser = sKaoqinSumMapper.noKaoqinUser(parMap);
        List<String> resList = new ArrayList<>();
        for (Map<String, Object> map : noKaoqinUser) {
            DeptVo vo = new DeptVo(map.get("id").toString(), map.get("name").toString(), 2);
            resList.add(JSON.toJSONString(vo));
        }
        return resList;
    }
}
