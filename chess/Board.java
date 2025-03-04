package chess;

import java.util.ArrayList;

public class Board {
    private static Piece[][] board;

    public Board() {
        board = new Piece[8][8];
    }

    public static void initialize() {
        board = new Piece[8][8];
        // Initialize pieces on the board
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(Chess.Player.white, ReturnPiece.PieceType.WP, ReturnPiece.PieceFile.values()[i], 2);
            board[6][i] = new Pawn(Chess.Player.black, ReturnPiece.PieceType.BP, ReturnPiece.PieceFile.values()[i], 7);
        }
        board[0][0] = new Rook(Chess.Player.white, ReturnPiece.PieceType.WR, ReturnPiece.PieceFile.a, 1);
        board[0][1] = new Knight(Chess.Player.white, ReturnPiece.PieceType.WN, ReturnPiece.PieceFile.b, 1);
        board[0][2] = new Bishop(Chess.Player.white, ReturnPiece.PieceType.WB, ReturnPiece.PieceFile.c, 1);
        board[0][3] = new Queen(Chess.Player.white, ReturnPiece.PieceType.WQ, ReturnPiece.PieceFile.d, 1);
        board[0][4] = new King(Chess.Player.white, ReturnPiece.PieceType.WK, ReturnPiece.PieceFile.e, 1);
        board[0][5] = new Bishop(Chess.Player.white, ReturnPiece.PieceType.WB, ReturnPiece.PieceFile.f, 1);
        board[0][6] = new Knight(Chess.Player.white, ReturnPiece.PieceType.WN, ReturnPiece.PieceFile.g, 1);
        board[0][7] = new Rook(Chess.Player.white, ReturnPiece.PieceType.WR, ReturnPiece.PieceFile.h, 1);

        board[7][0] = new Rook(Chess.Player.black, ReturnPiece.PieceType.BR, ReturnPiece.PieceFile.a, 8);
        board[7][1] = new Knight(Chess.Player.black, ReturnPiece.PieceType.BN, ReturnPiece.PieceFile.b, 8);
        board[7][2] = new Bishop(Chess.Player.black, ReturnPiece.PieceType.BB, ReturnPiece.PieceFile.c, 8);
        board[7][3] = new Queen(Chess.Player.black, ReturnPiece.PieceType.BQ, ReturnPiece.PieceFile.d, 8);
        board[7][4] = new King(Chess.Player.black, ReturnPiece.PieceType.BK, ReturnPiece.PieceFile.e, 8);
        board[7][5] = new Bishop(Chess.Player.black, ReturnPiece.PieceType.BB, ReturnPiece.PieceFile.f, 8);
        board[7][6] = new Knight(Chess.Player.black, ReturnPiece.PieceType.BN, ReturnPiece.PieceFile.g, 8);
        board[7][7] = new Rook(Chess.Player.black, ReturnPiece.PieceType.BR, ReturnPiece.PieceFile.h, 8);
    }

    public static ArrayList<ReturnPiece> getPieces() {
        ArrayList<ReturnPiece> pieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    pieces.add(board[i][j]);
                }
            }
        }
        return pieces;
    }

    public static void updateBoard(Piece piece) {
        int rank = piece.getRank();
        int file = piece.getFile().ordinal();
        Piece destination = getPiece(piece.getFile(), rank);
        if (destination != null) {
            removePiece(destination);
        }
        board[rank-1][file] = piece;
    }

    public static void removePiece(Piece piece) {
        int rank = piece.getRank();
        int file = piece.getFile().ordinal();
        board[rank-1][file] = null;
    }

    public static Piece getPiece(ReturnPiece.PieceFile file, int rank) {
        if (rank <= 0 || rank > 8) {
            throw new IllegalArgumentException("Invalid rank: " + rank);
        }
        int fileIndex = file.ordinal();
        return board[rank-1][fileIndex];
    }

    public static Piece getPiece(ReturnPiece.PieceType type) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece.getType() == type) {
                    return piece;
                }
            }
        }
        return null;
    }

    public static boolean isPathClear(ReturnPiece.PieceFile fromFile, int fromRank, ReturnPiece.PieceFile toFile, int toRank) {
        int fStep;
        int rStep;
        ReturnPiece.PieceFile currFile = fromFile;
        int currRank = fromRank;

        if (fromFile.compareTo(toFile) < 0) {
            fStep = 1;
        } else if (fromFile.compareTo(toFile) > 0) {
            fStep = -1;
        } else {
            fStep = 0;
        }

        if (fromRank < toRank) {
            rStep = 1;
        } else if (fromRank > toRank) {
            rStep = -1;
        } else {
            rStep = 0;
        }

        if (fStep != 0) {
            currFile = ReturnPiece.PieceFile.values()[currFile.ordinal() + fStep];
        }
        currRank += rStep;

        while ((currRank != toRank) || (currFile != toFile) && currFile.ordinal() >= 0 && currFile.ordinal() <= 7 && currRank >= 1 && currRank <= 8) {
            if (board[currRank-1][currFile.ordinal()] != null) {
                return false;
            }
            if (fStep != 0) {
                currFile = ReturnPiece.PieceFile.values()[currFile.ordinal() + fStep];
            }
            currRank += rStep;
        }
        return true;
    }

    public static boolean checkForCheck(Chess.Player player, ReturnPiece.PieceFile targetFile, int targetRank) {
        // Piece king = null;
        // ReturnPiece.PieceType kType;
        
        // if (player == Chess.Player.white) {
        //     kType = ReturnPiece.PieceType.WK;
        // } else {
        //     kType = ReturnPiece.PieceType.BK;
        // }

        // for (int i = 0; i < 8; i++) {
        //     for (int j = 0; j < 8; j++) {
        //         if (board[i][j] != null && board[i][j].getType() == kType) {
        //             king = board[i][j];
        //             break;
        //         }
        //     }
        //     if (king != null) {
        //         break;
        //     }
        // }
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                // if (piece != null && piece.getColor() != player) {
                //     Piece test = null;
                //     if (piece instanceof Pawn) {
                //         test = new Pawn(piece.getColor(), piece.getType(), piece.getFile(), piece.getRank());
                //     } else if (test instanceof Rook) {
                //         test = new Rook(piece.getColor(), piece.getType(), piece.getFile(), piece.getRank());
                //     } else if (test instanceof Knight) {
                //         test = new Knight(piece.getColor(), piece.getType(), piece.getFile(), piece.getRank());
                //     } else if (test instanceof Bishop) {
                //         test = new Bishop(piece.getColor(), piece.getType(), piece.getFile(), piece.getRank());
                //     } else if (test instanceof Queen) {
                //         test = new Queen(piece.getColor(), piece.getType(), piece.getFile(), piece.getRank());
                //     } else if (test instanceof King) {
                //         test = new King(piece.getColor(), piece.getType(), piece.getFile(), piece.getRank());
                //     }
                    if (piece instanceof King) {
                        continue;
                    }
                    if (piece != null) {
                        if  (piece.canMove(targetFile, targetRank) && piece.getColor() != player) {
                            //System.out.println(piece + " can attack " + targetFile + targetRank);
                            return true;
                        }
                    }
                }
            }
        return false;
    }

    public static boolean checkForCheck(Chess.Player player) {
        Piece king = getPiece(player == Chess.Player.white ? ReturnPiece.PieceType.WK : ReturnPiece.PieceType.BK);
        ReturnPiece.PieceFile targetFile = king.getFile();
        int targetRank = king.getRank();
        return checkForCheck(player, targetFile, targetRank);
    }

    public static boolean checkForCheckmate(Chess.Player player)
    {
        ReturnPiece.PieceType kType = player == Chess.Player.white ? ReturnPiece.PieceType.WK : ReturnPiece.PieceType.BK;
        Piece king = getPiece(kType);
        ReturnPiece.PieceFile kingFile = king.getFile();
        int kingRank = king.getRank();
        //check if king is in check
        
        if (!checkForCheck(player, kingFile, kingRank))
        {
            return false;
        }
        //these checks are to check if the king can move out of check (destination is not in check nor occupied by friendly piece)
        //check left square
        if (kingFile != ReturnPiece.PieceFile.a){
            ReturnPiece.PieceFile leftFile = ReturnPiece.PieceFile.values()[kingFile.ordinal()-1];
            if (king.canMove(leftFile, kingRank)){
                return false;
            }
        }
        //check right square
        if (kingFile != ReturnPiece.PieceFile.h){
            ReturnPiece.PieceFile rightFile = ReturnPiece.PieceFile.values()[kingFile.ordinal()+1];
            if (king.canMove(rightFile, kingRank)){
                return false;
            }
        }
        if (kingRank != 1)
        {
            //check down square
            if (!checkForCheck(player, kingFile, kingRank-1) || getPiece(kingFile, kingRank-1).getColor() == player){
                return false;
            }
            //check down left square
            if (kingFile != ReturnPiece.PieceFile.a){
                ReturnPiece.PieceFile leftFile = ReturnPiece.PieceFile.values()[kingFile.ordinal()-1];
                if (king.canMove(leftFile, kingRank-1)){
                    return false;
                }
            }
            //check down right square
            if (kingFile != ReturnPiece.PieceFile.h){
                ReturnPiece.PieceFile rightFile = ReturnPiece.PieceFile.values()[kingFile.ordinal()+1];
                if (king.canMove(rightFile, kingRank-1)){
                    return false;
                }
            }
        }
        else if (kingRank != 8)
        {
            //check up square
            if (king.canMove(kingFile, kingRank+1)){
                return false;
            }
            //check up left square
            if (kingFile != ReturnPiece.PieceFile.a){
                ReturnPiece.PieceFile leftFile = ReturnPiece.PieceFile.values()[kingFile.ordinal()-1];
                if (king.canMove(leftFile, kingRank+1)){
                    return false;
                }
            }
            //check up right square
            if (kingFile != ReturnPiece.PieceFile.h){
                ReturnPiece.PieceFile rightFile = ReturnPiece.PieceFile.values()[kingFile.ordinal()+1];
                if (king.canMove(rightFile, kingRank+1)){
                    return false;
                }
            }
        }

        if (canBlockOrCapture(player)) { //if you can block or capture the attacking piece
            return false;
        }
        return true; 
    }

    private static boolean canBlockOrCapture(Chess.Player player)
    {
        //check if any piece can block or capture the attacking piece
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece.getColor() == player) {
                    for (int k = 0; k < 8; k++)
                    {
                        for (int l = 0; l < 8; l++)
                        {
                            Piece target = board[k][l];
                            if (target != null && target.getColor() == player)
                            {
                                continue;
                            }
                            Piece ogPiece;
                            switch (piece.getType())
                            {
                                case WP:
                                case BP:
                                    ogPiece = new Pawn(piece.getColor(), piece.getType(), piece.getFile(), piece.getRank());
                                    break;
                                case WR:
                                case BR:
                                    ogPiece = new Rook(piece.getColor(), piece.getType(), piece.getFile(), piece.getRank());
                                    break;
                                case WN:
                                case BN:
                                    ogPiece = new Knight(piece.getColor(), piece.getType(), piece.getFile(), piece.getRank());
                                    break;
                                case WB:
                                case BB:
                                    ogPiece = new Bishop(piece.getColor(), piece.getType(), piece.getFile(), piece.getRank());
                                    break;
                                case WQ:
                                case BQ:
                                    ogPiece = new Queen(piece.getColor(), piece.getType(), piece.getFile(), piece.getRank());
                                    break;
                                case WK:
                                case BK:
                                    ogPiece = new King(piece.getColor(), piece.getType(), piece.getFile(), piece.getRank());
                                    break;
                                default:
                                    ogPiece = null;
                                    break;
                            }
                            if (piece.move(ReturnPiece.PieceFile.values()[k-1], l+1) != null && piece.getColor() == player) //still working here because canMove doesn't test for still being in check
                            {
                                removePiece(piece);
                                updateBoard(ogPiece);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
