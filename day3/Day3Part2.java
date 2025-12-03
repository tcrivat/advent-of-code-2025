import java.io.*;

public class Day3Part2 {

    private static final int BATTERIES_COUNT = 12;
    private static long answer = 0;
    
    private static long solve(String bank) {
        long maxVoltage = 0;
        int startPosition = 0;
        
        /*
            We do BATTERIES_COUNT passes, each time finding the battery with the highest voltage
            between the startPosition and stopPosition.
            startPosition -> the next position after the battery found in the previous iteration
            stopPosition -> for the battery number b, we make sure that we do not visit the
                last BATTERIES_COUNT - b - 1 positions, so there are enough positions left for
                the remainig BATTERIES_COUNT - b - 1 batteries.
        */
        for (int b = 0; b < BATTERIES_COUNT; b++) {
            int maxBattery = 0;
            int stopPosition = bank.length() - (BATTERIES_COUNT - b);
            for (int i = startPosition; i <= stopPosition; i++) {
                int battery = bank.charAt(i) - '0';
                if (battery > maxBattery) {
                    maxBattery = battery;
                    startPosition = i + 1;
                }
            }
            maxVoltage = maxVoltage * 10 + maxBattery;
        }
        
        return maxVoltage;
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                answer += solve(line);
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        String input_file = "input" + args[0] + ".txt";
        readInput(input_file);
        System.out.println(answer);
    }
}
