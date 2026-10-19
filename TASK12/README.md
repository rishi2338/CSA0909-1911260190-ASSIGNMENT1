# Task 12: Extending the Parking System

## Aim
To extend the parking system to support additional requirements.

## Description
The extended design supports additional vehicle types, parking passes and floors with different numbers of parking slots.

An e-bike can have its own parking-fee rule. A delivery van can use a time-based fee rule. A valid pass can reduce or remove the applicable fee according to the implemented rules.

Jagged arrays can represent parking floors that have different capacities.

## How to Run
Open a terminal in the Task12 folder and execute:

```bash
javac -d out src/parking/ParkingSystemDemo.java
java -cp out parking.ParkingSystemDemo
```

## Testing
Check that:
- Different vehicle types receive the correct fee.
- Parking slots can be allocated and released.
- Passes are handled according to the program rules.
- Floors can have different numbers of slots.

## Conclusion
This task helped me understand how an existing Java program can be extended to support new requirements while reusing its original classes and methods.
