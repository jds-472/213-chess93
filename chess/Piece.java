package chess;

abstract class Piece extends ReturnPiece{
    protected Chess.Player color;
    protected boolean hasMoved;
    protected int lastTurn;
    
    public Piece(Chess.Player color, PieceType type, PieceFile file, int rank) {
        this.color = color;
        this.pieceType = type;
        this.pieceFile = file;
        this.pieceRank = rank;
        hasMoved = false;
        lastTurn = 0;
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

    public int getLastTurn() {
        return lastTurn;
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
        lastTurn = Clock.getCurrentTurn();
        Clock.incrementTurn();
    }
}

class Pawn extends Piece {
    private int squaresMoved;
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
            if (rankTo == 1 || rankTo == 8) {
                pieceType = color == Chess.Player.white ? PieceType.WQ : PieceType.BQ;
            }
            if (fileDiff == 0 && destination == null && (Math.abs(rankDiff) == 1 || (Math.abs(rankDiff) == 2 && !hasMoved))) {
                updatePosition(fileTo, rankTo);
                squaresMoved = Math.abs(rankDiff);
                return this;
            }
            if (destination != null && Math.abs(fileDiff) == 1 && Math.abs(rankDiff) == 1) {
                updatePosition(fileTo, rankTo);
                squaresMoved = Math.abs(rankDiff);
                return this;
            }
            if (destination == null && Math.abs(fileDiff) == 1 && Math.abs(rankDiff) == 1) {
                Piece enPassant = Board.getPiece(fileTo, pieceRank);
                if (enPassant != null && ((enPassant.getType() == PieceType.WP && color == Chess.Player.black) || enPassant.getType() == PieceType.BP && color == Chess.Player.white) && enPassant.getLastTurn() == Clock.getCurrentTurn() - 1 && ((Pawn) enPassant).getSquaresMoved() == 2) {
                    Board.removePiece(enPassant);
                    updatePosition(fileTo, rankTo);
                    squaresMoved = Math.abs(rankDiff);
                    return this;
                }
            }
        }
        return null;
    }

    public Piece promote(PieceFile fileTo, int rankTo, String type) {
        if (rankTo != 1 && rankTo != 8) {
            return null;
        }
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

    public int getSquaresMoved() {
        return squaresMoved;
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
                return this;
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
        int rankDiff = Math.abs(rankTo - pieceRank);
        int fileDiff = Math.abs(fileTo.compareTo(pieceFile));
        if ((rankDiff == 2 && fileDiff == 1) || (rankDiff == 1 && fileDiff == 2)) {
            if (dest == null || dest.getColor() != color) {
                updatePosition(fileTo, rankTo);
                return this;
            }
        }
        return null;
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

        if (rankDiff <= 1 && fileDiff <= 1 && (rankDiff != 0 || fileDiff != 0)) { // Normal king movement
            Piece ogPiece = Board.getPiece(fileTo, rankTo);
            Piece oldPos = Board.getPiece(pieceFile, pieceRank);
            Board.removePiece(this);
            pieceFile = fileTo;
            pieceRank = rankTo;
            Board.updateBoard(this);
            boolean inCheck = Board.checkForCheck(color);

            if (inCheck) {
                Board.removePiece(this);
                pieceFile = oldPos.getFile();
                pieceRank = oldPos.getRank();
                Board.updateBoard(oldPos);
                if (ogPiece != null) {
                    Board.updateBoard(ogPiece);
                }
                return null;
            }
            
            updatePosition(fileTo, rankTo);
            return this;
        }

        if (rankDiff == 0 && fileDiff == 2 && !hasMoved) {
            Piece rook = fileTo.compareTo(pieceFile) < 0 ? Board.getPiece(PieceFile.a, pieceRank) : Board.getPiece(PieceFile.h, pieceRank);
            if (rook != null && !rook.getHasMoved() && ((rook.getType() == PieceType.WR && color == Chess.Player.white) || (rook.getType() == PieceType.BR && color == Chess.Player.black))) {
                PieceFile rookFileTo = fileTo.compareTo(pieceFile) < 0 ? PieceFile.d : PieceFile.f;
                if (rook.move(rookFileTo, rankTo) != null) {
                    updatePosition(fileTo, rankTo);
                    return this;
                }
            }
        }
        return null; 
    }
}
