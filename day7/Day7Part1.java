import java.io.*;

public class Day7Part1 {

    private static int n;
    private static char[][] map;
    private static long answer = 0;
    
    private static boolean isBeam(int i, int j) {
        return map[i][j] == 'S' || map[i][j] == '|';
    }
    
    private static boolean isSplitter(int i, int j) {
        return map[i][j] == '^';
    }
    
    private static boolean isOnMap(int i, int j) {
        return i >= 0 && j >= 0 && i < n && j < n;
    }
    
    private static void putBeam(int i, int j) {
        if (isOnMap(i, j)) {
            map[i][j] = '|';
        }
    }
    
    private static void solve() {
        /*
            For each position on the map, I check if the position above contains a tachyon
            beam which needs to be propagated. If the current position contains a splitter,
            the beam will propagate to the left and to the right of the current position,
            and I also increase the total number of splits.
        */
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isBeam(i - 1, j)) {
                    if (isSplitter(i, j)) {
                        putBeam(i, j - 1);
                        putBeam(i, j + 1);
                        answer++;
                    } else {
                        putBeam(i, j);
                    }
                }
            }
        }
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            map = br.lines().map(String::toCharArray).toArray(char[][]::new);
            n = map.length - 1;
        }
    }
    
    public static void main(String[] args) throws IOException {
        String input_file = "input" + args[0] + ".txt";
        readInput(input_file);
        solve();
        System.out.println(answer);
    }
}
