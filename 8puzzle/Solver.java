import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    private final Board initial;
    private SearchNode lastSearchNode;

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode previous;

        public SearchNode(Board board) {
            this.board = board;
            this.moves = 0;
            this.previous = null;
        }

        public SearchNode(Board board, SearchNode previous) {
            this.board = board;
            this.moves = previous.moves + 1;
            this.previous = previous;
        }

        public int compareTo(SearchNode that) {
            return (this.board.manhattan() - that.board.manhattan()) + (this.moves - that.moves);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        this.initial = initial;

        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
        minPQ.insert(new SearchNode(initial));

        // twin
        MinPQ<SearchNode> twinPQ = new MinPQ<>();
        twinPQ.insert(new SearchNode(initial.twin()));

        while (true) {
            this.lastSearchNode = nextFrontier(minPQ);
            if (this.lastSearchNode != null || nextFrontier(twinPQ) != null) return;
        }

    }

    private SearchNode nextFrontier(MinPQ<SearchNode> minPQ) {
        if (minPQ.isEmpty()) return null;

        SearchNode current = minPQ.delMin();

        if (current.board.isGoal()) return current;

        current.board.neighbors().forEach(n -> {
            if (current.previous != null && current.previous.board.equals(n)) {
                return;
            }

            minPQ.insert(new SearchNode(n, current));
        });

        return null;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        if (initial.isGoal()) return false;

        return true;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return this.lastSearchNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        List<Board> solution = new ArrayList<Board>();
        SearchNode tmp = lastSearchNode;
        while (tmp.previous != null) {
            solution.add(tmp.board);
            tmp = tmp.previous;
        }
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        // tests go here
    }
}
