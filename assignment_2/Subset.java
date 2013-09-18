// COURSERA Algorithms 1:
// Assignment 2.

public class Subset {
   public static void main(String[] args)
   {
       String item;
       RandomizedQueue<String> s = new RandomizedQueue<String>();
       int k = Integer.parseInt(args[0]);
       while (!StdIn.isEmpty())
       {
           item = StdIn.readString();
           s.enqueue(item);
       }
       for (int i = 0; i < k; i++)
           StdOut.println(s.dequeue());
   }
}

