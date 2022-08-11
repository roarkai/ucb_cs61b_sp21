package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> myComparator;

    public MaxArrayDeque(Comparator<T> c) {
        myComparator = c;
    }

    public T max() {
        return max(myComparator);
    }

    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }

        int maxIndex = 0;
        for (int i = 0; i < this.size(); i++) {
            if (c.compare(this.get(i), this.get(maxIndex)) > 0) {
                maxIndex = i;
            }
        }
        return this.get(maxIndex);
    }

}
