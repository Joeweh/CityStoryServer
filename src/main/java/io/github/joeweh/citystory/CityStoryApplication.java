package io.github.joeweh.citystory;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"io.github.joeweh.citystory"})
public class CityStoryApplication {
  public static void main(String[] args) {
    SpringApplication.run(CityStoryApplication.class, args);
  }
}