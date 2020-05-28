package com.askfm.demo.service;

public interface RequestFrequencyLimiter {
  void checkRequestsFrequency(String countryCode);
}
