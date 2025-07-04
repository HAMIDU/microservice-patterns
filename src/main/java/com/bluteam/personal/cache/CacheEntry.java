package com.bluteam.personal.cache;

import java.time.LocalDateTime;

public class CacheEntry {

    Object value;
    LocalDateTime createdDateTime;
    int ttl;

    public CacheEntry(Object value, LocalDateTime createdDateTime, int ttl) {
        this.value = value;
        this.createdDateTime = createdDateTime;
        this.ttl = ttl;
    }

    public Object getValue() {
        return value;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public int getTtl() {
        return ttl;
    }

    public String toString() {
        return value.toString();
    }
}
