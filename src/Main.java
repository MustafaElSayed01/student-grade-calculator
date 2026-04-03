// TODO: implement showStudentReport() — display one student's grades and average
// TODO: implement showClassReport() — display all students sorted by average
// TODO: implement saveReportToFile() — write class report to report.txt
// TODO: implement menu loop — show options, read choice, call correct method

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Main {

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


}
