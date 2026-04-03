// TODO: implement showStudentReport() — display one student's grades and average
// TODO: implement showClassReport() — display all students sorted by average
// TODO: implement saveReportToFile() — write class report to report.txt
// TODO: implement menu loop — show options, read choice, call correct method

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {

    }

    public static String capitalizeWords(String text) {
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

    static void addStudent(Scanner scanner) {
        System.out.println("Please Enter Student ID:");
        String idInput = scanner.nextLine();
        try {
            int id = Integer.parseInt(idInput);
            if (id <= 0) {
                System.out.println("ID must be a positive number.");
                return;
            }
            boolean isExists = students.stream().anyMatch(student -> student.getId() == id);
            if (isExists) {
                System.out.println("Student with ID " + id + " already exists.");
                return;
            }
            System.out.println("Please Enter Student Name:");
            String name = scanner.nextLine();
            String capitalizedName = capitalizeWords(name.trim());
            Student student = new Student(id, capitalizedName);
            students.add(student);
        } catch (NumberFormatException e) {
            System.out.println("Not a number! Please enter a valid id.");
        }
    }

    static void addGrade(Scanner scanner) {
        System.out.println("Please Enter Student ID:");
        String idInput = scanner.nextLine();
        try {
            int id = Integer.parseInt(idInput);
            if (id <= 0) {
                System.out.println("ID must be a positive number.");
                return;
            }
            Optional<Student> found = students.stream().filter(s -> s.getId() == id).findFirst();
            if (found.isEmpty()) {
                System.out.println("No student with ID " + id);
                return;
            }
            Student student = found.get();

            System.out.println("Please Enter Class Name");
            String subject = scanner.nextLine();
            String capitalizedSubject = capitalizeWords(subject.trim());
            System.out.println("Please Enter Grade:");
            String grade = scanner.nextLine();
            try {
                double gradeValue = Double.parseDouble(grade);
                student.addGrade(capitalizedSubject, gradeValue);
            } catch (NumberFormatException e) {
                System.out.println("Not a number! Please enter a valid grade.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Not a number! Please enter a valid one.");
        }
    }

}
