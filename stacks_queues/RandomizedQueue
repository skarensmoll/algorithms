import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] bag;
    private int counter = 0;
    private int tail;

    // construct an empty randomized queue
    public RandomizedQueue() {
        resize(1);
    }

    private void resize(int newCapacity) {
        Item[] newBag = (Item[]) new Object[newCapacity];

        if (bag != null) {
            for (int i = 0; i < counter; i++) {
                newBag[i] = bag[i];
            }
        }

        bag = newBag;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return counter == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return counter;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (counter >= bag.length) {
            resize(bag.length * 2);
        }

        counter++;
        tail = counter - 1;
        bag[tail] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int randomPosition = StdRandom.uniform(0, counter);
        Item item = bag[randomPosition];
        bag[randomPosition] = bag[tail];
        bag[tail] = null;

        counter--;
        tail--;

        if (counter == 0) return item;

        double totalCapacity = bag.length;
        double actualCapacity = counter;
        double percentage = (actualCapacity / totalCapacity);

        if (percentage <= 0.25) {
            resize(bag.length / 2);
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int randomNum = StdRandom.uniform(0, counter);
        return bag[randomNum];
    }

    private class ListIterator implements Iterator<Item> {
        int sum = counter;
        Item[] bagCopy = bag;

        @Override
        public boolean hasNext() {
            return sum > 0;
        }

        @Override
        public Item next() {
            if (sum == 0) {
                throw new NoSuchElementException();
            }
            int randomNum = StdRandom.uniform(0, sum);
            Item item = bagCopy[randomNum];
            bagCopy[randomNum] = bagCopy[sum - 1];
            sum--;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue("S");
        randomizedQueue.enqueue("K");
        randomizedQueue.enqueue("A");
        randomizedQueue.enqueue("R");
        randomizedQueue.dequeue();
        StdOut.println("size: " + randomizedQueue.size());
    }
}
