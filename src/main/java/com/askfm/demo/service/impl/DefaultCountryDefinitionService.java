package com.askfm.demo.service.impl;

import com.askfm.demo.service.CountryDefinitionService;

import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;

@Service
public class DefaultCountryDefinitionService implements CountryDefinitionService {

  private static final String COUNTRY_CODE_MOCK = "lv";

  @Override
  public String getCountryCode(ServletRequest request) {
    return COUNTRY_CODE_MOCK;
  }

}
