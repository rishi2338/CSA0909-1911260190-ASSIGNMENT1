public class Main {
    static class ParkingSlot {
        private final int floor, number;
        private String regNo;
        ParkingSlot(int floor,int number) {
            if(floor<0 || number<0) throw new IllegalArgumentException("Invalid slot");
            this.floor=floor; this.number=number;
        }
        public void park(String regNo) {
            if(regNo==null || regNo.isBlank()) throw new IllegalArgumentException("Registration required");
            if(this.regNo!=null) throw new IllegalStateException("Slot occupied");
            this.regNo=regNo;
        }
        public void release() {
            if(regNo==null) throw new IllegalStateException("Slot already free");
            regNo=null;
        }
        public String getRegNo() { return regNo; }
        public String toString() { return "Floor "+floor+", slot "+number+", vehicle="+regNo; }
    }
    public static void main(String[] args) {
        ParkingSlot s=new ParkingSlot(0,1);
        s.park("TN01AB1234"); System.out.println(s);
        s.release(); System.out.println(s);
    }
}
