/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
       
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);

        ArrayList<LineSegment> lineSegmentArrays = new ArrayList<LineSegment>();
        for (int j = 0; j < sortedPoints.length - 3; j++) {
            Point p = sortedPoints[j];
            Point[] pointsBySlope = sortedPoints.clone();
            Arrays.sort(pointsBySlope, p.slopeOrder());

            int counter = 1;

            LineSegment lineSegment = null;

            for (int i = 0; i < pointsBySlope.length - 1; i++) {
                if (p.compareTo(pointsBySlope[i]) == 0) {
                    continue;
                }

                double slope = p.slopeTo(pointsBySlope[i]);
                double nextSlope = p.slopeTo(pointsBySlope[i + 1]);

                if (slope == nextSlope) {
                    counter++;
                    lineSegment = new LineSegment(p, pointsBySlope[i + 1]);

                    if (counter >= 3) {
                        Point start = pointsBySlope[(i + 1) - (counter - 1)];
                        if (p.compareTo(start) >= 0) {
                            continue;
                        }
                    }

                    if (counter == 3) {
                        lineSegmentArrays.add(lineSegment);
                    }

                    if (counter > 3) {
                        lineSegmentArrays.remove(lineSegmentArrays.size() - 1);
                        lineSegmentArrays.add(lineSegment);
                    }
                }
                else {
                    counter = 1;
                }
            }
        }

        lineSegments = lineSegmentArrays.toArray(new LineSegment[lineSegmentArrays.size()]);
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }
}
