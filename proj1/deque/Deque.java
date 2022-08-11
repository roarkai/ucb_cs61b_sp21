package deque;

public interface Deque<T> {
    // $$no need for 'public'
    void addFirst(T item);
    void addLast(T item);
    default public boolean isEmpty() {
		return size() == 0;
    }
    int size();
    void printDeque();
    T removeFirst();
    T removeLast();
    T get(int index);
}
