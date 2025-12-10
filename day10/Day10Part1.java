import java.io.*;
import java.util.*;

public class Day10Part1 {

    /*
        I store a button as a bitmask with one bit for each light
        - the bit is 1 if when pressing the button the corresponding light
        is toggled
        - the bit is 0 if pressing the button does not affect the light
        The light diagram is also a bitmask using the same convention
        To generate all the possible subsets representing pressed buttons,
        I iterate from 0 to 2^n-1, with n being the number of buttons.
        Every bit of this counter corresponds to a button:
        - the bit is 1 if the button is pressed
        - the bit is 0 otherwise
    */
    
    private static int[] buttons;
    private static int diagram;
    private static long answer = 0;
    
    private static int getLights(int counter) {
        int lights = 0;
        for (int i = 0; i < buttons.length; i++) {
            if ((counter & 1) != 0) {
                lights ^= buttons[i];
            }
            counter >>= 1;
        }
        return lights;
    }
    
    private static int solve() {
        int max = 1 << buttons.length;
        int minPresses = buttons.length;
        for (int counter = 0; counter < max; counter++) {
            if (getLights(counter) == diagram) {
                int presses = Integer.bitCount(counter);
                minPresses = Math.min(minPresses, presses);
            }
        }
        return minPresses;
    }
    
    private static String trim(String element) {
        return element.substring(1, element.length() - 1);
    }
    
    private static int parseDiagram(String element) {
        int diagram = 0;
        for (int i = 0; i < element.length(); i++) {
            if (element.charAt(i) == '#') {
                diagram |= 1 << i;
            }
        }
        return diagram;
    }
    
    private static int parseButton(String element) {
        int button = 0;
        for (String light : element.split(",")) {
            button |= 1 << Integer.parseInt(light);
        }
        return button;
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(" ");
                buttons = new int[elements.length - 2];
                int i = 0;
                for (String element : elements) {
                    if (element.charAt(0) == '[') {
                        diagram = parseDiagram(trim(element));
                    } else if (element.charAt(0) == '{') {
                        // do nothing now
                    } else {
                        buttons[i++] = parseButton(trim(element));
                    }
                }
                answer += solve();
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        String input_file = "input" + args[0] + ".txt";
        readInput(input_file);
        System.out.println(answer);
    }
}
