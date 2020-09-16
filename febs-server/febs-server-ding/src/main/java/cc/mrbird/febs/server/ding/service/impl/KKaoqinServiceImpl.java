package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.constant.FebsConstant;
import cc.mrbird.febs.common.core.entity.ding.*;
import cc.mrbird.febs.common.core.utils.DateUtil;
import cc.mrbird.febs.server.ding.controller.req.MyReq;
import cc.mrbird.febs.server.ding.mapper.KKaoqinMapper;
import cc.mrbird.febs.server.ding.mapper.KUserMapper;
import cc.mrbird.febs.server.ding.service.IKKaoqinService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service实现
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class KKaoqinServiceImpl extends ServiceImpl<KUserMapper, KUser> implements IKKaoqinService {

    private final KKaoqinMapper kKaoqinMapper;


    @Override
    public  Map<String,Object> findKDayDetails(MyReq myReq) {
        Map<String, String> map = new HashMap();
        map.put("userId", myReq.getUserId());
        map.put("workDate", myReq.getWorkDate().substring(0, 7));
        List<KDayDetail> kDayDetails = kKaoqinMapper.findKDayDetails(map);
        List<KAttendance> kAttendances = kKaoqinMapper.selectAttendance(map);
        KDayDetail kDayDetailSum = kKaoqinMapper.findKDayDetailsSum(map);

        kDayDetails.stream().forEach(item -> item.setWorkDateStr(DateUtil.getDateFormat(item.getWorkDate(), "yyyy-MM-dd")));
        Map<String, KDayDetail> dayMap = kDayDetails.stream().collect(Collectors.toMap(KDayDetail::getWorkDateStr, KDayDetail -> KDayDetail));


        kAttendances.stream().forEach(item -> item.setWorkDateStr(DateUtil.getDateFormat(item.getWorkDate(), "yyyy-MM-dd")));
        Map<String, KDayDetail> attMap = kAttendances.stream().map(x -> new KDayDetail(x.getWorkDateStr(), x.getClassName()))
                .collect(Collectors.toMap(KDayDetail::getWorkDateStr, KDayDetail -> KDayDetail));

        attMap.forEach((key, value) -> dayMap.merge(key, value, (v1, v2) -> v1));
        Map<String,Object> posMap=new HashMap<>();
        posMap.put("dayMap",dayMap);
        posMap.put("daySum",kDayDetailSum);
        return posMap;
    }

    @Override
    public KClass selectClass(MyReq myReq) {
        Map<String, String> map = new HashMap();
        map.put("className", myReq.getClassName());
        KClass kClasses = kKaoqinMapper.selectClass(map);
        return kClasses;
    }

    @Override
    public List<KLeave> selectLeave(MyReq myReq) {
        Map<String, String> map = new HashMap();
        map.put("userId", myReq.getUserId());
        map.put("workDate", myReq.getWorkDate().substring(0, 10));
        List<KLeave> list = kKaoqinMapper.selectLeave(map);
        return list;
    }
}
