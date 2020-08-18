/* *****************************************************************************
 *  Name: Karen Lorena Niño Pedraza
 *  Date: 07 August 2020
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;
    private final ArrayList<LineSegment> listSegments = new ArrayList<LineSegment>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        int n = points.length;
        Arrays.sort(points);

        for (int i = 0; i < (n - 3); i++) {
            for (int j = i + 1; j < (n - 2); j++) {
                for (int m = j + 1; m < (n - 1); m++) {
                    for (int p = m + 1; p < n; p++) {
                        boolean slope =
                                ((points[i].slopeTo(points[j]) == points[j].slopeTo(points[m])) &&
                                        (points[j].slopeTo(points[m]) == points[m]
                                                .slopeTo(points[p])));

                        if (slope) {
                            listSegments.add(new LineSegment(points[i], points[p]));
                        }
                    }
                }
            }
        }

        lineSegments = listSegments.toArray(new LineSegment[listSegments.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.clone();
    }
}
