
public class PointSET {
    private SET<Point2D> set;
    
    public PointSET() {                              // construct an empty set of points
        set = new SET<Point2D>();
    }
    
    public boolean isEmpty() {                       // is the set empty?
        return set.isEmpty();
    }
    
    public int size() {                              // number of points in the set
        return set.size();
    }
    
    public void insert(Point2D p) {                  // add the point p to the set (if it is not already in the set)
        set.add(p);
    }
    
    public boolean contains(Point2D p) {              // does the set contain the point p?
        return set.contains(p);
    }
    
    public void draw() {                             // draw all of the points to standard draw
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.05);
        for (Point2D p : set)
            p.draw(); 
    }
    
    public Iterable<Point2D> range(RectHV rect) {     // all points in the set that are inside the rectangle
        Queue<Point2D> q = new Queue<Point2D>();
        for (Point2D p : set)
            if (rect.contains(p))
                q.enqueue(p);
        return q;
    }
    
    public Point2D nearest(Point2D trgP) {              // a nearest neighbor in the set to p; null if set is empty
        if (isEmpty())
            return null;
        Point2D curP = null;
        for (Point2D p : set) {
            if (curP == null)
                curP = p;
            if (curP.distanceSquaredTo(trgP) > p.distanceSquaredTo(trgP))
                curP = p;
        }
        return curP;
    }
    
    public static void main(String[] args) {
        PointSET kdtree = new PointSET();
        Point2D p = new Point2D(.5, .5);
        Point2D p1 = new Point2D(.35, .15);
        Point2D p2 = new Point2D(.45, .5);
        Point2D p3 = new Point2D(.5, .15);
        Point2D p4 = new Point2D(.15, .5);
        Point2D p5 = new Point2D(.3, .3);
        Point2D p6 = new Point2D(.3, .3);
        kdtree.insert(p);
        kdtree.insert(p1);
        kdtree.insert(p2);
        kdtree.insert(p3);
        kdtree.insert(p4);
        kdtree.insert(p5);
        kdtree.insert(p6);
        Iterable<Point2D> q;
        
        RectHV mRect = new RectHV(0, 0, 1, 1);
        q = kdtree.range(mRect);

        for (Point2D point : q) {
            StdOut.println(point.toString());
        }
    }
}