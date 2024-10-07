package tictactoe;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AI {
    private final Board board;
    private final Random random = new Random();

    public AI(Board board) {
        this.board = board;
    }

    public int[] hardMove(Player currentPlayer) {
        return minimax(board, currentPlayer, true, currentPlayer).move;
    }

    private class MoveScore {
        int[] move;
        int score;

        MoveScore(int[] move, int score) {
            this.move = move;
            this.score = score;
        }
    }

    private MoveScore minimax(Board board, Player currentPlayer, boolean isMaximizing, Player aiPlayer) {
        if (board.hasPlayerWon(aiPlayer)) {
            return new MoveScore(null, 10);
        } else if (board.hasPlayerWon(aiPlayer.other())) {
            return new MoveScore(null, -10);
        } else if (board.isFull()) {
            return new MoveScore(null, 0);
        }

        List<int[]> availableCells = getAvailableCells(board);
        MoveScore bestMoveScore = new MoveScore(null, isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE);

        for (int[] cell : availableCells) {
            Board newBoard = board.copy();
            newBoard.makeMove(cell[0], cell[1], currentPlayer);

            MoveScore currentMoveScore = minimax(newBoard, currentPlayer.other(), !isMaximizing, aiPlayer);

            if (isMaximizing) {
                if (currentMoveScore.score > bestMoveScore.score) {
                    bestMoveScore.score = currentMoveScore.score;
                    bestMoveScore.move = cell;
                }
            } else {
                if (currentMoveScore.score < bestMoveScore.score) {
                    bestMoveScore.score = currentMoveScore.score;
                    bestMoveScore.move = cell;
                }
            }
        }

        return bestMoveScore;
    }

    private List<int[]> getAvailableCells(Board board) {
        List<int[]> availableCells = new ArrayList<>();
        for (int x = 1; x <= 3; x++) {
            for (int y = 1; y <= 3; y++) {
                if (board.getPlayerAt(x, y) == Player.NONE) {
                    availableCells.add(new int[]{x, y});
                }
            }
        }
        return availableCells;
    }

    public int[] easyMove() {
        int x, y;

        while (true) {
            x = random.nextInt(3) + 1; // Random number between 1 and 3
            y = random.nextInt(3) + 1; // Random number between 1 and 3

            if (board.getPlayerAt(x, y) == Player.NONE) {
                break;
            }
        }
        return new int[]{x, y};
    }

    public int[] mediumMove(Player currentPlayer) {
        // Check if AI can win
        for (int x = 1; x <= 3; x++) {
            for (int y = 1; y <= 3; y++) {
                if (board.getPlayerAt(x, y) == Player.NONE) {
                    board.makeMove(x, y, currentPlayer);
                    if (board.hasPlayerWon(currentPlayer)) {
                        board.makeMove(x, y, Player.NONE); // Reset cell
                        return new int[]{x, y};
                    }
                    board.makeMove(x, y, Player.NONE); // Reset cell
                }
            }
        }

        // Check if opponent can win
        Player opponent = currentPlayer.other();
        for (int x = 1; x <= 3; x++) {
            for (int y = 1; y <= 3; y++) {
                if (board.getPlayerAt(x, y) == Player.NONE) {
                    board.makeMove(x, y, opponent);
                    if (board.hasPlayerWon(opponent)) {
                        board.makeMove(x, y, Player.NONE); // Reset cell
                        return new int[]{x, y};
                    }
                    board.makeMove(x, y, Player.NONE); // Reset cell
                }
            }
        }

        // If neither of the above two conditions is met, make an easy move
        return easyMove();
    }
}
