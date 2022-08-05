package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }
    @Test
    public void testSquarePrimes2() {
        // prime is at the end
        IntList lst = IntList.of(0, 0, 16, 18, 17, 3);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("0 -> 0 -> 16 -> 18 -> 289 -> 9", lst.toString());
        assertTrue(changed);
    }
    @Test
    public void testSquarePrimes3() {
        // no prime
        IntList lst = IntList.of(14, 15, 16, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 18", lst.toString());
        assertTrue(!changed);
    }
    @Test
    public void testSquarePrimes4() {
        // no prime
        IntList lst = IntList.of(14);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14", lst.toString());
        assertTrue(!changed);
    }
}
