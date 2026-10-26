import java.util.*;

/** Design challenge: extensible pricing rules and uneven floor capacities. */
public class Main {
    interface Pass { boolean valid(); }
    static class MonthlyPass implements Pass {
        public boolean valid() { return true; }
    }
    static class OneDayPass implements Pass {
        private final java.time.LocalDate date;
        OneDayPass(java.time.LocalDate date) { this.date=date; }
        public boolean valid() { return date.equals(java.time.LocalDate.now()); }
    }
    interface PricingRule { double fee(Vehicle v, int minutes, java.time.LocalTime entry); }
    static abstract class Vehicle {
        final String regNo;
        Vehicle(String r) { if (r==null || r.isBlank()) throw new IllegalArgumentException(); regNo=r; }
        abstract double hourlyRate();
    }
    static class TwoWheeler extends Vehicle { TwoWheeler(String r){super(r);} double hourlyRate(){return 10;} }
    static class Car extends Vehicle { Car(String r){super(r);} double hourlyRate(){return 30;} }
    static class ElectricCar extends Car { ElectricCar(String r){super(r);} }
    static class EBike extends Vehicle { EBike(String r){super(r);} double hourlyRate(){return 0;} }
    static class DeliveryVan extends Vehicle { DeliveryVan(String r){super(r);} double hourlyRate(){return 30;} }

    static class StandardPricing implements PricingRule {
        public double fee(Vehicle v, int minutes, java.time.LocalTime entry) {
            if (minutes < 0) throw new IllegalArgumentException("Negative duration");
            if (v instanceof EBike) return 15; // flat fee
            int hours=(minutes+59)/60;
            double rate=v.hourlyRate();
            if (v instanceof DeliveryVan && !entry.isBefore(java.time.LocalTime.of(9,0))
                    && entry.isBefore(java.time.LocalTime.of(17,0))) rate*=2;
            return hours*rate;
        }
    }
    static class Facility {
        final Vehicle[][] slots;
        Facility(int[] capacities) {
            slots=new Vehicle[capacities.length][];
            for(int i=0;i<capacities.length;i++) slots[i]=new Vehicle[capacities[i]];
        }
        int[] allocate(Vehicle v) {
            for(int f=0;f<slots.length;f++) for(int s=0;s<slots[f].length;s++)
                if(slots[f][s]==null){slots[f][s]=v;return new int[]{f,s};}
            return null;
        }
    }
    static double finalFee(Vehicle v, int minutes, java.time.LocalTime entry,
                           PricingRule pricing, Pass pass, boolean studentOrStaff) {
        if (studentOrStaff && pass instanceof MonthlyPass && pass.valid()) return 0;
        if (pass instanceof OneDayPass && pass.valid()) return 0;
        return pricing.fee(v,minutes,entry);
    }
    public static void main(String[] args) {
        Facility facility=new Facility(new int[]{20,35,40,45,50});
        PricingRule pricing=new StandardPricing();
        System.out.println("E-bike flat fee: Rs "+finalFee(new EBike("EB01"),120,
                java.time.LocalTime.of(10,0),pricing,null,false));
        System.out.println("Van at 10 AM for 2 hours: Rs "+finalFee(new DeliveryVan("DV01"),120,
                java.time.LocalTime.of(10,0),pricing,null,false));
        System.out.println("Visitor one-day pass: Rs "+finalFee(new Car("CA01"),180,
                java.time.LocalTime.of(10,0),pricing,new OneDayPass(java.time.LocalDate.now()),false));
        System.out.println("Uneven floors: "+facility.slots.length+" floors; capacities "+
                Arrays.toString(Arrays.stream(facility.slots).mapToInt(a->a.length).toArray()));
    }
}
