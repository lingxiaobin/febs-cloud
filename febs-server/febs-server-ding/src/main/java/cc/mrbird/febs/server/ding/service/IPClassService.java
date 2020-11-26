package cc.mrbird.febs.server.ding.service;


import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.PClass;
import cc.mrbird.febs.server.ding.controller.req.PClassReq;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;


/**
 *  Service接口
 *
 * @author MrBird
 * @date 2020-11-06 08:35:28
 */
public interface IPClassService extends IService<PClass> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param pClass pClass
     * @return IPage<PClass>
     */
    Map<String, Object> findPClasss(QueryRequest request, PClassReq pClassReq);

    /**
     * 查询（所有）
     *
     * @param pClass pClass
     * @return List<PClass>
     */
    List<PClass> findPClasss(PClass pClass);

    /**
     * 新增
     *
     * @param pClass pClass
     */
    void createPClass(PClass pClass);

    /**
     * 修改
     *
     * @param pClass pClass
     */
    void updatePClass(PClass pClass);

    /**
     * 删除
     *
     * @param pClass pClass
     */
    void deletePClass(PClass pClass);
}
