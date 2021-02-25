package cc.mrbird.febs.server.ding.controller.excel.redis;

import cc.mrbird.febs.common.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExcelRedis<T> {
    private final RedisService redisService;

    public String setErrorDate(List<T> stateRes){
        String id=UUID.randomUUID().toString();
        redisService.set(id,stateRes,180L);
        return id;
    }

    public List<T> setErrorDate(String id){
        List<T> objects =(List<T>) redisService.get(id);
        return objects;
    }
}
