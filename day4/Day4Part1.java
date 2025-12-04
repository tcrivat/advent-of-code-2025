import java.io.*;

public class Day4Part1 {

    private static int n;
    private static char[][] map;
    private static long answer = 0;
    
    private static boolean isOccupied(int i, int j) {
        return map[i][j] == '@';
    }
    
    private static boolean isOnMap(int i, int j) {
        return i >= 0 && j >= 0 && i < n && j < n;
    }
    
    private static int countOccupiedNeighbors(int i, int j) {
        int count = 0;
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                if (!(di == 0 && dj == 0) && isOnMap(i + di, j + dj) && isOccupied(i + di, j + dj)) {
                    count++;
                }
            }
        }
        return count;
    }
    
    private static void solve() {
        /*
            I simply iterate through all the positions on the map and count the
            occupied positions with less that 4 occupied neighbors
        */
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isOccupied(i, j) && countOccupiedNeighbors(i, j) < 4) {
                    answer++;
                }
            }
        }
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            map = br.lines().map(String::toCharArray).toArray(char[][]::new);
            n = map.length;
        }
    }
    
    public static void main(String[] args) throws IOException {
        String input_file = "input" + args[0] + ".txt";
        readInput(input_file);
        solve();
        System.out.println(answer);
    }
}
