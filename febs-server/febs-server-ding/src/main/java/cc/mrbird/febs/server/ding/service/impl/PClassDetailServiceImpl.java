package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.ding.PClassDetail;
import cc.mrbird.febs.server.ding.mapper.PClassDetailMapper;
import cc.mrbird.febs.server.ding.service.IPClassDetailService;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cc.mrbird.febs.common.core.entity.QueryRequest;

import java.util.List;

/**
 *  Service实现
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
    public IPage<PClassDetail> findPClassDetails(QueryRequest request, PClassDetail pClassDetail) {
        LambdaQueryWrapper<PClassDetail> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<PClassDetail> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
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
        this.saveOrUpdate(pClassDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePClassDetail(PClassDetail pClassDetail) {
        LambdaQueryWrapper<PClassDetail> wapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wapper);
    }
}
