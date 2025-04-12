package io.github.joeweh.citystory.services;

import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import io.github.joeweh.citystory.entities.Landmark;
import io.github.joeweh.citystory.entities.LatLong;

@Repository
public class RatingService {
  private final DataSource dataSource;

  public RatingService(final DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public List<Landmark> getLandmarks() {

    try (PreparedStatement stmt = dataSource.getConnection().prepareStatement("SELECT * FROM landmarks")) {
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

  // TODO update rating if it exists, otherwise create it
  public void updateOrCreateRating(String landmarkId, String userId, double value) {
    try (PreparedStatement stmt = dataSource.getConnection().prepareStatement("INSERT INTO ratings VALUES()")) {
      boolean a = stmt.execute();
      System.out.println(a);
    }

    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}