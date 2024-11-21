package kwangwoon.chambit.dontworry.domain.importantIndex.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import kwangwoon.chambit.dontworry.domain.importantIndex.domain.IndexInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class IndexInfoRepository {
    private final RedisTemplate<String,Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public <T extends IndexInfo> List<T> getEntitiesByPattern(String keyPattern, Class<T> clazz){
        Set<String> keys = redisTemplate.keys(keyPattern);
        List<T> entities = new ArrayList<>();

        for(String key : keys){
            Map<Object, Object> hashValues = redisTemplate.opsForHash().entries(key);
            T entity = objectMapper.convertValue(hashValues, clazz);
            entity.setCode(key);
            entities.add(entity);
        }

        return entities;
    }
}
