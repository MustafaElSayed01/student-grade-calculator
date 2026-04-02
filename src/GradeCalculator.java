import java.util.ArrayList;
import java.util.HashMap;

/**
 * Utility class that provides grade calculation operations
 * for students and classes.
 *
 * <p>This class includes methods to:
 * <ul>
 *   <li>Calculate the average grade of a student</li>
 *   <li>Determine the letter grade based on a numeric average</li>
 *   <li>Calculate the average grade across a group of students</li>
 * </ul>
 *
 * <p>All methods are static and the class is intended to be used
 * as a helper for academic performance calculations.</p>
 */
public class GradeCalculator {

    /**
     * Calculates the numeric average of a student's grades.
     *
     * <p>The method iterates through the provided subject grades
     * and computes the arithmetic mean.</p>
     *
     * @param grades a map containing subject names as keys and
     *               their corresponding grades as values
     * @return the average grade of all subjects, or {@code 0.0}
     * if the map is {@code null} or empty
     */
    public static double calculateAverage(HashMap<String, Double> grades) {

        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }

        int subjectsCount = grades.size();
        double sum = 0.0;

        for (Double value : grades.values()) {
            sum += value;
        }

        return sum / subjectsCount;
    }

    /**
     * Determines the letter grade corresponding to a numeric average.
     *
     * <p>The grading scale is defined as:</p>
     * <ul>
     *   <li>A: 90 – 100</li>
     *   <li>B: 80 – 89</li>
     *   <li>C: 70 – 79</li>
     *   <li>D: 60 – 69</li>
     *   <li>F: below 60</li>
     * </ul>
     *
     * @param average the numeric average grade
     * @return the corresponding letter grade
     */
    public static char getLetterGrade(double average) {

        if (average >= 90) {
            return 'A';

        } else if (average >= 80) {
            return 'B';

        } else if (average >= 70) {
            return 'C';

        } else if (average >= 60) {
            return 'D';

        } else {
            return 'F';
        }
    }

    /**
     * Calculates the average grade of an entire class of students.
     *
     * <p>This method computes the average of each student's
     * subject average and then calculates the overall mean
     * for all students.</p>
     *
     * @param students a list of students whose grades will be evaluated
     * @return the class average grade, or {@code 0.0} if the list
     * is {@code null} or empty
     */
    public static double calculateClassAverage(ArrayList<Student> students) {

        if (students == null || students.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;

        for (Student student : students) {
            sum += calculateAverage(student.getSubjectGrades());
        }

        return sum / students.size();
    }
}