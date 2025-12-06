import java.io.*;
import java.util.*;

class Interval implements Comparable<Interval> {
    
    private final long start, end;
    
    public Interval(long start, long end) {
        this.start = start;
        this.end = end;
    }
    
    public boolean overlapsWith(Interval other) {
        return other.start <= end && other.end >= start;
    }
    
    public Interval mergeWith(Interval other) {
        if (!this.overlapsWith(other)) {
            String message = this + " and " + other + " do not overlap";
            throw new IllegalArgumentException(message);
        }
        return new Interval(Math.min(start, other.start), Math.max(end, other.end));
    }
    
    public long size() {
        return end - start + 1;
    }
    
    public int compareTo(Interval other) {
        return Long.valueOf(start).compareTo(other.start);
    }
    
    public String toString() {
        return start + "-" + end;
    }
}

public class Day5Part2 {

    private static List<Interval> intervals = new ArrayList<>();
    private static long answer = 0;
    
    private static void solve() {
        /*
            I sort the intervals by the start value and iterate through them
            one by one, merging the overlapping intervals. When the intervals
            do not overlap, I add their size to the answer.
        */
        Collections.sort(intervals);
        
        Interval previous = intervals.get(0);
        for (int i = 1; i < intervals.size(); i++) {
            Interval current = intervals.get(i);
            if (previous.overlapsWith(current)) {
                previous = previous.mergeWith(current);
            } else {
                answer += previous.size();
                previous = current;
            }
        }
        answer += previous.size();
    }
    
    private static void readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while (!(line = br.readLine()).isEmpty()) {
                String[] interval = line.split("-");
                long start = Long.valueOf(interval[0]);
                long end = Long.valueOf(interval[1]);
                intervals.add(new Interval(start, end));
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        String input_file = "input" + args[0] + ".txt";
        readInput(input_file);
        solve();
        System.out.println(answer);
    }
}
