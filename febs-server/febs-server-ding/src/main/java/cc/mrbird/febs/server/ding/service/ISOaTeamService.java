package cc.mrbird.febs.server.ding.service;


import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.SOaTeam;
import cc.mrbird.febs.server.ding.controller.req.FlushReq;
import cc.mrbird.febs.server.ding.controller.req.SOaKpiReq;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 *  Service接口
 *
 * @author MrBird
 * @date 2021-01-02 11:43:09
 */
public interface ISOaTeamService extends IService<SOaTeam> {

    boolean flush(FlushReq flushReq) throws ParseException;
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param sOaTeam sOaTeam
     * @return IPage<SOaTeam>
     */
    Map<String, Object> findSOaTeams(QueryRequest request, SOaKpiReq sOaKpiReq);

    /**
     * 查询（所有）
     *
     * @param sOaTeam sOaTeam
     * @return List<SOaTeam>
     */
    List<SOaTeam> findSOaTeams(SOaTeam sOaTeam);

    /**
     * 新增
     *
     * @param sOaTeam sOaTeam
     */
    void createSOaTeam(SOaTeam sOaTeam);

    /**
     * 修改
     *
     * @param sOaTeam sOaTeam
     */
    void updateSOaTeam(SOaTeam sOaTeam);

    /**
     * 删除
     *
     * @param sOaTeam sOaTeam
     */
    void deleteSOaTeam(SOaTeam sOaTeam);
}
