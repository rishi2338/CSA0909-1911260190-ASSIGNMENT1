# Task 10 — packages and encapsulation

Suggested package layout:
```text
parking.model      Vehicle, Car, ParkingTicket, ParkingSlot, ParkingPass
parking.pricing    PricingRule, HourlyPricingRule
parking.service    ParkingService, ParkingFacility
parking.app        Main
```

Use `private` for mutable state and `private final` for immutable identifiers. Use `public` for the intended API, `protected` for subclass extension points, and package-private for implementation details shared only inside a package. Getters expose values without exposing mutation; setters validate non-negative durations, nonblank registrations, valid slot bounds and non-negative kWh before changing state.

Wrapper classes such as `Integer` are useful in collections and may be autoboxed from `int`. Do not compare boxed integers with `==` for numeric equality; use `equals` or unbox safely. Public fields with caller-side validation allow any caller to bypass checks. Validation itself is O(1) per update, but duplicated validation spreads across every caller and increases maintenance/error risk.
