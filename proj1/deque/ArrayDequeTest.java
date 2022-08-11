package deque;

import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class ArrayDequeTest {

    @Test
    /** compare a AD with a LLD, when they hava same elements, they should be equal*/
    public void sameEleADLLD() {
        Deque<Integer> ad = new ArrayDeque<>();
        Deque<Integer> lld = new LinkedListDeque<>();

        for (int i = 0; i < 10; i++) {
            int randn = i;
            ad.addFirst(randn);
            lld.addFirst(randn);
        }

        boolean same = ad.equals(lld);
        assertEquals(true, same);
    }

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
        ArrayDeque<String> ad1 = new ArrayDeque<String>();

        assertTrue("A newly initialized adeque should be empty", ad1.isEmpty());
        ad1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, ad1.size());
        assertFalse("ad1 should now contain 1 item", ad1.isEmpty());

        ad1.addLast("middle");
        assertEquals(2, ad1.size());

        ad1.addLast("back");
        assertEquals(3, ad1.size());

        System.out.println("Printing out deque: ");
        ad1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        // should be empty
        assertTrue("ad1 should be empty upon initialization", ad1.isEmpty());

        ad1.addFirst(10);
        // should not be empty
        assertFalse("ad1 should contain 1 item", ad1.isEmpty());

        ad1.removeFirst();
        // should be empty
        assertTrue("ad1 should be empty after removal", ad1.isEmpty());
    }

    @Test
    /* Check if you can create ArrayDeques with different parameterized types*/
    public void multipleParamTest() {
        ArrayDeque<String>  ad1 = new ArrayDeque<String>();
        ArrayDeque<Double>  ad2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> ad3 = new ArrayDeque<Boolean>();

        ad1.addFirst("string");
        ad2.addFirst(3.14159);
        ad3.addFirst(true);

        String s = ad1.removeFirst();
        double d = ad2.removeFirst();
        boolean b = ad3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty ArrayDeque. */
    public void emptyNullReturnTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, ad1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, ad1.removeLast());
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigadequeTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 1_000_000; i++) {
            ad1.addLast(i);
        }

        for (double i = 0; i < 500_000; i++) {
            assertEquals("Should have the same value", i, (double) ad1.removeFirst(), 0.0);
        }

        for (double i = 999_999; i > 500_000; i--) {
            assertEquals("Should have the same value", i, (double) ad1.removeLast(), 0.0);
        }
    }



    @Test
    public void equaladStringTest() {


        ArrayDeque<String> ad1 = new ArrayDeque<>();
        ArrayDeque<String> ad2 = new ArrayDeque<>();

        // saem obj
        ArrayDeque<String> ad4 = ad1;
        boolean equalstring14 = ad1.equals(ad4);
        assertEquals(true, equalstring14);

        // empty DDL
        boolean equalstring12 = ad1.equals(ad2);
        assertEquals(true, equalstring12);

        ad1.addFirst("front");
        ad1.addLast("middle");
        ad1.addLast("back");

        ad2.addFirst("front");
        ad2.addLast("middle");
        ad2.addLast("back");

        // same elements
        equalstring12 = ad1.equals(ad2);
        assertEquals(true, equalstring12);

        // different size
        ArrayDeque<String> ad3 = new ArrayDeque<>();
        ad3.addFirst("front");
        ad3.addLast("middle");

        boolean equalstring23 = ad2.equals(ad3);
        assertEquals(false, equalstring23);

        // different type
        ArrayDeque<Integer> ad5 = new ArrayDeque<>();
        ad5.addFirst(1);
        ad5.addLast(2);

        boolean equalstring35 = ad3.equals(ad5);
        assertEquals(false, equalstring35);
    }

    @Test
    public void iteratorTest() {
        ArrayDeque<String>  ad1 = new ArrayDeque<String>();

        ad1.addFirst("hello");
        ad1.addFirst("roark");
        ad1.addFirst("well done");

        for(String s : ad1) {
            System.out.print(s + " ");
        }

    }
}