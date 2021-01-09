package cc.mrbird.febs.server.ding.service;

import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.K24680;
import cc.mrbird.febs.common.core.entity.ding.KUser;
import cc.mrbird.febs.server.ding.controller.req.DeptReq;
import cc.mrbird.febs.server.ding.controller.req.KUserReq;
import cc.mrbird.febs.server.ding.controller.vo.DeptVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taobao.api.ApiException;

import java.util.List;
import java.util.Map;

/**
 *  Service接口
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
public interface IKUserService extends IService<KUser> {

    List<KUser> findKUsers( KUser kUser);


    List<DeptVo> findDeptAndUser(DeptReq deptReq);
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param kUser kUser
     * @return IPage<KUser>
     */
    IPage<KUser> findKUsers(QueryRequest request,  KUserReq req);

    IPage<KUser> findKUsersOther(QueryRequest request,  KUserReq req);


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

    List<K24680> findK24680(QueryRequest queryRequest, K24680 k24680);


    Map<String,String> findKUserOption() throws ApiException;

    boolean updateKUserDingApi(KUser kUser) throws ApiException;


    List<Map<String,Object>> findLikeUser(String name, Integer leaveType);
}
