package adt;

public class BinarySearchTree<T extends Comparable<T>> implements BinarySearchTreeInterface<T> {

    private static class Node<T> {

        T value;
        Node<T> left, right;

        Node(T value) {
            this.value = value;
        }
    }
    private Node<T> root;

    @Override
    public void insert(T value) {
        root = insertRec(root, value);
    }

    private Node<T> insertRec(Node<T> node, T value) {
        if (node == null) {
            return new Node<>(value);
        }
        int cmp = value.compareTo(node.value);
        if (cmp < 0) {
            node.left = insertRec(node.left, value);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, value);
        }
        return node;
    }

    @Override
    public boolean contains(T value) {
        return containsRec(root, value);
    }

    private boolean containsRec(Node<T> node, T value) {
        if (node == null) {
            return false;
        }
        int cmp = value.compareTo(node.value);
        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            return containsRec(node.left, value);
        } else {
            return containsRec(node.right, value);
        }
    }

    @Override
    public void inOrderTraversal(Visitor<T> visitor) {
        inOrderRec(root, visitor);
    }

    private void inOrderRec(Node<T> node, Visitor<T> visitor) {
        if (node != null) {
            inOrderRec(node.left, visitor);
            visitor.visit(node.value);
            inOrderRec(node.right, visitor);
        }
    }
}
