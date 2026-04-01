package model;

public class Room {
    private String roomNumber;
    private double price;

    public Room(String roomNumber, double price) {
        this.roomNumber = roomNumber;
        this.price = price;
    }

    @Override
    public String toString() {
        return "   Room {Room Number = " + roomNumber + ", Room Price = " + price + '}';
    }
}
