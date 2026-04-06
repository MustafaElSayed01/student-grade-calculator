# Student Grade Calculator

A command-line Java application for managing student records and academic grades. Users can register students, record subject grades, generate detailed individual and class-wide reports, and export results to a text file.

## Concepts Practiced

- `ArrayList` and `HashMap` for in-memory data management
- `Optional` for safe student lookup without null references
- Method overloading for flexible API design
- Static helper methods with single-responsibility design
- `do-while` and `while` loops for input retry and menu flow
- Input validation using `try/catch`, `NumberFormatException`, and `IllegalArgumentException`
- `Stream` API with `.filter()`, `.findFirst()`, and `.anyMatch()`
- `Comparator.comparingDouble()` with `.reversed()` for sorting
- `StringBuilder` for efficient string construction
- `FileWriter` with try-with-resources for safe file I/O
- `Scanner` for structured console input handling
- Full Javadoc documentation on all classes and methods
- Feature branching and pull request workflow with semantic commits

## Requirements

- Java 25 (LTS) — [Download from Adoptium](https://adoptium.net)

## Grade Scale

| Average Score | Letter Grade |
|---------------|--------------|
| 90 – 100      | A            |
| 80 – 89       | B            |
| 70 – 79       | C            |
| 60 – 69       | D            |
| Below 60      | F            |

## How to Run

Clone the repository:
```bash
git clone https://github.com/MustafaElSayed01/student-grade-calculator.git
cd student-grade-calculator
```

Compile and run:
```bash
javac src/Student.java src/GradeCalculator.java src/Main.java
java -cp src Main
```

## Sample Output
```
Welcome to Student System!
Pick one of the following options:
1. Add Student
2. Add Grade
3. Student Report
4. Class Report
5. Export class report into txt
1
Please Enter Student ID:
1
Please Enter Student Name:
mustafa elsayed
Please Enter Class Name:
Math
Enter Class Grade:
96
Add another grade for this student? (yes/no): y
Please Enter Class Name:
Arabic
Enter Class Grade:
100
Add another grade for this student? (yes/no): n
Another Service (yes/no): y
Pick one of the following options:
1. Add Student
2. Add Grade
3. Student Report
4. Class Report
5. Export class report into txt
4
Detailed Report For Mustafa Elsayed
ID: 1 Name: Mustafa Elsayed
Arabic: 100.0  A
Math: 96.0  A
Average Grade: 98.0  A
Another Service (yes/no): n
Thanks for using Student System!
```

## Project Structure
```
student-grade-calculator/
├── src/
│   ├── GradeCalculator.java
│   ├── Student.java
│   └── Main.java
├── .gitignore
└── README.md
```

## Author

Mustafa ElSayed — [GitHub](https://github.com/MustafaElSayed01) · [LinkedIn](https://www.linkedin.com/in/mustafaelsayed01)