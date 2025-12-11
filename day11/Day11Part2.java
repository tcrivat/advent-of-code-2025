import java.io.*;
import java.util.*;

public class Day11Part2 {

    private static Map<String, List<String>> adjLists = new HashMap<>();
    private static Map<String, Long> memo = new HashMap<>();
    private static long answer = 0;
    
    /*
        Count all the paths starting from the specified node using DFS,
        using memoization so we do not search the same sub-tree again
    */
    private static long solve(String node, boolean visitedDac, boolean visitedFft) {
        String encoded = node + visitedDac + visitedFft;
        if (memo.containsKey(encoded)) {
            return memo.get(encoded);
        }
        
        long paths = 0;
        if (node.equals("out")) {
            if (visitedDac && visitedFft) {
                paths = 1;
            }
        } else {
            if (node.equals("dac")) {
                visitedDac = true;
            }
            if (node.equals("fft")) {
                visitedFft = true;
            }
            for (String next : adjLists.get(node)) {
                paths += solve(next, visitedDac, visitedFft);
            }
        }
        
        memo.put(encoded, paths);
        return paths;
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(" ");
                List<String> adjList = new ArrayList<>();
                for (int i = 1; i < elements.length; i++) {
                    adjList.add(elements[i]);
                }
                String node = elements[0].substring(0, elements[0].length() - 1);
                adjLists.put(node, adjList);
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        String input_file = "input" + args[0] + ".txt";
        readInput(input_file);
        answer = solve("svr", false, false);
        System.out.println(answer);
    }
}
