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

    public static Piece getPiece(ReturnPiece.PieceFile file, int rank) {
        if (rank <= 0 || rank > 8) {
            throw new IllegalArgumentException("Invalid rank: " + rank);
        }
        int fileIndex = file.ordinal();
        return board[rank-1][fileIndex];
    }


}
