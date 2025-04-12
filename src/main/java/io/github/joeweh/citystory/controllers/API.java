package io.github.joeweh.citystory.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.github.joeweh.citystory.entities.Landmark;
import io.github.joeweh.citystory.services.RatingService;

@RestController
public class API {
  private final ObjectMapper mapper;

  // Constructor injection
  private final RatingService ratingService;

  public API(final RatingService ratingService) {
    this.ratingService = ratingService;
    this.mapper = new ObjectMapper();
  }

  @GetMapping("/ping")
  public ResponseEntity<String> healthCheck() {
    return new ResponseEntity<>("pong", HttpStatus.OK);
  }

  @GetMapping("/landmarks")
  public ResponseEntity<String> getLandmarks() {
    List<Landmark> landmarks = ratingService.getLandmarks();

    try {
      return new ResponseEntity<>(mapper.writeValueAsString(landmarks), HttpStatus.OK);
    } catch (JsonProcessingException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/landmarks/{landmarkId}/ratings")
  public ResponseEntity<String> updateOrCreateRating(@PathVariable String landmarkId, @RequestBody String json) {
    try {
      JsonNode node = mapper.readTree(json);
      ratingService.updateOrCreateRating(landmarkId, node.get("userId").asText(), node.get("value").asInt());
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}