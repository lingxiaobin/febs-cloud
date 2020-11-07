package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.ding.PClass;
import cc.mrbird.febs.server.ding.mapper.PClassMapper;
import cc.mrbird.febs.server.ding.service.IPClassService;
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

import java.util.Date;
import java.util.List;

/**
 *  Service实现
 *
 * @author MrBird
 * @date 2020-11-06 08:35:28
 */
@DS("winserver")
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PClassServiceImpl extends ServiceImpl<PClassMapper, PClass> implements IPClassService {

    private final PClassMapper pClassMapper;

    @Override
    public IPage<PClass> findPClasss(QueryRequest request, PClass pClass) {
        LambdaQueryWrapper<PClass> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<PClass> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<PClass> findPClasss(PClass pClass) {
        LambdaQueryWrapper<PClass> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPClass(PClass pClass) {
        this.save(pClass);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePClass(PClass pClass) {
        if (pClass.getId()==null){
            pClass.setCreateTime(new Date());
        }else {
            pClass.setUpdateTime(new Date());
        }
        this.saveOrUpdate(pClass);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePClass(PClass pClass) {
        LambdaQueryWrapper<PClass> wapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wapper);
    }
}
