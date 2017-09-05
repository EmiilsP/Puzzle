import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

public class Board {
    private int[][] board;
    private int size;
    private int moves;
    private int wrong;


    public Board(int[][] blocks) {  // construct a board from an n-by-n array of blocks // (where blocks[i][j] = block in row i, column j)
        size = blocks.length;
        board = copy(blocks, size);
        moves = 0;
    }

    public int dimension() { // board dimension n
        return size;
    }

    public int hamming() {   // number of blocks out of place
        wrong();
        return wrong + moves;
    }

    public int manhattan() {               // sum of Manhattan distances between blocks and goal
        int count = 1;
        int vertical;
        int horizontal;
        int steps = 0;
        for (int[] x : board) {
            for (int y : x) {
                if (y != count && y != 0) {
                    // System.out.println(count);
                    if (y % size != 0) { // argument position from first row and first column
                        horizontal = (y % size) - 1;
                        vertical = y / size;
                    } else {
                        horizontal = size - 1;
                        vertical = (y / size) - 1;
                    }
                    if (count % size != 0) { // update the real manhattan by slip off
                        horizontal -= (count % size) - 1;
                        vertical -= (count / size);
                    } else {
                        horizontal -= size - 1;
                        vertical -= (count / size) - 1;
                    }
                    if (vertical < 0) { // it doesn't matter does it go right or left - so all should be positive
                        vertical *= -1;
                    }
                    if (horizontal < 0) {
                        horizontal *= -1;
                    }
                    steps += horizontal + vertical;
                    horizontal = 0;
                    vertical = 0;
                }
                count++;
            }
        }
        return steps + moves;
    }


    public boolean isGoal() { // is this board the goal board?
        wrong();
        return (wrong == 0) ? true : false;
    }

    public Board twin() {   // a board that is obtained by exchanging any pair of blocks
        int[][] freshBoard = Arrays.copyOf(this.board, size);
        int x = 0;
        int y = 0;
        while (freshBoard[x][y] == 0 || freshBoard[x][y + 1] == 0) {
            y++;
            if (y >= freshBoard.length - 1) {
                x++;
                y = 0;
            }
        }
        exchange(freshBoard, x, y, x, y + 1);
        return new Board(freshBoard);
    }

    public boolean equals(Object y) {        // does this board equal y?
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board second = (Board) y;
        if (this.size != second.size) return false;
        for (int i = 0; i < size; i++) {
            if (this.board[i].length != second.board[i].length) return false;
            for (int j = 0; j < board[i].length; j++) {
                if (this.board[i][j] != second.board[i][j]) return false;
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {     // all neighboring boards
        int x = -1;
        int y = -1;
        loop:
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == 0) {
                    x = row;
                    y = col;
                    break loop;
                }
            }
        }
        int[][] exchangeArray;
        Board brosBoard;
        LinkedList<Board> bros = new LinkedList<>();
        if (x > 0) { // exchange up
            exchangeArray = copy(board, size);
            exchange(exchangeArray, x, y, x - 1, y);
            brosBoard = new Board(exchangeArray);
            bros.add(brosBoard);
        }
        if (x < size - 1) { // exchange down
            exchangeArray = copy(board, size);
            exchange(exchangeArray, x, y, x + 1, y);
            brosBoard = new Board(exchangeArray);
            bros.add(brosBoard);
        }
        if (y > 0) { // exchange left
            exchangeArray = copy(board, size);
            exchange(exchangeArray, x, y, x, y - 1);
            brosBoard = new Board(exchangeArray);
            bros.add(brosBoard);
        }
        if (y < size - 1) { // exchange right
            exchangeArray = copy(board, size);
            exchange(exchangeArray, x, y, x, y + 1);
            brosBoard = new Board(exchangeArray);
            bros.adchangedd(brosBoard);
        }
        return bros;
    }

    public String toString() { // string representation of this board (in the output format specified below)
        StringBuilder string = new StringBuilder();
        string.append(String.valueOf(size) + '\n');
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                string.append(" ");
                string.append(board[i][j]);
            }
            string.append('\n');
        }
        return string.toString();
    }

    private void wrong() {
        int checkWrong = 0;
        int count = 1;
        for (int[] x : board) {
            for (int y : x) {
                if (y != count++) {
                    checkWrong++;
                }
            }
        }
        wrong = checkWrong - 1; //because we don't count 0
    }

    private int[][] copy(int[][] twoD, int size) { // to protect us from shallow copy
        int[][] deepCopy = new int[size][size];
        int count = 0;
        for (int[] array : twoD) {
            deepCopy[count++] = Arrays.copyOf(array, size);
        }
        return deepCopy;
    }

    private void exchange(int[][] blocks, int xFirst, int yFirst, int xSecond, int ySecond) {
        int exchange = blocks[xFirst][yFirst];
        blocks[xFirst][yFirst] = blocks[xSecond][ySecond];
        blocks[xSecond][ySecond] = exchange;
    }
}

