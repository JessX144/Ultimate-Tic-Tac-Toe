package model;

import java.util.Objects;

public class Move {

    private final Coords board;
    private final Coords move;

    public Move(Coords board, Coords move) {
        this.board = board;
        this.move = move;
    }

    public Move(String message) {
        if (message == null)
            throw new IllegalArgumentException("Move From Message Expect But Found Null");

        if (!message.matches("\\d,\\d;\\d,\\d"))
            throw new IllegalArgumentException("Move From Message Expect To Match \\d,\\d;\\d,\\d but found " + message);

        String[] points = message.split(";");
        String[] boardRowCol =  points[0].split(",");
        String[] moveRowCol =  points[1].split(",");

        this.board = new Coords(Integer.parseInt(boardRowCol[0]),Integer.parseInt(boardRowCol[1]));
        this.move = new Coords(Integer.parseInt(moveRowCol[0]),Integer.parseInt(moveRowCol[1]));
    }

    public Coords getBoard() {
        return board;
    }

    public Coords getMove() {
        return move;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move1 = (Move) o;
        return board.equals(move1.board)
                && move.equals(move1.move);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, move);
    }

    @Override
    public String toString() {
        return "send:" +
                this.getBoard().getRow() +
                "," +
                this.getBoard().getColumn() +
                ";" +
                this.getMove().getRow() +
                "," +
                this.getMove().getColumn();
    }
}
