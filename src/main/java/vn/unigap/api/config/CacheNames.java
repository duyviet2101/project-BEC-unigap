package vn.unigap.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "redis")
public class CacheNames {

    @Data
    public static class CacheName {
        private String name;
        private int ttl;
    }

    List<CacheName> cacheNames;
}
