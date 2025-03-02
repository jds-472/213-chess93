package chess;

public class Clock {
    private static int currentTurn = 0;

    public Clock() {
        currentTurn = 0;
    }

    public static void incrementTurn() {
        currentTurn++;
    }

    public static int getCurrentTurn() {
        return currentTurn;
    }

    public static void setCurrentTurn(int turn) {
        currentTurn = turn;
    }

    public static void reset() {
        currentTurn = 0;
    }
}