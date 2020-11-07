package cc.mrbird.febs.server.ding.feign.fallback;

import cc.mrbird.febs.server.ding.feign.IRemoteTradeLogService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author MrBird
 */
@Slf4j
@Component
public class RemoteTradeLogServiceFallback implements FallbackFactory<IRemoteTradeLogService> {
    @Override
    public IRemoteTradeLogService create(Throwable throwable) {
        return tradeLog -> log.error("调用失败", throwable);
    }
}
