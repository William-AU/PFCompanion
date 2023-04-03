package application.model.datastructures;

import java.util.List;

/**
 * A simple implementation of a circular doubly linked list.
 * Only implements traversing and instantiation from a {@link java.util.List}
 */
public class CircularDoublyLinkedList<E> {
    private Node<E> head = null;
    private Node<E> current = null;
    public CircularDoublyLinkedList(List<E> list) {
        if (list.isEmpty()) throw new IllegalArgumentException("Cannot instantiate empty CircularDoublyLinkedList");
        list.forEach(this::addNode);
        current = head;
    }

    public E traverseForwards() {
        current = current.next;
        return current.item;
    }

    public E traverseBackwards() {
        current = current.previous;
        return current.item;
    }

    public E getCurrentElement() {
        return current.item;
    }

    private void addNode(E toAdd) {
        if (head == null) {
            Node<E> newNode = new Node<>(toAdd);
            head = newNode;
            newNode.previous = newNode.next = newNode;
            return;
        }
        Node<E> last = head.previous;
        Node<E> newNode = new Node<>(toAdd);
        newNode.next = head;
        head.previous = newNode;
        newNode.previous = last;
        last.next = newNode;
    }



    private static class Node <T> {
        private T item;
        private Node<T> previous;
        private Node<T> next;

        public Node(T item) {
            this.item = item;
        }

        @Override
        public String toString() {
            return item.toString();
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        Node temp = head;
        String prefix = "";
        while (temp.next != head) {
            stringBuilder.append(prefix).append(temp.item);
            prefix = ", ";
            temp = temp.next;
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
