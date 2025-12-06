import java.io.*;
import java.util.*;

public class Day5Part1 {

    // fresh ranges stored as two parallel arrays
    private static List<Long> start = new ArrayList<>();
    private static List<Long> end = new ArrayList<>();
    
    private static long answer = 0;
    
    private static boolean isFresh(Long id) {
        /*
            I simply iterate through all the ranges and check if any contains the id
        */
        for (int i = 0; i < start.size(); i++) {
            if (id >= start.get(i) && id <= end.get(i)) {
                return true;
            }
        }
        return false;
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while (!(line = br.readLine()).isEmpty()) {
                String[] interval = line.split("-");
                start.add(Long.valueOf(interval[0]));
                end.add(Long.valueOf(interval[1]));
            }
            while ((line = br.readLine()) != null) {
                if (isFresh(Long.valueOf(line))) {
                    answer++;
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
