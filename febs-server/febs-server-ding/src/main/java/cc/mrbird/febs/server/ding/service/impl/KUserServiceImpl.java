package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.constant.FebsConstant;
import cc.mrbird.febs.common.core.entity.ding.K24680;
import cc.mrbird.febs.common.core.entity.ding.KUser;
import cc.mrbird.febs.server.ding.mapper.KUserMapper;
import cc.mrbird.febs.server.ding.service.IKKaoqinService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<K24680> findK24680(QueryRequest request, K24680 k24680) {
        Map<String, String> parMap = new HashMap<>();
        parMap.put("timeFrom", k24680.getTimeFrom());
        parMap.put("timeTo", k24680.getTimeTo());
        parMap.put("asc_desc", "asc");
        if (StringUtils.isNotBlank(k24680.getName())) {
            parMap.put("name", k24680.getName());
        }
        if (StringUtils.isNotBlank(request.getField())
                && StringUtils.isNotBlank(request.getOrder())
                && !StringUtils.equalsIgnoreCase(request.getField(), "null")
                && !StringUtils.equalsIgnoreCase(request.getOrder(), "null")) {
            parMap.put("order_field", request.getField());
            if (StringUtils.equals(request.getOrder(), FebsConstant.ORDER_DESC)) {
                parMap.put("asc_desc", "desc");
            }
        } else {
            parMap.put("order_field", "kk.orders");
        }
        List<K24680> k24680s = kUserMapper.selectK24680Where(parMap);
//        for (K24680 k246801 : k24680s) {
//            if (k246801.getJiabanWeekend2() != null && k246801.getJiabanWeekend2().equals("0.00")) {
//                k246801.setJiabanWeekend2(null);
//            }
//            if (k246801.getProcessWeekendTiaoxiu() != null && k246801.getProcessWeekendTiaoxiu().equals("0.00")) {
//                k246801.setProcessWeekendTiaoxiu(null);
//            }
//            if (k246801.getFreeJiaban() != null && k246801.getFreeJiaban().equals("0.00")) {
//                k246801.setFreeJiaban(null);
//            }
//        }
        return k24680s;
    }
}
