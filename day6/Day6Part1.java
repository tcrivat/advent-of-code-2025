import java.io.*;
import java.util.*;

/*
    Process the input file row by row and, for each column, accumulate both the sum 
    and the product of its encountered elements. When processing the last row, choose
    between one or the other depending on the operator present in that specific column.
*/

public class Day6Part1 {

    private static List<Long> sumTotal = new ArrayList<>();
    private static List<Long> prodTotal = new ArrayList<>();
    private static long answer = 0;
    
    private static void ensureSize(int size) {
        while (sumTotal.size() < size) {
            sumTotal.add(0l);
            prodTotal.add(1l);
        }
    }
    
    private static void processRow(String[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            long number = Long.valueOf(numbers[i]);
            sumTotal.set(i, sumTotal.get(i) + number);
            prodTotal.set(i, prodTotal.get(i) * number);
        }
    }
    
    private static void computeResult(String[] ops) {
        for (int i = 0; i < ops.length; i++) {
            if (ops[i].equals("+")) {
                answer += sumTotal.get(i);
            } else if (ops[i].equals("*")) {
                answer += prodTotal.get(i);
            }
        }
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] numbers = line.trim().split(" +");
                ensureSize(numbers.length);
                if (!numbers[0].equals("+") && !numbers[0].equals("*")) {
                    processRow(numbers);
                } else {
                    computeResult(numbers);
                }
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        String input_file = "input" + args[0] + ".txt";
        readInput(input_file);
        System.out.println(answer);
    }
}
