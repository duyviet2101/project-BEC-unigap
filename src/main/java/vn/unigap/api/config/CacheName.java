package vn.unigap.api.config;

import lombok.Data;

@Data
public class CacheName {
    private String name;
    private int ttl;
}