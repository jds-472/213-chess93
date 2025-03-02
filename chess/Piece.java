package chess;

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
        this.hasMoved = false;
    }
    
    public Piece move(PieceFile fileTo, int rankTo) { // Implemented pawn movement rules 
        int rankDiff = rankTo - pieceRank;
        int fileDiff = fileTo.compareTo(pieceFile);

        if (Chess.Player.white == color) {
            if (fileDiff == 0 && Board.getPiece(fileTo, rankTo) == null) {
                if (rankDiff == 1) {
                    pieceFile = fileTo;
                    pieceRank = rankTo;
                    hasMoved = true;
                    return this;
                }
                if (rankDiff == 2 && !hasMoved) {
                    pieceFile = fileTo;
                    pieceRank = rankTo;
                    hasMoved = true;
                    return this;
                }
            }
            if (Board.getPiece(fileTo, rankTo) != null && Math.abs(fileDiff) == 1 && rankDiff == 1) {
                pieceFile = fileTo;
                pieceRank = rankTo;
                hasMoved = true;
                return this;
            }
        } else {
            if(fileDiff == 0 && Board.getPiece(fileTo, rankTo) == null) {
                if (rankDiff == -1) {
                    pieceFile = fileTo;
                    pieceRank = rankTo;
                    hasMoved = true;
                    return this;
                }
                if (rankDiff == -2 && !hasMoved) {
                    pieceFile = fileTo;
                    pieceRank = rankTo;
                    hasMoved = true;
                    return this;
                }
            }
            if(Board.getPiece(fileTo, rankTo) != null && Math.abs(fileDiff) == 1 && rankDiff == -1) {
                pieceFile = fileTo;
                pieceRank = rankTo;
                hasMoved = true;
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
    private boolean hasMoved;
    public Rook(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
        this.hasMoved = false; 
    }
    
    public Piece move(PieceFile fileTo, int rankTo) { // Implemented rook movement rules (still have to implement castling)
        if  (pieceRank == rankTo || pieceFile == fileTo) {
            if (Board.isPathClear(pieceFile, pieceRank, fileTo, rankTo)) { 
                pieceFile = fileTo;
                pieceRank = rankTo;
                hasMoved = true;
                return this;
            }
        }
        return null; 
    }

    public boolean getHasMoved() {
        return hasMoved;
    }
}

class Knight extends Piece {
    public Knight(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public Piece move(PieceFile fileTo, int rankTo) {
        Piece dest = Board.getPiece(fileTo, rankTo);
        if (dest != null && dest.getColor() == color) {
            return null;
        }
        pieceFile = fileTo;
        pieceRank = rankTo;
        return this; // Implement deleting the piece it captures
    }
}

class Bishop extends Piece {
    public Bishop(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
    }
    
    public Piece move(PieceFile fileTo, int rankTo) { // Implemented bishop movement rules
        if (Math.abs(fileTo.compareTo(pieceFile)) == Math.abs(rankTo - pieceRank)) {
            if (Board.isPathClear(pieceFile, pieceRank, fileTo, rankTo)) { 
                pieceFile = fileTo;
                pieceRank = rankTo;
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
        if (Math.abs(fileTo.compareTo(pieceFile)) == Math.abs(rankTo - pieceRank) || pieceRank == rankTo || pieceFile == fileTo) {
            if (Board.isPathClear(pieceFile, pieceRank, fileTo, rankTo)) { 
                pieceFile = fileTo;
                pieceRank = rankTo;
                return this;
            }
        }
        return null; 
    }
}

class King extends Piece {
    private boolean hasMoved;
    public King(Chess.Player color, PieceType type, PieceFile file, int rank) {
        super(color, type, file, rank);
        this.hasMoved = false;
    }
    
    public Piece move(PieceFile fileTo, int rankTo) { // Implemented king movement rules (still have to implement castling)
        int rankDiff = Math.abs(rankTo - pieceRank);
        int fileDiff = Math.abs(fileTo.compareTo(pieceFile));

        if (rankDiff <= 1 && fileDiff <= 1) {
            pieceFile = fileTo;
            pieceRank = rankTo;
            hasMoved = true;
        }
        return null; 
    }

    public boolean getHasMoved() {
        return hasMoved;
    }
}
