import java.io.*;

class Dial {
    
    private int position;
    private final int size;
    
    public Dial(int position, int size) {
        this.position = position;
        this.size = size;
        // System.out.format("The dial starts by pointing at %d.\n", position);
    }
    
    public void move(int amount) {
        // System.out.format("The dial is rotated %d ", amount);
        
        position = (position + amount) % size;
        if (position < 0) {
            position += size;
        }
        
        // System.out.format("to point at %d.\n", position);
    }
    
    public boolean isAtPosition(int position) {
        return this.position == position;
    }
}

public class Day1Part1 {

    private static final int START_POSITION = 50;
    private static final int DIAL_SIZE = 100;
    
    private static Dial dial = new Dial(START_POSITION, DIAL_SIZE);
    private static int answer = 0;
    
    private static void solve(int amount) {
        dial.move(amount);
        if (dial.isAtPosition(0)) {
            answer++;
        }
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace('R', '+').replace('L', '-');
                solve(Integer.parseInt(line));
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        String input_file = "input" + args[0] + ".txt";
        readInput(input_file);
        System.out.println(answer);
    }
    
}
