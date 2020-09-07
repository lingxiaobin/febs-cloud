package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.ding.KUser;
import cc.mrbird.febs.server.ding.mapper.KUserMapper;
import cc.mrbird.febs.server.ding.service.IKUserService;
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
 * @date 2020-09-07 11:15:39
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class KUserServiceImpl extends ServiceImpl<KUserMapper, KUser> implements IKUserService {

    private final KUserMapper kUserMapper;

    @Override
    public IPage<KUser> findKUsers(QueryRequest request, KUser kUser) {
        LambdaQueryWrapper<KUser> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<KUser> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<KUser> findKUsers(KUser kUser) {
        LambdaQueryWrapper<KUser> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createKUser(KUser kUser) {
        this.save(kUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateKUser(KUser kUser) {
        this.saveOrUpdate(kUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteKUser(KUser kUser) {
        LambdaQueryWrapper<KUser> wapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wapper);
    }
}
