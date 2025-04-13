package io.github.joeweh.citystory.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
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

    DataSource baseSource = builder.type(HikariDataSource.class).build();

    HikariConfig hikariConfig = new HikariConfig();

    hikariConfig.setDataSource(baseSource);
    hikariConfig.setMaximumPoolSize(10);
    hikariConfig.setMinimumIdle(5);
    hikariConfig.setIdleTimeout(300_000);        // 5 minutes
    hikariConfig.setMaxLifetime(1_800_000);      // 30 minutes
    hikariConfig.setKeepaliveTime(300_000);      // 5 minutes
    hikariConfig.setConnectionTimeout(30_000);   // 30 seconds
    hikariConfig.setConnectionTestQuery("SELECT 1");

    return new HikariDataSource(hikariConfig);
  }
}