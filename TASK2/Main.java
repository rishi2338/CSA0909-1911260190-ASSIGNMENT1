import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.print("Number of floors: "); int floors=sc.nextInt();
        int[][] slots=new int[floors][];
        for(int f=0;f<floors;f++) {
            System.out.print("Slots on floor "+f+": "); int n=sc.nextInt();
            if(n<0) throw new IllegalArgumentException("Capacity cannot be negative");
            slots[f]=new int[n];
        }
        System.out.print("Parking duration in minutes: "); long minutes=sc.nextLong();
        System.out.print("Rate per hour: "); double rate=sc.nextDouble();
        if(minutes<0 || rate<0) throw new IllegalArgumentException("Duration/rate cannot be negative");
        long hours=(minutes+59)/60;
        double fee=hours*rate;
        System.out.println("Floors="+slots.length+", billable hours="+hours+", fee=Rs "+fee);
        System.out.println("Example: 61 / 60 is integer 1; (61 + 59) / 60 correctly gives 2 started hours.");
        sc.close();
    }
}
