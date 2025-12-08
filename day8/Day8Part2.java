import java.io.*;
import java.util.*;

class JunctionBox {
    private static int circuitCount = 0;
    
    public final long x, y, z;
    public int circuit;
    
    public JunctionBox(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.circuit = circuitCount++;
    }
    
    public double distance(JunctionBox other) {
        return Math.sqrt((x - other.x) * (x - other.x) +
                         (y - other.y) * (y - other.y) +
                         (z - other.z) * (z - other.z));
    }
    
    public String toString() {
        return x + "," + y + "," + z + "(" + circuit + ")";
    }
}

class JunctionBoxPair implements Comparable<JunctionBoxPair> {
    public final JunctionBox jbox1, jbox2;
    public final double distance;
    
    public JunctionBoxPair(JunctionBox jbox1, JunctionBox jbox2) {
        this.jbox1 = jbox1;
        this.jbox2 = jbox2;
        this.distance = jbox1.distance(jbox2);
    }
    
    public int compareTo(JunctionBoxPair other) {
        return Double.valueOf(distance).compareTo(other.distance);
    }
    
    public String toString() {
        return jbox1.circuit + "-" + jbox2.circuit + "(" + distance + ")";
    }
}

public class Day8Part2 {

    private static int n;
    private static List<JunctionBox> junctionBoxes = new ArrayList<>();
    private static long answer = 0;
    
    private static void solve() {
        // Add all pairs to a priority queue (min-heap).
        PriorityQueue<JunctionBoxPair> pq = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                pq.add(new JunctionBoxPair(junctionBoxes.get(i), junctionBoxes.get(j)));
            }
        }
        
        int[] circuitSizes = new int[n];
        Arrays.fill(circuitSizes, 1);
        
        // Extract the pair with the shortest distance from the priority queue.
        // Connect the pair (first and second) by putting all the jboxes connected
        // to the second jbox in the same circuit as the first jbox.
        // Reapeat until the circuit contains all the jboxes or the queue is empty.
        while (!pq.isEmpty()) {
            JunctionBoxPair pair = pq.poll();
            int circuit1 = pair.jbox1.circuit;
            int circuit2 = pair.jbox2.circuit;
            if (circuit2 != circuit1) {
                circuitSizes[circuit2] = 0;
                for (int j = 0; j < n; j++) {
                    JunctionBox jbox = junctionBoxes.get(j);
                    if (jbox.circuit == circuit2) {
                        jbox.circuit = circuit1;
                        circuitSizes[circuit1]++;
                    }
                }
            }
            if (circuitSizes[circuit1] == n) {
                answer = pair.jbox1.x * pair.jbox2.x;
                break;
            }
        }
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] coordinates = line.trim().split(",");
                long x = Long.parseLong(coordinates[0]);
                long y = Long.parseLong(coordinates[1]);
                long z = Long.parseLong(coordinates[2]);
                junctionBoxes.add(new JunctionBox(x, y, z));
            }
            n = junctionBoxes.size();
        }
    }
    
    public static void main(String[] args) throws IOException {
        String input_file = "input" + args[0] + ".txt";
        readInput(input_file);
        solve();
        System.out.println(answer);
    }
}
