package com.askfm.demo.service.impl;

import com.askfm.demo.dto.CountryDataResponseDto;
import com.askfm.demo.service.CountryDefinitionService;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultCountryDefinitionServiceUnitTest {

  private static final String COUNTRY_CODE_US = "US";
  private static final String COUNTRY_CODE_MOCK = "LV";
  private static final String SUCCESS = "success";
  private static final String ADDRESS = "123.456.789.000";
  private static final String FAILED = "failed";

  private final RestTemplate mockRestTemplate = mock(RestTemplate.class);
  private final CountryDefinitionService countryDefinitionService = new DefaultCountryDefinitionService(mockRestTemplate);
  private final ResponseEntity<CountryDataResponseDto> mockResponseEntity = mock(ResponseEntity.class);

  @Test
  void shouldReturnCountryCodeIfSuccess() {
    //given
    var countryDataResponseDto = new CountryDataResponseDto();
    countryDataResponseDto.setStatus(SUCCESS);
    countryDataResponseDto.setCountryCode(COUNTRY_CODE_US);

    when(mockRestTemplate.exchange(anyString(), anyObject(), anyObject(), eq(CountryDataResponseDto.class))).thenReturn(mockResponseEntity);
    when(mockResponseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
    when(mockResponseEntity.getBody()).thenReturn(countryDataResponseDto);

    //when
    var countryCode = countryDefinitionService.getCountryCode(ADDRESS);

    //then
    assertEquals(COUNTRY_CODE_US, countryCode);
  }

  @Test
  void shouldReturnMockCountryCodeIfThrowException() {
    //given
    when(mockRestTemplate.exchange(anyString(), anyObject(), anyObject(), eq(CountryDataResponseDto.class))).thenThrow(new RuntimeException());

    //when
    var countryCode = countryDefinitionService.getCountryCode(ADDRESS);

    //then
    assertEquals(COUNTRY_CODE_MOCK, countryCode);
  }

  @Test
  void shouldReturnMockCountryCodeIfRequestIsNotSuccess() {
    //given
    var countryDataResponseDto = new CountryDataResponseDto();
    countryDataResponseDto.setStatus(FAILED);
    countryDataResponseDto.setCountryCode(COUNTRY_CODE_US);
    when(mockResponseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
    when(mockResponseEntity.getBody()).thenReturn(countryDataResponseDto);

    when(mockRestTemplate.exchange(anyString(), anyObject(), anyObject(), eq(CountryDataResponseDto.class))).thenReturn(mockResponseEntity);

    //when
    var countryCode = countryDefinitionService.getCountryCode(ADDRESS);

    //then
    assertEquals(COUNTRY_CODE_MOCK, countryCode);
  }

}