package parking;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Demonstration of an extensible campus parking design.
 * Compile from this folder with: javac -d out src/parking/ParkingSystemDemo.java
 * Run with: java -cp out parking.ParkingSystemDemo
 */
public class ParkingSystemDemo {
    public static void main(String[] args) {
        int[] slotsPerFloor = {3, 2, 4, 3, 2}; // jagged layout: floors may differ
        int[] chargingSlotsPerFloor = {1, 0, 1, 0, 1};
        ParkingFacility facility = new ParkingFacility(slotsPerFloor, chargingSlotsPerFloor);
        LocalDateTime entry = LocalDateTime.of(2026, 10, 19, 8, 30);

        Vehicle bike = new TwoWheeler("TN01AA1001");
        Vehicle car = new Car("TN01BB2002");
        Vehicle ev = new ElectricCar("TN01CC3003");
        Vehicle eBike = new EBike("TN01DD4004");
        Vehicle van = new DeliveryVan("TN01EE5005");

        ParkingPass monthly = new MonthlyPass("STU-101", LocalDate.of(2026, 10, 1),
                LocalDate.of(2026, 10, 31));
        ParkingPass dayPass = new OneDayPass("VIS-201", LocalDate.of(2026, 10, 19));

        ParkingTicket t1 = facility.park(bike, entry, null);
        ParkingTicket t2 = facility.park(car, entry, null);
        ParkingTicket t3 = facility.park(ev, entry, null);
        ParkingTicket t4 = facility.park(eBike, entry, null);
        ParkingTicket t5 = facility.park(van, entry, dayPass);

        System.out.println("Allocated tickets: " + facility.activeTicketCount());
        printExit(facility, t1, entry.plusMinutes(61), 0);
        printExit(facility, t2, entry.plusMinutes(120), 0);
        printExit(facility, t3, entry.plusMinutes(130), 10);
        printExit(facility, t4, entry.plusMinutes(45), 0);
        printExit(facility, t5, entry.plusHours(2), 0);

        ParkingTicket passTicket = facility.park(new Car("TN01FF6006"), entry, monthly);
        System.out.println("Monthly-pass ticket fee: Rs " +
                facility.exit(passTicket.getVehicle().getRegNo(), entry.plusHours(3), 0));
        System.out.println("Active tickets after exits: " + facility.activeTicketCount());
        System.out.println("Total collected: Rs " + facility.totalCollected());
        System.out.println("Jagged floor count: " + facility.floorCount());
    }

    private static void printExit(ParkingFacility facility, ParkingTicket ticket,
                                  LocalDateTime exit, int chargingKWh) {
        double fee = facility.exit(ticket.getVehicle().getRegNo(), exit, chargingKWh);
        System.out.println(ticket.getVehicle().getClass().getSimpleName() + " "
                + ticket.getVehicle().getRegNo() + " ticket=" + ticket.getTicketNo()
                + " fee=Rs " + fee);
    }
}

interface Chargeable {
    double chargingFee(int kWh);
}

interface ParkingPass {
    boolean isValidAt(LocalDateTime time);
    String passId();
}

final class MonthlyPass implements ParkingPass {
    private final String id;
    private final LocalDate start;
    private final LocalDate end;

    MonthlyPass(String id, LocalDate start, LocalDate end) {
        this.id = Objects.requireNonNull(id);
        if (end.isBefore(start)) throw new IllegalArgumentException("End date before start date");
        this.start = start;
        this.end = end;
    }

    public boolean isValidAt(LocalDateTime time) {
        LocalDate date = time.toLocalDate();
        return !date.isBefore(start) && !date.isAfter(end);
    }

    public String passId() { return id; }
}

final class OneDayPass implements ParkingPass {
    private final String id;
    private final LocalDate date;

    OneDayPass(String id, LocalDate date) {
        this.id = Objects.requireNonNull(id);
        this.date = Objects.requireNonNull(date);
    }

    public boolean isValidAt(LocalDateTime time) { return date.equals(time.toLocalDate()); }
    public String passId() { return id; }
}

abstract class Vehicle {
    private final String regNo;

    protected Vehicle(String regNo) {
        if (regNo == null || regNo.isBlank()) throw new IllegalArgumentException("Registration is required");
        this.regNo = regNo;
    }

    public final String getRegNo() { return regNo; }
    public abstract double parkingFee(long minutes, LocalDateTime entry, LocalDateTime exit);

    protected static long startedHours(long minutes) {
        if (minutes < 0) throw new IllegalArgumentException("Duration cannot be negative");
        return (minutes + 59) / 60;
    }

    @Override public final boolean equals(Object other) {
        return other instanceof Vehicle && regNo.equals(((Vehicle) other).regNo);
    }
    @Override public final int hashCode() { return regNo.hashCode(); }
    @Override public String toString() { return getClass().getSimpleName() + "[" + regNo + "]"; }
}

final class TwoWheeler extends Vehicle {
    TwoWheeler(String regNo) { super(regNo); }
    @Override public double parkingFee(long minutes, LocalDateTime entry, LocalDateTime exit) {
        return startedHours(minutes) * 10.0;
    }
}

class Car extends Vehicle {
    Car(String regNo) { super(regNo); }
    @Override public double parkingFee(long minutes, LocalDateTime entry, LocalDateTime exit) {
        return startedHours(minutes) * 30.0;
    }
}

final class ElectricCar extends Car implements Chargeable {
    ElectricCar(String regNo) { super(regNo); }
    @Override public double chargingFee(int kWh) {
        if (kWh < 0) throw new IllegalArgumentException("kWh cannot be negative");
        return kWh * 12.0;
    }
}

final class EBike extends Vehicle {
    private static final double FLAT_FEE = 20.0;
    EBike(String regNo) { super(regNo); }
    @Override public double parkingFee(long minutes, LocalDateTime entry, LocalDateTime exit) {
        if (minutes < 0) throw new IllegalArgumentException("Duration cannot be negative");
        return minutes == 0 ? 0.0 : FLAT_FEE;
    }
}

final class DeliveryVan extends Vehicle {
    private static final double CAR_RATE = 30.0;
    private static final LocalTime DAY_START = LocalTime.of(9, 0);
    private static final LocalTime DAY_END = LocalTime.of(17, 0);
    DeliveryVan(String regNo) { super(regNo); }

    @Override public double parkingFee(long minutes, LocalDateTime entry, LocalDateTime exit) {
        if (minutes < 0) throw new IllegalArgumentException("Duration cannot be negative");
        long hours = startedHours(minutes);
        double fee = 0;
        // Documented assignment assumption: each billed hour is charged at the
        // daytime rate if that billed hour starts from 09:00 through 16:59.
        for (long i = 0; i < hours; i++) {
            LocalDateTime billedHourStart = entry.plusHours(i);
            LocalTime time = billedHourStart.toLocalTime();
            boolean daytime = !time.isBefore(DAY_START) && time.isBefore(DAY_END);
            fee += daytime ? 2 * CAR_RATE : CAR_RATE;
        }
        return fee;
    }
}

final class ParkingSlot {
    private final int floor;
    private final int number;
    private final boolean charging;
    private Vehicle vehicle;
    private ParkingTicket ticket;

    ParkingSlot(int floor, int number, boolean charging) {
        this.floor = floor; this.number = number; this.charging = charging;
    }
    boolean isFree() { return vehicle == null; }
    boolean isCharging() { return charging; }
    int floor() { return floor; }
    int number() { return number; }
    void occupy(Vehicle vehicle, ParkingTicket ticket) {
        if (!isFree()) throw new IllegalStateException("Slot already occupied");
        this.vehicle = vehicle; this.ticket = ticket;
    }
    void release() { vehicle = null; ticket = null; }
}

final class ParkingTicket {
    private static long nextTicketNo = 1;
    private final long ticketNo;
    private final Vehicle vehicle;
    private final int floor;
    private final int slot;
    private final LocalDateTime entryTime;
    private final ParkingPass pass;

    ParkingTicket(Vehicle vehicle, int floor, int slot, LocalDateTime entryTime, ParkingPass pass) {
        this.ticketNo = nextTicketNo++;
        this.vehicle = Objects.requireNonNull(vehicle);
        this.floor = floor; this.slot = slot;
        this.entryTime = Objects.requireNonNull(entryTime);
        this.pass = pass;
    }
    long getTicketNo() { return ticketNo; }
    Vehicle getVehicle() { return vehicle; }
    int getFloor() { return floor; }
    int getSlot() { return slot; }
    LocalDateTime getEntryTime() { return entryTime; }
    ParkingPass getPass() { return pass; }
}

final class ParkingFacility {
    private final ParkingSlot[][] floors;
    private final Map<String, ParkingTicket> activeTickets = new HashMap<>();
    private double totalCollected;

    ParkingFacility(int[] slotsPerFloor, int[] chargingSlotsPerFloor) {
        if (slotsPerFloor == null || chargingSlotsPerFloor == null
                || slotsPerFloor.length != chargingSlotsPerFloor.length) {
            throw new IllegalArgumentException("Floor arrays must have the same length");
        }
        floors = new ParkingSlot[slotsPerFloor.length][];
        for (int f = 0; f < slotsPerFloor.length; f++) {
            if (slotsPerFloor[f] < 0 || chargingSlotsPerFloor[f] < 0
                    || chargingSlotsPerFloor[f] > slotsPerFloor[f]) {
                throw new IllegalArgumentException("Invalid slot count on floor " + f);
            }
            floors[f] = new ParkingSlot[slotsPerFloor[f]];
            for (int s = 0; s < slotsPerFloor[f]; s++) {
                floors[f][s] = new ParkingSlot(f, s, s < chargingSlotsPerFloor[f]);
            }
        }
    }

    ParkingTicket park(Vehicle vehicle, LocalDateTime entry, ParkingPass pass) {
        Objects.requireNonNull(vehicle); Objects.requireNonNull(entry);
        if (activeTickets.containsKey(vehicle.getRegNo())) {
            throw new IllegalArgumentException("Registration is already parked");
        }
        ParkingSlot chosen = findSlot(vehicle instanceof Chargeable);
        if (chosen == null) throw new IllegalStateException("No suitable slot is available");

        ParkingTicket ticket = new ParkingTicket(vehicle, chosen.floor(), chosen.number(), entry, pass);
        chosen.occupy(vehicle, ticket);
        activeTickets.put(vehicle.getRegNo(), ticket);
        return ticket;
    }

    private ParkingSlot findSlot(boolean preferCharging) {
        if (preferCharging) {
            for (ParkingSlot[] floor : floors)
                for (ParkingSlot slot : floor)
                    if (slot.isFree() && slot.isCharging()) return slot;
        }
        for (ParkingSlot[] floor : floors)
            for (ParkingSlot slot : floor)
                if (slot.isFree() && !slot.isCharging()) return slot;
        for (ParkingSlot[] floor : floors)
            for (ParkingSlot slot : floor)
                if (slot.isFree()) return slot;
        return null;
    }

    double exit(String regNo, LocalDateTime exit, int chargingKWh) {
        ParkingTicket ticket = activeTickets.get(regNo);
        if (ticket == null) throw new IllegalArgumentException("No active ticket for " + regNo);
        if (exit.isBefore(ticket.getEntryTime())) throw new IllegalArgumentException("Exit precedes entry");
        long minutes = Duration.between(ticket.getEntryTime(), exit).toMinutes();
        double fee = 0.0;
        ParkingPass pass = ticket.getPass();
        if (pass == null || !pass.isValidAt(exit)) {
            fee = ticket.getVehicle().parkingFee(minutes, ticket.getEntryTime(), exit);
            if (ticket.getVehicle() instanceof Chargeable c) fee += c.chargingFee(chargingKWh);
        }
        floors[ticket.getFloor()][ticket.getSlot()].release();
        activeTickets.remove(regNo);
        totalCollected += fee;
        return fee;
    }

    int activeTicketCount() { return activeTickets.size(); }
    int floorCount() { return floors.length; }
    double totalCollected() { return totalCollected; }
}
