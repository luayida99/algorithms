package algorithms;
public class sudokuSolver {
    private static void printBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println("");
        }
    }

    private static boolean isSafe(int[][] board, int row, int col, int input) {
        //check row clashes
        for (int i = 0; i < board.length; i++) {
            if (board[row][i] == input) {
                return false;
            }
        }

        //check column clashes
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] == input) {
                return false;
            }
        } 

        int boxLength = (int) Math.sqrt(board.length);
        int boxRowStart = row - row % boxLength;
        int boxColStart = col - col % boxLength;
        for (int r = boxRowStart; r < boxRowStart + boxLength; r++) {
            for (int c = boxColStart; c < boxColStart + boxLength; c++) {
                if (board[r][c] == input) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean sudokuSolve(int[][] board) {
        int n = board.length;
        int row = -100;
        int col = -100;
        boolean isEmpty = true;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        if (isEmpty) {
            return true;
        }

        for (int input = 1; input <= n; input++) {
            if (isSafe(board, row, col, input)) {
                board[row][col] = input;
                if (sudokuSolve(board)) {
                    return true;
                } else {
                    board[row][col] = 0;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        int[][] board = new int[][] {
            { 9, 0, 0, 0, 0, 0, 0, 8, 0 },
            { 0, 0, 0, 9, 0, 1, 5, 0, 0 },
            { 0, 4, 0, 0, 0, 0, 7, 0, 0 },
            { 4, 6, 2, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 8, 0, 0, 3, 0, 0, 0 },
            { 0, 0, 7, 0, 5, 8, 0, 0, 0 },
            { 7, 0, 0, 0, 2, 0, 0, 0, 4 },
            { 0, 0, 0, 8, 0, 0, 0, 6, 2 },
            { 0, 0, 0, 0, 6, 0, 0, 0, 0 }
        };

        if (sudokuSolve(board)) {
            printBoard(board);
        } else {
            System.out.println("No solution");
        }
    }
}
