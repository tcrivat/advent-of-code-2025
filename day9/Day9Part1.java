import java.io.*;
import java.util.*;

public class Day9Part1 {

    private static int n;
    private static List<Integer> x = new ArrayList<>();
    private static List<Integer> y = new ArrayList<>();
    private static long answer = 0;
    
    private static long computeArea(int x1, int y1, int x2, int y2) {
        return (long) (Math.abs(x1 - x2) + 1) * (Math.abs(y1 - y2) + 1);
    }
    
    private static void solve() {
        // compute the area between every pair of points
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                long area = computeArea(x.get(i), y.get(i), x.get(j), y.get(j));
                answer = Math.max(answer, area);
            }
        }
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] coordinates = line.trim().split(",");
                x.add(Integer.parseInt(coordinates[0]));
                y.add(Integer.parseInt(coordinates[1]));
            }
            n = x.size();
        }
    }
    
    public static void main(String[] args) throws IOException {
        String input_file = "input" + args[0] + ".txt";
        readInput(input_file);
        solve();
        System.out.println(answer);
    }
}
