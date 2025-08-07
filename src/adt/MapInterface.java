package adt;

public interface MapInterface<K, V> {

    void put(K key, V value); // Add or update a key-value pair

    V get(K key); // Get value by key

    V remove(K key); // Remove by key, return value or null

    boolean containsKey(K key); // Check if key exists

    int size(); // Number of key-value pairs

    boolean isEmpty(); // Is map empty

    void clear(); // Remove all entries
}
