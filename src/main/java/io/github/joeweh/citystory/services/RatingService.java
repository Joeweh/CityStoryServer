package io.github.joeweh.citystory.services;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import io.github.joeweh.citystory.entities.Landmark;
import io.github.joeweh.citystory.entities.LatLong;
import io.github.joeweh.citystory.entities.RatingDAO;

@Repository
public class RatingService {
  private final DataSource dataSource;

  // Constructor Injection
  public RatingService(final DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public List<Landmark> getLandmarks() throws SQLException {
    try (Connection con = dataSource.getConnection()) {
      try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM landmarks")) {
        try (ResultSet rs = stmt.executeQuery()) {

          List<Landmark> landmarks = new ArrayList<>();

          while (rs.next()) {
            String uid = rs.getString("uid");
            String name = rs.getString("name");
            String description = rs.getString("description");
            double latitude = rs.getDouble("latitude");
            double longitude = rs.getDouble("longitude");
            double averageRating = rs.getDouble("avg_rating");

            landmarks.add(new Landmark(uid, name, description, new LatLong(latitude, longitude), averageRating));
          }

          return landmarks;
        }
      }
    }
  }

  public boolean updateOrCreateRating(String landmarkId, String userId, int value) throws IllegalArgumentException, SQLException {
    try (Connection con = dataSource.getConnection()) {
      try (PreparedStatement stmt = con.prepareStatement("SELECT 1 FROM landmarks WHERE uid=?")) {
        stmt.setString(1, landmarkId);

        try (ResultSet rs = stmt.executeQuery()) {
          if (!rs.next()) {
            throw new IllegalArgumentException("Landmark does not exist");
          }
        }
      }

      try (PreparedStatement stmt = con.prepareStatement("SELECT 1 FROM ratings WHERE landmarkId=? AND userId=?")) {
        stmt.setString(1, landmarkId);
        stmt.setString(2, userId);

        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) {
            updateRating(con, landmarkId, userId, value);
            return false;
          }

          else {
            createRating(con, landmarkId, userId, value);
            return true;
          }
        }
      }
    }
  }

  private void createRating(Connection con, String landmarkId, String userId, int value) throws SQLException {
    try (PreparedStatement stmt = con.prepareStatement("INSERT INTO ratings VALUES(?, ?, ?, ?)")) {
      stmt.setString(1, UUID.randomUUID().toString());
      stmt.setString(2, landmarkId);
      stmt.setString(3, userId);
      stmt.setInt(4, value);

      boolean returnType = stmt.execute();

      System.out.println("RT: " + returnType);
    }
  }

  private void updateRating(Connection con, String landmarkId, String userId, int newValue) throws SQLException {
    try (PreparedStatement stmt = con.prepareStatement("UPDATE ratings SET value=? WHERE landmarkId=? AND userId=?")) {
      stmt.setInt(1, newValue);
      stmt.setString(2, landmarkId);
      stmt.setString(3, userId);

      int updateCount = stmt.executeUpdate();

      System.out.println("Update Count: " + updateCount);
    }
  }

  public List<RatingDAO> getUserRatings(String userId) throws SQLException {
    try (Connection con = dataSource.getConnection()) {
      try (PreparedStatement stmt = con.prepareStatement("SELECT (ratings.landmarkId, ratings.value) FROM ratings WHERE userId=?")) {
        stmt.setString(1, userId);

        try (ResultSet rs = stmt.executeQuery()) {
          List<RatingDAO> ratings = new ArrayList<>();

          while (rs.next()) {
            String landmarkId = rs.getString("landmarkId");
            int value = rs.getInt("value");

            ratings.add(new RatingDAO(landmarkId, value));
          }

          return ratings;
        }
      }
    }
  }
}