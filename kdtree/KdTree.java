/* *****************************************************************************
 *  Name: Vikram Bhatt
 *  Date: 27 March, 2021
 *  Description: Kd-Trees Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final int VERTICAL = 1;
    private static final int HORIZONTAL = 0;
    private Node root;

    public KdTree() {

    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        System.out.println(kdtree.size());
        kdtree.draw();
    }

    public boolean isEmpty() {
        return size() == 0;
    }                      // is the set empty?

    public int size() {
        return size(root);
    } // number of points in the set

    private int size(Node node) {
        if (node == null) return 0;
        else return node.size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("insert point null");
        }
        if (root == null) {
            root = new Node(p,1, VERTICAL);
            root.size = 1;
        }
        else root = insert(root, p, root.orientation);
    }        // add the point to the set (if it is not already in the set)

    private Node insert(Node x, Point2D p, int parentOrientation) {
        if (x == null)
            return new Node(p,1, parentOrientation == VERTICAL ? HORIZONTAL : VERTICAL);
        int cmp = x.orientation == VERTICAL ? Point2D.X_ORDER.compare(p, x.point2D) :
                  Point2D.Y_ORDER.compare(p, x.point2D);
        if (cmp < 0) x.left = insert(x.left, p, x.orientation);
        else if (cmp > 0) x.right = insert(x.right, p, x.orientation);
        else x.point2D = p;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public boolean contains(Point2D p) {
        return false;
    }    // does the set contain point p?

    public void draw() {
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        StdDraw.setScale(-0.05, 1.05);
        StdDraw.square(0.5, 0.5, 0.5);
        StdDraw.enableDoubleBuffering();
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            StdDraw.setPenRadius(0.01);
            StdDraw.point(x.point2D.x(), x.point2D.y());
            if (x.orientation == VERTICAL) {
                StdDraw.setPenRadius();
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(x.point2D.x(), 0.0, x.point2D.x(), 1.0);
                StdDraw.setPenColor();
            }
            if (x.orientation == HORIZONTAL) {
                StdDraw.setPenRadius();
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(0.0, x.point2D.y(), 1.0, x.point2D.y());
                StdDraw.setPenColor();
            }
            queue.enqueue(x.left);
            queue.enqueue(x.right);
            StdDraw.show();
        }
    }         // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> keys = new Queue<Point2D>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            if (rect.contains(x.point2D)) keys.enqueue(x.point2D);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }      // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        return null;
    }         // a nearest neighbor in the set to point p; null if the set is empty

    private class Node {
        private Point2D point2D;
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree
        private int orientation;

        public Node(Point2D point2D, int size, int orientation) {
            assert orientation == VERTICAL || orientation == HORIZONTAL;
            this.point2D = point2D;
            this.size = size;
            this.orientation = orientation;
        }

        public String toString() {
            return "Node{" +
                    "point2D=" + point2D +
                    ", left=" + left +
                    ", right=" + right +
                    ", size=" + size +
                    ", orientation=" + orientation +
                    '}';
        }
    }
}
