package cc.mrbird.febs.server.ding.service;

import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.KClass;
import cc.mrbird.febs.common.core.entity.ding.KLeave;
import cc.mrbird.febs.common.core.entity.ding.KUser;
import cc.mrbird.febs.common.core.entity.ding.SDayDetailSum;
import cc.mrbird.febs.server.ding.controller.req.MyReq;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumReq;
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

     void flush(SKaoqinSumReq sKaoqinSumReq) throws ParseException;

    List<SDayDetailSum>  findSKaoqinSums(QueryRequest request, SKaoqinSumReq sKaoqinSumReq);
}
