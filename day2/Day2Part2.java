import java.io.*;

public class Day2Part2 {

    private static final String INPUT_FILE = "input.txt";
    private static long answer = 0;
    
    private static boolean isInvalid(long number) {
        char[] s = Long.toString(number).toCharArray();
        outer: for (int l = 1; l <= s.length / 2; l++) {
            // l = length of the repeating sequence
            if (s.length % l != 0) {
                continue outer;
            }
            for (int i = l; i < s.length; i++) {
                if (s[i] != s[i - l]) {
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
        readInput(INPUT_FILE);
        System.out.println(answer);
    }
}
