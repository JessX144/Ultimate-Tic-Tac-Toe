package players;

import model.Move;
import model.Result;

/**
 * The interface Player.
 */
public interface Player {

    /**
     * Process Input.
     * Will Process Input String From UABC.
     * @implNote input follows format (<command> <message>).
     * {@link model.Command}
     *
     * @param input the message received from the server.
     */
    public void processInput(String input);

    /**
     * Write Move.
     * Will Take Move, Format Correctly And Write To UABC.
     * @see #write(String)
     *
     * @param move Then Move You Wish To Make.
     */
    public void writeMove(Move move);

    /**
     * Write Message.
     * Take A String And Writes To UABC.
     *
     * @param message the message
     */
    public void write(String message);

    /**
     * Debug Message.
     * Take A String And Writes To Debug Console.
     *
     * @param message the message
     */
    public void debug(String message);
    
    /**
     * New Game.
     * Prepare The Board.
     */
    public void init();

    /**
     * Get Move.
     * You Are Starting First.
     * Pick A Board And A Move.
     *
     * @return Your First Move
     */
    public Move getMove();

    /**
     * Opponent Move.
     * Your Opponent Made A Move.
     * Respond With A Move Where {@return Move}{@link model.Move#board} Matches {@param opponentsMove}.{@link model.Move#board}
     *
     * @param opponentsMove the opponents move
     * @return Your Move In Response
     */
    public Move opponentMove(Move opponentsMove);

    /**
     * Game Over.
     * The Game Is Over. You Might Still With The Match.
     * Another Game Will Start Soon.
     *
     * @param result (WIN, LOSE, TIE)
     * @param previousMove - Null unless you were not last move
     */
    public void gameOver(Result result, Move previousMove);

    /**
     * Match Over.
     * The Match Is Over.
     * You May Get To Play Again. But For Now, You Have No Opponent.
     *
     * @param result (WIN, LOSE, TIE)
     */
    public void matchOver(Result result);
    
    /**
     * Timed Out.
     * The Match Is Over.
     */
    public void timeout();

}
