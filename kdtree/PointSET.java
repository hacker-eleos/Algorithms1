/* *****************************************************************************
 *  Name:  Vikram Bhatt
 *  Date: 26 March 2021
 *  Description: Kd-Trees
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private final SET<Point2D> pointSet;

    public PointSET() {
        this.pointSet = new SET<>();
    }                           // construct an empty set of points

    public static void main(String[] args) {
        PointSET pointSET = new PointSET();
        pointSET.insert(new Point2D(0.0, 0.0));
        pointSET.insert(new Point2D(0.1, 0.4));
        pointSET.insert(new Point2D(0.6, 0.5));
        for (Point2D point2D : pointSET.range(new RectHV(0.4, 0.3, 0.8, 0.6))) {
            System.out.println(point2D);
        }
    }                  // unit testing of the methods (optional)

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("null argument");
        }
        if (!contains(p)) pointSet.add(p);
    }              // add the point to the set (if it is not already in the set)

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("null argument");
        }
        Bag<Point2D> point2DBag = new Bag<>();
        for (Point2D point2D : pointSet) {
            if (rect.contains(point2D)) point2DBag.add(point2D);
        }
        return point2DBag;
    } // all points that are inside the rectangle (or on the boundary)

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("null argument");
        }
        return pointSet.contains(p);
    }            // does the set contain point p?

    public int size() {
        return pointSet.size();
    }                         // number of points in the set

    public void draw() {
        for (Point2D point2D : pointSet) {
            StdDraw.point(point2D.x(), point2D.y());
        }
    }                         // draw all points to standard draw

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("null argument");
        }
        if (isEmpty()) return null;
        Point2D nearestNeighbour = null;
        double nearestDistance = Double.POSITIVE_INFINITY;
        for (Point2D point2D : pointSet) {
            if (p.distanceSquaredTo(point2D) < nearestDistance) {
                nearestNeighbour = point2D;
                nearestDistance = p.distanceSquaredTo(point2D);
            }
        }
        return nearestNeighbour;
    } // a nearest neighbor in the set to point p; null if the set is empty

    public boolean isEmpty() {
        return pointSet.isEmpty();
    }                      // is the set empty?
}