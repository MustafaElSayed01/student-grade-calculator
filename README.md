# Student Grade Calculator

A command-line Java application for managing student records and academic grades. Users can register students, record subject grades, generate detailed individual and class-wide reports, and export results to a text file.

## Concepts Practiced

### OOP — Encapsulation
Private fields with controlled access through getters. No unnecessary setters.
```java
public class Student {
    private final int id;
    private final String name;
    private final HashMap<String, Double> subjectGrades = new HashMap<>();

    public int getId() { return id; }
    public String getName() { return name; }
    public HashMap<String, Double> getSubjectGrades() { return subjectGrades; }
}
```

---

### Input Validation with `try/catch`
Numeric input is parsed safely with meaningful error messages and retry loops.
```java
static int readStudentId(Scanner scanner) {
    while (true) {
        System.out.println("Please Enter Student ID:");
        String idInput = scanner.nextLine();
        try {
            int id = Integer.parseInt(idInput);
            if (isPositiveId(id)) return id;
            System.out.println("ID must be a positive number, Please enter a valid ID:");
        } catch (NumberFormatException e) {
            System.out.println("Not a number! Please enter a valid id.");
        }
    }
}
```

---

### `Optional` for Safe Lookup
`Optional` is used instead of returning `null` or sentinel values when searching for a student.
```java
static Optional<Student> findStudentById(int id) {
    return studentRegistry.stream()
            .filter(s -> s.getId() == id)
            .findFirst();
}
```

---

### Stream API
The Stream API is used for filtering collections without manual loops.
```java
Optional<Student> found = studentRegistry.stream()
        .filter(s -> s.getId() == id)
        .findFirst();
```

---

### Method Overloading
`studentReport()` and `recordGrade()` are overloaded to support both direct object access and user-driven ID lookup — keeping callers clean.
```java
// Called internally with a known Student object
static String studentReport(Student student) { ... }

// Called from the menu — prompts user for ID, then delegates
static String studentReport(Scanner scanner) {
    int id = readStudentId(scanner);
    Optional<Student> found = findStudentById(id);
    if (found.isEmpty()) {
        System.out.println("No student with ID " + id);
        return "";
    }
    return studentReport(found.get());
}
```

---

### Single Responsibility
Each method does exactly one thing. Helper methods are extracted to avoid duplication.
```java
static boolean isPositiveId(int id) { return id > 0; }
static String toTitleCase(String text) { ... }
static Optional<Student> findStudentById(int id) { ... }
static int readStudentId(Scanner scanner) { ... }
```

---

### `Comparator` with Sorting
The class report sorts students by average grade in descending order using a typed comparator.
```java
studentRegistry.sort(
    Comparator.comparingDouble(
        (Student s) -> GradeCalculator.calculateAverage(s.getSubjectGrades())
    ).reversed()
);
```

---

### `StringBuilder` for Report Generation
`StringBuilder` is used over string concatenation for efficient multi-line report construction.
```java
StringBuilder result = new StringBuilder();
result.append("ID: ").append(id)
      .append(" Name: ").append(studentName).append("\n");

studentGrades.forEach((key, value) -> {
    result.append(key).append(": ").append(value)
          .append("  ").append(GradeCalculator.getLetterGrade(value)).append("\n");
});
```

---

### `HashMap` for Grade Storage
Each student stores their subject grades as a `HashMap<String, Double>` — keyed by subject name.
```java
public void addGrade(String subject, double grade) {
    if (grade < 0 || grade > 100) {
        throw new IllegalArgumentException("Grade must be between 0 and 100.");
    }
    subjectGrades.put(subject, grade);
}
```

---

### File I/O with Try-With-Resources
`FileWriter` is wrapped in a try-with-resources block to guarantee the file handle is closed even if an exception occurs.
```java
static void saveReportToFile(String fileName) {
    if (!fileName.endsWith(".txt")) fileName += ".txt";
    try (FileWriter fileWriter = new FileWriter(fileName)) {
        fileWriter.write(classReport());
    } catch (IOException e) {
        System.out.println("Something went wrong while writing the report: " + e.getMessage());
    }
}
```

---

### `do-while` Loop for Menu Flow
The main menu runs at least once and keeps looping until the user chooses to exit.
```java
do {
    System.out.println("Pick one of the following options:");
    // ... display options ...
    switch (input) {
        case "1" -> addStudent(scanner);
        case "2" -> recordGrade(scanner);
        case "3" -> System.out.println(studentReport(scanner));
        case "4" -> System.out.println(classReport());
        case "5" -> saveReportToFile(fileName);
        default  -> System.out.println("Please pick a valid option.");
    }
} while (askForAnotherService(scanner));
```

---

### Guard Clauses
Early returns on invalid state keep methods flat and readable — no deep nesting.
```java
static void addStudent(Scanner scanner) {
    int id = readStudentId(scanner);

    Optional<Student> found = findStudentById(id);
    if (found.isPresent()) {
        System.out.println("Student with ID " + id + " already exists.");
        return;
    }
    // happy path continues...
}
```

---

### Full Javadoc
Every class and method is documented with `@param`, `@return`, and `{@link}` cross-references.
```java
/**
 * Reads and validates a student identifier from user input.
 *
 * <p>The method retries until a valid positive integer is entered.</p>
 *
 * @param scanner scanner used to read console input
 * @return a valid positive student ID
 */
static int readStudentId(Scanner scanner) { ... }
```

---

## Grade Scale

| Average Score | Letter Grade |
|---------------|--------------|
| 90 – 100      | A            |
| 80 – 89       | B            |
| 70 – 79       | C            |
| 60 – 69       | D            |
| Below 60      | F            |

## Requirements

- Java 25 (LTS) — [Download from Adoptium](https://adoptium.net)

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
...
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