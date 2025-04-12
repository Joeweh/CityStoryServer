package io.github.joeweh.citystory.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfig {
  @Value(value = "${spring.datasource.url}")
  private String url;

  @Value(value = "${spring.datasource.username}")
  private String username;

  @Value(value = "${spring.datasource.password}")
  private String password;

  @Bean
  public DataSource getDataSource() {
    DataSourceBuilder<?> builder = DataSourceBuilder.create();

    builder.driverClassName("com.mysql.cj.jdbc.Driver");
    builder.url(url);
    builder.username(username);
    builder.password(password);

    return builder.type(HikariDataSource.class).build();
  }
}