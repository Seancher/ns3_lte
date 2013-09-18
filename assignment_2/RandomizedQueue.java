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

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;         // array of items
    private int N;            // number of elements on stack

    // create an empty stack
    public RandomizedQueue() // construct an empty randomized queue
    {
        a = (Item[]) new Object[2];
    }
    
   public boolean isEmpty()           // is the queue empty?
   {
       return N == 0;
   }
   
   public int size()                  // return the number of items on the queue
   {
   return N;
   }
   
    // resize the underlying array holding the elements
    private void resize(int capacity) 
    {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }
   
   public void enqueue(Item item)     // add the item
   {
       if (item == null) throw new java.lang.NullPointerException();
       if (N == a.length) resize(2 * a.length);    // double size of array if necessary
       a[N++] = item;                            // add item
   }
       
   public Item dequeue()              // delete and return a random item
   {
       if (isEmpty()) throw new java.util.NoSuchElementException();
       int rndId = StdRandom.uniform(N);
       Item item = a[rndId];
       a[rndId] = a[N-1];
       a[N-1] = null;
       N--;
       
       // shrink size of array if necessary
       if (N > 0 && N == a.length/4) resize(a.length/2);
       return item;
   }
       
   public Item sample()               // return (but do not delete) a random item
   {
       if (isEmpty()) throw new java.util.NoSuchElementException();
       int rndId = StdRandom.uniform(N);
       return a[rndId];
   }
       
   // return an independent iterator over items in random order 
   public Iterator<Item> iterator()  { return new RandomizedQueueIterator();  }
   
   private class RandomizedQueueIterator implements Iterator<Item> 
   {
       private int i;
       
       public RandomizedQueueIterator() {
           i = N;
       }
       
       public boolean hasNext() {
           return i > 0;
       }
       
       public void remove() {
           throw new UnsupportedOperationException();
       }
       
       public Item next() {
           if (!hasNext()) throw new NoSuchElementException();
           return a[--i];
       }       
   }
   
   
   /***********************************************************************
    * Test routine.
    **********************************************************************/
   public static void main(String[] args) {
       RandomizedQueue<String> s = new RandomizedQueue<String>();
       while (!StdIn.isEmpty()) {
           String item = StdIn.readString();
           if (!item.equals("-")) s.enqueue(item);
           else if (!s.isEmpty()) StdOut.print(s.dequeue() + " ");
           StdOut.print("size " + s.size());
       }
       StdOut.println("(" + s.size() + " left on stack)");
   }
}