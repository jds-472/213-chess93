package Chess;

public class Board {
    public static final boolean WHITE = true;
    public static final boolean BLACK = false;
    private Piece[][] board;

    public Board() {
        board = new Piece[8][8];
    }
    public void initialize() {
        // Initialize pieces on the board
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(WHITE);
            board[6][i] = new Pawn(BLACK);
        }
        board[0][0] = new Rook(WHITE);
        board[0][1] = new Knight(WHITE);
        board[0][2] = new Bishop(WHITE);
        board[0][3] = new Queen(WHITE);
        board[0][4] = new King(WHITE);
        board[0][5] = new Bishop(WHITE);
        board[0][6] = new Knight(WHITE);
        board[0][7] = new Rook(WHITE);

        board[7][0] = new Rook(BLACK);
        board[7][1] = new Knight(BLACK);
        board[7][2] = new Bishop(BLACK);
        board[7][3] = new Queen(BLACK);
        board[7][4] = new King(BLACK);
        board[7][5] = new Bishop(BLACK);
        board[7][6] = new Knight(BLACK);
        board[7][7] = new Rook(BLACK);
    }
}
