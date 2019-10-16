package model.board;

import model.Result;

import java.awt.Point;

public interface Board<T> extends Cloneable {

    T getCell(int x, int y);
    T getCell(Point point);

    void setCell(int x, int y, T value);
    void setCell(Point point, T value);

    Result getResult();
    boolean isFinished();
    int getSize();
    String prettyPrint();
    int movesRemaining();

}
