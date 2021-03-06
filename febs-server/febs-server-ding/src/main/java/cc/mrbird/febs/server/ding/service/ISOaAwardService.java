package cc.mrbird.febs.server.ding.service;

import cc.mrbird.febs.common.core.entity.ding.SOaAward;

import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.server.ding.controller.req.FlushReq;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumReq;
import cc.mrbird.febs.server.ding.controller.req.SOaAwardReq;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 *  Service接口
 *
 * @author MrBird
 * @date 2020-12-01 18:07:41
 */
public interface ISOaAwardService extends IService<SOaAward> {

    boolean flush(FlushReq flushReq);
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param sOaAward sOaAward
     * @return IPage<SOaAward>
     */
    Map<String, Object> findSOaAwards(QueryRequest request, SOaAwardReq sOaAward);

    /**
     * 查询（所有）
     *
     * @param sOaAward sOaAward
     * @return List<SOaAward>
     */
    List<SOaAward> findSOaAwards(SOaAward sOaAward);

    /**
     * 新增
     *
     * @param sOaAward sOaAward
     */
    void createSOaAward(SOaAward sOaAward);

    /**
     * 修改
     *
     * @param sOaAward sOaAward
     */
    void updateSOaAward(SOaAward sOaAward);

    /**
     * 删除
     *
     * @param sOaAward sOaAward
     */
    void deleteSOaAward(SOaAward sOaAward);
}
