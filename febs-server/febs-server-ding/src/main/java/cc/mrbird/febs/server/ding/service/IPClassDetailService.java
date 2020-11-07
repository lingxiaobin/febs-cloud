package cc.mrbird.febs.server.ding.service;


import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.PClassDetail;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
     * @param pClassDetail pClassDetail
     * @return IPage<PClassDetail>
     */
    IPage<PClassDetail> findPClassDetails(QueryRequest request, PClassDetail pClassDetail);

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
