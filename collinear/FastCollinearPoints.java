/* *****************************************************************************
 *  Name: Vikram Bhatt
 *  Date: 16 March 2021
 *  Description: Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final int n;
    private final LineSegment[] lineSegments;
    private final int numberOfSegments;


    public FastCollinearPoints(Point[] points) {
        checkValidity(points);
        Point[] pointsArray = Arrays.copyOf(points, points.length);
        n = pointsArray.length;
        Point[] pointsCopy = Arrays.copyOf(pointsArray, n);
        ArrayList<Point> maximalPoints = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Point p = pointsArray[i];
            // System.out.println("Current point: "+p);
            Arrays.sort(pointsCopy, p.slopeOrder());
            assert p.compareTo(pointsCopy[0]) == 0;
            double currentSlope = p.slopeTo(pointsCopy[1]);
            // System.out.println("i = " + i);
            // System.out.println("p = " + p);
            // for (Point point : pointsCopy) {
            //     System.out.print(point + ": " + p.slopeTo(point) + ", ");
            // }
            // System.out.println("-----------------------");
            ArrayList<Point[]> collinearPoints = new ArrayList<Point[]>();
            ArrayList<Point> subArray = new ArrayList<>();
            subArray.add(p);
            for (int j = 1; j < n; j++) {
                double newSlope = p.slopeTo(pointsCopy[j]);
                if (currentSlope == newSlope) {
                    subArray.add(pointsCopy[j]);
                }
                else if (subArray.size() >= 4) {
                    Point[] temp = new Point[subArray.size()];
                    subArray.toArray(temp);
                    collinearPoints.add(temp);
                    subArray.clear();
                    subArray.add(p);
                    subArray.add(pointsCopy[j]);
                }
                else {
                    subArray.clear();
                    subArray.add(p);
                    subArray.add(pointsCopy[j]);
                }
                currentSlope = newSlope;
            }
            if (subArray.size() >= 4) {
                Point[] temp = new Point[subArray.size()];
                subArray.toArray(temp);
                collinearPoints.add(temp);
                subArray.clear();
            }

            for (Point[] collinearPointArray : collinearPoints) {
                if (collinearPointArray.length >= 4) {
                    Arrays.sort(collinearPointArray);
                    // for (Point point : collinearPointArray) {
                    //     System.out.println(point);
                    // }
                    addIfDuplicatesDontExist(collinearPointArray[0],
                                             collinearPointArray[collinearPointArray.length - 1],
                                             maximalPoints);
                }
            }
        }
        assert maximalPoints.size() % 2 == 0;
        // for (Point maximalPoint : maximalPoints) {
        //     System.out.println("maximalPoint = " + maximalPoint);
        // }
        lineSegments = new LineSegment[maximalPoints.size() / 2];
        for (int i = 0; i < lineSegments.length; i++) {
            lineSegments[i] = new LineSegment(maximalPoints.get(2 * i),
                                              maximalPoints.get(2 * i + 1));
        }
        numberOfSegments = lineSegments.length;
    }     // finds all line segments containing 4 or more points

    private void checkValidity(Point[] points) {
        if (points == null) throw new IllegalArgumentException("null argument to constructor");
        for (int i = 0; i < n; i++) {
            if (points[i] == null) throw new IllegalArgumentException("null point");
            for (int j = 0; (j < n); j++) {
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    public LineSegment[] segments() {
        LineSegment[] copy = Arrays.copyOf(lineSegments, lineSegments.length);
        return copy;
    } // the line segments

    private void addIfDuplicatesDontExist(Point p, Point q, ArrayList<Point> maximalPoints) {

        for (int i = 0; i < maximalPoints.size(); i = i + 2) {
            if ((p.compareTo(maximalPoints.get(i)) == 0) && (q.compareTo(maximalPoints.get(i + 1))
                    == 0)) return;
        }
        maximalPoints.add(p);
        maximalPoints.add(q);
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }      // the number of line segments

}