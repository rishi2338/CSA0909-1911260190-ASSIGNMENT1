# Task 3 — Control logic and pseudocode

```text
ALLOCATE(vehicleNo)
  FOR each floor f
    FOR each slot s on floor f
      IF slot[f][s] is free
        store vehicleNo in slot[f][s]
        RETURN (f, s)
  RETURN NO_SLOT

CALCULATE_FEE(minutes, rate, dailyMaximum)
  IF minutes < 0: RETURN INVALID
  hours = (minutes + 59) DIV 60
  fee = hours * rate
  IF dailyMaximum is enabled: fee = MIN(fee, dailyMaximum)
  RETURN fee
```

The nested loops stop immediately with `return` when a slot is found. If no slot is free, the method returns `null`. `break` exits a loop; `continue` skips an unsuitable slot; `return` ends the operation. In the supplied implementation, direct return avoids needless scanning.

Complexity: scan-based allocation O(T) worst case, where T is total slots; release O(1); storage O(T). With a stack/queue of free slot coordinates, allocation and release can be O(1) amortised, with O(T) additional storage for free-slot coordinates and optional per-floor counts.
