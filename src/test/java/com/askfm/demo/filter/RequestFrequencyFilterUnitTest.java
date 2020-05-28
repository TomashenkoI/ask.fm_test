package com.askfm.demo.filter;

import com.askfm.demo.exception.LargeRequestFrequencyException;
import com.askfm.demo.service.CountryDefinitionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestFrequencyFilterUnitTest {

  public static final int AVAILABLE_PERMITS_PER_SECOND = 2;

  private final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
  private final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
  private final FilterChain mockFilterChain = mock(FilterChain.class);
  private final CountryDefinitionService countryDefinitionService = mock(CountryDefinitionService.class);
  private final RequestFrequencyFilter requestFrequencyFilter = new RequestFrequencyFilter(countryDefinitionService);

  @BeforeEach
  void beforeEach() {
    ReflectionTestUtils.setField(requestFrequencyFilter, "permitsPerSecond", AVAILABLE_PERMITS_PER_SECOND);
  }

  @Test
  @DisplayName("should not throw exception for single request")
  void shouldNotThrowExceptionEnoughPermits() {
    //given
    when(countryDefinitionService.getCountryCode(any())).thenReturn("lv");

    //then
    assertDoesNotThrow(() -> {
      for (int i = 0; i < AVAILABLE_PERMITS_PER_SECOND; i++) {
        requestFrequencyFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
      }
    });
  }

  @Test
  @DisplayName("should throw exception if not enough permits")
  void shouldThrowExceptionIfNotEnoughPermits() {
    //given
    var cyclesNumber = 5;
    when(countryDefinitionService.getCountryCode(any())).thenReturn("lv");

    //then
    var exception = assertThrows(LargeRequestFrequencyException.class, () -> {
      for (int i = 0; i < cyclesNumber; i++) {
        requestFrequencyFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
      }
    });
    assertEquals("There are more than " + AVAILABLE_PERMITS_PER_SECOND + " requests at the same second from the same country !", exception.getMessage());
  }

}