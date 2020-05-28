package com.askfm.demo.service.impl;


import com.askfm.demo.exception.LargeRequestFrequencyException;
import com.askfm.demo.service.RequestFrequencyLimiter;

import org.apache.commons.lang3.concurrent.TimedSemaphore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DefaultRequestFrequencyLimiter implements RequestFrequencyLimiter {

  private static final int TIME_PERIOD = 1;

  @Value("${permits_per_second}")
  private Integer permitsPerSecond;

  private final Map<String, TimedSemaphore> countryToTimedSemaphoreMap = new ConcurrentHashMap<>();

  @Override
  public void checkRequestsFrequency(String countryCode) {
    checkFrequencyFromCountry(countryCode);
  }

  private void checkFrequencyFromCountry(String countryCode) {
    var timedSemaphore = countryToTimedSemaphoreMap.computeIfAbsent(
        countryCode,
        f -> new TimedSemaphore(TIME_PERIOD, TimeUnit.SECONDS, permitsPerSecond)
    );
    if (!timedSemaphore.tryAcquire()) {
      throw new LargeRequestFrequencyException("There are more than " + permitsPerSecond + " requests at the same second from the same country !");
    }
  }
}
