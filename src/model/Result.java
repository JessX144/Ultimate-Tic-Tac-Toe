package model;

public enum Result {
    WIN,
    LOSE,
    TIE,
    UNFINISHED;

    public static Result getFromStringMessage(String commandMessage) {
        switch (commandMessage){
            case "win": return WIN;
            case "lose": return LOSE;
            case "tie": return TIE;
        }
        return null;
    }
}
