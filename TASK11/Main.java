import java.time.Duration;
import java.time.Instant;
import java.util.*;

/** Runnable demonstration of a small, validated smart-parking system. */
public class Main {
    enum UserType { STUDENT, STAFF, VISITOR }

    interface PricingRule { double fee(Vehicle v, long minutes); }
    interface Chargeable { double chargingFee(int kWh); }

    static abstract class Vehicle {
        private final String regNo;
        Vehicle(String regNo) {
            if (regNo == null || regNo.isBlank()) throw new IllegalArgumentException("Registration required");
            this.regNo = regNo;
        }
        String regNo() { return regNo; }
        abstract double rate();
        double fee(long minutes) {
            if (minutes < 0) throw new IllegalArgumentException("Negative duration");
            long hours = (minutes + 59) / 60;
            return hours * rate();
        }
        @Override public boolean equals(Object o) { return o instanceof Vehicle v && regNo.equals(v.regNo); }
        @Override public int hashCode() { return regNo.hashCode(); }
    }
    static class TwoWheeler extends Vehicle { TwoWheeler(String r) { super(r); } double rate() { return 10; } }
    static class Car extends Vehicle { Car(String r) { super(r); } double rate() { return 30; } }
    static class ElectricCar extends Car implements Chargeable {
        ElectricCar(String r) { super(r); }
        public double chargingFee(int kWh) {
            if (kWh < 0) throw new IllegalArgumentException("Negative kWh");
            return 12.0 * kWh;
        }
    }
    static class ParkingTicket {
        final String id;
        final Vehicle vehicle;
        final int floor, slot;
        final Instant entry;
        ParkingTicket(String id, Vehicle v, int f, int s, Instant entry) {
            this.id=id; vehicle=v; floor=f; slot=s; this.entry=entry;
        }
    }
    static class Facility {
        final Vehicle[][] slots;
        Facility(int[] capacities) {
            slots = new Vehicle[capacities.length][];
            for (int i=0; i<capacities.length; i++) {
                if (capacities[i] < 0) throw new IllegalArgumentException("Negative capacity");
                slots[i] = new Vehicle[capacities[i]];
            }
        }
        int[] allocate(Vehicle v) {
            for (int f=0; f<slots.length; f++)
                for (int s=0; s<slots[f].length; s++)
                    if (slots[f][s] == null) { slots[f][s]=v; return new int[]{f,s}; }
            return null;
        }
        void release(int f, int s) {
            if (f<0 || f>=slots.length || s<0 || s>=slots[f].length || slots[f][s]==null)
                throw new IllegalArgumentException("Invalid or empty slot");
            slots[f][s]=null;
        }
    }
    static class Service {
        final Facility facility;
        final Map<String, ParkingTicket> active = new HashMap<>();
        long nextTicket = 1;
        Service(Facility f) { facility=f; }
        ParkingTicket enter(Vehicle v, UserType user, boolean validMonthlyPass, Instant now) {
            if (active.containsKey(v.regNo())) throw new IllegalArgumentException("Duplicate active registration");
            int[] pos=facility.allocate(v);
            if (pos==null) throw new IllegalStateException("Parking full");
            ParkingTicket t=new ParkingTicket("P-"+(nextTicket++), v, pos[0], pos[1], now);
            active.put(v.regNo(), t);
            return t;
        }
        double exit(String regNo, Instant now, UserType user, boolean validMonthlyPass) {
            ParkingTicket t=active.remove(regNo);
            if (t==null) throw new IllegalArgumentException("No active ticket");
            facility.release(t.floor,t.slot);
            long minutes=Duration.between(t.entry,now).toMinutes();
            if (minutes<0) throw new IllegalArgumentException("Exit precedes entry");
            if ((user==UserType.STUDENT || user==UserType.STAFF) && validMonthlyPass) return 0;
            double fee=t.vehicle.fee(minutes);
            if (t.vehicle instanceof Chargeable c) fee += c.chargingFee(0);
            return fee;
        }
    }

    public static void main(String[] args) {
        Facility f=new Facility(new int[]{2,1});
        Service s=new Service(f);
        Instant start=Instant.parse("2026-01-01T09:00:00Z");
        ParkingTicket a=s.enter(new TwoWheeler("TN01A1"), UserType.VISITOR, false, start);
        ParkingTicket b=s.enter(new Car("TN02B2"), UserType.VISITOR, false, start);
        ParkingTicket c=s.enter(new ElectricCar("TN03C3"), UserType.VISITOR, false, start);
        System.out.println("Tickets: "+a.id+" "+b.id+" "+c.id);
        System.out.println("Last slot allocated: floor="+c.floor+", slot="+c.slot);
        System.out.println("TwoWheeler fee (61 min): "+new TwoWheeler("TN04D4").fee(61));
        System.out.println("Car fee (exactly 60 min): "+new Car("TN05E5").fee(60));
        System.out.println("Monthly pass fee: "+s.exit("TN01A1", start.plusSeconds(3600), UserType.STUDENT, true));
        System.out.println("Car fee: "+s.exit("TN02B2", start.plusSeconds(3600), UserType.VISITOR, false));
        System.out.println("EV fee (charging excluded unless kWh supplied): "+s.exit("TN03C3", start.plusSeconds(3600), UserType.VISITOR, false));
        try { s.exit("UNKNOWN", start.plusSeconds(3600), UserType.VISITOR, false); }
        catch (IllegalArgumentException ex) { System.out.println("Invalid registration handled: "+ex.getMessage()); }
    }
}
