package adt;

public interface BinarySearchTreeInterface<T extends Comparable<T>> {

    void insert(T value);

    boolean contains(T value);

    void inOrderTraversal(Visitor<T> visitor);

    interface Visitor<T> {

        void visit(T value);
    }
}
