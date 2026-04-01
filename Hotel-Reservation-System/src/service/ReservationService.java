package service;

import db.DatabaseConnection;

import java.sql.*;

public class ReservationService {


    public void makeReservation(String customerName, String category, Date checkIn, Date checkOut) {
        String findRoom = "SELECT room_id, room_number, category FROM rooms WHERE category = ? AND is_available = TRUE LIMIT 1";
        String insertReservation = "INSERT INTO reservations (customer_name, room_id, check_in, check_out, payment_status) VALUES (?,?,?,?,?)";
        String updateRoom = "UPDATE rooms SET is_available = FALSE WHERE room_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement psFind = conn.prepareStatement(findRoom);
             PreparedStatement psInsert = conn.prepareStatement(insertReservation, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psUpdate = conn.prepareStatement(updateRoom)) {

            psFind.setString(1, category);
            ResultSet rs = psFind.executeQuery();

            if (!rs.next()) {
                System.out.println("No available rooms found in category: " + category);
                return;
            }

            int roomId = rs.getInt("room_id");
            String roomNumber = rs.getString("room_number");
            String roomCategory = rs.getString("category");


            psInsert.setString(1, customerName);
            psInsert.setInt(2, roomId);
            psInsert.setDate(3, checkIn);
            psInsert.setDate(4, checkOut);
            psInsert.setString(5, "Pending");
            psInsert.executeUpdate();

            int reservationId = -1;
            try (ResultSet generatedKeys = psInsert.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservationId = generatedKeys.getInt(1);
                }
            }


            psUpdate.setInt(1, roomId);
            psUpdate.executeUpdate();

            System.out.println("Reservation successful for " + customerName +
                    " (Reservation ID: " + reservationId +
                    ", Room Number: " + roomNumber +
                    ", Category: " + roomCategory + ")");

        } catch (SQLException e) {
            System.err.println("Error making reservation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cancelReservation(int reservationId) {
        String getReservation = "SELECT room_id, payment_status FROM reservations WHERE reservation_id = ?";
        String updateRoom = "UPDATE rooms SET is_available = TRUE WHERE room_id = ?";
        String cancelReservation = "UPDATE reservations SET payment_status = ? WHERE reservation_id = ?";
        String deleteReservation = "DELETE FROM reservations WHERE reservation_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement psGet = conn.prepareStatement(getReservation);
             PreparedStatement psUpdateRoom = conn.prepareStatement(updateRoom);
             PreparedStatement psCancel = conn.prepareStatement(cancelReservation);
             PreparedStatement psDelete = conn.prepareStatement(deleteReservation)) {


            psGet.setInt(1, reservationId);
            ResultSet rs = psGet.executeQuery();

            if (!rs.next()) {
                System.out.println("Reservation ID " + reservationId + " not found.");
                return;
            }

            int roomId = rs.getInt("room_id");
            String status = rs.getString("payment_status");


            if ("Cancelled".equalsIgnoreCase(status)) {
                System.out.println("This reservation is already cancelled!");
                return;
            }
            if (!"Paid".equalsIgnoreCase(status)) {
                System.out.println("Reservation " + reservationId + " cannot be cancelled. Payment status is: " + status);
                return;
            }


            psUpdateRoom.setInt(1, roomId);
            psUpdateRoom.executeUpdate();


            psCancel.setString(1, "Cancelled");
            psCancel.setInt(2, reservationId);
            psCancel.executeUpdate();


            psDelete.setInt(1, reservationId);
            psDelete.executeUpdate();

            System.out.println("Reservation " + reservationId + " cancelled and deleted successfully.");

        } catch (SQLException e) {
            System.err.println("Error cancelling reservation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void simulatePayment(int reservationId) throws InterruptedException {
        String updatePayment = "UPDATE reservations SET payment_status = ? WHERE reservation_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(updatePayment)) {

            ps.setString(1, "Paid");
            ps.setInt(2, reservationId);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Payment processed for Reservation " + reservationId);
                int i = 1;
                System.out.print("Processing .");
                while (i < 21) {
                    ++i;
                    System.out.print(".");
                    Thread.sleep(500);
                }
                System.out.println("\nPayment Paid Successfully");
            } else {
                System.out.println("Reservation ID not found.");
            }

        } catch (SQLException e) {
            System.err.println("Error processing payment: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
