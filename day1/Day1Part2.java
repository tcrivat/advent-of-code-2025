import java.io.*;

import java.io.*;

class Dial {
    
    private int position;
    private final int size;
    
    public Dial(int position, int size) {
        this.position = position;
        this.size = size;
        // System.out.format("The dial starts by pointing at %d.\n", position);
    }
    
    public int moveAndCount(int amount) {
        // System.out.format("The dial is rotated %d ", amount);
        
        boolean startFrom0 = (position == 0);
        int count = Math.abs(amount / size); // every full rotation has to pass through 0
        amount %= size;
        
        position += amount;
        // any rotation to the right that goes beyond size or
        // any rotation to the left that goes beyond 0 (exept when starting from 0)
        // will also pass through 0 one more time
        if (position >= size || position <= 0 && !startFrom0) {
            count++;
        }
        position = (position + size) % size;
        
        // System.out.format("to point at %d, passing through zero %d times.\n", position, count);
        return count;
    }
}

public class Day1Part2 {

    private static final String INPUT_FILE = "input.txt";
    private static final int START_POSITION = 50;
    private static final int DIAL_SIZE = 100;
    
    private static Dial dial = new Dial(START_POSITION, DIAL_SIZE);
    private static int answer = 0;
    
    private static void solveLine(int amount) {
        answer += dial.moveAndCount(amount);
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace('R', '+').replace('L', '-');
                solveLine(Integer.parseInt(line));
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        readInput(INPUT_FILE);
        System.out.println(answer);
    }
    
}
