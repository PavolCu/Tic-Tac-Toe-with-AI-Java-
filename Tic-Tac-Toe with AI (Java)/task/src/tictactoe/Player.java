package tictactoe;

public enum Player {
    X, O, NONE;

    public Player other() {
        return this == X ? O : X;
    }

    @Override
    public String toString() {
        switch(this) {
            case X: return "X";
            case O: return "O";
            default: return "_";

        }
    }
}
