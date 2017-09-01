import edu.princeton.cs.algs4.MinPQ;
public class Solver {

    public Solver(Board initial){   // find a solution to the initial board (using the A* algorithm)
           }

    // public boolean isSolvable()   // is the initial board solvable?
    // public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
   // public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable

    private static class Step{
        private int moves;
        private Board board;
        private Step stepBefore;

        private Step(Board board, int moves, Step stepBefore){
            this.moves = moves;
            this.board = board;
            this.stepBefore = stepBefore;
        }

        public int getMoves() {
            return moves;
        }

        public Board getBoard() {
            return board;
        }

        public Step getStepBefore() {
            return stepBefore;
        }
    }
   // public static void main(String[] args) // solve a slider puzzle (given below)
}