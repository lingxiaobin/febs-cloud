package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.constant.PageConstant;
import cc.mrbird.febs.common.core.entity.ding.PClass;
import cc.mrbird.febs.common.core.entity.ding.PClassDetailAll;
import cc.mrbird.febs.server.ding.controller.req.PClassReq;
import cc.mrbird.febs.server.ding.mapper.PClassDetailMapper;
import cc.mrbird.febs.server.ding.mapper.PClassMapper;
import cc.mrbird.febs.server.ding.service.IPClassService;
import com.baomidou.dynamic.datasource.annotation.DS;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final PClassDetailMapper pClassDetailMapper;
    @Override
    public Map<String, Object> findPClasss(QueryRequest request, PClassReq pClassReq) {
        Map<String, Object> parMap = new HashMap<>();
        if (StringUtils.isNotEmpty(pClassReq.getClassName())){
            parMap.put("classNameOne", pClassReq.isSelOne());
            parMap.put("className", pClassReq.getClassName());
        }
        if (pClassReq.getGiveLessonsArr().length > 0) {
            parMap.put("giveLessonsArr", pClassReq.getGiveLessonsArr());
        }
        if (pClassReq.getExamineArr().length > 0) {
            parMap.put("examineArr", pClassReq.getExamineArr());
        }
        if (pClassReq.getTrainArr().length > 0) {
            parMap.put("trainArr", pClassReq.getTrainArr());
        }
        if (!request.isAll()) {
            parMap.put("pageNum", (request.getPageNum()-1) * request.getPageSize());
            parMap.put("size", request.getPageSize());
        }
        parMap.put("isAll", request.isAll());
        List<PClass> map = pClassDetailMapper.selectPClass(parMap);
        parMap.put("isAll", true);
        Long aLong = pClassDetailMapper.selectPClassCount(parMap);


        Map<String, Object> data = new HashMap<>(2);
        data.put(PageConstant.ROWS, map);
        data.put(PageConstant.TOTAL, aLong);
        return data;
    }

    @Override
    public List<PClass> findPClasss(PClass pClass) {
        if (StringUtils.isEmpty(pClass.getName())){
            return null;
        }
        LambdaQueryWrapper<PClass> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(PClass::getName)
        .like(PClass::getName,pClass.getName());
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
