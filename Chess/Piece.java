package Chess;

abstract class Piece {
    public static final boolean WHITE = true;
    public static final boolean BLACK = false;
    protected boolean color;
    
    public Piece(boolean color) {
        this.color = color;
    }
    
    public boolean getColor() {
        return color;
    }
    
    public abstract boolean canMove(String from, String to, Board board);
}

class Pawn extends Piece {
    public Pawn(boolean color) {
        super(color);
    }
    
    public boolean canMove(String from, String to, Board board) {
        return true; // Implement pawn movement rules
    }
}

class Rook extends Piece {
    public Rook(boolean color) {
        super(color);
    }
    
    public boolean canMove(String from, String to, Board board) {
        return true; // Implement rook movement rules
    }
}

class Knight extends Piece {
    public Knight(boolean color) {
        super(color);
    }
    
    public boolean canMove(String from, String to, Board board) {
        return true; // Implement knight movement rules
    }
}

class Bishop extends Piece {
    public Bishop(boolean color) {
        super(color);
    }
    
    public boolean canMove(String from, String to, Board board) {
        return true; // Implement bishop movement rules
    }
}

class Queen extends Piece {
    public Queen(boolean color) {
        super(color);
    }
    
    public boolean canMove(String from, String to, Board board) {
        return true; // Implement queen movement rules
    }
}

class King extends Piece {
    public King(boolean color) {
        super(color);
    }
    
    public boolean canMove(String from, String to, Board board) {
        return true; // Implement king movement rules
    }
}
