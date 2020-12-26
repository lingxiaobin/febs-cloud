package cc.mrbird.febs.server.ding.service;


import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.PClassDetail;
import cc.mrbird.febs.common.core.entity.ding.PClassDetailAll;
import cc.mrbird.febs.server.ding.controller.req.PClassReq;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 *  Service接口
 *
 * @author MrBird
 * @date 2020-11-06 08:37:21
 */
public interface IPClassDetailService extends IService<PClassDetail> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @return IPage<PClassDetail>
     */
    Map<String, Object> findPClassDetails(QueryRequest request, PClassReq pClassReq);

    Map<Integer, long[]> selectPClassDetailStateIn( PClassReq pClassReq);
    /**
     * 查询（所有）
     *
     * @param pClassDetail pClassDetail
     * @return List<PClassDetail>
     */
    List<PClassDetail> findPClassDetails(PClassDetail pClassDetail);

    /**
     * 新增
     *
     * @param pClassDetail pClassDetail
     */
    void createPClassDetail(PClassDetail pClassDetail);

    /**
     * 修改
     *
     * @param pClassDetail pClassDetail
     */
    void updatePClassDetail(PClassDetail pClassDetail);

    /**
     * 删除
     *
     * @param pClassDetail pClassDetail
     */
    void deletePClassDetail(PClassDetail pClassDetail);
}
