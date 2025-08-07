package adt;

public class List<T> implements ListInterface<T> {

    private static class Node<T> {

        T value;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }
    private Node<T> head;
    private int size = 0;

    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = node;
        } else {
            Node<T> curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = node;
        }
        size++;
    }

    public T get(int index) {
        checkIndex(index);
        Node<T> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.value;
    }

    public void set(int index, T value) {
        checkIndex(index);
        Node<T> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        curr.value = value;
    }

    public T remove(int index) {
        checkIndex(index);
        if (index == 0) {
            T val = head.value;
            head = head.next;
            size--;
            return val;
        }
        Node<T> prev = head;
        for (int i = 0; i < index - 1; i++) {
            prev = prev.next;
        }
        T val = prev.next.value;
        prev.next = prev.next.next;
        size--;
        return val;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        head = null;
        size = 0;
    }

    // Search for the first index of a value (using equals)
    public int indexOf(T value) {
        Node<T> curr = head;
        int idx = 0;
        while (curr != null) {
            if (curr.value.equals(value)) {
                return idx;
            }
            curr = curr.next;
            idx++;
        }
        return -1;
    }

    // Filter: returns a new List<T> with elements matching the predicate
    public List<T> filter(Predicate<T> predicate) {
        List<T> result = new List<>();
        Node<T> curr = head;
        while (curr != null) {
            if (predicate.test(curr.value)) {
                result.add(curr.value);
            }
            curr = curr.next;
        }
        return result;
    }

    // Sort: simple bubble sort (requires Comparable)
    public void sort() {
        if (size < 2) return;
        boolean swapped;
        do {
            swapped = false;
            Node<T> curr = head;
            while (curr != null && curr.next != null) {
                Comparable<T> a = (Comparable<T>) curr.value;
                T b = curr.next.value;
                if (a.compareTo(b) > 0) {
                    T tmp = curr.value;
                    curr.value = curr.next.value;
                    curr.next.value = tmp;
                    swapped = true;
                }
                curr = curr.next;
            }
        } while (swapped);
    }

    // Predicate interface for filter
    public interface Predicate<T> {
        boolean test(T value);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
