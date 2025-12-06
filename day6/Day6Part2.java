import java.io.*;
import java.util.*;

/*
    Process the input file column by column and assemble the number present on each
    column. Depending on the last operator encountered, add or multiply this number
    to an acumulator. When encountering an empty column, update the result with the
    value stored in the acumulator and reset the acumulator on the next column.
*/

public class Day6Part2 {

    private static int rows, columns;
    private static char[][] worksheet;
    private static long answer = 0;
    
    private static void processWorksheetByColumns() {
        long total = 0;
        char op = '+';
        for (int col = 0; col < columns; col++) {
            StringBuilder sb = new StringBuilder();
            for (int row = 0; row < rows - 1; row++) {
                sb.append(worksheet[row][col]);
            }
            String number = sb.toString().trim();
            if (number.equals("")) {
                answer += total;
            } else {
                if (worksheet[rows - 1][col] == '+') {
                    total = 0;
                    op = '+';
                } else if (worksheet[rows - 1][col] == '*') {
                    total = 1;
                    op = '*';
                }
                if (op == '+') {
                    total += Long.valueOf(number);
                } else {
                    total *= Long.valueOf(number);
                }
            }
        }
        answer += total;
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            worksheet = br.lines().map(String::toCharArray).toArray(char[][]::new);
            rows = worksheet.length;
            columns = worksheet[0].length;
        }
    }
    
    public static void main(String[] args) throws IOException {
        String input_file = "input" + args[0] + ".txt";
        readInput(input_file);
        processWorksheetByColumns();
        System.out.println(answer);
    }
}
