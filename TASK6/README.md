# Task 6 — Inheritance hierarchy

```text
                 Vehicle (abstract)
                 /       |        \
        TwoWheeler      Car     DeliveryVan
                         |
                     ElectricCar
```

This is hierarchical inheritance from `Vehicle`, with multilevel inheritance for `ElectricCar extends Car extends Vehicle`. Common registration and fee API belong to `Vehicle`; each concrete subtype supplies its hourly rate; `ElectricCar` adds charging behaviour through `Chargeable`. `super(regNo)` invokes the parent constructor before subclass initialisation.

Constructor trace for `new ElectricCar("TN03C3")`: `Vehicle` constructor → `Car` constructor → `ElectricCar` constructor. Java requires the superclass constructor to complete first.

Alternative composition: `Vehicle` has a `VehicleType`/`PricingRule` object. Adding a category can then mean adding a policy object rather than a subclass. Both can calculate a fee in O(1) when the rate lookup is O(1); composition is often easier when types and pricing rules change independently. Inheritance stores a subtype object and common state; composition additionally stores a policy reference per vehicle (O(1) extra space).
