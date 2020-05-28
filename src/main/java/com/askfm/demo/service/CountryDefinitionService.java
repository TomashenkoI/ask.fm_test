package com.askfm.demo.service;

import javax.servlet.ServletRequest;

public interface CountryDefinitionService {
  String getCountryCode(ServletRequest request);
}
