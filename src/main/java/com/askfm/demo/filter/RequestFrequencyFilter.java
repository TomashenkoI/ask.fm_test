package com.askfm.demo.filter;


import com.askfm.demo.exception.LargeRequestFrequencyException;
import com.askfm.demo.service.CountryDefinitionService;

import org.apache.commons.lang3.concurrent.TimedSemaphore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RequestFrequencyFilter extends GenericFilterBean {

  private static final int TIME_PERIOD = 1;

  @Value("${permitsPerSecond}")
  private Integer permitsPerSecond;

  private final CountryDefinitionService countryDefinitionService;
  private final Map<String, TimedSemaphore> countryToTimedSemaphoreMap = new ConcurrentHashMap<>();

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    var countryCode = countryDefinitionService.getCountryCode(request);
    checkFrequencyFromCountry(countryCode);
    chain.doFilter(request, response);
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
