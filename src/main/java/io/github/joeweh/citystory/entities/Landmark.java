package io.github.joeweh.citystory.entities;

public record Landmark(String uid, String name, String description, LatLong location, double averageRating) {}