import java.util.*;
public class Main {
    static class Vehicle {
        String regNo, type;
        long entryMinute;
        Vehicle(String regNo, String type, long entryMinute) {
            this.regNo=regNo; this.type=type; this.entryMinute=entryMinute;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Registration number: "); String reg=sc.nextLine();
        System.out.print("Vehicle type (two-wheeler/car/ev): "); String type=sc.nextLine();
        System.out.print("Entry minute: "); long time=sc.nextLong();
        Vehicle v=new Vehicle(reg,type,time);
        System.out.println("Vehicle created: "+v.regNo+", type="+v.type+", entry="+v.entryMinute);
        System.out.println("Rules: unique active registration; one vehicle per slot; valid time; ticket IDs are never reused.");
        sc.close();
    }
}
