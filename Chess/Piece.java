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

    public void setPieceType(PieceType type) {
        this.pieceType = type;
    }

    public void setPieceFile(PieceFile file) {
        this.pieceFile = file;
    }

    public void setPieceRank(int rank) {
        this.pieceRank = rank;
    }
    
    public abstract Piece move(PieceFile fileTo, int rankTo);
}

class Pawn extends Piece {
    private boolean hasMoved;
    public Pawn(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public Piece move(PieceFile fileTo, int rankTo) {
        return this; // Implement pawn movement rules
    }
}

class Rook extends Piece {
    private boolean hasMoved;
    public Rook(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public Piece move(PieceFile fileTo, int rankTo) {
        return this; // Implement rook movement rules
    }
}

class Knight extends Piece {
    public Knight(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public Piece move(PieceFile fileTo, int rankTo) {
        return this; // Implement knight movement rules
    }
}

class Bishop extends Piece {
    public Bishop(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public Piece move(PieceFile fileTo, int rankTo) {
        return this; // Implement bishop movement rules
    }
}

class Queen extends Piece {
    public Queen(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public Piece move(PieceFile fileTo, int rankTo) {
        return this; // Implement queen movement rules
    }
}

class King extends Piece {
    private boolean hasMoved;
    public King(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public Piece move(PieceFile fileTo, int rankTo) {
        return this; // Implement king movement rules
    }
}
