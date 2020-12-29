package cc.mrbird.febs.server.ding.service;


import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.SOaKpi;
import cc.mrbird.febs.server.ding.controller.req.FlushReq;
import cc.mrbird.febs.server.ding.controller.req.SOaKpiReq;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 *  Service接口
 *
 * @author MrBird
 * @date 2020-12-26 14:58:08
 */
public interface ISOaKpiService extends IService<SOaKpi> {

    boolean flush(FlushReq flushReq);
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param sOaKpi sOaKpi
     * @return IPage<SOaKpi>
     */
    Map<String, Object> findSOaKpis(QueryRequest request,  SOaKpiReq sOaKpiReq);

    /**
     * 查询（所有）
     *
     * @param sOaKpi sOaKpi
     * @return List<SOaKpi>
     */
    List<SOaKpi> findSOaKpis(SOaKpi sOaKpi);

    /**
     * 新增
     *
     * @param sOaKpi sOaKpi
     */
    void createSOaKpi(SOaKpi sOaKpi);

    /**
     * 修改
     *
     * @param sOaKpi sOaKpi
     */
    void updateSOaKpi(SOaKpi sOaKpi);

    /**
     * 删除
     *
     * @param sOaKpi sOaKpi
     */
    void deleteSOaKpi(SOaKpi sOaKpi);
}
