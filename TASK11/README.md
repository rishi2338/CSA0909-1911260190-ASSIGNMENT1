# Task 11 — implementation, tests and defence

`Main.java` is a runnable end-to-end example with jagged slot arrays, inheritance, an interface, encapsulation, unique ticket IDs, active-ticket lookup via `HashMap`, duplicate registration rejection, slot validation and monthly-pass handling. Sample rates: two-wheeler Rs 10/hour, car Rs 30/hour; every started hour is charged. Charging energy is supplied as a separate capability; the demo uses 0 kWh because no charging-energy input is part of the parking exit API.

## Test plan
| Test | Expected result |
|---|---|
| Allocate last free slot | Slot is returned |
| Facility full | Entry throws `Parking full` |
| 0-minute stay | 0 billable hours, Rs 0 |
| 60-minute car stay | Rs 30 |
| 61-minute two-wheeler stay | Rs 20 |
| Duplicate active registration | Rejected |
| Invalid/unknown exit registration | Rejected |
| Student/staff with valid monthly pass | Rs 0 |
| Visitor without pass | Full category fee |

Run `javac Main.java && java Main` to observe actual results and save your own screenshot. The sample total for one two-wheeler (61 minutes), one car (60 minutes), and one EV (60 minutes, excluding charging) is Rs 20 + Rs 30 + Rs 30 = Rs 80. The example's active ticket store uses `HashMap<String, ParkingTicket>`: expected O(1) average lookup/insert and O(n) storage. An array or ArrayList needs O(n) linear search and O(n) storage. A limitation is that the sample does not persist data and uses `double` rather than `BigDecimal` for currency.
