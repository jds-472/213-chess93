package chess;

abstract class Piece extends ReturnPiece{
    protected Chess.Player color;
    protected boolean hasMoved;
    
    public Piece(Chess.Player color, PieceType type, PieceFile file, int rank) {
        this.color = color;
        this.pieceType = type;
        this.pieceFile = file;
        this.pieceRank = rank;
        hasMoved = false;
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

    public boolean getHasMoved() {
        return hasMoved;
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

    public String toString() {
        return pieceType + " " + color + " " + pieceFile + " " + pieceRank;
    }
    
    public Piece move(PieceFile fileTo, int rankTo)
    {
        if (Board.getPiece(fileTo, rankTo) != null && Board.getPiece(fileTo, rankTo).getColor() == color) {
            return null;
        }
        return this;
    }

    protected void updatePosition(PieceFile fileTo, int rankTo) {
        Board.removePiece(this);
        pieceFile = fileTo;
        pieceRank = rankTo;
        hasMoved = true;
        Board.updateBoard(this);
    }
}

class Pawn extends Piece {
    private boolean hasMoved;
    public Pawn(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public Piece move(PieceFile fileTo, int rankTo) {
        if (super.move(fileTo, rankTo) == null) {
            return null;
        }
        int rankDiff = rankTo - pieceRank;
        int fileDiff = fileTo.compareTo(pieceFile);
        Piece destination = Board.getPiece(fileTo, rankTo);

        if ((color == Chess.Player.white && rankDiff > 0) || (color == Chess.Player.black && rankDiff < 0)) {
            if (fileDiff == 0 && destination == null && (Math.abs(rankDiff) == 1 || (Math.abs(rankDiff) == 2 && !hasMoved))) {
                if (rankTo == 1 || rankTo == 8) {
                    pieceType = color == Chess.Player.white ? PieceType.WQ : PieceType.BQ;
                }
                updatePosition(fileTo, rankTo);
                return this;
            }
            if (destination != null && Math.abs(fileDiff) == 1 && Math.abs(rankDiff) == 1) {
                updatePosition(fileTo, rankTo);
                return this;
            }
        }
        return null;
    }

    public Piece promote(PieceFile fileTo, int rankTo, String type) {
        move(fileTo, rankTo);
        switch (type) {
            case "N":
                pieceType = color == Chess.Player.white ? PieceType.WN : PieceType.BN;
                break;
            case "B":
                pieceType = color == Chess.Player.white ? PieceType.WB : PieceType.BB;
                break;
            case "R":
                pieceType = color == Chess.Player.white ? PieceType.WR : PieceType.BR;
                break;
            case "Q", "":
                pieceType = color == Chess.Player.white ? PieceType.WQ : PieceType.BQ;
                break;
            default:
                throw new IllegalArgumentException("Invalid piece type for promotion: " + type);
        }
        return this;
    }
}

class Rook extends Piece {
    public Rook(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
        this.hasMoved = false; 
    }
    
    public Piece move(PieceFile fileTo, int rankTo) { // Implemented rook movement rules (still have to implement castling)
        if (super.move(fileTo, rankTo) == null){
            return null;
        }
        if  (pieceRank == rankTo || pieceFile == fileTo) {
            if (Board.isPathClear(pieceFile, pieceRank, fileTo, rankTo)) { 
                updatePosition(fileTo, rankTo);
            }
        }
        return null; 
    }
}

class Knight extends Piece {
    public Knight(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public Piece move(PieceFile fileTo, int rankTo) {
        if (super.move(fileTo, rankTo) == null){
            return null;
        }
        Piece dest = Board.getPiece(fileTo, rankTo);
        if (dest != null && dest.getColor() == color) {
            return null;
        }
        updatePosition(fileTo, rankTo);
        return this; // Implement deleting the piece it captures
    }
}

class Bishop extends Piece {
    public Bishop(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public Piece move(PieceFile fileTo, int rankTo) { // Implemented bishop movement rules
        if (super.move(fileTo, rankTo) == null){
            return null;
        }
        if (Math.abs(fileTo.compareTo(pieceFile)) == Math.abs(rankTo - pieceRank)) {
            if (Board.isPathClear(pieceFile, pieceRank, fileTo, rankTo)) { 
                updatePosition(fileTo, rankTo);
                return this;
            }
        }
        return null; 
    }
}

class Queen extends Piece {
    public Queen(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public Piece move(PieceFile fileTo, int rankTo) { // Implemented queen movement rules
        if (super.move(fileTo, rankTo) == null){
            return null;
        }
        if (Math.abs(fileTo.compareTo(pieceFile)) == Math.abs(rankTo - pieceRank) || pieceRank == rankTo || pieceFile == fileTo) {
            if (Board.isPathClear(pieceFile, pieceRank, fileTo, rankTo)) { 
                updatePosition(fileTo, rankTo);
                return this;
            }
        }
        return null; 
    }
}

class King extends Piece {
    public King(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public Piece move(PieceFile fileTo, int rankTo) { // Implemented king movement rules (still have to implement castling)
        if (super.move(fileTo, rankTo) == null){
            return null;
        }
        int rankDiff = Math.abs(rankTo - pieceRank);
        int fileDiff = Math.abs(fileTo.compareTo(pieceFile));

        if (rankDiff <= 1 && fileDiff <= 1) {
            updatePosition(fileTo, rankTo);
            return this;
        }
        return null; 
    }
}
