import java.io.*;

public class Day7Part2 {

    private static int n;
    private static char[][] map;
    private static long[][] timelines;
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
    
    private static void putBeam(int i, int j, long c) {
        if (isOnMap(i, j)) {
            map[i][j] = '|';
            timelines[i][j] += c;
        }
    }
    
    private static void solve() {
        /*
            I iterate through the map in the same way as in part1, just now I also
            record for each beam position the number of timelines active for that
            beam (in a different matrix named 'timelines'). Each time a beam splits,
            the timelines count of the right and left beams will increase with the
            same amount.
        */
        for (int j = 0; j < n; j++) {
            if (isBeam(0, j)) {
                putBeam(0, j, 1);
            }
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isBeam(i - 1, j)) {
                    long t = timelines[i - 1][j];
                    if (isSplitter(i, j)) {
                        putBeam(i, j - 1, t);
                        putBeam(i, j + 1, t);
                    } else {
                        putBeam(i, j, t);
                    }
                }
            }
        }
        for (int j = 0; j < n; j++) {
            answer += timelines[n - 1][j];
        }
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            map = br.lines().map(String::toCharArray).toArray(char[][]::new);
            n = map.length - 1;
            timelines = new long[n][n];
        }
    }
    
    public static void main(String[] args) throws IOException {
        String input_file = "input" + args[0] + ".txt";
        readInput(input_file);
        solve();
        System.out.println(answer);
    }
}
