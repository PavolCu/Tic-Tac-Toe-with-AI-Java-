package tictactoe;

public class Board {
    public Board() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Player.NONE;
            }
        }
    }
    public Board copy() {
        Board newBoard = new Board();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newBoard.board[i][j] = this.board[i][j];
            }
        }
        return newBoard;
    }


    private final Player[][] board = new Player[3][3];

    public Player getPlayerAt(int x, int y) {
        return board[x - 1][y - 1];
    }
    public Board(String initialState) {

        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                switch (initialState.charAt(k)) {
                    case 'X' -> board[i][j] = Player.X;
                    case 'O' -> board[i][j] = Player.O;
                    default -> board[i][j] = Player.NONE;
                }
                k++;
            }
        }
    }

    public boolean isValidMove(int x, int y) {
        if (x < 1 || x > 3 || y < 1 || y > 3) {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        } else if (board[x - 1][y - 1] != Player.NONE) {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }
        return true;
    }

    public void makeMove(int x, int y, Player player) {
        board[x - 1][y - 1] = player;
    }

    public boolean hasPlayerWon(Player player) {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                    (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Player.NONE) {
                    return false;
                }
            }
        }
        return true;
    }

    public void display() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }
}
