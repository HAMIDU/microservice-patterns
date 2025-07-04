package com.bluteam.personal.cache;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;

public class CacheManager {

    ConcurrentHashMap<String, CacheEntry> cache = new ConcurrentHashMap<>();

    public CacheEntry get(String key) {
        return cache.get(key);
    }

    public void put(String key, Object value) {
        cache.put(key, new CacheEntry(value, LocalDateTime.now(), 10));
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public boolean isExpired(CacheEntry entry) {
        if (entry.value != null) {
            if (ChronoUnit.SECONDS.between(entry.createdDateTime, LocalDateTime.now()) > entry.ttl) {
                return true;
            }
        }
        return false;
    }
}
