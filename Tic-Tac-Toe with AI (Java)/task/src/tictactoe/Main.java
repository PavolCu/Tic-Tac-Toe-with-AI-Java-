package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        Board board;

        while (true) {
            System.out.print("Input command > ");
            String command = scanner.nextLine().trim();

            if (command.equals("exit")) {
                break;
            } else if (command.startsWith("start")) {
                String[] params = command.split(" ");
                if (params.length != 3 ||
                        (!params[1].equals("user") && !params[1].equals("easy") && !params[1].equals("medium") && !params[1].equals("hard")) ||
                        (!params[2].equals("user") && !params[2].equals("easy") && !params[2].equals("medium") && !params[2].equals("hard"))) {
                    System.out.println("Bad parameters!");
                    continue;
                }
                board = new Board();
                String xPlayer, oPlayer;
                xPlayer = params[1];
                oPlayer = params[2];

                playGame(board, xPlayer, oPlayer);
            } else {
                System.out.println("Bad parameters!");
            }
        }
    }

    public static void playGame(Board board, String xPlayer, String oPlayer) {
        Scanner scanner = new Scanner(System.in);
        AI computer = new AI(board);
        Player currentPlayer = Player.X;

        board.display();

        while (true) {
            if (currentPlayer == Player.X) {
                if (xPlayer.equals("user")) {
                    //board.display();
                    handleUserMove(board, currentPlayer, scanner);
                } else {
                    handleAIMove(board, currentPlayer, xPlayer, computer);
                }
            } else {  // currentPlayer == Player.O
                if (oPlayer.equals("user")) {
                    //board.display();
                    handleUserMove(board, currentPlayer, scanner);
                } else {
                    handleAIMove(board, currentPlayer, oPlayer, computer);
                }
            }

            if (board.hasPlayerWon(currentPlayer)) {
                //board.display();
                System.out.println(currentPlayer + " wins");
                break;
            } else if (board.isFull()) {
                //board.display();
                System.out.println("Draw");
                break;
            }
            currentPlayer = currentPlayer.other();
        }
    }

    public static void handleUserMove(Board board, Player player, Scanner scanner) {
        while (true) {
            System.out.print("Enter the coordinates: > ");
            String input = scanner.nextLine();
            String[] inputs = input.split("\\s+");

            if (inputs.length != 2) {
                System.out.println("You should enter numbers!");
                continue;
            }

            int x, y;
            try {
                x = Integer.parseInt(inputs[0]);
                y = Integer.parseInt(inputs[1]);
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
                continue;
            }

            if (board.isValidMove(x, y)) {
                board.makeMove(x, y, player);
                board.display(); // Display the board after the move
                break;
            }
        }
    }


    public static void handleAIMove(Board board, Player player, String level, AI computer) {
        System.out.println("Making move level \"" + level + "\"");
        int[] aiMove;
        switch (level) {
            case "easy":
                aiMove = computer.easyMove();
                break;
            case "medium":
                aiMove = computer.mediumMove(player);
                break;
            case "hard":
                aiMove = computer.hardMove(player);
                break;
            default:
                throw new IllegalStateException("Unexpected level: " + level);
        }
        board.makeMove(aiMove[0], aiMove[1], player);
        board.display(); // Board is displayed here
    }

}