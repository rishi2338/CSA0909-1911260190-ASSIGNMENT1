# Campus Smart-Parking System — Java Assignment 1

## Student Details

- **Name:** Your Name
- **Register Number:** Your Register Number
- **Course:** CSA09
- **Assignment:** Assignment 1
- **Programming Language:** Java

## 1. Introduction

The Campus Smart-Parking System is a simple Java application designed to manage vehicle parking inside a college campus. It helps allocate parking slots, store vehicle details, release slots when vehicles leave and calculate parking fees.

This assignment demonstrates the basic concepts of Java programming through a parking management example.

## 2. Objectives

- To understand classes and objects.
- To use constructors to initialise objects.
- To implement inheritance and method overriding.
- To understand polymorphism, abstraction and interfaces.
- To use arrays for parking slot management.
- To practise encapsulation and static variables.
- To develop and test a basic parking system.

## 3. Technologies Used

- **Programming Language:** Java
- **Editor:** VS Code or any Java-supported editor
- **Compiler:** Java Compiler (`javac`)
- **Execution:** Java Virtual Machine (JVM)

## 4. Assignment Tasks

This assignment covers 12 tasks:

1. Understanding the parking system requirements.
2. Selecting suitable data types and data structures.
3. Implementing parking slot allocation and release.
4. Understanding classes, objects and constructors.
5. Using static variables and understanding object life cycles.
6. Implementing inheritance for different vehicle types.
7. Demonstrating method overriding and object equality.
8. Demonstrating polymorphism and parking fee calculation.
9. Understanding abstraction and interfaces.
10. Using packages and encapsulation.
11. Combining components into an integrated parking system.
12. Extending the parking system to handle additional requirements.

## 5. Project Structure

```text
CSA09-RegisterNumber-Assignment1/
│
├── Task01/
│   └── README.md
├── Task02/
│   └── README.md
├── Task03/
│   ├── ParkingFacilityDemo.java
│   └── README.md
├── Task04/
│   └── README.md
├── Task05/
│   └── README.md
├── Task06/
│   └── README.md
├── Task07/
│   └── README.md
├── Task08/
│   ├── FeeDemo.java
│   └── README.md
├── Task09/
│   └── README.md
├── Task10/
│   └── README.md
├── Task11/
│   └── ParkingSystemDemo.java
├── Task12/
│   ├── ParkingSystemDemo.java
│   └── README.md
└── report/
    └── Assignment_Report.docx
```

*Note: This is an example folder structure. Adjust the filenames and paths to match your actual project.*

## 6. Requirements

- Install Java Development Kit (JDK) 17 or later.
- Use a Java-supported editor or terminal.
- Save each Java program with the same name as its public class.

## 7. How to Compile and Run

Open the terminal in the folder containing the Java file.

For example, to run Task 3:

```bash
javac ParkingFacilityDemo.java
java ParkingFacilityDemo
```

To run Task 8:

```bash
javac FeeDemo.java
java FeeDemo
```

For programs that use a package, compile and run them using their package structure. For example, from the Task 11 folder:

```bash
javac -d out ParkingSystemDemo.java
java -cp out parking.ParkingSystemDemo
```

## 8. Testing

The programs should be tested with different situations, such as:

- Parking a vehicle in an empty slot.
- Allocating several slots.
- Checking what happens when parking is full.
- Releasing an occupied slot.
- Trying to release a slot that is already empty.
- Calculating fees for different vehicle types.
- Checking the status of parking slots after vehicles leave.

The output should be compared with the expected output for each task.

## 9. Learning Outcomes

After completing this assignment, I gained a basic understanding of Java classes, objects, constructors, inheritance, polymorphism, interfaces, arrays and methods. I also learned how to divide a larger problem into smaller parts and test a program step by step.

## 10. Limitations

The basic implementation is intended for learning purposes. It does not necessarily include a graphical user interface, a database, real-time vehicle detection or a complete online parking management system.

Some parking fees and rules may be assumptions used for demonstration. They should be checked against the requirements of each task.

## 11. Future Improvements

The system could be improved by adding:

- A graphical user interface.
- Database storage for vehicle and ticket details.
- Automatic entry and exit time recording.
- More vehicle categories.
- Electric-vehicle charging fee management.
- Parking passes and booking facilities.
- Reports showing parking usage and fee collection.

## 12. Conclusion

The Campus Smart-Parking System helped me practise Java programming concepts through a practical example. By completing the tasks, I learned how classes, objects, arrays, inheritance and methods can be used to manage parking information. This assignment also helped me improve my problem-solving and programming skills.
