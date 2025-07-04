package com.bluteam.personal.ratelimit;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiter {

    ConcurrentMap<String, UserCallInfo> userLimits = new ConcurrentHashMap<>();

    int requestCount;
    int periodOfLimitBasedSecond;

    public RateLimiter(int requestCount, int periodOfLimitBasedSecond) {
        this.requestCount = requestCount;
        this.periodOfLimitBasedSecond = periodOfLimitBasedSecond;
    }

    public boolean isAllowed(String user) {
        if (userLimits.containsKey(user)) {
            userLimits.get(user).callCount.incrementAndGet();
        } else {
            userLimits.put(user, new UserCallInfo(new AtomicInteger(1), LocalDateTime.now()));
            return Boolean.TRUE;
        }
        if (userLimits.get(user).callCount.intValue() > requestCount) {
            if (ChronoUnit.SECONDS.between(userLimits.get(user).firstCallInPeriod, LocalDateTime.now()) > periodOfLimitBasedSecond) {
                userLimits.remove(user);
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } else {
            return Boolean.TRUE;
        }
    }
}
