import java.util.Objects;
public class Main {
    static class Vehicle {
        private final String regNo;
        Vehicle(String regNo) {
            if(regNo==null || regNo.isBlank()) throw new IllegalArgumentException();
            this.regNo=regNo;
        }
        @Override public boolean equals(Object o) {
            return o instanceof Vehicle v && regNo.equals(v.regNo);
        }
        @Override public int hashCode() { return Objects.hash(regNo); }
        @Override public String toString() { return "Vehicle["+regNo+"]"; }
    }
    public static void main(String[] args) {
        Vehicle a=new Vehicle("TN01AB1234"), b=new Vehicle("TN01AB1234");
        System.out.println(a); System.out.println("Equal="+a.equals(b));
        System.out.println("Same hash="+(a.hashCode()==b.hashCode()));
    }
}
