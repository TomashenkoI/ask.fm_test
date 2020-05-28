package com.askfm.demo.service;

import java.net.http.HttpRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public interface CountryDefinitionService {
  String getCountryCode(ServletRequest request);
}
