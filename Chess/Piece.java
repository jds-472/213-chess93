package Chess;

abstract class Piece {
    protected Player color;
    
    public Piece(Player color) {
        this.color = color;
    }
    
    public Player getColor() {
        return color;
    }
    
    public abstract boolean canMove(String from, String to, Board board);
}

class Pawn extends Piece {
    public Pawn(Player color) {
        super(color);
    }
    
    public boolean canMove(String from, String to, Board board) {
        return true; // Implement pawn movement rules
    }
}

class Rook extends Piece {
    public Rook(Player color) {
        super(color);
    }
    
    public boolean canMove(String from, String to, Board board) {
        return true; // Implement rook movement rules
    }
}

class Knight extends Piece {
    public Knight(Player color) {
        super(color);
    }
    
    public boolean canMove(String from, String to, Board board) {
        return true; // Implement knight movement rules
    }
}

class Bishop extends Piece {
    public Bishop(Player color) {
        super(color);
    }
    
    public boolean canMove(String from, String to, Board board) {
        return true; // Implement bishop movement rules
    }
}

class Queen extends Piece {
    public Queen(Player color) {
        super(color);
    }
    
    public boolean canMove(String from, String to, Board board) {
        return true; // Implement queen movement rules
    }
}

class King extends Piece {
    public King(Player color) {
        super(color);
    }
    
    public boolean canMove(String from, String to, Board board) {
        return true; // Implement king movement rules
    }
}
