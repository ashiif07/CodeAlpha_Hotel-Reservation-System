
import service.RoomService;
import service.ReservationService;
import model.Room;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        RoomService roomService = new RoomService();
        ReservationService reservationService = new ReservationService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== Hotel Reservation System =====");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Search available rooms");
            System.out.println("2. Make Reservation");
            System.out.println("3. Simulate payment");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. Exit");

            System.out.print("\nEnter your Choice:\t");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Room Category (Deluxe/Suite/Standard): ");
                    String category = scanner.next();
                    List<Room> availableRooms = roomService.getAvailableRooms(category);
                    if (!availableRooms.isEmpty()) {
                        System.out.println("Available Rooms:");
                        int n = 0;
                        for (Room room : availableRooms) {
                            System.out.println(room);
                            n++;
                        }
                        System.out.println("   Total " + n + " Available Room in '" + category.toUpperCase() + "' Category");
                    }    break;
                case 2:
                    System.out.print("Enter Customer Name: ");
                    String customerName = scanner.nextLine();
                    System.out.print("Enter Room Category(Deluxe/Suite/Standard): ");
                    String roomCategory = scanner.next();
                    scanner.nextLine();
                    System.out.print("Enter check-in date (YYYY-MM-DD): ");
                    Date checkIn = Date.valueOf(scanner.nextLine());
                    System.out.print("Enter check-out date (YYYY-MM-DD): ");
                    Date checkOut = Date.valueOf(scanner.nextLine());

                    reservationService.makeReservation(customerName, roomCategory, checkIn, checkOut);
                    break;
                case 4:
                    System.out.print("Enter reservation ID to Cancel: ");
                    int reservationId = scanner.nextInt();
                    reservationService.cancelReservation(reservationId);
                    break;
                case 3:
                    System.out.print("Enter reservation ID to pay: ");
                    int payId = scanner.nextInt();
                    reservationService.simulatePayment(payId);
                    break;
                case 5:
                    System.out.print("Existing from Hotel Reservation System ");
                    int i = 1;
                    while (i < 6) {
                        ++i;
                        System.out.print(".");
                        Thread.sleep(500);
                    }
                    System.out.println();
                    System.out.println("\n--- Thank You for Using Hotel Reservation System ---\n");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
