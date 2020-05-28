package com.askfm.demo.service.impl;

import com.askfm.demo.dto.CountryDataResponseDto;
import com.askfm.demo.service.CountryDefinitionService;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultCountryDefinitionService implements CountryDefinitionService {

  private static final String BASE_SERVICE_URL = "http://ip-api.com/json/";
  private static final String SUCCESS = "success";
  private static final String COUNTRY_CODE_MOCK = "LV";

  private final RestTemplate restTemplate;

  @Override
  public String getCountryCode(String address) {
    try {
      var url = BASE_SERVICE_URL + address;
      var response = restTemplate.exchange(url, HttpMethod.GET, null, CountryDataResponseDto.class);
      var body = response.getBody();
      if (response.getStatusCode() == HttpStatus.OK && body != null && body.getStatus().equals(SUCCESS)) {
        return body.getCountryCode();
      }
    } catch (Exception e) {
      log.warn(e.getMessage());
    }
    return COUNTRY_CODE_MOCK;
  }

}
