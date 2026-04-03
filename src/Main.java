// TODO: implement showStudentReport() — display one student's grades and average
// TODO: implement showClassReport() — display all students sorted by average
// TODO: implement saveReportToFile() — write class report to report.txt
// TODO: implement menu loop — show options, read choice, call correct method

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

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
                result.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
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
        return studentRegistry.stream()
                .filter(s -> s.getId() == id)
                .findFirst();
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
     * Displays the academic report for a specific student.
     *
     * <p>The report should include the list of subjects,
     * recorded grades, calculated average score, and
     * the corresponding letter grade.</p>
     *
     * @param id the identifier of the student whose report
     *           should be displayed
     */
    static void printStudentReport(int id) {

    }

}