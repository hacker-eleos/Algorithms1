/* *****************************************************************************
 *  Name: Vikram Bhatt
 *  Date: Mar 16, 2021
 *  Description: Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final Point[] points;

    public BruteCollinearPoints(Point[] points) {
        checkValidity(points);
        this.points = points;
    } // finds all line segments containing 4 points

    private void checkValidity(Point[] points) {
        if (points == null) throw new IllegalArgumentException("null argument to constructor");
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            Point currentPoint = points[i];
            Point nextPoint = points[i + 1];
            if ((currentPoint == null) || (nextPoint == null))
                throw new IllegalArgumentException("null point");
            if (currentPoint.compareTo(nextPoint) == 0) throw new IllegalArgumentException(
                    "duplicate points found at " + i + ", " + (i + 1) + ": " + currentPoint);
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    public int numberOfSegments() {
        return segments().length;
    }        // the number of line segments

    public LineSegment[] segments() {
        ArrayList<LineSegment> segments = new ArrayList<LineSegment>(points.length);
        for (int i = 0; i < points.length - 3; i++) {
            Point p = points[i];
            for (int j = i + 1; j < points.length - 2; j++) {
                Point q = points[j];
                for (int k = j + 1; k < points.length - 1; k++) {
                    Point r = points[k];
                    for (int l = k + 1; l < points.length; l++) {
                        Point s = points[l];
                        if ((p.slopeTo(q) == p.slopeTo(r)) && (p.slopeTo(r) == p.slopeTo(s))) {
                            LineSegment segment = new LineSegment(p, s);
                            if (!segments.contains(segment)) segments.add(new LineSegment(p, s));
                        }
                    }
                }
            }
        }
        return segments.toArray(new LineSegment[segments.size()]);
    }                // the line segments
}
