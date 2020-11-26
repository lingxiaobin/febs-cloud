package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.api.DingApiUtil;
import cc.mrbird.febs.common.core.entity.constant.FebsConstant;
import cc.mrbird.febs.common.core.entity.ding.DeptNum;
import cc.mrbird.febs.common.core.entity.ding.K24680;
import cc.mrbird.febs.common.core.entity.ding.KUser;
import cc.mrbird.febs.server.ding.controller.req.DeptReq;
import cc.mrbird.febs.server.ding.controller.vo.DeptVo;
import cc.mrbird.febs.server.ding.mapper.KUserMapper;
import cc.mrbird.febs.server.ding.service.IKKaoqinService;
import cc.mrbird.febs.server.ding.service.IKUserService;
import com.taobao.api.ApiException;
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

import java.util.*;

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
    public List<KUser> findKUsers(KUser kUser) {
        if (StringUtils.isEmpty(kUser.getName())){
            return null;
        }
        LambdaQueryWrapper<KUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(KUser::getId,KUser::getJobnumber, KUser::getDeptName ,KUser::getName, KUser::getLeaveType);
        queryWrapper.like(KUser::getName,kUser.getName());
        queryWrapper.orderByDesc(KUser::getLeaveType);   //为了去重
        List<KUser> kUsers = this.baseMapper.selectList(queryWrapper);
        Iterator<KUser> iterator = kUsers.iterator();

        while(iterator.hasNext()){
            System.out.println(iterator.next().getName());
        }
        return this.baseMapper.selectList(queryWrapper);
    }

    public List<DeptVo> findDeptAndUser(DeptReq deptReq){
        List<DeptNum> deptNums = this.baseMapper.selectDetpsNum(deptReq.getId());
        List<KUser> users = this.baseMapper.selectUsersNum(deptReq.getId());
        List<DeptVo> deptVos=new ArrayList<>();
        for (DeptNum deptNum : deptNums) {
            DeptVo vo=new DeptVo() ;
            vo.setId(deptNum.getId());
            vo.setName(deptNum.getName());
            vo.setType(1);
            deptVos.add(vo);
        }
        for (KUser user : users) {
            DeptVo vo=new DeptVo() ;
            vo.setId(user.getId());
            vo.setName(user.getName());
            vo.setType(2);
            deptVos.add(vo);
        }
        return deptVos;
    }
    @Override
    public IPage<KUser> findKUsers(QueryRequest request, KUser kUser) {
        LambdaQueryWrapper<KUser> queryWrapper = new LambdaQueryWrapper<>();
        if (!kUser.isAdmin()) {
            queryWrapper.select(KUser::getId, KUser::getName, KUser::getPosition, KUser::getJobnumber, KUser::getDeptName, KUser::getLeaveType);
        }
        if (StringUtils.isNotEmpty(kUser.getName())) {
            queryWrapper.like(KUser::getName, kUser.getName());
        }

        if (kUser.getUserTypes() != null && kUser.getUserTypes().length > 0) {     //员工类型
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
        if (kUser.getDispatchFactorys() != null && kUser.getDispatchFactorys().length > 0) {       //劳务派遣公司
            boolean bool = false;
            for (int i = 0; i < kUser.getDispatchFactorys().length; i++) {
                if (kUser.getDispatchFactorys()[i].equals("空"))
                    bool = true;
            }
            if (bool) {
                queryWrapper.and(i -> i.in(KUser::getDispatchFactory, kUser.getDispatchFactorys()).or().isNull(KUser::getDispatchFactory));
            } else {
                queryWrapper.in(KUser::getDispatchFactory, kUser.getDispatchFactorys());
            }
        }
        if (kUser.getPayComputeTypes() != null && kUser.getPayComputeTypes().length > 0) {
            boolean bool = false;
            for (int i = 0; i < kUser.getPayComputeTypes().length; i++) {
                if (kUser.getPayComputeTypes()[i].equals("空"))
                    bool = true;
            }
            if (bool) {
                queryWrapper.and(i -> i.in(KUser::getPayComputeType, kUser.getPayComputeTypes()).or().isNull(KUser::getPayComputeType));
            } else {
                queryWrapper.in(KUser::getPayComputeType, kUser.getPayComputeTypes());
            }
        }
        if (kUser.getLeaveReasonType() != null) {
            if (kUser.getLeaveReasonType().equals("非空")) {
                queryWrapper.isNotNull(KUser::getLeaveReasonType);
            } else {
                queryWrapper.isNull(KUser::getLeaveReasonType);
            }
        }
        IPage<KUser> kUserIPage = null;
        if (request.isAll()) {
            kUserIPage = new Page<>();
            kUserIPage.setRecords(this.baseMapper.selectList(queryWrapper));
        } else {
            Page<KUser> page = new Page<>(request.getPageNum(), request.getPageSize());
            kUserIPage = this.page(page, queryWrapper);
        }
        return kUserIPage;
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
////            if (k246801.getJiabanWeekend2() != null && k246801.getJiabanWeekend2().equals("0.00")) {
////                k246801.setJiabanWeekend2(null);
////            }
////            if (k246801.getProcessWeekendTiaoxiu() != null && k246801.getProcessWeekendTiaoxiu().equals("0.00")) {
////                k246801.setProcessWeekendTiaoxiu(null);
////            }
////            if (k246801.getFreeJiaban() != null && k246801.getFreeJiaban().equals("0.00")) {
////                k246801.setFreeJiaban(null);
////            }
////        }
        return k24680s;
    }

    @Override
    public Map<String, String> findKUserOption() throws ApiException {
        Map<String, String> kUserOption = DingApiUtil.findKUserOption();
        return kUserOption;
    }

    @Override
    public boolean updateKUserDingApi(KUser kUser) throws ApiException {
        Map<String, String> map = new HashMap<>();
        map.put("userId", kUser.getId());
        KUser kUserOld = kUserMapper.selectById(kUser.getId());
//        if (StringUtils.isNotEmpty(kUser.getName()) && !kUser.getName().equals(kUserOld.getName())) {
//            System.out.println("getName需要修改");
//            map.put("sys00-name", kUser.getName());
//        }
//        if (StringUtils.isNotEmpty(kUser.getJobnumber()) && !kUser.getJobnumber().equals(kUserOld.getJobnumber())) {
//            System.out.println("getJobnumber需要修改");
//            map.put("sys00-jobNumber", kUser.getJobnumber());
//        }
//        if (kUser.getPosition() != null && !kUser.getPosition().equals(kUserOld.getPosition())) {
//            System.out.println("getPosition需要修改");
//            map.put("sys00-position", kUser.getPosition());
//        }
        if (kUser.getUserType() != null && !kUser.getUserType().equals(kUserOld.getUserType())) {
            System.out.println("userType需要修改");
            map.put("sys01-employeeType", kUser.getUserType());
        }
        if (kUser.getPayPlace() != null && !kUser.getPayPlace().equals(kUserOld.getPayPlace())) {
            System.out.println("PayPlaces需要修改");
            map.put("31a29b0a-4c9a-470e-bdf5-760c8c304704", kUser.getPayPlace());
        }
//        if (kUser.getDispatchFactory() != null && !kUser.getDispatchFactory().equals(kUserOld.getDispatchFactory())) {
//            System.out.println("dispatchFactory需要修改");
//            map.put("379c084d-abfc-4af1-b777-a530fd282ada", kUser.getDispatchFactory());
//        }
        if (kUser.getPayComputeType() != null && !kUser.getPayComputeType().equals(kUserOld.getPayComputeType())) {
            System.out.println("payComputeType需要修改");
            map.put("b2931cda-df37-4317-8bda-3edb9d3aa257", kUser.getPayComputeType());
        }
        return DingApiUtil.updateKUserDingApi(map);
    }

}
