package io.github.joeweh.citystory.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {
  @Value(value = "${spring.datasource.url}")
  private String url;

  @Value(value = "${spring.datasource.username}")
  private String username;

  @Value(value = "${spring.datasource.password}")
  private String password;

  @Bean
  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
            url,
            username,
            password
    );
  }
}