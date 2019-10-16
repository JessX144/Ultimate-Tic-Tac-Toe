package model.board;

import model.Coords;
import model.Move;
import model.PlayerEnum;


public class Cell implements Cloneable {

    private PlayerEnum player;
    private final Coords board;
    private final Coords cell;

    public Cell(Coords board, Coords cell) {
        this.board = board;
        this.cell = cell;
    }

    public Cell(PlayerEnum player, Coords board, Coords cell) {
        this.player = player;
        this.board = board;
        this.cell = cell;
    }

    private Cell(Cell from) {
        this.player = from.player;
        this.board = new Coords(from.board.getRow(), from.board.getColumn());
        this.cell = new Coords(from.cell.getRow(), from.cell.getColumn());
    }

    public PlayerEnum getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEnum player) {
        this.player = player;
    }

    public Coords getBoard() {
        return board;
    }

    public Coords getCell() {
        return cell;
    }

    public Move asMove() {
        return new Move(board, cell);
    }

    @Override
    public Cell clone() {
        return new Cell(this);
    }

}
