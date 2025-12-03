import java.io.*;

public class Day2Part2 {

    private static long answer = 0;
    
    private static boolean isInvalid(long number) {
        char[] digits = Long.toString(number).toCharArray();
        outer: for (int l = 1; l <= digits.length / 2; l++) {
            // l = length of the repeating sequence
            if (digits.length % l != 0) {
                continue outer;
            }
            for (int i = l; i < digits.length; i++) {
                if (digits[i] != digits[i - l]) {
                    continue outer;
                }
            }
            return true;
        }
        return false;
    }
    
    private static void solve(long left, long right) {
        for (long number = left; number <= right; number++) {
            if (isInvalid(number)) {
                answer += number;
            }
        }
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] intervals = line.split(",");
                for (String interval : intervals) {
                    String[] pair = interval.split("-");
                    solve(Long.parseLong(pair[0]), Long.parseLong(pair[1]));
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
