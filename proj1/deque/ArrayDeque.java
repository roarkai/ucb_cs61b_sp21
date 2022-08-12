package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {

    /** instance variables */
    private int size;
    private int capacity;
    private int nextFirst;
    private int nextLast;
    private T[] items;
    private int resizeFactor;

    // can be any index of the array, the first item would take this position
    private int newstart = 4;

    /** Constructor */
    public ArrayDeque() {
        capacity = 8;
        resizeFactor = 2;
        items = (T[]) new Object[capacity];
        size = 0;
        nextFirst = newstart;
        nextLast = newstart + 1;
    }

    /** Inserts item into the position at nextFirst.
     *  If item is null, do nothing. If items is full, double array's capcity. */
    public void addFirst(T item) {
        if (item == null) {
            return;
        }

        items[nextFirst] = item;
        size++;
        if (size == capacity) {
            // resize and reset the position of nextFirst and nextLast
            resize(size * 2, nextFirst);
        } else {
            // just update index of nextFirst and nextLast, forward <-
            nextFirst = onePosiForward(nextFirst);
        }

    }

    /** Inserts item into the back of the Deque.
     *  If item is null, do nothing. @return void.  */
    public void addLast(T item) {
        if (item == null) {
            return;
        }

        items[nextLast] = item;
        size++;
        if (size == capacity) {
            // resize and reset the position of nextFirst and nextLast
            resize(size * 2, firstIndex());
        } else {
            // just update index of nextLast, back ->
            nextLast = onePosiBack(nextLast);
        }
    }

    /** Removes and returns the item at the front of the Deque.
     *  @return the items removed. */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        int firstInd = firstIndex();
        T firstItem = items[firstInd];
        items[firstInd] = null;
        nextFirst = firstInd;
        size--;

        if (capacity > 8 && size < capacity / resizeFactor) {
            resize(capacity / resizeFactor, firstIndex());
        }
        return firstItem;
    }

    /** Removes and returns the item at the back of the Deque.
     *  @return the items removed.  */
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        int lastInd = onePosiForward(nextLast);
        T lastItem = items[lastInd];
        items[lastInd] = null;
        nextLast = lastInd;
        size--;

        if (capacity > 8 && size < (capacity / resizeFactor)) {
            resize(capacity / resizeFactor, firstIndex());
        }
        return lastItem;
    }

    /** @return the item at the given index. If non-exist @return null.
     *  0 is the Front, use iterate. */
    public T get(int index) {
        if (index > size) {
            return null;
        }

        int posi = (firstIndex() + index) % capacity;
        return items[posi];
    }

    /** Prints the items in the deque from first to last. */
    public void printDeque() {
        if (size == 0) {
            return;
        }

        int firstInd = firstIndex();
        for (int i = 0; i < size - 1; i++) {
            System.out.print(items[(firstInd + i) % capacity] + " ");
        }
        System.out.println(items[(firstInd + size - 1) % capacity]);
    }

    /** @return number if items in the deque. */
    public int size() {
        return size;
    }

    /** create class myLLDIterator which implements interface Iterator  */
    private class MyADIterator implements Iterator<T> {
        private int posi = 0;
        public boolean hasNext() {
            return get(posi + 1) != null;
        }
        public T next() {
            T returnItem = get(posi);
            posi = onePosiBack(posi);
            return returnItem;
        }
    }
    /** implements the iterator method in interface Iterable */
    public Iterator<T> iterator() {
        return new MyADIterator();
    }

    @Override
    public boolean equals(Object other) {
        // refer to the same obj on heap
        if (this == other) {
            return true;
        }

        // special case
        if (other == null) {
            return false;
        }

        // same class
        if (!(other instanceof Deque)) {
            return false;
        }

        // same size
        Deque<T> target = (Deque<T>) other;
        if (this.size() != target.size()) {
            return false;
        }

        // same elements and order
        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(target.get(i))) {
                return false;
            }
        }
        return true;
    }

    private int firstIndex() {
        return onePosiBack(nextFirst);
    }

    /** Helper fuction, get the previous index, suppose move circularly */
    private int onePosiForward(int index) {
        int prevPosi = (index + capacity - 1) % capacity;
        return prevPosi;
    }
    /** Helper fuction, get the next index, suppose move circularly */
    private int onePosiBack(int index) {
        int nextPosi = (index + 1) % capacity;
        return nextPosi;
    }

    /** Helper fuction, given nextLast and size, count the position of nextFirst */
    private int resetnextLast(int startInd) {
        int nextlastInd = (startInd + size + 1) % capacity;
        return nextlastInd;
    }

    /** Helper function, double the size of the items
     *  put the first item of the old array at the newstart of the newarray. */
    private void resize(int newCap, int first) {
        T[] newItems = (T[]) new Object[newCap];
        for (int i = 0; i < size; i++) {
            newItems[(i + newstart) % newCap] = items[(i + first) % capacity];
        }
        items = newItems;
        capacity = newCap;

        // nextFirst doesn't take (newstart - 1), in case newstart set to 0
        nextFirst = (newstart + capacity - 1) % capacity;
        nextLast = resetnextLast(nextFirst);
    }
}

