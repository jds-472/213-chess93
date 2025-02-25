package Chess;

abstract class Piece extends ReturnPiece{
    protected Chess.Player color;
    
    public Piece(Chess.Player color, PieceType type, PieceFile file, int rank) {
        this.color = color;
        this.pieceType = type;
        this.pieceFile = file;
        this.pieceRank = rank;
    }
    
    public Chess.Player getColor() {
        return color;
    }

    public PieceType getType() {
        return pieceType;
    }

    public PieceFile getFile() {
        return pieceFile;
    }

    public int getRank() {
        return pieceRank;
    }
    
    public abstract boolean canMove(String from, String to, Board board);
}

class Pawn extends Piece {
    public Pawn(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public boolean canMove(String from, String to, Board board) {
        return true; // Implement pawn movement rules
    }
}

class Rook extends Piece {
    public Rook(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public boolean canMove(String from, String to, Board board) {
        return true; // Implement rook movement rules
    }
}

class Knight extends Piece {
    public Knight(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public boolean canMove(String from, String to, Board board) {
        return true; // Implement knight movement rules
    }
}

class Bishop extends Piece {
    public Bishop(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public boolean canMove(String from, String to, Board board) {
        return true; // Implement bishop movement rules
    }
}

class Queen extends Piece {
    public Queen(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public boolean canMove(String from, String to, Board board) {
        return true; // Implement queen movement rules
    }
}

class King extends Piece {
    public King(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public boolean canMove(String from, String to, Board board) {
        return true; // Implement king movement rules
    }
}
