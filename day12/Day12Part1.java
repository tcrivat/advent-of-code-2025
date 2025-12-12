import java.io.*;
import java.util.*;

class Shape {
    
    public static final int SIZE = 3;
    
    private char[][][] rotations = new char[4][][];
    private final int occupiedUnits;
    
    private int countOccupiedUnits(char[][] pattern) {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (pattern[i][j] == '#') {
                    count++;
                }
            }
        }
        return count;
    }
    
    private char[][] rotate(char[][] pattern1) {
        char[][] pattern2 = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                pattern2[j][SIZE - i - 1] = pattern1[i][j];
            }
        }
        return pattern2;
    }
    
    public Shape(char[][] pattern) {
        occupiedUnits = countOccupiedUnits(pattern);
        rotations[0] = pattern;
        for (int i = 1; i < rotations.length; i++) {
            rotations[i] = rotate(rotations[i - 1]);
        }
    }
    
    public int getOccupiedUnits() {
        return occupiedUnits;
    }
    
    public boolean canFitOnMap(char[][] map, int row, int col, int rotation) {
        char[][] pattern = rotations[rotation];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (pattern[i][j] == '#' && map[row + i][col + j] == '#') {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void putOnMap(char[][] map, int row, int col, int rotation) {
        char[][] pattern = rotations[rotation];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (pattern[i][j] == '#') {
                    map[row + i][col + j] = '#';
                }
            }
        }
    }
    
    public void removeFromMap(char[][] map, int row, int col, int rotation) {
        char[][] pattern = rotations[rotation];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (pattern[i][j] == '#') {
                    map[row + i][col + j] = '.';
                }
            }
        }
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < rotations.length; j++) {
                sb.append(rotations[j][i]);
                sb.append("  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

public class Day12Part1 {

    private static final int SHAPE_COUNT = 6;
    
    private static Shape[] shapes = new Shape[SHAPE_COUNT];
    private static long answer = 0;
    
    private static void printMap(char[][] map) {
        for (char[] line : map) {
            System.out.println(line);
        }
    }
    
    /*
        I use backtracking to try to fit all the (possibly rotated) presents
        into the region
    */
    private static boolean solve(char[][] map, int[] presents) {
        int pos = 0; // current position in the presents array
        while (pos < SHAPE_COUNT && presents[pos] == 0) {
            pos++;
        }
        if (pos == SHAPE_COUNT) {
            return true;
        }
        presents[pos]--;
        
        // trying to fit shapes[pos] somewhere on the map
        for (int i = 0; i <= map.length - Shape.SIZE; i++) {
            for (int j = 0; j <= map[0].length - Shape.SIZE; j++) {
                for (int r = 0; r < 4; r++) {
                    if (shapes[pos].canFitOnMap(map, i, j, r)) {
                        shapes[pos].putOnMap(map, i, j, r);
                        if (solve(map, presents)) {
                            return true;
                        }
                        shapes[pos].removeFromMap(map, i, j, r);
                    }
                }
            }
        }
        presents[pos]++;
        return false;
    }
    
    // For a solution to be possible, the region needs to have available at
    // least the number of units (#) used by all the presents
    private static boolean isPossible(int rows, int cols, int[] presents) {
        int available = rows * cols;
        for (int i = 0; i < SHAPE_COUNT; i++) {
            available -= presents[i] * shapes[i].getOccupiedUnits();
            if (available < 0) {
                return false;
            }
        }
        return true;
    }
    
    // For a solution to be trivial, the region needs to be big enough so that
    // all the presents fit inside irrespective of their shape
    private static boolean isTrivial(int rows, int cols, int[] presents) {
        int total = Arrays.stream(presents).sum();
        int places = (rows / Shape.SIZE) * (cols / Shape.SIZE);
        return total <= places;
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            
            // read the shapes
            for (int i = 0; i < SHAPE_COUNT; i++) {
                line = br.readLine();
                assert line.equals(i + ":");
                
                char[][] pattern = new char[Shape.SIZE][Shape.SIZE];
                for (int j = 0; j < Shape.SIZE; j++) {
                    line = br.readLine();
                    pattern[j] = line.toCharArray();
                }
                shapes[i] = new Shape(pattern);
                // System.out.println(shapes[i]);
                
                line = br.readLine();
            }
            
            // read the regions
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(": ");
                
                String[] mapSize = elements[0].split("x");
                int cols = Integer.parseInt(mapSize[0]);
                int rows = Integer.parseInt(mapSize[1]);
                int[] presents = Arrays.stream(elements[1].split(" "))
                                       .mapToInt(Integer::parseInt)
                                       .toArray();
                
                if (!isPossible(rows, cols, presents)) {
                    // impossible solution: not enough units available for all presents
                    continue;
                }
                if (isTrivial(rows, cols, presents)) {
                    // trivial solution: all presents fit irrespective of shape
                    answer += 1;
                    continue;
                }
                
                char[][] map = new char[rows][cols];
                for (char[] row : map) {
                    Arrays.fill(row, '.');
                }
                boolean solved = solve(map, presents);
                // if (solved) {
                    // System.out.println("Found a solution: ");
                    // printMap(map);
                // } else {
                    // System.out.println("No solution found!");
                // }
                // System.out.println();
                answer += solved ? 1 : 0;
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        String input_file = "input" + args[0] + ".txt";
        readInput(input_file);
        System.out.println(answer);
    }
}
