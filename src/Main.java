import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Entry point and command controller for the Student Grade Management application.
 *
 * <p>This class coordinates user interaction and manages the lifecycle of
 * student records and grade operations. It maintains the in-memory registry
 * of students and provides helper methods for input validation, student lookup,
 * and text normalization.</p>
 */
public class Main {

    /**
     * Registry containing all students currently stored in the application.
     */
    private static final ArrayList<Student> studentRegistry = new ArrayList<>();

    /**
     * Application entry point.
     *
     * <p>This method initializes the program and is responsible for starting
     * the user interaction loop.</p>
     *
     * @param args command-line arguments passed to the program
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        runStudentSystem(scanner);
    }

    /**
     * Converts a string into title case by capitalizing the first letter
     * of each word and converting the remaining characters to lowercase.
     *
     * @param text the text to format
     * @return a normalized string where each word begins with an uppercase letter,
     * or the original value if the input is {@code null} or empty
     */
    static String toTitleCase(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        String[] words = text.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase()).append(" ");
            }
        }

        return result.toString().trim();
    }

    /**
     * Searches for a student in the registry by their identifier.
     *
     * @param id the student identifier
     * @return an {@link Optional} containing the student if found,
     * otherwise an empty Optional
     */
    static Optional<Student> findStudentById(int id) {
        return studentRegistry.stream().filter(s -> s.getId() == id).findFirst();
    }

    /**
     * Validates whether a provided student identifier is positive.
     *
     * @param id the identifier to validate
     * @return {@code true} if the identifier is greater than zero,
     * otherwise {@code false}
     */
    static boolean isPositiveId(int id) {
        return id > 0;
    }

    /**
     * Reads and validates a student identifier from user input.
     *
     * <p>The method attempts to parse the entered value into a positive integer. </p>
     *
     * @param scanner scanner used to read console input
     * @return valid student ID,
     */
    static int readStudentId(Scanner scanner) {
        while (true) {
            System.out.println("Please Enter Student ID:");
            String idInput = scanner.nextLine();
            try {
                int id = Integer.parseInt(idInput);

                if (isPositiveId(id)) {
                    return id;
                }

                System.out.println("ID must be a positive number,  Please enter a valid id.");

            } catch (NumberFormatException e) {
                System.out.println("Not a number! Please enter a valid id.");
            }
        }
    }

    /**
     * Registers a new student in the system and optionally records their grades.
     *
     * <p>The method prompts the user to enter a student ID and checks whether
     * a student with the same ID already exists in the registry. If the ID is
     * already assigned, the operation terminates and a message is displayed.</p>
     *
     * <p>For a unique ID, the user is prompted to enter the student's name.
     * The name is normalized to title case before creating the student record.
     * The new student is then added to the student registry.</p>
     *
     * <p>After successful registration, the method immediately invokes
     * {@link #recordGrade(Scanner, Student)} to allow entering one or more
     * subject grades for the newly created student.</p>
     *
     * @param scanner scanner used to read user input for the student ID
     *                and student name
     */
    static void addStudent(Scanner scanner) {

        int id = readStudentId(scanner);

        Optional<Student> found = findStudentById(id);

        if (found.isPresent()) {
            System.out.println("Student with ID " + id + " already exists.");
            return;
        }
        System.out.println("Please Enter Student Name:");
        String name = scanner.nextLine();

        String formattedName = toTitleCase(name.trim());

        Student student = new Student(id, formattedName);

        studentRegistry.add(student);
        recordGrade(scanner, student);
    }

    /**
     * Records one or more subject grades for the specified student.
     *
     * <p>The method repeatedly prompts the user to enter a subject name and
     * its corresponding numeric grade. The subject name is normalized to
     * title case before being stored.</p>
     *
     * <p>Grade input is validated to ensure it is numeric and conforms to
     * the constraints enforced by the student grade registration logic.
     * If invalid input is provided, the user is prompted again until a
     * valid grade is entered.</p>
     *
     * <p>After successfully recording a grade, the user is asked whether
     * they want to add another grade for the same student.</p>
     *
     * @param scanner scanner used to read user input
     * @param student the student whose grades will be updated
     */
    static void recordGrade(Scanner scanner, Student student) {

        do {
            System.out.println("Please Enter Class Name");
            String subject = scanner.nextLine();

            String formattedSubject = toTitleCase(subject.trim());
            while (true) {
                System.out.println("Enter Class Grade:");
                String grade = scanner.nextLine();

                try {
                    double gradeValue = Double.parseDouble(grade);
                    student.addGrade(formattedSubject, gradeValue);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Not a number! Please enter a valid grade.");

                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        while (askForAnotherService(scanner, "Add another grade for this student?"));
    }

    /**
     * Records grades for a student selected by ID.
     *
     * <p>The method prompts the user to enter a student ID, attempts to locate
     * the corresponding student in the registry, and if found delegates the
     * grade recording process to {@link #recordGrade(Scanner, Student)}.</p>
     *
     * <p>If no student exists with the provided ID, the method prints a message
     * and terminates without recording any grades.</p>
     *
     * @param scanner scanner used to read user input
     */
    static void recordGrade(Scanner scanner) {

        int id = readStudentId(scanner);

        Optional<Student> found = findStudentById(id);

        if (found.isEmpty()) {
            System.out.println("No student with ID " + id);
            return;
        }

        Student student = found.get();
        recordGrade(scanner, student);
    }

    /**
     * Builds a detailed academic report for the provided student.
     *
     * <p>The report contains the student's identification information,
     * all recorded subjects with their numeric grades, the corresponding
     * letter grades, and the calculated overall average grade with its
     * letter equivalent.</p>
     *
     * @param student the student whose academic report should be generated
     * @return a formatted string containing the student's grades and average
     */
    static String studentReport(Student student) {
        int id = student.getId();
        String studentName = student.getName();
        HashMap<String, Double> studentGrades = student.getSubjectGrades();
        StringBuilder result = new StringBuilder();
        result.append("Detailed Report For ").append(studentName).append("\n").append("ID: ").append(id).append(" Name: ").append(studentName).append("\n");

        studentGrades.forEach((key, value) -> {
            result.append(key).append(": ").append(value).append("  ").append(GradeCalculator.getLetterGrade(value)).append("\n");
        });
        Double studentAverage = GradeCalculator.calculateAverage(studentGrades);
        result.append("Average Grade: ").append(studentAverage).append("  ").append(GradeCalculator.getLetterGrade(studentAverage));
        return result.toString();
    }

    /**
     * Retrieves a student by prompting for an ID and returns the student's report.
     *
     * <p>The method reads a student ID from the provided input source,
     * validates the value, searches the student registry, and delegates
     * report generation to {@code studentReport(Student)} if a matching
     * student is found.</p>
     *
     * <p>If the input is invalid or the student does not exist,
     * the method returns an empty string.</p>
     *
     * @param scanner the input source used to read the student ID
     * @return the formatted student report, or an empty string if no student is found
     */
    static String studentReport(Scanner scanner) {
        int id = readStudentId(scanner);

        Optional<Student> found = findStudentById(id);
        if (found.isEmpty()) {
            System.out.println("No student with ID " + id);
            return "";
        }
        return studentReport(found.get());
    }

    /**
     * Generates a complete academic report for all students in the registry.
     *
     * <p>The method first sorts the student registry in descending order
     * based on each student's average grade. The average is calculated
     * using {@link GradeCalculator#calculateAverage(java.util.HashMap)}.</p>
     *
     * <p>After sorting, the method generates an individual detailed report
     * for every student by invoking {@link #studentReport(Student)} and
     * appends each report to a single aggregated result.</p>
     *
     * <p>The final output contains the detailed report of every student,
     * ordered from the highest average grade to lowest.</p>
     *
     * @return a formatted string containing the reports of all students
     * sorted by descending average grade
     */
    static String classReport() {
        if (studentRegistry.isEmpty()) {
            System.out.println("Class is empty");
            return "";
        }

        studentRegistry.sort(
                Comparator.comparingDouble(
                        (Student s) -> GradeCalculator.calculateAverage(s.getSubjectGrades())
                ).reversed()
        );

        StringBuilder result = new StringBuilder();

        studentRegistry.forEach(student -> {
            result.append(studentReport(student));
        });

        return result.toString();
    }

    /**
     * Writes the full class academic report to a file.
     *
     * <p>The method generates the report by invoking {@link #classReport()}
     * and writes the resulting formatted string to the specified file using
     * a {@link java.io.FileWriter}. If the file does not exist, it will be
     * created; otherwise, its contents will be overwritten.</p>
     *
     * <p>If an I/O error occurs during the writing process, the method catches
     * the exception and prints an error message to the console.</p>
     *
     * @param fileName the name or path of the file where the report will be saved
     */
    static void saveReportToFile(String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(classReport());
        } catch (IOException e) {
            System.out.println("Something went wrong while writing the report: " + e.getMessage());
        }
    }

    /**
     * Prompts the user to decide whether to continue using the system.
     *
     * <p>This is a convenience overload that displays a default prompt message
     * and delegates the input handling to {@link #askForAnotherService(Scanner, String)}.</p>
     *
     * @param scanner scanner used to read user input
     * @return {@code true} if the user confirms continuation ({@code yes}/{@code y}),
     * {@code false} if the user declines ({@code no}/{@code n})
     */
    static boolean askForAnotherService(Scanner scanner) {
        return askForAnotherService(scanner, "Another Service");
    }

    /**
     * Prompts the user with a custom message asking whether to continue.
     *
     * <p>The method repeatedly requests input until a valid response is provided.
     * Accepted affirmative responses are {@code "yes"} and {@code "y"}, while
     * negative responses are {@code "no"} and {@code "n"}. The comparison is
     * performed in a case-insensitive manner after trimming whitespace.</p>
     *
     * @param scanner scanner used to read user input
     * @param message prompt message displayed to the user before requesting input
     * @return {@code true} if the user confirms continuation ({@code yes}/{@code y}),
     * {@code false} if the user declines ({@code no}/{@code n})
     */
    static boolean askForAnotherService(Scanner scanner, String message) {

        while (true) {
            System.out.printf("%s (yes/no): ", message);
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "yes", "y" -> {
                    return true;
                }
                case "no", "n" -> {
                    return false;
                }
                default -> System.out.println("Please type yes (y) or no (n).");
            }
        }
    }

    /**
     * Runs the interactive console-based student management system.
     *
     * <p>The method displays a menu of available operations and continuously
     * prompts the user to select an option until the user chooses to stop.
     * Each option triggers a corresponding system action:</p>
     *
     * <ul>
     *     <li>Add a new student</li>
     *     <li>Record a grade for an existing student</li>
     *     <li>Generate a detailed report for a specific student</li>
     *     <li>Generate a report for the entire class</li>
     *     <li>Export the class report to a text file</li>
     * </ul>
     *
     * <p>User input is read from the provided {@link Scanner}. If an invalid
     * option is entered, the system prompts the user to choose a valid one.</p>
     *
     * @param scanner the scanner used to read user input during the session
     */
    static void runStudentSystem(Scanner scanner) {
        System.out.println("Welcome to Student System!");
        do {
            System.out.println("Pick one of the following options:");
            System.out.println("1. Add Student");
            System.out.println("2. Add Grade");
            System.out.println("3. Student Report");
            System.out.println("4. Class Report");
            System.out.println("5. Export class report into txt");

            String input = scanner.nextLine().trim().toLowerCase();
            switch (input) {
                case "1" -> addStudent(scanner);
                case "2" -> recordGrade(scanner);
                case "3" -> System.out.println(studentReport(scanner));
                case "4" -> System.out.println(classReport());
                case "5" -> {
                    System.out.println("Enter the file name:");
                    String fileName = scanner.nextLine().trim().toLowerCase();
                    saveReportToFile(fileName);
                }
                default -> System.out.println("Please pick a valid option.");
            }
        } while (askForAnotherService(scanner));
        System.out.println("Thanks for using Student System!");
    }

}