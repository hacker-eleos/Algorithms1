/* *****************************************************************************
 *  Name: Vikram Bhatt
 *  Date: 14 March, 2021
 *  Description: Randomized Queues
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;
    private int n;
    private Item[] queue;

    // construct an empty randomized queue
    public RandomizedQueue() {
        n = 0;
        queue = (Item[]) new Object[INIT_CAPACITY];
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        System.out.println("Is dequeue empty?: " + queue.isEmpty());
        queue.enqueue(12);
        queue.enqueue(-4);
        queue.enqueue(44);
        queue.enqueue(32);
        for (Integer i : queue) {
            System.out.printf(i + ", ");
        }
        System.out.println();
        for (Integer i : queue) {
            System.out.printf(i + ", ");
        }
        System.out.println();
        // queue.printElems();
        // queue.printElems();
        System.out.println(queue.dequeue());
        // queue.printElems();
        System.out.println(queue.dequeue());
        // queue.printElems();
        System.out.println(queue.sample());
        System.out.println("Size: " + queue.size());
        queue.enqueue(9);
        // queue.printElems();
        // queue.dequeue();
        // queue.printElems();
        for (Integer i : queue) {
            System.out.printf(i + ", ");
        }
        System.out.println();


    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("null element");
        if (n == queue.length) resize(2 * queue.length);    // double size of array if necessary
        queue[n] = item; // add item
        n++;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    // private void printElems() {
    //     for (Item item : queue) {
    //         System.out.print(item + ", ");
    //     }
    //     System.out.println();
    // }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Empty deque");
        int index = StdRandom.uniform(n);
        Item item = queue[index];
        queue[index] = queue[--n];
        queue[n] = null;
        if (n > 0 && (n == queue.length / 4)) resize(queue.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Empty deque");
        int index = StdRandom.uniform(n);
        Item item = queue[index];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int[] indices = new int[n];
        private int i = 0;

        public RandomizedQueueIterator() {
            for (int j = 0; j < n; j++) {
                indices[j] = j;
            }
            StdRandom.shuffle(indices);
        }


        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return i < n;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("empty iterator");
            Item item = queue[indices[i]];
            i++;
            return item;
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.
         * <p>
         * The behavior of an iterator is unspecified if the underlying collection
         * is modified while the iteration is in progress in any way other than by
         * calling this method, unless an overriding class has specified a
         * concurrent modification policy.
         * <p>
         * The behavior of an iterator is unspecified if this method is called
         * after a call to the {@link #forEachRemaining forEachRemaining} method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has
         *                                       already
         *                                       been called after the last call to the {@code
         *                                       next}
         *                                       method
         * @implSpec The default implementation throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("call to remove");
        }
    }

}