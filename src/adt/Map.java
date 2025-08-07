package adt;

public class Map<K, V> implements MapInterface<K, V> {

    private static class Node<K, V> {

        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    private Node<K, V> head;
    private int size = 0;

    public void put(K key, V value) {
        Node<K, V> curr = head;
        while (curr != null) {
            if (curr.key.equals(key)) {
                curr.value = value;
                return;
            }
            curr = curr.next;
        }
        head = new Node<>(key, value, head);
        size++;
    }

    // Get value by key
    public V get(K key) {
        Node<K, V> curr = head;
        while (curr != null) {
            if (curr.key.equals(key)) {
                return curr.value;
            }
            curr = curr.next;
        }
        return null;
    }

    // Remove by key
    public V remove(K key) {
        Node<K, V> curr = head, prev = null;
        while (curr != null) {
            if (curr.key.equals(key)) {
                if (prev == null) {
                    head = curr.next;
                } else {
                    prev.next = curr.next;
                }
                size--;
                return curr.value;
            }
            prev = curr;
            curr = curr.next;
        }
        return null;
    }

    // Check if key exists
    public boolean containsKey(K key) {
        Node<K, V> curr = head;
        while (curr != null) {
            if (curr.key.equals(key)) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    // Number of key-value pairs
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

    // Filter: returns a new Map<K, V> with entries matching the predicate
    public Map<K, V> filter(Predicate<K, V> predicate) {
        Map<K, V> result = new Map<>();
        Node<K, V> curr = head;
        while (curr != null) {
            if (predicate.test(curr.key, curr.value)) {
                result.put(curr.key, curr.value);
            }
            curr = curr.next;
        }
        return result;
    }

    // Predicate interface for filter
    public interface Predicate<K, V> {
        boolean test(K key, V value);
    }
}
