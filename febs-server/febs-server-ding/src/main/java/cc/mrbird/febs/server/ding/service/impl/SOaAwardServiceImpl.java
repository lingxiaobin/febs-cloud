package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.ding.SOaAward;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumReq;
import cc.mrbird.febs.server.ding.mapper.SOaAwardMapper;
import cc.mrbird.febs.server.ding.mapper.SOaMapper;
import cc.mrbird.febs.server.ding.service.ISOaAwardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cc.mrbird.febs.common.core.entity.QueryRequest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * Service实现
 *
 * @author MrBird
 * @date 2020-12-01 18:07:41
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SOaAwardServiceImpl extends ServiceImpl<SOaAwardMapper, SOaAward> implements ISOaAwardService {

    private final SOaAwardMapper sOaAwardMapper;

    private final SOaMapper sOaMapper;


    public boolean flushOaAward(SKaoqinSumReq sKaoqinSumReq)  {
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("workDate", sKaoqinSumReq.getWorkDate());
        List<Map<String, Object>> oaAwardFlush = sOaMapper.findOaAwardFlush(parMap);

        List<SOaAward> sOaAwards = new ArrayList<>();
        for (Map<String, Object> map : oaAwardFlush) {
            SOaAward sOaAward = new SOaAward();
            sOaAward.setFactory(map.get("获取需求工厂值").toString());
            sOaAward.setFromUser(map.get("发起人").toString());
            sOaAward.setWorkDate((Date) map.get("时间"));
            sOaAward.setFromUser(map.get("发起人").toString());
//            sOaAward.setFromUser(map.get("获取责任工序负责人").toString());
            sOaAward.setToAddUser(map.get("奖励人").toString());
            sOaAward.setToAddJobnumber(map.get("奖励人工号").toString());
            sOaAward.setToSubUser(map.get("罚款人").toString());
            sOaAward.setToSubJobnumber(map.get("罚款人工号").toString());
            try {
                sOaAward.setMoneyAdd(new BigDecimal(map.get("奖励金额").toString()));
            } catch (Exception e) {
            }
            sOaAward.setMoneyAddText(map.get("奖励金额").toString());
            try {
                sOaAward.setMoneySub(new BigDecimal(map.get("罚款金额").toString()));
            } catch (Exception e) {
            }
            sOaAward.setMoneySubText(map.get("罚款金额").toString());
            sOaAward.setErrorDetail(map.get("品质异常描述").toString());
            sOaAward.setErrorSolveIdea(map.get("品质异常处理方案").toString());
            sOaAwards.add(sOaAward);
        }
        this.saveOrUpdateBatch(sOaAwards);
        return true;
    }


    @Override
    public IPage<SOaAward> findSOaAwards(QueryRequest request, SOaAward sOaAward) {
        LambdaQueryWrapper<SOaAward> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<SOaAward> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<SOaAward> findSOaAwards(SOaAward sOaAward) {
        LambdaQueryWrapper<SOaAward> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSOaAward(SOaAward sOaAward) {
        this.save(sOaAward);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSOaAward(SOaAward sOaAward) {
        this.saveOrUpdate(sOaAward);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSOaAward(SOaAward sOaAward) {
        LambdaQueryWrapper<SOaAward> wapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wapper);
    }


}
