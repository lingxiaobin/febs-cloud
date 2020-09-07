package cc.mrbird.febs.server.ding.service;

import cc.mrbird.febs.common.core.entity.ding.KUser;

import cc.mrbird.febs.common.core.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
public interface IKUserService extends IService<KUser> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param kUser kUser
     * @return IPage<KUser>
     */
    IPage<KUser> findKUsers(QueryRequest request, KUser kUser);

    /**
     * 查询（所有）
     *
     * @param kUser kUser
     * @return List<KUser>
     */
    List<KUser> findKUsers(KUser kUser);

    /**
     * 新增
     *
     * @param kUser kUser
     */
    void createKUser(KUser kUser);

    /**
     * 修改
     *
     * @param kUser kUser
     */
    void updateKUser(KUser kUser);

    /**
     * 删除
     *
     * @param kUser kUser
     */
    void deleteKUser(KUser kUser);
}
