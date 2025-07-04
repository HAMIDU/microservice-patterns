package com.bluteam.personal.ratelimit;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class UserCallInfo {

    AtomicInteger callCount;
    LocalDateTime firstCallInPeriod;

    public UserCallInfo(AtomicInteger callCount, LocalDateTime firstCallInPeriod) {
        this.callCount = callCount;
        this.firstCallInPeriod = firstCallInPeriod;
    }
}
