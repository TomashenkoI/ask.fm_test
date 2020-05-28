package com.askfm.demo;

import com.askfm.demo.filter.RequestFrequencyFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@SpringBootApplication
public class DemoApplication {

  private final RequestFrequencyFilter requestFrequencyFilter;

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  public FilterRegistrationBean<RequestFrequencyFilter> filterRegistrationBean() {
    FilterRegistrationBean<RequestFrequencyFilter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(requestFrequencyFilter);
    filterRegistrationBean.setUrlPatterns(Collections.singleton("/loan/apply"));

    return filterRegistrationBean;
  }

}
