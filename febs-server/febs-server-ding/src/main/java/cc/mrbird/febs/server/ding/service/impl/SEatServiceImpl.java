package cc.mrbird.febs.server.ding.service.impl;


import cc.mrbird.febs.common.core.entity.constant.PageConstant;
import cc.mrbird.febs.common.core.entity.ding.SEat;
import cc.mrbird.febs.common.core.utils.DateUtil;
import cc.mrbird.febs.server.ding.controller.req.FlushReq;
import cc.mrbird.febs.server.ding.controller.req.SOaKpiReq;

import cc.mrbird.febs.server.ding.mapper.SEatMapper;
import cc.mrbird.febs.server.ding.mapper.SKaoqinSumMapper;
import cc.mrbird.febs.server.ding.mapper.SOaMapper;
import cc.mrbird.febs.server.ding.service.ISEatService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import cc.mrbird.febs.common.core.entity.QueryRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.*;

/**
 * Service实现
 *
 * @author MrBird
 * @date 2021-01-04 08:39:20
 */
@DS("winserver")
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SEatServiceImpl extends ServiceImpl<SEatMapper, SEat> implements ISEatService {

    private final SEatMapper sEatMapper;
    private final SOaMapper sOaMapper;
    private final SKaoqinSumMapper sKaoqinSumMapper;

    public boolean flush(FlushReq flushReq) throws ParseException {
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("workDate", flushReq.getWorkDate());
        //原本数据信息
        List<Map<String, Object>> isUpdateList = sEatMapper.selectIsUpdate(parMap);
        Set<String> updateSet01 = new HashSet<>();
        Set<String> updateSet_1 = new HashSet<>();
        for (Map<String, Object> map : isUpdateList) {
            String id = map.get("id").toString();
            Integer isUpdate = (Integer) map.get("is_update");
            if (isUpdate == -1) {
                updateSet_1.add(id);
            } else {
                updateSet01.add(id);
            }
        }
        List<SEat> addList = new ArrayList<>();
        List<SEat> updateList = new ArrayList<>();
        List<Map<String, Object>> dayCountList = sKaoqinSumMapper.findDayCount(parMap);  //全部的考勤用户
        parMap.put("isAll", true);
        List<SEat> sEats = sEatMapper.findAll(parMap);  //获取已经使用的金额
        //查询
        parMap.put("dept_name", "工程中心-工程组");
        parMap.put("class_name", "惠东行政中班");
        Date limitDate=new Date();   //限制的时间
        int order=1;
        int isToDay = 0;  //是否是第二天的时间
        List<Map<String, Object>> dayEat1 = sKaoqinSumMapper.findDayDetailbyEat(parMap);  //获取指定部门,班次的的考勤明细,判断用来计算伙食补助
        for (Map<String, Object> map : dayEat1) {
            Date onDutyRestTime = DateUtil.restTimePing((Date) map.get("work_date"), limitDate, isToDay);
            Date offDutyRestTime =null;
            if (order==1){

            }
        }
        Map<String, SEat> sEatsMap = new HashMap<>();
        List<SEat> sEatAll = new ArrayList<>();
        int workMonthSum12345 = 0;
        int workMonthSum6 = 0;
        Date date = DateUtil.getDateParse(flushReq.getWorkDate(), "yyyy-MM");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (SEat sEat : sEats) {
            sEatsMap.put(sEat.getJobnumber(), sEat);
        }

        for (int i = 0; i < maxDay; i++) {
            int week = cal.get(Calendar.DAY_OF_WEEK);
            if (week != Calendar.SUNDAY && week != Calendar.SATURDAY) { //平时
                workMonthSum12345++;
            } else if (week == Calendar.SATURDAY) {//  周六
                workMonthSum6++;
            }
            cal.add(Calendar.DATE, 1);//在第一天的基础上加1
        }
        BigDecimal mult = new BigDecimal("23").divide(new BigDecimal("3"), 2, RoundingMode.DOWN);
        for (Map<String, Object> map : dayCountList) {
            String jobnumber = (String) map.get("jobnumber");
            SEat sEat = sEatsMap.get(jobnumber);
            if (sEat == null) {
                sEat = new SEat();
                sEat.setId(flushReq.getWorkDate() + map.get("id"));
                sEat.setWorkDate(date);
                sEat.setJobnumber(jobnumber);
                sEat.setUseNum(BigDecimal.ZERO);
            }
            sEat.setDeptName((String) map.get("dept_name"));
            sEat.setPayComputeType((String) map.get("pay_compute_type"));
            sEat.setPosition((String) map.get("position"));
            sEat.setName((String) map.get("name"));
            sEat.setDayNum((BigDecimal) map.get("monthCount"));  //考勤天数
            if (map.get("position") != null && ((String) map.get("position")).indexOf("经理") > 0) {   // 经理级别的
                if (sEat.getDayNum().intValue() >= (workMonthSum12345 + workMonthSum6)) {
                    sEat.setRuleNum(new BigDecimal("670"));
                    sEat.setMinNum(new BigDecimal("230"));
                } else {
                    sEat.setRuleNum(sEat.getDayNum().multiply(new BigDecimal("29")));
                    sEat.setMinNum(sEat.getDayNum().multiply(mult));
                }
            } else if ("月薪".equals(map.get("pay_compute_type")) || "财务".equals(map.get("pay_compute_type"))) {
                if (sEat.getDayNum().intValue() >= (workMonthSum12345 + workMonthSum6)) {
                    sEat.setRuleNum(new BigDecimal("350"));
                    sEat.setMinNum(new BigDecimal("230"));
                } else {
                    sEat.setRuleNum(sEat.getDayNum().multiply(new BigDecimal("11.5")));
                    sEat.setMinNum(sEat.getDayNum().multiply(mult));
                }
            } else if ("时薪".equals(map.get("pay_compute_type"))) {
                if (sEat.getDayNum().intValue() >= (maxDay - 2)) {  //这个月的天数减二
                    sEat.setRuleNum(new BigDecimal("350"));
                    sEat.setMinNum(new BigDecimal("230"));
                } else {
                    sEat.setRuleNum(sEat.getDayNum().multiply(new BigDecimal("11.5")));
                    sEat.setMinNum(sEat.getDayNum().multiply(mult));
                }
            }
            BigDecimal subtract = sEat.getRuleNum().subtract(sEat.getUseNum().max(sEat.getMinNum()));
            sEat.setNum(subtract);
            sEatAll.add(sEat);
        }
        for (SEat soa : sEatAll) {
            if (updateSet01.contains(soa.getId().toString())) {
                soa.setUpdateTime(new Date());
                soa.setIsUpdate(1);
                updateList.add(soa);
            } else if (updateSet_1.contains(soa.getId().toString())) {
            } else {
                soa.setCreateTime(new Date());
                soa.setIsUpdate(0);
                addList.add(soa);
            }
        }
        this.saveBatch(addList);
        if (updateList.size() > 0) {
            this.updateBatchById(updateList);
        }
        return true;
    }

    @Override
    public Map<String, Object> findSEats(QueryRequest request, SOaKpiReq req) {
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("workDate", req.getWorkDate());
        if (req.getPayPlaces().length > 0) {
            parMap.put("payPlaces", req.getPayPlaces());
        }
        if (req.getPayComputeTypes() != null && req.getPayComputeTypes().length > 0) {
            parMap.put("payComputeTypes", req.getPayComputeTypes());
        }
        parMap.put("isAll", request.isAll());
        if (!request.isAll()) {
            parMap.put("pageNum", (request.getPageNum() - 1) * request.getPageSize());
            parMap.put("size", request.getPageSize());
        }
        List<SEat> list = sEatMapper.findAll(parMap);
        Long count = 0L;
        if (!request.isAll()) {
            count = sEatMapper.findAllCount(parMap);
        }
        Map<String, Object> data = new HashMap<>(3);
        data.put(PageConstant.ROWS, list);
        data.put(PageConstant.TOTAL, count);
        return data;
    }

    @Override
    public List<SEat> findSEats(SEat sEat) {
        LambdaQueryWrapper<SEat> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSEat(SEat sEat) {
        this.save(sEat);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSEat(SEat sEat) {
        this.saveOrUpdate(sEat);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSEat(SEat sEat) {
        LambdaQueryWrapper<SEat> wapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wapper);
    }
}
