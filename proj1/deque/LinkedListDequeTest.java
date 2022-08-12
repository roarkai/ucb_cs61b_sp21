package deque;

import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class LinkedListDequeTest {
    @Test
    public void testEqual() {
	int n = 1_000_000;
	ArrayDeque<Integer> ad = new ArrayDeque<>();
    LinkedListDeque<Integer> lld = new LinkedListDeque<>();
    LinkedListDeque<Integer> lld2 = new LinkedListDeque<>();

    int size = 0;
    System.out.print(size + " ");
    System.out.print(ad.size() + " ");
    System.out.print(lld.size() + " ");
    System.out.println(lld2.size());
    for (int i = 0; i < n; i++) {
        int flag = (int)(Math.random() * 2);
	    int randn = (int)(Math.random() * 100);
        if (flag == 0) {
	        ad.addFirst(randn);
	        lld.addFirst(randn);
            lld2.addFirst(randn);
            size++;
        } else {
            ad.removeLast();
            lld.removeLast();
            lld2.removeLast();
            if (size == 0) {
                continue;
            } else {
                size--;
            }
        }
    }
    System.out.print(size + " ");
    System.out.print(ad.size() + " ");
    System.out.print(lld.size() + " ");
    System.out.println(lld2.size());
    int k = 0;
	for (int item : ad) {
        boolean x1 = (item == (int)lld.get(k));
        boolean x2 = (item == (int)lld2.get(k));
        assertEquals(true, x1);
        assertEquals(true, x2);
        k++;
	}
    }

    @Test
    public void testGetRecursive() {
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        int n = 10;
        for (int i = 0; i < n; i++) {
            int randn = i;
            lld.addFirst(randn);
        }
        for (int i = 0; i < n; i++) {
            System.out.print(i + " = " + lld.getRecursive(i) + " -> reference ans: ");
            System.out.println(i + " = " + lld.get(i));
        }
    }
    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void sameEleADLLD() {
        Deque<Integer> ad = new ArrayDeque<>();
        Deque<Integer> lld = new LinkedListDeque<>();

        for (int i = 0; i < 10; i++) {
            int randn = i;
            ad.addFirst(randn);
            lld.addFirst(randn);
        }

        boolean same = lld.equals(ad);
        assertEquals(true, same);
    }

    public void addIsEmptySizeTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
		lld1.addFirst("front");

		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

		lld1.addLast("middle");
		assertEquals(2, lld1.size());

		lld1.addLast("back");
		assertEquals(3, lld1.size());

		System.out.println("Printing out deque: ");
		lld1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty
		assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty
		assertFalse("lld1 should contain 1 item", lld1.isEmpty());

		lld1.removeFirst();
		// should be empty
		assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {
        LinkedListDeque<String>  lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double>  lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }
    }

    @Test
        public void equalLLDStringTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();
        LinkedListDeque<String> lld2 = new LinkedListDeque<>();

        // saem obj
        LinkedListDeque<String> lld4 = lld1;
        boolean equalstring14 = lld1.equals(lld4);
        assertEquals(true, equalstring14);

        // empty DDL
        boolean equalstring12 = lld1.equals(lld2);
        assertEquals(true, equalstring12);

		lld1.addFirst("front");
		lld1.addLast("middle");
		lld1.addLast("back");

        lld2.addFirst("front");
		lld2.addLast("middle");
		lld2.addLast("back");

        // same elements
        equalstring12 = lld1.equals(lld2);
        assertEquals(true, equalstring12);

        // different size
        LinkedListDeque<String> lld3 = new LinkedListDeque<>();
        lld3.addFirst("front");
		lld3.addLast("middle");

        boolean equalstring23 = lld2.equals(lld3);
        assertEquals(false, equalstring23);

        // different type
        LinkedListDeque<Integer> lld5 = new LinkedListDeque<>();
        lld5.addFirst(1);
		lld5.addLast(2);

        boolean equalstring35 = lld3.equals(lld5);
        assertEquals(false, equalstring35);
    }

    @Test
    public void iteratorTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

        lld1.addFirst("hello");
        lld1.addFirst("roark");
        lld1.addFirst("well done");

        for(String s : lld1) {
            System.out.print(s + " ");
        }
    }
}
