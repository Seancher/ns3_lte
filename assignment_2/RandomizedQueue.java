// COURSERA Algorithms 1:
// Randomized queue. A randomized queue is similar to a stack or queue, except that 
// the item removed is chosen uniformly at random from items in the data structure.
// Create a generic data type RandomizedQueue that implements the following API:
//
//public class RandomizedQueue<Item> implements Iterable<Item> {
//   public RandomizedQueue()           // construct an empty randomized queue
//   public boolean isEmpty()           // is the queue empty?
//   public int size()                  // return the number of items on the queue
//   public void enqueue(Item item)     // add the item
//   public Item dequeue()              // delete and return a random item
//   public Item sample()               // return (but do not delete) a random item
//   public Iterator<Item> iterator()   // return an independent iterator over items in random order
//}

// Sentinel nodes

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N;
    private Node first, last;
    
    private class Node
    {
        private Item item;
        private Node next, prev;
    }
   
    public RandomizedQueue()                     // construct an empty RandomizedQueue
    {
        first = null;
        last = null;
        N = 0;
    }
   
    public boolean isEmpty()           // is the RandomizedQueue empty?
    { return N == 0; }
    
    public int size()                  // return the number of items on the RandomizedQueue
    { return N; }
    
    public void enqueue(Item item)    // add the item
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
    
    private Item removeFirst()          // delete and return the item at the front
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;
        N--;
        if (isEmpty()) last = null;
        else first.prev = null;
        return item;
    }
    
    private Item removeLast()           // delete and return the item at the end
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        N--;
        if (isEmpty()) first = null;
        else last.next = null;
        return item;
    }
    
     public Item dequeue()          // delete and return a random item
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int rndId = StdRandom.uniform(N) + 1;
        Iterator<Item> sampleIter = iterator();
        Node prevNode, curNode, nextNode;
        curNode = first;
        for (int i = 1; i < rndId; i++)
        {
            curNode = curNode.next;
        }
        
        if (curNode.next == null) removeLast();
        else
        {
            if (curNode.prev == null) removeFirst();
            else
            {        
                prevNode = curNode.prev;
                nextNode = curNode.next;
                prevNode.next = nextNode;
                nextNode.prev = prevNode;
                N--;
            }
        }
        return curNode.item;
    }
    
    public Item sample()          // return (but do not delete) a random item
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int rndId = StdRandom.uniform(N) + 1;
        Iterator<Item> sampleIter = iterator();
        for (int i = 1; i < rndId; i++)
        {
            sampleIter.next();
        }
        return sampleIter.next();
    }
    
   /**
     * Return an iterator to the stack that iterates through the items in LIFO order.
     */
    public Iterator<Item> iterator()  { return new ListIterator();  }

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
        RandomizedQueue<Integer> myRndQueue = new RandomizedQueue<Integer>();
        StdOut.println("RandomizedQueue size " + myRndQueue.size());
        myRndQueue.enqueue(4);
        for (int i = 0; i < 5; i++)
        {
            StdOut.println(" ");
            StdOut.println("   new first " + i);
            myRndQueue.enqueue(i);
            StdOut.println("RandomizedQueue size " + myRndQueue.size());
        }
        
        // test iterator
        StdOut.println("TEST ITERATOR");
        Iterator<Integer> myRndQIter = myRndQueue.iterator();
        while (myRndQIter.hasNext())
        { StdOut.println(myRndQIter.next()); }
        
        // test sample
//        StdOut.println("TEST SAMPLE");
//        for (int i = 0; i < 5; i++)
//        {
//            StdOut.println(" test random sample");
//            StdOut.println("sample value " + myRndQueue.sample());
//        }
        
        // test deque
        StdOut.println("RANDOM DEQUEUE TEST");
        for (int i = 0; i < 6; i++)
        {       
            StdOut.println("size " + myRndQueue.size());
            StdOut.println("random dequeue " + myRndQueue.dequeue());
        }
    }
}