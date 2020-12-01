package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.constant.PageConstant;
import cc.mrbird.febs.common.core.entity.ding.SDayDetailSum;
import cc.mrbird.febs.common.core.utils.DateUtil;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumEnitReq;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumReq;
import cc.mrbird.febs.server.ding.controller.vo.DeptVo;
import cc.mrbird.febs.server.ding.mapper.SKaoqinSumMapper;
import cc.mrbird.febs.server.ding.mapper.SOaMapper;
import cc.mrbird.febs.server.ding.service.ISOaService;
import cc.mrbird.febs.server.ding.service.ISSalarySettingService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service实现
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SOaServiceImpl  implements ISOaService {

    private final ISSalarySettingService isSalarySettingService;



}
