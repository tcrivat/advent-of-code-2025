import java.io.*;
import java.util.*;

public class Day11Part1 {

    private static Map<String, List<String>> adjLists = new HashMap<>();
    private static long answer = 0;
    
    /*
        Count all the paths starting from the specified node using DFS
    */
    private static long solve(String node) {
        long paths = 0;
        if (node.equals("out")) {
            paths = 1;
        } else {
            for (String next : adjLists.get(node)) {
                paths += solve(next);
            }
        }
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
        answer = solve("you");
        System.out.println(answer);
    }
}
