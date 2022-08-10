package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T> {

	/** nest class DNode, each node has two ref, 
	 *  one for prevNode, the other for nextNode */
	private static class DNode<T> {
		public T item;
		public DNode<T> prevDNode;
		public DNode<T> nextDNode;

		// constructor for class DNode
		public DNode(T value) {
			item = value;
			prevDNode = null;
			nextDNode = null;
		}
	}

	/** instance variables */
    private int size;
    private DNode<T> sentiFront;
    private DNode<T> sentiBack;
    
    /** Constructor for class LLD. */
    public LinkedListDeque() {
		size = 0;
		sentiFront = new DNode<>(null);
		sentiBack = new DNode<>(null);
		sentiFront.nextDNode = sentiBack;
		sentiBack.prevDNode = sentiFront;
	}

	/** Helper method: insert an item after the parameter prev */
	private void insertNode(T item, DNode<T> prev) {
		DNode<T> oldnext = prev.nextDNode;
		DNode<T> newNode = new DNode<>(item);
		newNode.prevDNode = prev;
		newNode.nextDNode = oldnext;
		oldnext.prevDNode = newNode;
		prev.nextDNode = newNode;
	}

	/** Inserts item into the front of the Deque.
	 *  If item is null, do nothing. @return void.  */
	public void addFirst(T item){
		if(item == null) {
			return;
		} else {
			insertNode(item, sentiFront);
			size++;
		}
	}

	/** Inserts item into the back of the Deque.
	 *  If item is null, do nothing. @return void.  */
	public void addLast(T item) {
		if(item == null) {
			return;
		} else {
			insertNode(item, sentiBack.prevDNode);
			size++;
		}
	}

	/** Helper method: remove an item after the parameter DNode */
	private T removeNext(DNode<T> prev) {
		T item = prev.nextDNode.item;
		prev.nextDNode.nextDNode.prevDNode = prev;
		prev.nextDNode = prev.nextDNode.nextDNode;
		return item;
	}

	/** Removes and returns the item at the front of the Deque.
	 *  @return the items removed.  */
    public T removeFirst() {
    	if(size == 0) {
    		return null;
    	}
    	T first = removeNext(sentiFront);
    	size--;
    	return first;
    }

	/** Removes and returns the item at the back of the Deque.
	 *  @return the items removed.  */
    public T removeLast() {
        if(size == 0) {
    		return null;
    	}
    	T last = removeNext(sentiBack.prevDNode.prevDNode);
    	size--;
    	return last;
    }

    /** @return the item at the given index. If non-exist @return null.
     *  0 is the Front, use iterate. */
    public T get(int index) {
    	if(index > size) {
    		return null;
    	}

        DNode<T> firstNode = sentiFront.nextDNode;
        DNode<T> ithNode = firstNode;
        for(int i = 0; i < index; i++) {
            ithNode = ithNode.nextDNode;
    	}
    	return ithNode.item;
    }

    /** @return the item at the given index. If non-exist @return null.
     *  use recursive. */
    public T getRecursive(int index) {
    	if(index > size) {
    		return null;
    	}

        if(index == 0) {
        	return sentiFront.nextDNode.item;
        } else {
        	return getRecursive(index - 1);
        }
    }

	/** Prints the items in the deque from first to last. */
	public void printDeque() {
		if(size == 0) {
			return;
		}

		DNode<T> node = sentiFront;
		for(int i = 0; i < size - 1; i++) {
			node = node.nextDNode;
			System.out.print(node.item + " ");
		}
		System.out.println(node.item);
	}

    /** @return number if items in the deque. */
    public int size() {
		return size;
    }

	/** @return true if deque is empty. */
	public boolean isEmpty() {
		return size == 0;
	}

	/** create class myLLDIterator which implements interface Iterator  */
	private class MyLLDIterator implements Iterator<T> {
		private int posi = 0;
		public boolean hasNext() {
			return posi < size;
		}
		public T next() {
			T returnItem = get(posi);
			posi++;
			return returnItem;
		}
	}
	/** implements the iterator method in interface Iterable */
	public Iterator<T> iterator() {
		return new MyLLDIterator();
	}

	@Override
	public boolean equals(Object other) {
		// refer to the same obj on heap
		if(this == other) {
			return true;
		}

		// special case
		if(other == null) {
			return false;
		}

		// same class
		if (this.getClass() != other.getClass()) {
			System.out.println("different calss.");
			return false;
		}

		// same size
		LinkedListDeque<T> target = (LinkedListDeque<T>) other;
		if (this.size() != target.size()) {
			return false;
		}

		// same elements and order
		DNode<T> thisNode = this.sentiFront.nextDNode;
		DNode<T> targNode = target.sentiFront.nextDNode;
		for (int i = 0; i < size; i++) {
			if (!thisNode.item.equals(targNode.item)) {
				return false;
			}
		}
		return true;
	}
}