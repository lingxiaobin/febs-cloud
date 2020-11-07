package cc.mrbird.febs.server.job.feign.fallback;

import cc.mrbird.febs.common.core.annotation.Fallback;
import cc.mrbird.febs.server.job.feign.IRemoteSaleService;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author MrBird
 */
@Slf4j
@Fallback
public class RemoteSaleServiceFallback implements FallbackFactory<IRemoteSaleService> {

    @Override
    public IRemoteSaleService create(Throwable throwable) {
        return (str) -> {
            log.error("获取用户信息失败", throwable);
            return null;
        };
    }
}