public class KdTree {
    private int size;
    private Node headNode;
    private Point2D closestP;
    
    public KdTree() {                              // construct an empty set of points
        size = 0;
        headNode = null;
        closestP = null;
    }
    
    public boolean isEmpty() {                       // is the set empty?
        return size == 0;
    }
    
    public int size() {                              // number of points in the set
        return size;
    }
    
    public void insert(Point2D p) {                  // add the point p to the set (if it is not already in the set)
        Node trvlHead;
        if (!contains(p)) {
            if (size == 0) {
                headNode = new Node(p, new RectHV(0, 0, 1, 1), false);
                headNode.lb = new Node(new RectHV(0, 0, p.x(), 1), true);
                headNode.rb = new Node(new RectHV(p.x(), 0, 1, 1), true);
            }
            else {
                trvlHead = go2RightSt(p, headNode);
                while (trvlHead.p != null)
                    trvlHead = go2RightSt(p, trvlHead);
                trvlHead.p = p;
                insertSubNodes(p, trvlHead);
            }
            size++;
        }
    }
    
    private void insertSubNodes(Point2D p, Node n) {
        double xl = n.rect.xmin();
        double xh = n.rect.xmax();
        double yl = n.rect.ymin();
        double yh = n.rect.ymax();
        if (!n.lvlX) {
            double xs = p.x();
            n.lb = new Node(new RectHV(xl, yl, xs, yh), !n.lvlX);
            n.rb = new Node(new RectHV(xs, yl, xh, yh), !n.lvlX);
        }
        if (n.lvlX) {
            double ys = p.y();
            n.lb = new Node(new RectHV(xl, yl, xh, ys), !n.lvlX);
            n.rb = new Node(new RectHV(xl, ys, xh, yh), !n.lvlX);
        }
    }
    
    public boolean contains(Point2D p) {              // does the set contain the point p?
        if (size == 0) return false;
        Point2D nearestP = nearest(p);
        return nearestP.equals(p);
    }
    
    public void draw() {                             // draw all of the points to standard draw
        drawPntLines(headNode);
    }
    
    private void drawPntLines(Node n) {
        if (n.p != null) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.05);
            n.p.draw();
            StdDraw.setPenRadius(.02);
            if (!n.lvlX) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
            }
            drawPntLines(n.lb);
            drawPntLines(n.rb);
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {     // all points in the set that are inside the rectangle
        Queue<Point2D> q = new Queue<Point2D>();
        if (size == 0) 
            return null;
        return rangeSearch(rect, headNode, q);
    }
     
    private Queue<Point2D> rangeSearch(RectHV rect, Node headNode, Queue<Point2D> q) {
        RectHV lR = headNode.lb.rect;
        RectHV rR = headNode.rb.rect;
        Queue<Point2D> qout = q;
        if (rect.intersects(lR) & rect.intersects(rR)) {
            if (rect.contains(headNode.p))
                qout.enqueue(headNode.p);
            if (headNode.lb.p != null)
                qout = rangeSearch(rect, headNode.lb, qout);
            if (headNode.rb.p != null)
                qout = rangeSearch(rect, headNode.rb, qout);
        }

        if (rect.intersects(lR) & !(rect.intersects(rR))) {
            if (headNode.lb.p != null)
                qout = rangeSearch(rect, headNode.lb, qout);
        }
        
        if (!(rect.intersects(lR)) & rect.intersects(rR)) {
            if (headNode.rb.p != null)
                qout = rangeSearch(rect, headNode.rb, qout);
        }
        return qout;
    }
    
    public Point2D nearest(Point2D trgP) {              // a nearest neighbor in the set to p; null if set is empty
        Point2D headP;
        if (size == 0)
            return null;
        closestP = headNode.p;
        searchNearest(trgP, headNode);
        return closestP;
    }
        
    private void searchNearest(Point2D trgP, Node headNode) {
        Node mjrNode = null, mnrNode = null;
        RectHV lRect = headNode.lb.rect;
        RectHV rRect = headNode.rb.rect;

        if (lRect.distanceSquaredTo(trgP) < rRect.distanceSquaredTo(trgP)) {
            mjrNode = headNode.lb;
            mnrNode = headNode.rb;
        }
        else {
            mjrNode = headNode.rb;
            mnrNode = headNode.lb;
        }
    
        if (trgP.distanceSquaredTo(headNode.p) < trgP.distanceSquaredTo(closestP))
            closestP = headNode.p;
        
        if (mjrNode.p != null) {
            if (trgP.distanceSquaredTo(mjrNode.p) < trgP.distanceSquaredTo(closestP))
                closestP = mjrNode.p;
            searchNearest(trgP, mjrNode);
        }
        if ((mnrNode.p != null) & (mnrNode.rect.distanceSquaredTo(trgP) < closestP.distanceSquaredTo(trgP)))
            searchNearest(trgP, mnrNode);
    }
    
    private Node go2RightSt(Point2D p, Node n) {
        double valN = 0, valP = 0;
        if (!n.lvlX) {
            valN = n.p.x();
            valP = p.x();
        }
        if (n.lvlX) {
            valN = n.p.y();
            valP = p.y();
        }
        // choose sub-tree
        if (valP <= valN) // left sub-tree
            return n.lb;
        else return n.rb; // right sub-tree
    }
    
    private Node go2LeftSt(Point2D p, Node n) {
        double valN = 0, valP = 0;
        if (!n.lvlX) {
            valN = n.p.x();
            valP = p.x();
        }
        if (n.lvlX) {
            valN = n.p.y();
            valP = p.y();
        }
        // choose sub-tree
        if (valP <= valN) // right sub-tree
            return n.rb;
        else return n.lb; // left sub-tree
    }
    
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb, rb;    // the left/bottom subtree
        private boolean lvlX;      // 0-x; 1-y;
        
        public Node(Point2D p, RectHV rect, boolean lvlX) {
            this.p = p;
            this.rect = rect;
            this.lvlX = lvlX;
        }
        public Node(RectHV rect, boolean lvlX) {
            this.rect = rect;
            this.lvlX = lvlX;
        }
    }
    
     /**
     * Unit tests the point data type.
     */
    public static void main(String[] args) {
        KdTree kdtree = new KdTree();
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
        
        RectHV mRect = new RectHV(0.5, 0.5, 1, 1);
        boolean myb = mRect.contains(p);
        StdOut.println(myb);
        q = kdtree.range(mRect);

        for (Point2D point : q) {
            StdOut.println(point.toString());
        }
    }
}