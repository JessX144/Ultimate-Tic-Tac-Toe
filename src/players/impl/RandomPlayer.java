package players.impl;

import model.Coords;
import model.Move;
import model.Result;
import model.board.Cell;
import model.board.UTTTSubBoard;
import players.AbstractPlayer;

import model.PlayerEnum;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import java.util.Random;

public class RandomPlayer extends AbstractPlayer {

    private final static Random randomNum = new Random();

    public RandomPlayer() {
        super();
    }
    
    @Override
    public void init() {
        this.newBoard();
    }

    List<Cell> matchedCells = new ArrayList<Cell>();
    Boolean firstMove = true;

    @Override
    public Move getMove() {
        List<UTTTSubBoard> unfinishedBoards = this.getCurrentBoard().getUnfinishedBoards();
        UTTTSubBoard board = unfinishedBoards.get(randomNum.nextInt(unfinishedBoards.size()));
        List<Cell> cells = board.getEmptyCells();
        //Cell cell = cells.get(randomNum.nextInt(cells.size()));
        
        //GET BEST MOVE
        Cell matchedHorizontal = horizontal(board, cells);
        Cell matchedVertical = vertical(board, cells);
        Cell matchedDiagonal = diagonal(board, cells);

        Cell cell = cells.get(randomNum.nextInt(cells.size()));

        Move move = cell.asMove();

        if (!firstMove) {
            if (matchedHorizontal != null)
                move = matchedHorizontal.asMove();
            else if (matchedVertical != null)
                move = matchedVertical.asMove();
            else if (matchedDiagonal != null)
                move = matchedDiagonal.asMove();
            else 
            {
                Iterator iterator = matchedCells.iterator();
                Cell newCell = (Cell) iterator.next();
                move = newCell.asMove();
            }
        }
        
        
        this.getCurrentBoard().addMyMove(move);
        firstMove = false;
        return move;
    }

    //given the list of cells with Coordintates 
    public Cell horizontal(UTTTSubBoard board, List<Cell> cells) {
        Iterator iterator = cells.iterator();
        while(iterator.hasNext()) {
            Cell cell = (Cell) iterator.next();

            int x = cell.getCell().getRow();
            int y = cell.getCell().getRow();

            int firstIndexToCheck = ((((y+1)%3)+3)%3);
            int secondIndexToCheck = ((((y-1)%3)+3)%3);

            Coords firstCoord = new Coords(x, firstIndexToCheck);
            Coords secondCoord = new Coords(x, secondIndexToCheck);

            Cell firstCell =  board.getCell(firstCoord);
            Cell secondCell = board.getCell(secondCoord);

            if (firstCell.getPlayer() == PlayerEnum.ME && secondCell.getPlayer() == PlayerEnum.ME){
                return cell;
            }
            else if (firstCell.getPlayer() == PlayerEnum.ME || secondCell.getPlayer() == PlayerEnum.ME) {
                matchedCells.add(cell);
            }
        }
        return null;
    }

    //given the list of cells with coordintates 
    public Cell vertical(UTTTSubBoard board, List<Cell> cells) {
        Iterator iterator = cells.iterator();
        while(iterator.hasNext()) {
            Cell cell = (Cell) iterator.next();

            int x = cell.getCell().getRow();
            int y = cell.getCell().getRow();

            int firstIndexToCheck = ((((x+1)%3)+3)%3);
            int secondIndexToCheck = ((((x-1)%3)+3)%3);

            Coords firstCoord = new Coords(firstIndexToCheck, y);
            Coords secondCoord = new Coords(secondIndexToCheck, y);

            Cell firstCell = board.getCell(firstCoord);
            Cell secondCell = board.getCell(secondCoord);

            if (firstCell.getPlayer() == PlayerEnum.ME && secondCell.getPlayer() == PlayerEnum.ME){
                return cell;
            }
            else if (firstCell.getPlayer() == PlayerEnum.ME || secondCell.getPlayer() == PlayerEnum.ME) {
                matchedCells.add(cell);
            }
        }
        return null;
    }

    //given the list of cells with coordintates 
    public Cell diagonal(UTTTSubBoard board, List<Cell> cells) {
        Iterator iterator = cells.iterator();
        while(iterator.hasNext()) {
            Cell cell = (Cell) iterator.next();

            int x = cell.getCell().getRow();
            int y = cell.getCell().getRow();

            if ((x + y)%2 == 0){ 

                if (x == 1 && y == 1){
                    int firstXIndexToCheck = ((((x+1)%3)+3)%3);
                    int secondXIndexToCheck = ((((x-1)%3)+3)%3);

                    int firstYIndexToCheck = ((((y+1)%3)+3)%3);
                    int secondYIndexToCheck = ((((y-1)%3)+3)%3);

                    Coords firstCoord = new Coords(firstXIndexToCheck, firstYIndexToCheck);
                    Coords secondCoord = new Coords(secondXIndexToCheck, secondYIndexToCheck);
                    Coords thirdCoord = new Coords(secondXIndexToCheck, firstYIndexToCheck);
                    Coords fourthCoord = new Coords(firstXIndexToCheck, secondYIndexToCheck);
                    
                    Cell firstCell = board.getCell(firstCoord);
                    Cell secondCell = board.getCell(secondCoord);
                    Cell thirdCell = board.getCell(thirdCoord);
                    Cell fourthCell = board.getCell(fourthCoord);

                    if (firstCell.getPlayer() == PlayerEnum.ME && secondCell.getPlayer() == PlayerEnum.ME){
                        return cell;
                    }
                    else if (thirdCell.getPlayer() == PlayerEnum.ME && fourthCell.getPlayer() == PlayerEnum.ME){
                        return cell;
                    }
                    // else if (firstCell.getPlayer() == PlayerEnum.ME || secondCell.getPlayer() == PlayerEnum.ME
                    //         || thirdCell.getPlayer() == PlayerEnum.ME || fourthCell.getPlayer() == PlayerEnum.ME) {
                    //     matchedCells.add(cell);
                    // }
                }

                else {

                    int firstXIndexToCheck = ((((x+1)%3)+3)%3);
                    int secondXIndexToCheck = ((((x-1)%3)+3)%3);

                    int firstYIndexToCheck = ((((y+1)%3)+3)%3);
                    int secondYIndexToCheck = ((((y-1)%3)+3)%3);

                    Coords firstCoord = new Coords(firstXIndexToCheck, firstYIndexToCheck);
                    Coords secondCoord = new Coords(secondXIndexToCheck, secondYIndexToCheck);

                    Cell firstCell = board.getCell(firstCoord);
                    Cell secondCell = board.getCell(secondCoord);

                    if (firstCell.getPlayer() == PlayerEnum.ME && secondCell.getPlayer() == PlayerEnum.ME){
                        return cell;
                    }
                    else if (firstCell.getPlayer() == PlayerEnum.ME || secondCell.getPlayer() == PlayerEnum.ME) {
                        matchedCells.add(cell);
                    }

                }//else
            }
        }
        return null;
    }

    @Override
    public Move opponentMove(Move opponentsMove) {
        this.getCurrentBoard().addOpponentMove(opponentsMove);
        if (this.getCurrentBoard().isFinished(opponentsMove.getMove())) return this.getMove();

        List<Cell> cells = this.getCurrentBoard().getEmptyCells(opponentsMove.getMove());
        if (cells.size() == 0) return this.getMove();
        Move myMove = cells.get(randomNum.nextInt(cells.size())).asMove();
        this.getCurrentBoard().addMyMove(myMove);

        return myMove;
    }

    @Override
    public void gameOver(Result result, Move lastMove) {
//        if (lastMove != null)
//            this.getCurrentBoard().addOpponentMove(lastMove);
//        this.debug("GAME OVER: " + result);
    }

    @Override
    public void matchOver(Result result) {
//        this.debug("MATCH OVER: " + result);
    }
    
    @Override
    public void timeout() {
//        this.debug("TIMED OUT");
    }

}
