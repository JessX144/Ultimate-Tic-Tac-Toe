package players;

import model.Command;
import model.Move;
import model.Result;
import model.board.UTTTBoard;

public abstract class AbstractPlayer implements Player {

    private UTTTBoard currentBoard;

    public AbstractPlayer() {
        this.currentBoard = new UTTTBoard(3);
    }

    @Override
    public final void processInput(String input) {
        if (input == null) return;
        debug(input);
        String[] parts = input.split(" ");
        try {
            Command command = Command.getFromCommandMessage(parts[0]);
            switch (command) {
                case INIT:
                    this.init();
                    break;
                case MOVE:
                    this.writeMove(this.getMove());
                    break;
                case OPPONENT:
                    this.writeMove(this.opponentMove(new Move(parts[1])));
                    break;
                case GAME:
                    this.gameOver(Result.getFromStringMessage(parts[1]), parts.length < 3 ? null : new Move(parts[2]));
                    break;
                case MATCH:
                    this.matchOver(Result.getFromStringMessage(parts[1]));
                    break;
                case TIMEOUT:
                    this.timeout();
                    break;
            }
        } catch (EnumConstantNotPresentException e) {
            System.err.println("Message \"" + input + "\" Could Not Be Processed - No Matching Command" );
        }
    }

    @Override
    public void writeMove(Move move) {
        this.write(move.toString());
    }

    @Override
    public void write(String message) {
        debug(message);
        System.out.println(message);
    }

    @Override
    public void debug(String message) {
        if (message != null)
            if (message.startsWith("send:"))
                System.out.println("DEBUG:" + message.replaceFirst("send:", ""));
            else
                System.out.println("DEBUG:" + message);
    }

    protected UTTTBoard getCurrentBoard() {
        return currentBoard;
    }

    protected void newBoard() {
        this.currentBoard = new UTTTBoard(3);
    }
    
    @Override
    public abstract void init();
    
    @Override
    public abstract Move getMove();

    @Override
    public abstract Move opponentMove(Move opponentsMove);

    @Override
    public abstract void gameOver(Result result, Move lastMove);

    @Override
    public abstract void matchOver(Result result);
    
    @Override
    public abstract void timeout();

}
