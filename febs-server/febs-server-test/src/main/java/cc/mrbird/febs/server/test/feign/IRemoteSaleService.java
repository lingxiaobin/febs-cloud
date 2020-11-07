package cc.mrbird.febs.server.test.feign;

import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.constant.FebsServerConstant;
import cc.mrbird.febs.server.test.feign.fallback.RemoteSaleServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author MrBird
 */
@FeignClient(value = FebsServerConstant.FEBS_SERVER_DING, contextId = "saleServiceClient", fallbackFactory = RemoteSaleServiceFallback.class)
public interface IRemoteSaleService {

    /**
     * remote /user endpoint
     * @return FebsResponse
     */
    @GetMapping("eSaleMonth/flushSaleMonth")
    FebsResponse flushSaleMonth(@RequestParam("str") String str);
}
