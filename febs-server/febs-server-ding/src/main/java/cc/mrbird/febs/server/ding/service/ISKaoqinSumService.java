package cc.mrbird.febs.server.ding.service;

import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.KClass;
import cc.mrbird.febs.common.core.entity.ding.KLeave;
import cc.mrbird.febs.common.core.entity.ding.KUser;
import cc.mrbird.febs.common.core.entity.ding.SDayDetailSum;
import cc.mrbird.febs.server.ding.controller.req.*;
import cc.mrbird.febs.server.ding.controller.vo.DeptVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 *  Service接口
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
public interface ISKaoqinSumService extends IService<SDayDetailSum> {

    boolean flush(FlushReq flushReq) throws ParseException;

    Map<String, Object> findSKaoqinSums(QueryRequest request, SKaoqinSumReq sKaoqinSumReq);

    int updateSKaoqinSum(SKaoqinSumEnitReq enitReq, SDayDetailSum sDayDetailSum) throws ParseException;

    List<String> noKaoqinUser(SKaoqinSumReq sKaoqinSumReq);


}
