package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    AListNoResizing<Integer> noReList = new AListNoResizing<>();
    BuggyAList<Integer> ReList = new BuggyAList<>();
    @Test
    public void testThreeAddThreeRemove(){
        int n = 3;
        for(int i = 0; i < n; i++){
          noReList.addLast(i);
          ReList.addLast(i);
        }
        for(int i = 0; i < n; i++){
          assertEquals(noReList.removeLast(), ReList.removeLast());
        }
    }

    @Test
    public void RandomizedTest(){
      AListNoResizing<Integer> L = new AListNoResizing<>();
      BuggyAList<Integer> BL = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                BL.addLast(randVal);
                assertEquals(L.getLast(), BL.getLast());
            } else if (operationNumber == 1) {
                int size = L.size();
                int sizeBL = BL.size();
                assertEquals(size, sizeBL);
            } else if (operationNumber == 2 && L.size() == 0) {
                  continue;
            } else {
                int lastL = L.getLast();
                int lastBL = BL.getLast();
                assertEquals(lastL, lastBL);

                int removeL = L.removeLast();
                int removeBL = BL.removeLast();
                assertEquals(removeL, removeBL);

            }
        }
    }
}
