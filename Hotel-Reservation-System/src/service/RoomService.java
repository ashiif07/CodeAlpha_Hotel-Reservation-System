package service;

import db.DatabaseConnection;
import model.Room;

import java.sql.*;
import java.util.*;

public class RoomService {
    public List<Room> getAvailableRooms(String category) {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT  room_number, price " +
                "FROM rooms WHERE category = ? AND is_available = TRUE";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, category);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Room room = new Room(
                            rs.getString("room_number"),
                            rs.getDouble("price")
                    );
                    rooms.add(room);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching available rooms: " + e.getMessage());
            e.printStackTrace();
        }
        if (rooms.isEmpty()) {
            System.out.println("No available rooms found for category: " + category);
        }
        return rooms;
    }
}
