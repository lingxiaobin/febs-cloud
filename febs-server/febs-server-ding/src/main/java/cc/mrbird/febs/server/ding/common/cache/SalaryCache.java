package cc.mrbird.febs.server.ding.common.cache;

import cc.mrbird.febs.common.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalaryCache {
    private final RedisService redisService;


}
