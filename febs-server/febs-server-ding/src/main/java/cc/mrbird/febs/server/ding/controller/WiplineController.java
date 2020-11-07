package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.utils.FebsUtil;
import cc.mrbird.febs.server.ding.controller.req.WiplineReq;
import cc.mrbird.febs.server.ding.controller.vo.SaleDateValueVo;
import cc.mrbird.febs.server.ding.service.ISaleService;
import cc.mrbird.febs.server.ding.service.IWiplineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("wipline")
@RequiredArgsConstructor
public class WiplineController {

    private final IWiplineService wiplineService;

    /**
     * wip 在线查看
     *
     * @param
     * @return
     */
    @GetMapping
    @ResponseBody
    public FebsResponse selectWipline(WiplineReq wiplineReq)   {
        Map<String, Object> map = new HashMap<>(5);
        map.put("currentUser", FebsUtil.getCurrentUser());
        map.put("currentUsername", FebsUtil.getCurrentUsername());
        map.put("currentUserAuthority", FebsUtil.getCurrentUserAuthority());
        map.put("currentTokenValue", FebsUtil.getCurrentTokenValue());
        map.put("currentRequestIpAddress", FebsUtil.getHttpServletRequestIpAddress());
        return new FebsResponse().data(wiplineService.selectWipline(wiplineReq));
    }

}
