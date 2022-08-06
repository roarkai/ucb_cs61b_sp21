package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
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
        timeGetLast();
    }

    private  static void addNItems(SLList<Integer> testS, int n){
        for(int i = 0; i < n; i++)
            testS.addFirst(i);
    }

    private  static void getLastMTimes(SLList<Integer> testS, int m){
        int lastele;
        for (int i = 0; i < m; i++)
            lastele = testS.getLast();
    }

    public static void timeGetLast() {
        // create tablea items
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> ops = new AList<>();
         // 初始化Ns
        int max = 128000;
        for (int k = 1000; k <= max; k = k*2) {
            Ns.addLast(k);
        }

        // time the ops
        int M = 10000;
        for(int n = 0; n < Ns.size(); n++){
            SLList<Integer> testS = new SLList<>();
            addNItems(testS, Ns.get(n));

            Stopwatch sw = new Stopwatch();
            getLastMTimes(testS, M);
            double timeInSeconds = sw.elapsedTime();
            
            times.addLast(timeInSeconds);
            ops.addLast(M);
        }

        printTimingTable(Ns, times, ops);

    }

}
