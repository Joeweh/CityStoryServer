package io.github.joeweh.citystory.services;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.joeweh.citystory.entities.Landmark;

@Repository
public class RatingService {
  private final Connection connection;

  public RatingService(final Connection connection) {
    this.connection = connection;
  }

  public Landmark getLandmarkById(String uid) throws IllegalArgumentException {
    try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM landmarks WHERE uid=?")) {
      stmt.setString(1, uid);

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          throw new IllegalArgumentException(String.format("No place matching uid: %s", uid));
        }

        String name = rs.getString("name");

        // TODO get description
        return new Landmark(name, "");
      }
    }

    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}