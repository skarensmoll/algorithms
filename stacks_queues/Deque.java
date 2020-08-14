import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int counter = 0;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null && current.item != null;
        }

        @Override
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return counter;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = null;
        first.previous = null;

        if (oldFirst == null) {
            last = first;
        } else {
            oldFirst.previous = first;
            first.next = oldFirst;
        }

        counter += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = null;

        if (oldLast == null) {
            first = last;
        } else {
            last.previous = oldLast;
            oldLast.next = last;
        }

        counter += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = first.item;
        first = first.next;
        counter -= 1;

        if (counter == 0) {
            last = null;
            first = null;
        }

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node oldLast = last;
        Item item = last.item;
        last = oldLast.previous;
        counter -= 1;
        if (counter != 0) last.next = null;
        else {
            first = null;
            last = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque= new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.removeFirst();
        deque.addLast(4);
        deque.addLast(5);
        deque.removeFirst();
        deque.removeLast();
        deque.removeLast();
        deque.addLast(9);

        Iterator<Integer> iterator = deque.iterator();

        while (iterator.hasNext()) {
            int item = iterator.next();
            StdOut.println("Item: " + item);
        }
    }
}
