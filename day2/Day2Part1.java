import java.io.*;

public class Day2Part1 {

    private static final String INPUT_FILE = "input.txt";
    private static long answer = 0;
    
    private static boolean isInvalid(long number) {
        String s = Long.toString(number);
        if (s.length() % 2 == 1) {
            return false;
        } else {
            String firstHalf = s.substring(0, s.length() / 2);
            String secondHalf = s.substring(s.length() / 2, s.length());
            return firstHalf.equals(secondHalf);
        }
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
