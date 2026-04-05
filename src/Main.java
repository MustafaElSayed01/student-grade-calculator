// TODO: implement saveReportToFile() — write class report to report.txt
// TODO: implement menu loop — show options, read choice, call correct method

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
    private static ArrayList<Student> studentRegistry = new ArrayList<>();

    /**
     * Application entry point.
     *
     * <p>This method initializes the program and is responsible for starting
     * the user interaction loop.</p>
     *
     * @param args command-line arguments passed to the program
     */
    public static void main(String[] args) {

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
     * <p>The method attempts to parse the entered value into a positive integer.
     * Invalid inputs result in an empty Optional.</p>
     *
     * @param scanner scanner used to read console input
     * @return an Optional containing the valid student ID,
     * or an empty Optional if the input is invalid
     */
    static Optional<Integer> readStudentId(Scanner scanner) {
        System.out.println("Please Enter Student ID:");
        String idInput = scanner.nextLine();

        try {
            int id = Integer.parseInt(idInput);

            if (!isPositiveId(id)) {
                System.out.println("ID must be a positive number.");
                return Optional.empty();
            }

            return Optional.of(id);

        } catch (NumberFormatException e) {
            System.out.println("Not a number! Please enter a valid id.");
            return Optional.empty();
        }
    }

    /**
     * Registers a new student in the system.
     *
     * <p>The method reads a student ID and name from the user,
     * validates the information, and stores the new student
     * in the registry if the identifier is unique.</p>
     *
     * @param scanner scanner used to read user input
     */
    static void addStudent(Scanner scanner) {

        Optional<Integer> idOption = readStudentId(scanner);

        if (idOption.isEmpty()) {
            return;
        }

        int id = idOption.get();

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
    }

    /**
     * Records a grade for a specific student and subject.
     *
     * <p>The method retrieves the student by identifier, prompts for
     * a subject name and numeric grade, and stores the grade in the
     * student's grade record.</p>
     *
     * @param scanner scanner used to read user input
     */
    static void recordGrade(Scanner scanner) {

        Optional<Integer> idOption = readStudentId(scanner);

        if (idOption.isEmpty()) {
            return;
        }

        int id = idOption.get();

        Optional<Student> found = findStudentById(id);

        if (found.isEmpty()) {
            System.out.println("No student with ID " + id);
            return;
        }

        Student student = found.get();

        System.out.println("Please Enter Class Name");
        String subject = scanner.nextLine();

        String formattedSubject = toTitleCase(subject.trim());

        System.out.println("Please Enter Grade:");
        String grade = scanner.nextLine();

        try {
            double gradeValue = Double.parseDouble(grade);
            student.addGrade(formattedSubject, gradeValue);

        } catch (NumberFormatException e) {
            System.out.println("Not a number! Please enter a valid grade.");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
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
        Optional<Integer> idOption = readStudentId(scanner);

        if (idOption.isEmpty()) {
            return "";
        }

        int id = idOption.get();

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
        if(studentRegistry.isEmpty()) {
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
}