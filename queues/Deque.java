/* *****************************************************************************
 *  Name: Vikram Bhatt
 *  Date: Mar 14, 2021
 *  Description: Assignment
 **************************************************************************** */


import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    // construct an empty deque
    public Deque() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Deque<Integer> deque = new Deque<>();
        // deque.addFirst(1);
        // deque.addLast(2);
        // deque.addFirst(0);
        // for (Integer integer : deque) {
        //     System.out.println(integer);
        // }
        // deque.removeFirst();
        // deque.removeLast();
        // deque.removeFirst();
        // try {
        //     deque.removeLast();
        // }
        // catch (NoSuchElementException e) {
        //     System.out.println(e.getMessage());
        // }
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.removeLast();
        deque.addLast(2);
        deque.removeFirst();
        try {
            deque.removeLast();
        }
        catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;

        if (last == null) {
            last = first;
        }
        else {
            first.next.previous = first;
        }
        size++;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Empty deque");
        }
        Item item = last.item;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.previous;
            last.next = null;
        }
        size--;
        return item;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldLast;
        if (first == null) {
            first = last;
        }
        else {
            last.previous.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Empty deque");
        }
        Item item = first.item;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.previous = null;
        }
        size--;
        return item;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (first == null) && (last == null) && (size == 0);
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {

        Iterator<Item> itemIterator = new Iterator<Item>() {
            private Node current = first;

            public boolean hasNext() {
                return current != null;
            }

            public Item next() {
                if (!hasNext()) throw new NoSuchElementException("empty deque");
                Item item = current.item;
                current = current.next;
                return item;
            }

            public void remove() {
                throw new UnsupportedOperationException("remove not supported.");
            }
        };
        return itemIterator;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

}