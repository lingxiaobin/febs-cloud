package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.*;
import cc.mrbird.febs.common.core.utils.DateUtil;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumReq;
import cc.mrbird.febs.server.ding.controller.vo.DeptVo;
import cc.mrbird.febs.server.ding.mapper.SKaoqinSumMapper;
import cc.mrbird.febs.server.ding.service.ISKaoqinSumService;
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


    public void flush(SKaoqinSumReq sKaoqinSumReq) throws ParseException {
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("workDate", sKaoqinSumReq.getWorkDate());
        parMap.put("payPlaces", sKaoqinSumReq.getPayPlaces());
        List<SDayDetailSum> kDayDetailsSumList = sKaoqinSumMapper.findKDayDetailsSumByFlush(parMap);
        for (SDayDetailSum sDayDetailSum : kDayDetailsSumList) {
            sDayDetailSum.setId(sKaoqinSumReq.getWorkDate()+sDayDetailSum.getUserid());
            LambdaUpdateWrapper<SDayDetailSum> updateWrapper=new LambdaUpdateWrapper<>();
            updateWrapper.eq(SDayDetailSum::getId,sDayDetailSum.getId());
            updateWrapper.eq(SDayDetailSum::getIsUpdate,0);
            this.saveOrUpdate(sDayDetailSum,updateWrapper);
        }
    }

    @Override
    public List<SDayDetailSum> findSKaoqinSums(QueryRequest request, SKaoqinSumReq sKaoqinSumReq) {
        Map<String, Object> parMap = new HashMap<>();
        JSONArray jsonArray = JSON.parseArray(sKaoqinSumReq.getDeptsAndUsers());
        if (jsonArray.size() > 0) {
            List<String> deptIds = new ArrayList<>();
            List<String> userIds = new ArrayList<>();
            for (Object o : jsonArray) {
                JSONObject oo =JSON.parseObject(o.toString());
                if (oo.getIntValue("type")==1){
                    deptIds.add(oo.getString("id"));
                }else {
                    userIds.add(oo.getString("id"));
                }

            }
            parMap.put("deptIds", deptIds);
            parMap.put("userIds", userIds);
        }

        parMap.put("workDate", sKaoqinSumReq.getWorkDate());
        if (sKaoqinSumReq.getPayPlaces().length > 0) {
            parMap.put("payPlaces", sKaoqinSumReq.getPayPlaces());
        }
        if (!request.isAll()) {
            parMap.put("pageNum", (request.getPageNum() - 1) * request.getPageSize());
            parMap.put("size", request.getPageSize());
        }
        parMap.put("isAll", request.isAll());
        List<SDayDetailSum> kDayDetailsSum = sKaoqinSumMapper.findKDayDetailsSum(parMap);

        return kDayDetailsSum;
    }


}
