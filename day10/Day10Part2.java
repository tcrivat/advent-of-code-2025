import java.io.*;
import java.util.*;
import com.google.ortools.Loader;
import com.google.ortools.sat.*;

public class Day10Part2 {

    private static int[][] buttons;
    private static int[] levels;
    private static long answer = 0;
    
    /*
        I use Google OR-Tools to solve the system of linear equations
    */
    private static int solve(int[] levels, int[][] buttons) {
        CpModel model = new CpModel();
        
        // computing the coefficients
        int[][] coef = new int[levels.length][buttons.length];
        for (int i = 0; i < buttons.length; i++) {
            for (int counter : buttons[i]) {
                coef[counter][i] = 1;
            }
        }
        
        // initialize the variables
        IntVar[] x = new IntVar[buttons.length];
        for (int i = 0; i < buttons.length; i++) {
            x[i] = model.newIntVar(0, Integer.MAX_VALUE, "x" + i);
        }
        
        // build the linear equations
        for (int i = 0; i < levels.length; i++) {
            List<IntVar> var = new ArrayList<>();
            for (int j = 0; j < buttons.length; j++) {
                if (coef[i][j] == 1) {
                    var.add(x[j]);
                }
            }
            LinearExpr sum = LinearExpr.sum(var.toArray(new IntVar[var.size()]));
            model.addEquality(sum, levels[i]);
        }
        
        // objective: minimize x0 + x1 + x2 + ... + xn
        LinearExpr total = LinearExpr.sum(x);
        model.minimize(total);
        
        // solve the equations
        CpSolver solver = new CpSolver();
        CpSolverStatus status = solver.solve(model);
        
        int result = 0;
        if (status == CpSolverStatus.OPTIMAL) {
            for (int i = 0; i < buttons.length; i++) {
                result += solver.value(x[i]);
            }
        }
        return result;
    }
    
    private static String trim(String element) {
        return element.substring(1, element.length() - 1);
    }
    
    private static int[] parseIntArray(String element) {
        return Arrays.stream(trim(element).split(","))
                     .mapToInt(Integer::parseInt)
                     .toArray();
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int l = 0;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(" ");
                buttons = new int[elements.length - 2][];
                int i = 0;
                for (String element : elements) {
                    if (element.charAt(0) == '[') {
                        // do nothing now
                    } else if (element.charAt(0) == '{') {
                        levels = parseIntArray(element);
                    } else {
                        buttons[i++] = parseIntArray(element);
                    }
                }
                answer += solve(levels, buttons);
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        Loader.loadNativeLibraries();
        String input_file = "input" + args[0] + ".txt";
        readInput(input_file);
        System.out.println(answer);
    }
}
