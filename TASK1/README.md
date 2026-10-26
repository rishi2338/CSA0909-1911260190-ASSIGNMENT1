# Task 1 — Problem analysis and objects

## Inputs and outputs
Inputs: registration number, vehicle category, user category/pass validity, entry and exit times, requested slot/charging requirement, and floor capacities. Outputs: allocated floor/slot, unique ticket ID, entry/exit records, itemised fee, occupancy and error messages.

## Candidate classes and responsibilities
- `Vehicle`: common registration number and fee calculation contract.
- `TwoWheeler`, `Car`, `ElectricCar`: category-specific hourly rates; EV charging capability.
- `ParkingSlot`: slot identity, charging capability and current vehicle.
- `ParkingFacility`: owns floors/slots and allocates/releases slots.
- `ParkingTicket`: unique ticket, vehicle, slot and entry time; computes duration.
- `ParkingPass`: represents valid monthly or one-day entitlement.
- `PricingRule`: pricing policy kept separate from vehicle identity.
- `ParkingService`: coordinates entry/exit, duplicate-registration checks and payment.
- `UserType`: student, staff or visitor, for eligibility rules.

Nouns suggest candidate classes; verbs such as `allocate`, `release`, `enter`, `exit`, `calculateFee`, `validatePass` and `issueTicket` become methods. Classes are justified because each owns a distinct responsibility and can be tested independently.

## Business rules and assumptions
A slot holds at most one vehicle; a ticket ID is unique and never reused; a registration number cannot have two active tickets; only valid slot indices may be released; an EV requiring charging must use a charging slot; duration cannot be negative; started-hour billing rounds upward; a valid student/staff monthly pass makes parking free; visitors pay the standard fee unless a valid one-day pass applies. Assumption: rates are sample values and should be approved by the university; all times use the same local time zone.

## Alternative: parallel arrays
`String[] regNo`, `String[] type`, `long[] entryTime` need matching indices and careful synchronisation. Linear registration search is O(n), just as searching an array of objects is O(n). Object storage uses one reference per vehicle plus the object and its fields; parallel arrays store each field in separate arrays and may have lower per-record object overhead, but adding an attribute requires a new array and changes to every operation. An object lets a new attribute be added to `Vehicle` without maintaining parallel-index consistency.
