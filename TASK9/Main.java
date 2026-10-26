public class Main {
    interface Chargeable {
        double chargingFee(int kWh);
        default String chargingStatus() { return "Charging supported"; }
        static boolean validEnergy(int kWh) { return kWh>=0; }
    }
    static abstract class Vehicle {
        final String regNo;
        Vehicle(String regNo) { this.regNo=regNo; }
        abstract double parkingFee(int minutes);
    }
    static class ElectricCar extends Vehicle implements Chargeable {
        ElectricCar(String r) { super(r); }
        double parkingFee(int minutes) { return ((minutes+59)/60)*30.0; }
        public double chargingFee(int kWh) {
            if(!Chargeable.validEnergy(kWh)) throw new IllegalArgumentException();
            return kWh*12.0;
        }
    }
    public static void main(String[] args) {
        ElectricCar ev=new ElectricCar("TN03C3");
        System.out.println("Parking="+ev.parkingFee(130));
        System.out.println("Charging="+ev.chargingFee(10));
        System.out.println(ev.chargingStatus());
    }
}
