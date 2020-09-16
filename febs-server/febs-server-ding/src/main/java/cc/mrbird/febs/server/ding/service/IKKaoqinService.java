package cc.mrbird.febs.server.ding.service;

import cc.mrbird.febs.common.core.entity.ding.*;

import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.server.ding.controller.req.MyReq;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 *  Service接口
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
public interface IKKaoqinService extends IService<KUser> {

    Map<String,Object> findKDayDetails(MyReq myReq);
    KClass selectClass(MyReq myReq);

    List<KLeave> selectLeave(MyReq myReq);
}
