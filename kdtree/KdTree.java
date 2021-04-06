/* *****************************************************************************
 *  Name: Vikram Bhatt
 *  Date: 27 March, 2021
 *  Description: Kd-Trees Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

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
            System.out.println(kdtree.contains(p));
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
            root = new Node(p, 1, VERTICAL, new RectHV(0.0, 0.0, 1.0, 1.0));
        }
        else root = insert(root, null, p);
    }        // add the point to the set (if it is not already in the set)

    private Node insert(final Node x, final Node parent, final Point2D p) {
        if (x == null) return parent.getNewChildNode(p);
        int cmp = x.compareTo(p);
        if (cmp < 0) x.left = insert(x.left, x, p);
        else x.right = insert(x.right, x, p);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("null argument");
        }
        return contains(root, p);
    }    // does the set contain point p?

    private boolean contains(final Node node, final Point2D p) {
        if (node == null) return false;
        if (node.point2D.compareTo(p) == 0) return true;
        int cmp = node.compareTo(p);
        if (cmp < 0) return contains(node.left, p);
        else return contains(node.right, p);
    }


    public void draw() {
        StdDraw.setScale(-0.05, 1.05);
        StdDraw.square(0.5, 0.5, 0.5);
        draw(root, 0.0, 0.0, 1.0, 1.0);
    }         // draw all points to standard draw

    private void draw(Node n, double xMin, double yMin, double xMax, double yMax) {
        if (n == null) return;
        StdDraw.setPenRadius(0.02);
        StdDraw.point(n.point2D.x(), n.point2D.y());
        StdDraw.setPenRadius();
        if (n.orientation == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.point2D.x(), yMin, n.point2D.x(), yMax);
            StdDraw.setPenColor();
            draw(n.left, xMin, yMin, n.point2D.x(), yMax);
            draw(n.right, n.point2D.x(), yMin, xMax, yMax);
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xMin, n.point2D.y(), xMax, n.point2D.y());
            StdDraw.setPenColor();
            draw(n.left, xMin, yMin, xMax, n.point2D.y());
            draw(n.right, xMin, n.point2D.y(), xMax, yMax);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) return new ArrayList<>();
        return range(root, rect);
    }      // all points that are inside the rectangle (or on the boundary)


    private ArrayList<Point2D> range(Node node, RectHV rect) {
        ArrayList<Point2D> points = new ArrayList<>();
        if (node.rect.intersects(rect)) {
            if (rect.contains(node.point2D)) points.add(node.point2D);
            if (node.left != null) {
                points.addAll(range(node.left, rect));
            }
            if (node.right != null) {
                points.addAll(range(node.right, rect));
            }
        }
        return points;
    }


    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root != null) return nearest(root, p, root.point2D);
        return null;
    }         // a nearest neighbor in the set to point p; null if the set is empty

    private Point2D nearest(Node node, Point2D query, Point2D currentNearest) {
        if (node.point2D.equals(query)) return query;
        double currentNearestDistance = currentNearest.distanceSquaredTo(query);
        if (node.rect.distanceSquaredTo(query) > currentNearestDistance) return currentNearest;
        else {
            if (node.point2D.distanceSquaredTo(query) < currentNearestDistance) {
                currentNearest = node.point2D;
            }
            int cmp = node.compareTo(query);
            if (cmp < 0) {
                if (node.left != null) currentNearest = nearest(node.left, query, currentNearest);
                if (node.right != null) currentNearest = nearest(node.right, query, currentNearest);
            }
            else {
                if (node.right != null) currentNearest = nearest(node.right, query, currentNearest);
                if (node.left != null) currentNearest = nearest(node.left, query, currentNearest);
            }
        }
        return currentNearest;
    }


    private class Node implements Comparable<Point2D> {
        private final Point2D point2D;
        private final int orientation;
        private final RectHV rect;
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree

        public Node(Point2D point2D, int size, int orientation, RectHV rect) {
            assert orientation == VERTICAL || orientation == HORIZONTAL;
            this.point2D = point2D;
            this.size = size;
            this.orientation = orientation;
            this.rect = rect;
        }

        public Node getNewChildNode(Point2D p) {
            int cmp = this.compareTo(p);
            if (orientation == VERTICAL) {
                if (cmp < 0) return new Node(p, 1, HORIZONTAL,
                                             new RectHV(rect.xmin(), rect.ymin(), point2D.x(),
                                                        rect.ymax()));
                else return new Node(p, 1, HORIZONTAL,
                                     new RectHV(point2D.x(), rect.ymin(), rect.xmax(),
                                                rect.ymax()));
            }
            else {
                if (cmp < 0) return new Node(p, 1, VERTICAL,
                                             new RectHV(rect.xmin(), rect.ymin(), rect.xmax(),
                                                        point2D.y()));
                else return new Node(p, 1, VERTICAL,
                                     new RectHV(rect.xmin(), point2D.y(), rect.xmax(),
                                                rect.ymax()));
            }
        }

        public int compareTo(Point2D that) {
            if (orientation == VERTICAL) return Point2D.X_ORDER.compare(that, point2D);
            else return Point2D.Y_ORDER.compare(that, point2D);
        }

    }
}
