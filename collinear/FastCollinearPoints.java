/* *****************************************************************************
 *  Name: Vikram Bhatt
 *  Date: 16 March 2021
 *  Description: Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private final Point[] points;

    public FastCollinearPoints(Point[] points) {
        checkValidity(points);
        this.points = points;
    }     // finds all line segments containing 4 or more points

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    public int numberOfSegments() {
        return segments().length;
    }      // the number of line segments

    public LineSegment[] segments() {
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Arrays.sort(points, p.slopeOrder());
            assert p.compareTo(points[0]) == 0;
            for (int j = 1; j < points.length; j++) {

            }
        }
        throw new UnsupportedOperationException();
    }              // the line segments

}