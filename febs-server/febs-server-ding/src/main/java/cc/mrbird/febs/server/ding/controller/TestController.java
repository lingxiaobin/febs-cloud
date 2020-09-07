package cc.mrbird.febs.server.ding.controller;

import cc.mrbird.febs.common.core.entity.system.TradeLog;
import cc.mrbird.febs.common.core.utils.FebsUtil;
import cc.mrbird.febs.server.ding.service.ITradeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MrBird
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TestController {

    private final ITradeLogService tradeLogService;

    /**
     * 测试分布式事务
     */
    @GetMapping("pay")
    public void orderAndPay(TradeLog tradeLog) {
        this.tradeLogService.orderAndPay(tradeLog);
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("user")
    public Map<String, Object> currentUser() {
        Map<String, Object> map = new HashMap<>(5);
        map.put("currentUser", FebsUtil.getCurrentUser());
        map.put("currentUsername", FebsUtil.getCurrentUsername());
        map.put("currentUserAuthority", FebsUtil.getCurrentUserAuthority());
        map.put("currentTokenValue", FebsUtil.getCurrentTokenValue());
        map.put("currentRequestIpAddress", FebsUtil.getHttpServletRequestIpAddress());
        return map;
    }
}
