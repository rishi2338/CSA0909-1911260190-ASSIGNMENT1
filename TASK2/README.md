# Task 2 — Data representation

- Slot/floor index: `int`, because array indices are integers.
- Registration number and ticket ID: `String`, because identifiers may contain letters and leading zeroes.
- Entry time: `Instant` or `LocalDateTime`; elapsed duration: `long` minutes.
- Fee/rate: `double` for this introductory assignment; a production billing system should prefer `BigDecimal` for exact decimal currency.
- Floor structure: jagged `String[][]` or `ParkingSlot[][]`, because floors can have different capacities.
- Input: `Scanner` for interactive console input; command-line arguments are suitable for automation.

Avoid `minutes / 60` alone: 61 / 60 with integer operands equals 1, undercharging a started-hour policy. Use `(minutes + 59) / 60` for non-negative minutes. Avoid casting after integer division, e.g. `(double)(minutes / 60)` still loses the fraction; use `minutes / 60.0` if fractional hours are intended.

A single flat array with uniform capacity uses `floor * slotsPerFloor + slot` and has O(1) indexing, but uneven floors need prefix offsets or a separate mapping. A jagged array directly represents uneven floors and needs O(total slots) space. An `ArrayList<ParkingSlot[]>` is flexible in the number of floors but still indexes each floor's slots separately. Locating the first free slot by scanning is O(total slots); direct location by floor and slot is O(1).
