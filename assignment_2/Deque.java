// COURSERA Algorithms 1:
// Assignment 2. Dequeue. A double-ended queue or deque (pronounced "deck") is a generalization of
// a stack and a queue that supports inserting and removing items from either the front or the back 
// of the data structure.

// Sentinel nodes

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int N;
    private Node first, last;
    
    private class Node
    {
        private Item item;
        private Node next, prev;
    }
   
    public Deque()                     // construct an empty deque
    {
        first = null;
        last = null;
        N = 0;
    }
   
    public boolean isEmpty()           // is the deque empty?
    { return N == 0; }
    
    public int size()                  // return the number of items on the deque
    { return N; }
    
    public void addFirst(Item item)    // insert the item at the front
    {
        if (item == null) throw new java.lang.NullPointerException();   
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (isEmpty()) last = first;
        else oldfirst.prev = first;
        N++;
    }
    
    public void addLast(Item item)     // insert the item at the end
    {
        if (item == null) throw new java.lang.NullPointerException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.prev = oldlast;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        N++;   
    }
    
    public Item removeFirst()          // delete and return the item at the front
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;
        N--;
        if (isEmpty()) last = null;
        else first.prev = null;
        return item;
    }
    
    public Item removeLast()           // delete and return the item at the end
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        N--;
        if (isEmpty()) first = null;
        else last.next = null;
        return item;
    }
    
   /**
     * Return an iterator to the stack that iterates through the items in LIFO order.
     */
    public Iterator<Item> iterator()  { return new ListIterator(); }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  } 
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException(); 
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
    
    public static void main(String[] args)
    {
        // test deque
        Deque<Integer> mydeque = new Deque<Integer>();
        StdOut.println("deque size " + mydeque.size());
        StdOut.println("isempty " + mydeque.isEmpty());
        mydeque.addFirst(1);
        mydeque.addLast(1);
        mydeque.removeLast();
        StdOut.println("isempty " + mydeque.isEmpty());

        // test iterator
        Iterator<Integer> mydequeIter = mydeque.iterator();
        while (mydequeIter.hasNext())
        { StdOut.println(mydequeIter.next()); }
        

        
        
//        StdOut.println("deque size " + mydeque.size());
//        mydeque.addLast(4);
//        StdOut.println("remove from last " + mydeque.removeLast());
//        for (int i = 0; i < 5; i++)
//        {
//            StdOut.println(" ");
//            StdOut.println("   new last " + i);
//            mydeque.addLast(i);
//            StdOut.println("deque size " + mydeque.size());
//        }
//        
//        // test iterator
//        Iterator<Integer> mydequeIter = mydeque.iterator();
//        while (mydequeIter.hasNext())
//        { StdOut.println(mydequeIter.next()); }
    }
}