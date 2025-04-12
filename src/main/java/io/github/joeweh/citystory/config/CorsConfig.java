package io.github.joeweh.citystory.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**") // Apply CORS to all paths
            .allowedOrigins("*") // Replace with allowed origin(s)
            .allowedMethods("*") // Allowed HTTP methods
            .allowedHeaders("*"); // Allow all headers
  }
}