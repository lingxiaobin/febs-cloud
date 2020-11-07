package cc.mrbird.febs.server.ding.service;

import cc.mrbird.febs.server.ding.controller.req.WiplineReq;
import cc.mrbird.febs.server.ding.controller.vo.SaleDateValueVo;

import java.util.List;
import java.util.Map;

public interface IWiplineService {
    Map<String, Object> selectWipline(WiplineReq wiplineReq);

}
