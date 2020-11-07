package cc.mrbird.febs.server.ding.service.impl;


import cc.mrbird.febs.common.core.entity.ding.ESaleMonth;
import cc.mrbird.febs.server.ding.mapper.ESaleMonthMapper;
import cc.mrbird.febs.server.ding.service.IESaleMonthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cc.mrbird.febs.common.core.entity.QueryRequest;

import java.util.List;


/**
 *  Service实现
 *
 * @author MrBird
 * @date 2020-10-26 10:43:46
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ESaleMonthServiceImpl extends ServiceImpl<ESaleMonthMapper, ESaleMonth> implements IESaleMonthService {

    private final ESaleMonthMapper eSaleMonthMapper;

    @Override
    public IPage<ESaleMonth> findESaleMonths(QueryRequest request, ESaleMonth eSaleMonth) {
        LambdaQueryWrapper<ESaleMonth> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<ESaleMonth> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<ESaleMonth> findESaleMonths(ESaleMonth eSaleMonth) {
        LambdaQueryWrapper<ESaleMonth> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createESaleMonth(ESaleMonth eSaleMonth) {
        this.save(eSaleMonth);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateESaleMonth(ESaleMonth eSaleMonth) {
        this.saveOrUpdate(eSaleMonth);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteESaleMonth(ESaleMonth eSaleMonth) {
        LambdaQueryWrapper<ESaleMonth> wapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wapper);
    }
}
