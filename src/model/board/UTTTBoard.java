package model.board;

import model.Coords;
import model.Move;
import model.PlayerEnum;
import model.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UTTTBoard implements Cloneable {

    private final int size;
    private final int maxMoves;
    private final List<List<UTTTSubBoard>> board;

    private int movesTaken;
    private Result result;

    public UTTTBoard(int size) {
        this.size = size;
        this.maxMoves = (int) Math.pow(this.size, 4);
        this.board = new ArrayList<>(this.size);

        this.cleanBoard();
    }

    private UTTTBoard(UTTTBoard from) {
        this.size = from.size;
        this.maxMoves = from.maxMoves;
        this.movesTaken = from.movesTaken;
        this.result = from.result;
        this.board = new ArrayList<>(this.size);

        for (int row = 0; row < this.size; row++) {
            this.board.add(row, new ArrayList<>(this.size));
            for (int col = 0; col < this.size; col++) {
                this.board.get(row).add(col, from.getBoard(row, col).clone());
            }
        }

    }

    private void cleanBoard() {
        for (int row = 0; row < this.size; row++) {
            this.board.add(row, new ArrayList<>(this.size));
            for (int col = 0; col < this.size; col++) {
                this.board.get(row).add(col, new UTTTSubBoard(this.size, new Coords(row, col)));
            }
        }
        this.movesTaken = 0;
        this.result = Result.UNFINISHED;
    }

    private UTTTSubBoard getBoard(int x, int y) {
        if (x < 0 || x > size) return null;
        if (y < 0 || y > size) return null;
        return this.board.get(x).get(y);
    }

    private UTTTSubBoard getBoard(Coords coords) {
        return this.getBoard(coords.getRow(), coords.getColumn());
    }

    private Result getResultOnMove(Move move) {
        Result[] col = new Result[this.size];
        Result[] row = new Result[this.size];
        Result[] diag = new Result[this.size];
        Result[] rdiag = new Result[this.size];


        for (int i = 0; i < this.size; i++) {
            col[i] = this.getBoard(move.getBoard().getRow(), i).getResult();
            row[i] = this.getBoard(i, move.getBoard().getColumn()).getResult();
            diag[i] = this.getBoard(i, i).getResult();
            rdiag[i] = this.getBoard(i, this.size - 1 - i).getResult();
        }

        if (Arrays.stream(col).distinct().count() == 1 && col[0] != Result.TIE && col[0] != Result.UNFINISHED) return col[0];
        if (Arrays.stream(row).distinct().count() == 1 && row[0] != Result.TIE && row[0] != Result.UNFINISHED) return row[0];
        if (Arrays.stream(diag).distinct().count() == 1 && diag[0] != Result.TIE && diag[0] != Result.UNFINISHED) return diag[0];
        if (Arrays.stream(rdiag).distinct().count() == 1 && rdiag[0] != Result.TIE && rdiag[0] != Result.UNFINISHED) return rdiag[0];
        if (this.movesTaken == this.maxMoves) return Result.TIE;
        return Result.UNFINISHED;
    }

    private boolean makeMove(Move move, PlayerEnum player) {
        UTTTSubBoard subboard = this.getBoard(move.getBoard());
        if (subboard == null) return false;
        if (!subboard.makeMove(move.getMove(), player)) return false;

        this.movesTaken = this.movesTaken + 1;
        result = this.getResultOnMove(move);

        return true;
    }

    public boolean addMyMove(Move move) {
        return this.makeMove(move, PlayerEnum.ME);
    }

    public boolean addOpponentMove(Move move) {
        return this.makeMove(move, PlayerEnum.OPPONENT);
    }

    public Result getResult() {
        return result;
    }

    public boolean isFinished() {
        return this.result != Result.UNFINISHED;
    }

    public boolean isFinished(Coords board) {
        return this.getBoard(board).isFinished();
    }

    public Cell getCell(Move move) {
        UTTTSubBoard subBoard = this.getBoard(move.getBoard());
        if (subBoard == null) return null;
        return subBoard.getCell(move.getMove());
    }

    public List<Cell> getEmptyCells(Coords board) {
        if (board == null) return null;
        UTTTSubBoard subBoard = this.getBoard(board.getRow(), board.getColumn());
        if (subBoard == null) return null;

        return subBoard.getEmptyCells();
    }

    public List<UTTTSubBoard> getUnfinishedBoards() {
        return this.board.stream().flatMap(b -> b.stream().filter(board -> !board.isFinished())).collect(Collectors.toList());
    }

    @Override
    public UTTTBoard clone() {
        return new UTTTBoard(this);
    }

}
