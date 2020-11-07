package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.ding.PClassType;
import cc.mrbird.febs.server.ding.mapper.PClassTypeMapper;
import cc.mrbird.febs.server.ding.service.IPClassTypeService;
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
 * @date 2020-11-06 08:20:43
 */
@DS("winserver")
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PClassTypeServiceImpl extends ServiceImpl<PClassTypeMapper, PClassType> implements IPClassTypeService {

    private final PClassTypeMapper pClassTypeMapper;

    @Override
    public IPage<PClassType> findPClassTypes(QueryRequest request, PClassType pClassType) {
        LambdaQueryWrapper<PClassType> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<PClassType> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<PClassType> findPClassTypes(PClassType pClassType) {
        LambdaQueryWrapper<PClassType> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPClassType(PClassType pClassType) {
        this.save(pClassType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePClassType(PClassType pClassType) {
        this.saveOrUpdate(pClassType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePClassType(PClassType pClassType) {
        LambdaQueryWrapper<PClassType> wapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wapper);
    }
}
