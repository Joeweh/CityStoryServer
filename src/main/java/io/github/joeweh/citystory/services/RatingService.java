package io.github.joeweh.citystory.services;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.github.joeweh.citystory.entities.Landmark;
import io.github.joeweh.citystory.entities.LatLong;

@Repository
public class RatingService {
  private final Connection connection;

  public RatingService(final Connection connection) {
    this.connection = connection;
  }

  public List<Landmark> getLandmarks() {
    try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM landmarks")) {
      try (ResultSet rs = stmt.executeQuery()) {

        List<Landmark> landmarks = new ArrayList<>();

        while (rs.next()) {
          String uid = rs.getString("uid");
          String name = rs.getString("name");
          String description = rs.getString("description");
          double latitude = rs.getDouble("latitude");
          double longitude = rs.getDouble("longitude");

          landmarks.add(new Landmark(uid, name, description, new LatLong(latitude, longitude)));
        }

        return landmarks;
      }
    }

    // TODO handle appropriately
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Landmark getLandmarkById(String uid) throws IllegalArgumentException {
    try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM landmarks WHERE uid=?")) {
      stmt.setString(1, uid);

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          throw new IllegalArgumentException(String.format("No place matching uid: %s", uid));
        }

        String name = rs.getString("name");
        String description = rs.getString("description");
        double latitude = rs.getDouble("latitude");
        double longitude = rs.getDouble("longitude");

        return new Landmark(uid, name, description, new LatLong(latitude, longitude));
      }
    }

    // TODO handle appropriately
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}