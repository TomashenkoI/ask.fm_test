package com.askfm.demo.filter;

import com.askfm.demo.exception.LargeRequestFrequencyException;
import com.askfm.demo.service.RequestFrequencyLimiter;
import com.askfm.demo.service.impl.DefaultRequestFrequencyLimiter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestFrequencyLimiterUnitTest {

  private static final int AVAILABLE_PERMITS_PER_SECOND = 2;
  private static final String COUNTRY_CODE = "countryCode";

  private final RequestFrequencyLimiter requestFrequencyLimiter = new DefaultRequestFrequencyLimiter();

  @BeforeEach
  void beforeEach() {
    ReflectionTestUtils.setField(requestFrequencyLimiter, "permitsPerSecond", AVAILABLE_PERMITS_PER_SECOND);
  }

  @Test
  @DisplayName("should not throw exception if enough permits")
  void shouldNotThrowExceptionIfEnoughPermits() {
    //then
    assertDoesNotThrow(() -> {
      for (int i = 0; i < AVAILABLE_PERMITS_PER_SECOND; i++) {
        requestFrequencyLimiter.checkRequestsFrequency(COUNTRY_CODE);
      }
    });
  }

  @Test
  @DisplayName("should throw exception if not enough permits")
  void shouldThrowExceptionIfNotEnoughPermits() {
    //given
    var cyclesNumber = 5;

    //then
    var exception = assertThrows(LargeRequestFrequencyException.class, () -> {
      for (int i = 0; i < cyclesNumber; i++) {
        requestFrequencyLimiter.checkRequestsFrequency(COUNTRY_CODE);
      }
    });
    assertEquals(
        "There are more than " + AVAILABLE_PERMITS_PER_SECOND + " requests at the same second from the same country !",
        exception.getMessage()
    );
  }

}