package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    private static int fillAList(int n) {
        AList<Integer> testA = new AList<>();
        int opscnt = 0;
        for(int i = 0; i< n; i++){
            testA.addLast(i);
            opscnt++;
        }
        return opscnt;
    }
    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        // 新建Ns，times和ops
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();

        // 初始化Ns
        int max = 128000;
        for (int k = 1000; k <= max; k = k*2) {
            Ns.addLast(k);
        }

        //
        for (int n = 0; n < Ns.size(); n++){
            int opscnt;
            Stopwatch sw = new Stopwatch();
            opscnt = fillAList(Ns.get(n));
            opCounts.addLast(opscnt);
            double timeInSeconds = sw.elapsedTime();
            times.addLast(timeInSeconds);
        }

        //
        printTimingTable(Ns, times, opCounts);
    }
    }