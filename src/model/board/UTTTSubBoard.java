package model.board;

import model.Coords;
import model.PlayerEnum;
import model.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UTTTSubBoard implements Cloneable {

    private final int size;
    private final int maxMoves;
    private final Coords boardCoords;
    private final List<List<Cell>> board;

    private int movesTaken;
    private Result result;

    public UTTTSubBoard(int size, Coords boardCoords) {
        this.size = size;
        this.maxMoves = (int) Math.pow(this.size, 2);
        this.boardCoords = boardCoords;
        this.board = new ArrayList<>(this.size);

        this.cleanBoard();
    }

    private UTTTSubBoard(UTTTSubBoard from) {
        this.size = from.size;
        this.maxMoves = from.maxMoves;
        this.boardCoords = from.boardCoords;
        this.board = new ArrayList<>(this.size);
        this.movesTaken = from.movesTaken;
        this.result = from.result;

        for (int row = 0; row < this.size; row++) {
            this.board.add(row, new ArrayList<>(this.size));
            for (int col = 0; col < this.size; col++) {
                this.board.get(row).add(col, from.getCell(row, col).clone());
            }
        }
    }

    private void cleanBoard() {
        for (int row = 0; row < this.size; row++) {
            this.board.add(row, new ArrayList<>(this.size));
            for (int col = 0; col < this.size; col++) {
                this.board.get(row).add(col, new Cell(this.boardCoords, new Coords(row, col)));
            }
        }
        this.movesTaken = 0;
        this.result = Result.UNFINISHED;
    }

    private Cell getCell(int x, int y) {
        if (x < 0 || x > size) return null;
        if (y < 0 || y > size) return null;
        return this.board.get(x).get(y);
    }

    private Result getResultOnMove(Coords coords) {
        PlayerEnum[] col = new PlayerEnum[this.size];
        PlayerEnum[] row = new PlayerEnum[this.size];
        PlayerEnum[] diag = new PlayerEnum[this.size];
        PlayerEnum[] rdiag = new PlayerEnum[this.size];

        for (int i = 0; i < this.size; i++) {
            col[i] = this.getCell(coords.getRow(), i).getPlayer();
            row[i] = this.getCell(i, coords.getColumn()).getPlayer();
            diag[i] = this.getCell(i, i).getPlayer();
            rdiag[i] = this.getCell(i, this.size - 1 - i).getPlayer();
        }

        if (Arrays.stream(col).distinct().count() == 1 && col[0] != null) return col[0] == PlayerEnum.ME ? Result.WIN : Result.LOSE;
        if (Arrays.stream(row).distinct().count() == 1 && row[0] != null) return row[0] == PlayerEnum.ME ? Result.WIN : Result.LOSE;
        if (Arrays.stream(diag).distinct().count() == 1 && diag[0] != null) return diag[0] == PlayerEnum.ME ? Result.WIN : Result.LOSE;
        if (Arrays.stream(rdiag).distinct().count() == 1 && rdiag[0] != null) return rdiag[0] == PlayerEnum.ME ? Result.WIN : Result.LOSE;
        if (this.movesTaken == this.maxMoves) return Result.TIE;
        return Result.UNFINISHED;
    }

    public boolean makeMove(Coords coords, PlayerEnum player) {
        Cell cell = this.getCell(coords);
        if (cell.getPlayer() != null) return false;

        cell.setPlayer(player);
        this.movesTaken = this.movesTaken + 1;
        result = this.getResultOnMove(coords);

        return true;
    }

    public Result getResult() {
        return result;
    }

    public boolean isFinished() {
        return this.result != Result.UNFINISHED;
    }

    public Cell getCell(Coords coords) {
        return this.getCell(coords.getRow(), coords.getColumn());
    }

    //Returns a pair of coordinates and the board its on 
    public List<Cell> getEmptyCells() {
        return this.board.stream().flatMap(b -> b.stream().filter(c -> c.getPlayer() == null)).collect(Collectors.toList());
    }

    public Coords getBoardCoords() {
        return boardCoords;
    }

    @Override
    public UTTTSubBoard clone() {
        return new UTTTSubBoard(this);
    }
}
