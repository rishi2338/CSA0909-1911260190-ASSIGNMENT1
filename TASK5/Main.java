public class Main {
    static class Ticket {
        private static long nextId=1;
        final String id;
        Ticket() { id="P-"+nextId++; }
        static long issuedCount() { return nextId-1; }
    }
    public static void main(String[] args) {
        Ticket a=new Ticket(), b=new Ticket(), c=new Ticket();
        System.out.println(a.id); System.out.println(b.id); System.out.println(c.id);
        System.out.println("Tickets issued="+Ticket.issuedCount());
    }
}
