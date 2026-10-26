public class Main {
    static class ParkingTicket {
        private final String id, regNo;
        private final int floor, slot;
        ParkingTicket() { this("P-0","UNKNOWN",0,0); }
        ParkingTicket(String id,String regNo) { this(id,regNo,0,0); }
        ParkingTicket(String id,String regNo,int floor,int slot) {
            if(id==null || id.isBlank() || regNo==null || regNo.isBlank() || floor<0 || slot<0)
                throw new IllegalArgumentException("Invalid ticket data");
            this.id=id; this.regNo=regNo; this.floor=floor; this.slot=slot;
        }
        ParkingTicket(ParkingTicket other) { this(other.id,other.regNo,other.floor,other.slot); }
        public String toString() { return id+" "+regNo+" floor="+floor+" slot="+slot; }
    }
    public static void main(String[] args) {
        ParkingTicket t=new ParkingTicket("P-101","TN01AB1234",1,4);
        ParkingTicket copy=new ParkingTicket(t);
        System.out.println(t); System.out.println("Snapshot: "+copy);
    }
}
