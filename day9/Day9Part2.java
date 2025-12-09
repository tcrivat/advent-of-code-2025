import java.io.*;
import java.util.*;

public class Day9Part2 {

    private static int n, rows, cols;
    private static List<Integer> x = new ArrayList<>();
    private static List<Integer> y = new ArrayList<>();
    private static List<Integer> xOrig = new ArrayList<>();
    private static List<Integer> yOrig = new ArrayList<>();
    private static char[][] map;
    private static long answer = 0;
    
    private static void fill(int x1, int y1) {
        if (x1 < 0 || x1 >= cols ||
                y1 < 0 || y1 >= rows) {
            return;
        }
        if (map[y1][x1] != '?') {
            return;
        }
        map[y1][x1] = '.';
        fill(x1 - 1, y1);
        fill(x1 + 1, y1);
        fill(x1, y1 - 1);
        fill(x1, y1 + 1);
    }
    
    private static void initMap() {
        map = new char[rows][cols];
        
        // fill the whole map with '?'
        for (int i = 0; i < rows; i++) {
            Arrays.fill(map[i], '?');
        }
        
        // mark the red tiles
        for (int i = 0; i < n; i++) {
            map[y.get(i)][x.get(i)] = '#';
        }
        
        // mark the green tiles between the red tiles
        int x1 = x.get(n - 1);
        int y1 = y.get(n - 1);
        for (int i = 0; i < n; i++) {
            int x2 = x.get(i);
            int y2 = y.get(i);
            if (x1 == x2) {
                for (int y0 = Math.min(y1, y2) + 1; y0 < Math.max(y1, y2); y0++) {
                    map[y0][x1] = 'X';
                }
            } else if (y1 == y2) {
                for (int x0 = Math.min(x1, x2) + 1; x0 < Math.max(x1, x2); x0++) {
                    map[y1][x0] = 'X';
                }
            }
            x1 = x2;
            y1 = y2;
        }
        
        // fill the area outside the perimeter with '.'
        fill(0, 0);
        
        // mark the remaining area (the area inside the perimeter), as these
        // are also green tiles
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == '?') {
                    map[i][j] = 'X';
                }
            }
        }
    }
    
    private static boolean checkRedOrGreen(int x1, int y1, int x2, int y2) {
        // checks if the area contains only red and green tiles
        int xMin = Math.min(x1, x2);
        int xMax = Math.max(x1, x2);
        int yMin = Math.min(y1, y2);
        int yMax = Math.max(y1, y2);
        for (int i = yMin; i <= yMax; i++) {
            for (int j = xMin; j <= xMax; j++) {
                if (map[i][j] == '.') {
                    return false;
                }
            }
        }
        return true;
    }
    
    private static void markRectangle(int x1, int y1, int x2, int y2) {
        int xMin = Math.min(x1, x2);
        int xMax = Math.max(x1, x2);
        int yMin = Math.min(y1, y2);
        int yMax = Math.max(y1, y2);
        for (int i = yMin; i <= yMax; i++) {
            for (int j = xMin; j <= xMax; j++) {
                map[i][j] = 'O';
            }
        }
    }
    
    private static void printMap() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
    
    private static long computeArea(int x1, int y1, int x2, int y2) {
        return (long) (Math.abs(x1 - x2) + 1) * (Math.abs(y1 - y2) + 1);
    }
    
    private static void solve() {
        initMap();
        
        // compute the area between every pair of points that contains only
        // red or green tiles
        long maxArea = 0;
        int maxI = 0, maxJ = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (!checkRedOrGreen(x.get(i), y.get(i), x.get(j), y.get(j))) {
                    continue;
                }
                long area = computeArea(x.get(i), y.get(i), x.get(j), y.get(j));
                if (area > maxArea) {
                    maxArea = area;
                    maxI = i;
                    maxJ = j;
                }
            }
        }
        
        answer = computeArea(xOrig.get(maxI), yOrig.get(maxI),
                             xOrig.get(maxJ), yOrig.get(maxJ));
        
        // markRectangle(x.get(maxI), y.get(maxI),
                      // x.get(maxJ), y.get(maxJ));
        // printMap();
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] coordinates = line.trim().split(",");
                xOrig.add(Integer.parseInt(coordinates[0]));
                yOrig.add(Integer.parseInt(coordinates[1]));
                // divide the coordinates by 100 and round to the nearest integer
                x.add((int) Math.round(Double.parseDouble(coordinates[0]) / 100));
                y.add((int) Math.round(Double.parseDouble(coordinates[1]) / 100));
            }
            n = x.size();
            cols = Collections.max(x) + 2;
            rows = Collections.max(y) + 2;
            // System.out.println(cols);
            // System.out.println(rows);
        }
    }
    
    public static void main(String[] args) throws IOException {
        String input_file = "input" + args[0] + ".txt";
        readInput(input_file);
        solve();
        System.out.println(answer);
    }
}
