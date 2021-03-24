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
    private final LineSegment[] lineSegments;
    private final int numberOfSegments;
    private final Point[] copyOfPoints;
    public BruteCollinearPoints(Point[] points) {
        checkValidity(points);
        copyOfPoints = Arrays.copyOf(points, points.length);
        ArrayList<LineSegment> segments = new ArrayList<LineSegment>(copyOfPoints.length);
        for (int i = 0; i < copyOfPoints.length - 3; i++) {
            Point p = copyOfPoints[i];
            Point[] collinearArray = new Point[4];
            for (int j = i + 1; j < copyOfPoints.length - 2; j++) {
                Point q = copyOfPoints[j];
                double pqSlope = p.slopeTo(q);
                for (int k = j + 1; k < copyOfPoints.length - 1; k++) {
                    Point r = copyOfPoints[k];
                    double prSlope = p.slopeTo(r);
                    for (int m = k + 1; m < copyOfPoints.length; m++) {
                        Point s = copyOfPoints[m];
                        double psSlope = p.slopeTo(s);
                        if ((pqSlope == prSlope) && (pqSlope == psSlope)) {
                            collinearArray[0] = p;
                            collinearArray[1] = q;
                            collinearArray[2] = r;
                            collinearArray[3] = s;
                            Arrays.sort(collinearArray);
                            LineSegment segment = new LineSegment(collinearArray[0],
                                                                  collinearArray[3]);
                            segments.add(segment);
                        }
                    }
                }
            }
        }
        lineSegments = segments.toArray(new LineSegment[segments.size()]);
        numberOfSegments = lineSegments.length;
    } // finds all line segments containing 4 points

    private void checkValidity(Point[] points) {
        if (points == null) throw new IllegalArgumentException("null argument to constructor");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("null point");
            for (int j = 0; (j < points.length); j++) {
                if ((j == i)) continue;
                if (points[j] == null) throw new IllegalArgumentException("null point");
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException(
                        "duplicate points found at " + i + ", " + (i + 1) + ": " + points[i]);
            }
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

    public LineSegment[] segments() {
        LineSegment[] copy = Arrays.copyOf(lineSegments, lineSegments.length);
        return copy;

    }                // the line segments

    public int numberOfSegments() {
        return numberOfSegments;
    }        // the number of line segments
}
