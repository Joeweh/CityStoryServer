package io.github.joeweh.citystory.services;

import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import io.github.joeweh.citystory.entities.Landmark;
import io.github.joeweh.citystory.entities.LatLong;

@Repository
public class RatingService {
  private final DataSource dataSource;

  // Constructor Injection
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

  public void updateOrCreateRating(String landmarkId, String userId, int value) {
    try (PreparedStatement stmt = dataSource.getConnection().prepareStatement("SELECT 1 FROM ratings WHERE landmarkId=? AND userId=?")) {
      stmt.setString(1, landmarkId);
      stmt.setString(2, userId);

      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        updateRating(landmarkId, userId, value);
      }

      else {
        createRating(landmarkId, userId, value);
      }

      rs.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void createRating(String landmarkId, String userId, int value) {
    try (PreparedStatement stmt = dataSource.getConnection().prepareStatement("INSERT INTO ratings VALUES(?, ?, ?, ?)")) {
      stmt.setString(1, UUID.randomUUID().toString());
      stmt.setString(2, landmarkId);
      stmt.setString(3, userId);
      stmt.setInt(4, value);

      boolean returnType = stmt.execute();

      System.out.println("RT: " + returnType);
    }

    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void updateRating(String landmarkId, String userId, int newValue) {
    try (PreparedStatement stmt = dataSource.getConnection().prepareStatement("UPDATE ratings SET value=? WHERE landmarkId=? AND userId=?")) {
      stmt.setInt(1, newValue);
      stmt.setString(2, landmarkId);
      stmt.setString(3, userId);

      int updateCount = stmt.executeUpdate();

      System.out.println("Update Count: " + updateCount);
    }

    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}