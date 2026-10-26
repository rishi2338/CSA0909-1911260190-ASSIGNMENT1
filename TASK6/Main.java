public class Main {
    static abstract class Vehicle {
        protected final String regNo;
        Vehicle(String regNo) { this.regNo=regNo; System.out.println("Vehicle constructor"); }
        abstract double rate();
    }
    static class Car extends Vehicle {
        Car(String r) { super(r); System.out.println("Car constructor"); }
        double rate() { return 30; }
    }
    static class ElectricCar extends Car {
        ElectricCar(String r) { super(r); System.out.println("ElectricCar constructor"); }
    }
    public static void main(String[] args) {
        ElectricCar ev=new ElectricCar("TN03C3");
        System.out.println(ev.regNo+" rate="+ev.rate());
    }
}
