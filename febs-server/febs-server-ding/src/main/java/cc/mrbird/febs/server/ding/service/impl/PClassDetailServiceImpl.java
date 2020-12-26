package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.constant.PageConstant;
import cc.mrbird.febs.common.core.entity.ding.PClassDetail;
import cc.mrbird.febs.common.core.entity.ding.PClassDetailAll;
import cc.mrbird.febs.server.ding.controller.req.PClassReq;
import cc.mrbird.febs.server.ding.mapper.PClassDetailMapper;
import cc.mrbird.febs.server.ding.service.IPClassDetailService;
import com.baomidou.dynamic.datasource.annotation.DS;
import net.sf.jsqlparser.expression.operators.relational.OldOracleJoinBinaryExpression;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cc.mrbird.febs.common.core.entity.QueryRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service实现
 *
 * @author MrBird
 * @date 2020-11-06 08:37:21
 */
@DS("winserver")
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PClassDetailServiceImpl extends ServiceImpl<PClassDetailMapper, PClassDetail> implements IPClassDetailService {

    private final PClassDetailMapper pClassDetailMapper;

    @Override
    public Map<String, Object> findPClassDetails(QueryRequest request, PClassReq pClassReq) {
        Map<String, Object> parMap = new HashMap<>();
        if (StringUtils.isNotEmpty(pClassReq.getClassName())) {
            parMap.put("selOne", pClassReq.isSelOne());
            parMap.put("className", pClassReq.getClassName());
        }
        if (StringUtils.isNotEmpty(pClassReq.getJobnumber())) {
            parMap.put("jobnumber", pClassReq.getJobnumber());
        }
        if (pClassReq.getGiveLessonsArr().length > 0) {
            parMap.put("giveLessonsArr", pClassReq.getGiveLessonsArr());
        }
        if (pClassReq.getExamineArr().length > 0) {
            parMap.put("examineArr", pClassReq.getExamineArr());
        }
        if (pClassReq.getTrainArr().length > 0) {
            parMap.put("trainArr", pClassReq.getTrainArr());
        }
        if (!request.isAll()) {
            parMap.put("pageNum", (request.getPageNum() - 1) * request.getPageSize());
            parMap.put("size", request.getPageSize());
        }

        parMap.put("isAll", request.isAll());
        List<PClassDetailAll> map = pClassDetailMapper.selectPClassDetail(parMap);
        parMap.put("isAll", true);
        Long aLong = pClassDetailMapper.selectPClassDetailCount(parMap);

        Map<String, Object> data = new HashMap<>(2);
        data.put(PageConstant.ROWS, map);
        data.put(PageConstant.TOTAL, aLong);
        return data;
    }

    @Override
    public Map<Integer, long[]> selectPClassDetailStateIn(PClassReq pClassReq) {
        Map<String, Object> parMap = new HashMap<>();
        if (pClassReq.getClassIds() == null || pClassReq.getClassIds().length == 0) {
            return null;
        }
        parMap.put("classIds", pClassReq.getClassIds());
        parMap.put("state", 1);
        List<Map<String, Object>> count1List = pClassDetailMapper.selectPClassDetailStateIn(parMap);
        parMap.put("state", 2);
        List<Map<String, Object>> count2List = pClassDetailMapper.selectPClassDetailStateIn(parMap);
        Map<Integer, long[]> resMap = new HashMap<>();
        for (Map<String, Object> map : count1List) {
            Integer key = (Integer) map.get("id");
            long[] ints = resMap.get(key);
            if (ints == null) {
                ints = new long[2];
                resMap.put(key, ints);
            }
            ints[0] = (long) map.get("nums");
        }
        for (Map<String, Object> map : count2List) {
            Integer key = (Integer) map.get("id");
            long[] ints = resMap.get(key);
            if (ints == null) {
                ints = new long[2];
                resMap.put(key, ints);
            }
            ints[1] = (long) map.get("nums");
        }
        return resMap;
    }

    @Override
    public List<PClassDetail> findPClassDetails(PClassDetail pClassDetail) {
        LambdaQueryWrapper<PClassDetail> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPClassDetail(PClassDetail pClassDetail) {
        this.save(pClassDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePClassDetail(PClassDetail pClassDetail) {
        if (pClassDetail.getSignInTime()!=null || pClassDetail.getSignInState()!=null){
            pClassDetail.setIsSignIn(true);
        }
        if (pClassDetail.getFractionState()!=null || pClassDetail.getFraction()!=null || pClassDetail.getFractionTime()!=null){
            pClassDetail.setIsFranction(true);
        }
        this.saveOrUpdate(pClassDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePClassDetail(PClassDetail pClassDetail) {
        this.pClassDetailMapper.deleteById(pClassDetail.getId());
    }
}
