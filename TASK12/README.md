# Task 12 — re-evaluate and adapt

## A. Impact analysis
New requirements add e-bikes with a flat fee, delivery vans with a time-window multiplier, visitor one-day passes, and five floors with unequal slot counts. Vehicle categories and pricing change; a jagged array is appropriate because each floor has a different capacity. A central pricing-rule implementation and pass abstraction are affected, while the facility can remain independent of pricing.

## B. Evaluation
A pure inheritance design could add subclasses, but delivery-van time pricing does not fit neatly into a single hourly-rate override because the rule depends on entry time. A central `PricingRule` keeps time-dependent policy together. This is easier to audit, though a growing conditional may eventually be replaced by separate strategy objects. New types should not require changes to allocation logic.

## C. Adaptation
`Main.java` implements `EBike`, `DeliveryVan`, `PricingRule`, `Pass`, `MonthlyPass`, `OneDayPass` and a jagged five-floor facility. E-bike pricing is a flat Rs 15; van rate is twice the car rate when entry is 09:00–16:59; a valid one-day pass makes the fee zero. These are explicit sample policy assumptions and should be confirmed with the university. For production, validate pass date/time and apply one-day-pass policy only to the correct local date.

## D. Evidence-based defence
The sample adds new vehicle and pass classes plus pricing logic; the facility's allocation algorithm remains unchanged. A further category can be added by creating a `Vehicle` subtype and defining its policy. Trade-off: the simple `instanceof` checks in `StandardPricing` are easy to understand but less extensible than a registry of pricing strategies. Run `javac Main.java && java Main` to verify e-bike, van, pass and uneven-floor output; capture the actual terminal output for the report.
