import java.io.*;
import java.util.*;

class JunctionBox {
    private static int maxId = 0;
    
    private final long x, y, z;
    private final int id;
    
    public JunctionBox(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = maxId++;
    }
    
    public long getX() {
        return x;
    }
    
    public int getId() {
        return id;
    }
    
    public long distance(JunctionBox other) {
        return ((x - other.x) * (x - other.x) +
                (y - other.y) * (y - other.y) +
                (z - other.z) * (z - other.z)); // optimisation: no need for sqrt
    }
    
    public String toString() {
        return x + "," + y + "," + z + "(" + id + ")";
    }
}

class JunctionBoxPair implements Comparable<JunctionBoxPair> {
    private final JunctionBox jbox1, jbox2;
    private final long distance;
    
    public JunctionBoxPair(JunctionBox jbox1, JunctionBox jbox2) {
        this.jbox1 = jbox1;
        this.jbox2 = jbox2;
        this.distance = jbox1.distance(jbox2);
    }
    
    public JunctionBox getJbox1() {
        return jbox1;
    }
    
    public JunctionBox getJbox2() {
        return jbox2;
    }
    
    public int compareTo(JunctionBoxPair other) {
        return Long.valueOf(distance).compareTo(other.distance);
    }
    
    public String toString() {
        return jbox1.getId() + "-" + jbox2.getId() + "(" + distance + ")";
    }
}

class UnionFind {
    private final int n;
    private int[] parent;
    private int[] size;
    
    public UnionFind(int n) {
        this.n = n;
        this.parent = new int[n];
        this.size = new int[n];
        
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }
    
    public int find(int i) {
        if (parent[i] != i) {
            parent[i] = find(parent[i]); // path compression
        }
        return parent[i];
    }
    
    public int union(int i1, int i2) {
        int root1 = find(i1);
        int root2 = find(i2);
        if (root1 == root2) {
            return size[root1];
        }
        
        // union by size
        if (size[root1] < size[root2]) {
            parent[root1] = root2;
            size[root2] += size[root1];
            size[root1] = 0;
            return size[root2];
        } else {
            parent[root2] = root1;
            size[root1] += size[root2];
            size[root2] = 0;
            return size[root1];
        }
    }
}

public class Day8Part2V2 {

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
        
        // Extract the pair with the shortest distance from the priority queue.
        // Connect the pair using the union-find data structure.
        // Reapeat until the circuit contains all the jboxes or the queue is empty.
        UnionFind uf = new UnionFind(n);
        while (!pq.isEmpty()) {
            JunctionBoxPair pair = pq.poll();
            JunctionBox jbox1 = pair.getJbox1();
            JunctionBox jbox2 = pair.getJbox2();
            int size = uf.union(jbox1.getId(), jbox2.getId());
            if (size == n) { // all jboxes are connected
                answer = jbox1.getX() * jbox2.getX();
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
