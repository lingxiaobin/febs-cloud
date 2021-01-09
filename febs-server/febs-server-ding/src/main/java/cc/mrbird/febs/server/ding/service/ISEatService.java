package cc.mrbird.febs.server.ding.service;

import cc.mrbird.febs.common.core.entity.ding.SEat;
import cc.mrbird.febs.server.ding.controller.req.FlushReq;
import cc.mrbird.febs.server.ding.controller.req.SOaKpiReq;

import cc.mrbird.febs.common.core.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 *  Service接口
 *
 * @author MrBird
 * @date 2021-01-04 08:39:20
 */
public interface ISEatService extends IService<SEat> {

    boolean flush(FlushReq flushReq) throws ParseException;
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param sEat sEat
     * @return IPage<SEat>
     */
    Map<String, Object> findSEats(QueryRequest request, SOaKpiReq sOaKpiReq);

    /**
     * 查询（所有）
     *
     * @param sEat sEat
     * @return List<SEat>
     */
    List<SEat> findSEats(SEat sEat);

    /**
     * 新增
     *
     * @param sEat sEat
     */
    void createSEat(SEat sEat);

    /**
     * 修改
     *
     * @param sEat sEat
     */
    void updateSEat(SEat sEat);

    /**
     * 删除
     *
     * @param sEat sEat
     */
    void deleteSEat(SEat sEat);
}
