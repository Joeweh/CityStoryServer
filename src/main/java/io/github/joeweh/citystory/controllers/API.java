package io.github.joeweh.citystory.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.joeweh.citystory.entities.Landmark;
import io.github.joeweh.citystory.entities.Rating;
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

  @GetMapping("/landmarks/{id}")
  public ResponseEntity<String> getLandmarkById(@PathVariable String id) {
    try {
      Landmark landmark = ratingService.getLandmarkById(id);
      return new ResponseEntity<>(mapper.writeValueAsString(landmark), HttpStatus.OK);
    }

    catch (IllegalArgumentException exception) {
      return new ResponseEntity<>("Invalid Landmark Id", HttpStatus.BAD_REQUEST);
    }

    catch (JsonProcessingException exception) {
      return new ResponseEntity<>("Couldn't convert landmark to json", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // TODO implement
  @PostMapping("/landmarks/{landmarkId}/ratings")
  public ResponseEntity<String> addRatingToLandmark(@PathVariable String landmarkId, @RequestBody Rating rating) {
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  // TODO implement
  @PutMapping("/landmarks/{landmarkId}/ratings/{authorId}")
  public ResponseEntity<String> addRatingToLandmark(@PathVariable String landmarkId, @PathVariable String authorId, @RequestBody Rating rating) {
    return new ResponseEntity<>(HttpStatus.OK);
  }
}