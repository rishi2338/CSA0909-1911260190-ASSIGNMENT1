# Task 7 — overriding and Object methods

```java
import java.util.Objects;
abstract class VehicleIdentity {
    private final String regNo;
    protected VehicleIdentity(String regNo) {
        if (regNo == null || regNo.isBlank()) throw new IllegalArgumentException("Registration required");
        this.regNo = regNo;
    }
    public String getRegNo() { return regNo; }
    @Override public final boolean equals(Object o) {
        return o instanceof VehicleIdentity v && regNo.equals(v.regNo);
    }
    @Override public final int hashCode() { return Objects.hash(regNo); }
    @Override public String toString() { return getClass().getSimpleName() + "[" + regNo + "]"; }
}
```

`@Override` catches signature mistakes at compile time. The method name and parameter signature must match; return type must be covariant/compatible and visibility cannot be reduced. `final` is appropriate for immutable registration fields and, if equality must be consistent across all subtypes, for `equals` and `hashCode`. Registration-only equality means a `Car` and `ElectricCar` with the same registration compare equal; the assignment's FeeDemo demonstrates this, but a production design should decide whether category is part of identity. A switch-based fee method is O(1) for a fixed finite set of types, but adding a type changes the central method and often tests; overriding keeps type-specific rules in separate classes.
