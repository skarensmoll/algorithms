import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private final int[][] setTiles;

    // creates a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException();
        }

        this.setTiles = tiles.clone();
    }

    // string representation of this board
    public String toString() {
        StringBuilder representation = new StringBuilder();
        representation.append(this.setTiles.length + "\n");

        for (int i = 0; i < this.setTiles.length; i++) {
            for (int j = 0; j < this.setTiles.length; j++) {
                int tile = this.setTiles[i][j];
                representation.append(tile > 9 ? tile : (" " + tile));
                String end = (j + 1 == this.setTiles.length) ? "\n" : " ";
                representation.append(end);
            }
        }

        return representation.toString();
    }

    // board dimension n
    public int dimension() {
        return this.setTiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int hamm = 0;
        int counter = 1;
        int size = this.setTiles.length * this.setTiles.length;

        for (int i = 0; i < this.setTiles.length; i++) {
            for (int j = 0; j < this.setTiles.length; j++) {
                int tile = this.setTiles[i][j];

                if (!tilePositioned(counter, tile, size) && tile != 0) {
                    hamm++;
                }

                counter++;
            }
        }

        return hamm;
    }

    private boolean tilePositioned(int counter, int tile, int size) {
        if ((counter == size && tile == 0) || (tile != 0 && tile == counter)) {
            return true;
        }
        return false;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        int counter = 1;
        int size = this.setTiles.length * this.setTiles.length;
        int length = this.setTiles.length;

        for (int i = 0; i < this.setTiles.length; i++) {
            for (int j = 0; j < this.setTiles.length; j++) {
                int tile = this.setTiles[i][j];

                if (!tilePositioned(counter, tile, size) && tile != 0) {
                    int rowTarget = (int) Math.ceil((double) tile / length) - 1;
                    int colTarget = (tile - (length * rowTarget)) - 1;
                    int xMoves = Math.abs(i - rowTarget);
                    int yMoves = Math.abs(j - colTarget);

                    manhattan += xMoves + yMoves;
                }

                counter++;
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int counter = 1;
        int size = this.setTiles.length * this.setTiles.length;
        int positioned = 0;
        for (int i = 0; i < this.setTiles.length; i++) {
            for (int j = 0; j < this.setTiles.length; j++) {
                if (tilePositioned(counter, this.setTiles[i][j], size)) positioned++;
                counter++;
            }
        }
        return positioned == size;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board other = (Board) y;
        boolean equal = true;

        first:
        for (int i = 0; i < this.setTiles.length; i++) {
            for (int j = 0; j < this.setTiles.length; j++) {
                if (this.setTiles[i][j] != other.setTiles[i][j]) {
                    equal = false;
                    break first;
                }
            }
        }

        return other.dimension() == this.dimension() && equal;
    }

    private int[] getBlankSpot() {
        int[] position = new int[2];
        first:
        for (int i = 0; i < this.setTiles.length; i++) {
            for (int j = 0; j < this.setTiles.length; j++) {
                if (this.setTiles[i][j] == 0) {
                    position[0] = i;
                    position[1] = j;
                    break first;
                }
            }
        }

        return position;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<Board>();
        int[] position = getBlankSpot();
        int x = position[0];
        int y = position[1];

        // Left
        if (x - 1 >= 0) neighbors.add(new Board(swapTiles(x, y, x - 1, y)));

        // Right
        if (x + 1 < this.setTiles.length) neighbors.add(new Board(swapTiles(x, y, x + 1, y)));

        // Up
        if (y - 1 >= 0) neighbors.add(new Board(swapTiles(x, y, x, y - 1)));

        // Down
        if (y + 1 < this.setTiles.length) neighbors.add(new Board(swapTiles(x, y, x, y + 1)));

        return neighbors;
    }

    private int[][] swapTiles(int x, int y, int newX, int newY) {
        int[][] newTiles = cloneTiles();
        int previousTile = newTiles[newX][newY];
        newTiles[newX][newY] = 0;
        newTiles[x][y] = previousTile;
        return newTiles;
    }

    private int[][] cloneTiles() {
        int[][] copy = Arrays.stream(this.setTiles).map(int[]::clone).toArray(int[][]::new);
        return copy;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[] position = getBlankSpot();
        Board twin = new Board(this.setTiles);
        int i = position[0];
        int j = position[1];

        for (int y = 0; y < this.setTiles.length; y++) {
            for (int x = 0; x < this.setTiles.length - 1; x++) {
                if ((i != y || j != x) && (i != y || j != x + 1)) {
                    return new Board(exch(y, x, y, x + 1));
                }
            }
        }

        return twin;
    }

    private int[][] exch(int row, int col, int row1, int col1) {
        int[][] cpy = cloneTiles();
        int tmp = cpy[row][col];
        cpy[row][col] = cpy[row1][col1];
        cpy[row1][col1] = tmp;

        return cpy;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // here please write tests
    }
}
