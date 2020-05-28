package com.askfm.demo;

import org.testcontainers.containers.PostgreSQLContainer;

import java.util.TimeZone;

public abstract class PostgresqlDbBaseTest {
  private static final Integer POSTGRES_PORT = 5432;
  private static final String URL_PATTERN = "jdbc:postgresql://%s:%s/db";
  public static final String TEST_USERNAME = "user";
  public static final String TEST_PASSWORD = "pass";

  private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:9.6.1")
      .withUsername(TEST_USERNAME)
      .withPassword(TEST_PASSWORD)
      .withDatabaseName("db")
      .withExposedPorts(POSTGRES_PORT);

  static {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    container.start();
    Runtime.getRuntime().addShutdownHook(new Thread(container::stop));
    String url = getUrl();
    System.setProperty("spring.datasource.hikari.url", url);
    System.setProperty("spring.datasource.hikari.jdbc-url", url);
    System.setProperty("spring.datasource.hikari.username", TEST_USERNAME);
    System.setProperty("spring.datasource.hikari.password", TEST_PASSWORD);
  }

  public static String getUrl() {
    return String.format(URL_PATTERN, container.getContainerIpAddress(), container.getMappedPort(POSTGRES_PORT));
  }
}
