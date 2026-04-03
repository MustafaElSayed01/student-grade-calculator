import java.util.HashMap;

/**
 * Represents a student with a unique identifier, name,
 * and a collection of subject grades.
 *
 * <p>Each student can store grades for multiple subjects,
 * where the subject name acts as the key and the grade
 * (0–100) is stored as the value.</p>
 *
 * <p>This class provides functionality to add grades,
 * retrieve student information, and view the student's
 * academic record.</p>
 */
public class Student {
    /**
     * Unique identifier assigned to the student.
     */
    private int id;

    /**
     * The student's full name.
     */
    private String name;

    /**
     * Stores the student's grades per subject.
     * The key represents the subject name and the value
     * represents the grade (0–100).
     */
    private HashMap<String, Double> subjectGrades;

    /**
     * Constructs a new Student with the given name and ID.
     * Initializes an empty collection for subject grades.
     *
     * @param id   the student's unique identifier
     * @param name the student's name
     */
    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        subjectGrades = new HashMap<>();
    }

    /**
     * Returns the student's unique ID.
     *
     * @return the student's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the student's name.
     *
     * @return the name of the student
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the map containing all subject grades.
     *
     * <p>The returned map associates subject names
     * with their corresponding grades.</p>
     *
     * @return map of subjects and grades
     */
    public HashMap<String, Double> getSubjectGrades() {
        return subjectGrades;
    }

    /**
     * Adds or updates a grade for a given subject.
     *
     * <p>The grade must be within the range 0–100.
     * If the subject already exists, its grade will
     * be replaced with the new value.</p>
     *
     * @param subject the subject name
     * @param grade   the grade received in the subject
     * @throws IllegalArgumentException if the grade is
     *                                  outside the valid range (0–100)
     */
    public void addGrade(String subject, double grade) {

        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException(
                    "Grade must be between 0 and 100."
            );
        }

        subjectGrades.put(subject, grade);
    }

    /**
     * Returns a string representation of the student.
     *
     * @return formatted string containing the student ID
     * and name
     */
    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "'}";
    }
}