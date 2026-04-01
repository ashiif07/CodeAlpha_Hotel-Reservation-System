# 🏨 Hotel Reservation System

A simple **console-based Hotel Reservation System** built using **Java, JDBC, and MySQL**.
This project allows users to search rooms, book reservations, make payments, and cancel bookings.

---

## 🚀 Features

* Search available rooms by category (Standard / Deluxe / Suite)
* Book a room with customer details
* Simulate payment process
* Cancel reservation (only after payment)
* Database connectivity using MySQL

---

## 🛠️ Technologies Used

* Java (Core)
* JDBC
* MySQL
* OOP Concepts

---

## ⚙️ Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/hotel-reservation-system.git
   ```

2. Create database:

   ```sql
   CREATE DATABASE hotel_reservation;
   ```

3. Create tables:

   ```sql
   CREATE TABLE rooms (
       room_id INT PRIMARY KEY AUTO_INCREMENT,
       room_number VARCHAR(10),
       category VARCHAR(20),
       price DOUBLE,
       is_available BOOLEAN
   );

   CREATE TABLE reservations (
       reservation_id INT PRIMARY KEY AUTO_INCREMENT,
       customer_name VARCHAR(100),
       room_id INT,
       check_in DATE,
       check_out DATE,
       payment_status VARCHAR(20)
   );
   ```
---

4. Update DB credentials in `DatabaseConnection.java`

5. Add MySQL Connector JAR

6. Run `Main.java`

---

## 📋 Menu

```
1. Search available rooms
2. Make Reservation
3. Simulate payment
4. Cancel Reservation
5. Exit
```
---
📌 Future Improvements
GUI using JavaFX / Swing
User authentication system
Online payment integration
Booking history tracking
Admin panel

---
## 📂 Project Structure

```
hotel-reservation-system/
│
├── db/
│   └── DatabaseConnection.java     # Handles database connection
│
├── model/
│   └── Room.java                   # Room entity (room number, price)
│
├── service/
│   ├── RoomService.java            # Handles room-related operations
│   └── ReservationService.java     # Handles booking, payment, cancellation
│
├── Main.java                       # Entry point (User Interface)
│
└── README.md                       # Project documentation
```
---

## 📌 Notes

* Rooms become unavailable after booking
* Payment is required before cancellation
* Canceling a reservation frees the room again

---

## 👨‍💻 Author

Your Name
