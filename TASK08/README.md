# Task 8: Polymorphism and Fee Calculation

## Aim
To demonstrate polymorphism by calculating parking fees for different vehicle types.

## Description
The `FeeDemo` program creates different vehicle objects and calculates their parking fees.

Each vehicle type provides its own implementation of the parking-fee method. Java calls the appropriate method based on the actual object.

## Concepts Used
- Polymorphism
- Method overriding
- Abstract classes
- Interfaces
- Object comparison

## How to Run
```bash
javac FeeDemo.java
java FeeDemo
```

## Expected Output
```text
TwoWheeler TN01A1 -> Rs 30.0
Car TN02B2 -> Rs 90.0
ElectricCar TN03C3 -> Rs 210.0
true
```

## Conclusion
This task helped me understand how polymorphism allows different vehicle objects to use a common method with different results.
