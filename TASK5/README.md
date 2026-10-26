# Task 5 — static members and object life cycle

```java
class TicketIds {
    private static long nextId = 1;
    private TicketIds() {}
    public static synchronized String issue() { return "P-" + nextId++; }
}
```

The counter belongs to the class because all tickets must share one sequence. A per-facility instance counter can be appropriate if ticket IDs include a facility prefix, e.g. `N-P-1` and `S-P-1`; otherwise two facility objects could issue duplicate IDs. A shared global counter uses O(1) counter space for any number k of facilities and n tickets; instance counters use O(k) counter space. Active ticket objects become eligible for garbage collection after exit only when no live reference points to them. Passing a ticket to a method passes its reference value; returning a ticket returns a reference, not a duplicate object. Static methods are appropriate for ID generation and pure utility operations; static blocks are best reserved for class-level initialisation.
