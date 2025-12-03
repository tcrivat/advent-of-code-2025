import java.io.*;

public class Day3Part1 {

    private static long answer = 0;
    
    private static long solve(String bank) {
        /*
            In one pass, find the two batteries with the highest voltage:
            battery1 -> highest voltage between the first (inlcusive) and the last one (exclusive)
            battery2 -> highest voltage between battery1 (exclusive) and the last one (inclusive)
        */
        int battery1 = 0;
        int battery2 = 0;
        for (int i = 0; i < bank.length(); i++) {
            int battery = bank.charAt(i) - '0';
            if (battery > battery1 && i != bank.length() - 1) {
                battery1 = battery;
                battery2 = 0;
            } else if (battery > battery2) {
                battery2 = battery;
            }
        }
        return battery1 * 10 + battery2;
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                answer += solve(line);
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        String input_file = "input" + args[0] + ".txt";
        readInput(input_file);
        System.out.println(answer);
    }
}
