package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.ding.KUser;
import cc.mrbird.febs.server.ding.mapper.KUserMapper;
import cc.mrbird.febs.server.ding.service.IKUserService;
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

import java.util.List;

/**
 * Service实现
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
        if (StringUtils.isNotEmpty(kUser.getName())) {
            queryWrapper.like(KUser::getName, kUser.getName());
        }

        if (kUser.getUserTypes() != null && kUser.getUserTypes().length > 0) {     //工资核算地
            boolean bool = false;
            for (int i = 0; i < kUser.getUserTypes().length; i++) {
                if (kUser.getUserTypes()[i].equals("空"))
                    bool = true;
            }
            if (bool) {
                queryWrapper.and(i -> i.in(KUser::getUserType, kUser.getUserTypes()).or().isNull(KUser::getUserType));
            } else {
                queryWrapper.in(KUser::getUserType, kUser.getUserTypes());
            }
        }
        if (kUser.getLeaveTypes() != null && kUser.getLeaveTypes().length > 0) {    //在职状态
            queryWrapper.in(KUser::getLeaveType, kUser.getLeaveTypes());
        }

        if (kUser.getPayPlaces() != null && kUser.getPayPlaces().length > 0) {     //工资核算地
            boolean bool = false;
            for (int i = 0; i < kUser.getPayPlaces().length; i++) {
                if (kUser.getPayPlaces()[i].equals("空"))
                    bool = true;
            }
            if (bool) {
                queryWrapper.and(i -> i.in(KUser::getPayPlace, kUser.getPayPlaces()).or().isNull(KUser::getPayPlace));
            } else {
                queryWrapper.in(KUser::getPayPlace, kUser.getPayPlaces());
            }
        }
        if (kUser.getDispatchFactory() != null) {
            if (kUser.getDispatchFactory().equals("非空")) {
                queryWrapper.isNotNull(KUser::getDispatchFactory);
            } else {
                queryWrapper.isNull(KUser::getDispatchFactory);
            }
        }
        if (kUser.getPayComputeType() != null) {
            if (kUser.getPayComputeType().equals("非空")) {
                queryWrapper.isNotNull(KUser::getPayComputeType);
            } else {
                queryWrapper.isNull(KUser::getPayComputeType);
            }
        }
        if (kUser.getLeaveReasonType() != null) {
            if (kUser.getLeaveReasonType().equals("非空")) {
                queryWrapper.isNotNull(KUser::getLeaveReasonType);
            } else {
                queryWrapper.isNull(KUser::getLeaveReasonType);
            }
        }

        Page<KUser> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<KUser> findKUsers(KUser kUser) {
        LambdaQueryWrapper<KUser> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(kUser.getName())) {
            queryWrapper.like(KUser::getName, kUser.getName());
        }

        if (kUser.getUserTypes() != null && kUser.getUserTypes().length > 0) {     //工资核算地
            boolean bool = false;
            for (int i = 0; i < kUser.getUserTypes().length; i++) {
                if (kUser.getUserTypes()[i].equals("空"))
                    bool = true;
            }
            if (bool) {
                queryWrapper.and(i -> i.in(KUser::getUserType, kUser.getUserTypes()).or().isNull(KUser::getUserType));
            } else {
                queryWrapper.in(KUser::getUserType, kUser.getUserTypes());
            }
        }
        if (kUser.getLeaveTypes() != null && kUser.getLeaveTypes().length > 0) {    //在职状态
            queryWrapper.in(KUser::getLeaveType, kUser.getLeaveTypes());
        }

        if (kUser.getPayPlaces() != null && kUser.getPayPlaces().length > 0) {     //工资核算地
            boolean bool = false;
            for (int i = 0; i < kUser.getPayPlaces().length; i++) {
                if (kUser.getPayPlaces()[i].equals("空"))
                    bool = true;
            }
            if (bool) {
                queryWrapper.and(i -> i.in(KUser::getPayPlace, kUser.getPayPlaces()).or().isNull(KUser::getPayPlace));
            } else {
                queryWrapper.in(KUser::getPayPlace, kUser.getPayPlaces());
            }
        }
        if (kUser.getDispatchFactory() != null) {
            if (kUser.getDispatchFactory().equals("非空")) {
                queryWrapper.isNotNull(KUser::getDispatchFactory);
            } else {
                queryWrapper.isNull(KUser::getDispatchFactory);
            }
        }
        if (kUser.getPayComputeType() != null) {
            if (kUser.getPayComputeType().equals("非空")) {
                queryWrapper.isNotNull(KUser::getPayComputeType);
            } else {
                queryWrapper.isNull(KUser::getPayComputeType);
            }
        }
        if (kUser.getLeaveReasonType() != null) {
            if (kUser.getLeaveReasonType().equals("非空")) {
                queryWrapper.isNotNull(KUser::getLeaveReasonType);
            } else {
                queryWrapper.isNull(KUser::getLeaveReasonType);
            }
        }
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
