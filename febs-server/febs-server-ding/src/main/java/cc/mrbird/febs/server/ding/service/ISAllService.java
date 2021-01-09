package cc.mrbird.febs.server.ding.service;



import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.SAll;
import cc.mrbird.febs.server.ding.controller.req.BaseReq;
import cc.mrbird.febs.server.ding.controller.req.FlushReq;
import cc.mrbird.febs.server.ding.controller.req.SAllReq;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 *  Service接口
 *
 * @author MrBird
 * @date 2020-12-04 14:33:28
 */
public interface ISAllService extends IService<SAll> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param sAll sAll
     * @return IPage<SAll>
     */
    Map<String, Object>  findSAlls(QueryRequest request, SAllReq req);

    /**
     * 查询（所有）
     *
     * @param sAll sAll
     * @return List<SAll>
     */
    List<SAll> findSAlls(SAll sAll);

    /**
     * 新增
     *
     * @param sAll sAll
     */
    void createSAll(SAll sAll);

    /**
     * 修改
     *
     * @param sAll sAll
     */
    void updateSAll(SAll sAll);

    /**
     * 删除
     *
     * @param sAll sAll
     */
    void deleteSAll(SAll sAll);

    Boolean ayncDate(FlushReq flushReq) throws ParseException;
}
