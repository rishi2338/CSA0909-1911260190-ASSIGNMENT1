# Task 4 — Classes, objects and constructors

```java
class ParkingTicket {
    private final String ticketId;
    private final String registration;
    private final int floor;
    private final int slot;

    ParkingTicket() { this("T-UNASSIGNED", "UNKNOWN", 0, 0); }
    ParkingTicket(String ticketId, String registration) { this(ticketId, registration, 0, 0); }
    ParkingTicket(String ticketId, String registration, int floor, int slot) {
        if (ticketId == null || ticketId.isBlank() || registration == null || registration.isBlank())
            throw new IllegalArgumentException("Ticket and registration are required");
        if (floor < 0 || slot < 0) throw new IllegalArgumentException("Invalid position");
        this.ticketId = ticketId;
        this.registration = registration;
        this.floor = floor;
        this.slot = slot;
    }
    ParkingTicket(ParkingTicket other) {
        this(other.ticketId, other.registration, other.floor, other.slot);
    }
    public String getTicketId() { return ticketId; }
    public String getRegistration() { return registration; }
    public int getFloor() { return floor; }
    public int getSlot() { return slot; }
}
```

The overloaded constructors support default, partial and full initialisation. `this(...)` chains constructors and avoids repeated validation. A copy constructor is useful for making a snapshot, but a duplicate ticket should not be issued with the same unique ID; a copied ticket must receive a new ID if it represents a new ticket. Local reference variables are typically on the stack; ticket objects and their fields are allocated on the heap. Creating n tickets takes O(n) total time and O(n) total space. Setter-based construction has similar asymptotic cost but can expose temporarily invalid objects; a static factory can validate and return only valid instances.
