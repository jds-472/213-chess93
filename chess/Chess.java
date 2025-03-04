package chess;

import java.util.ArrayList;

public class Chess {

        enum Player { white, black }
	private static Player currPlayer;
    
	/**
	 * Plays the next move for whichever player has the turn.
	 * 
	 * @param move String for next move, e.g. "a2 a3"
	 * 
	 * @return A ReturnPlay instance that contains the result of the move.
	 *         See the section "The Chess class" in the assignment description for details of
	 *         the contents of the returned ReturnPlay instance.
	 */
	public static ReturnPlay play(String move) {
		ReturnPlay result = new ReturnPlay();
		result.piecesOnBoard = Board.getPieces();
		if (move.equals("resign")){
			if(currPlayer == Player.white){
				result.message = ReturnPlay.Message.RESIGN_WHITE_WINS;
				return result;
			}
			else{
				result.message = ReturnPlay.Message.RESIGN_BLACK_WINS;
				return result;
			}
		}
		String[] moveParts = move.split(" ");
		if (moveParts.length < 2 || moveParts.length > 3){
			result.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return result;
		}
		ReturnPiece.PieceFile fromFile = ReturnPiece.PieceFile.valueOf(moveParts[0].substring(0, 1));
		int fromRank = Integer.parseInt(moveParts[0].substring(1));
		ReturnPiece.PieceFile toFile = ReturnPiece.PieceFile.valueOf(moveParts[1].substring(0, 1));
		int toRank = Integer.parseInt(moveParts[1].substring(1));
		Piece current = Board.getPiece(fromFile, fromRank);
		if (current == null){
			result.message = ReturnPlay.Message.ILLEGAL_MOVE;
			//System.out.println("current is null");
			return result;
		}
		if (current.getColor() != currPlayer){
			result.message = ReturnPlay.Message.ILLEGAL_MOVE;
			//System.out.println("wrong color");
			return result;
		}
		if (moveParts.length == 3 && moveParts[2].equals("draw?"))
		{
			result.message = ReturnPlay.Message.DRAW;
		}
		else if (moveParts.length == 3)
		{
			current = ((Pawn) current).promote(toFile, toRank, moveParts[2]);
		}
		else
		{
			current = current.move(toFile, toRank);
		}
		
		//if the move was invalid the piece wil return null
		if (current == null){
			result.message = ReturnPlay.Message.ILLEGAL_MOVE;
			//System.out.println("current became null after move");
			return result;
		}
		Piece blackKing = Board.getPiece(ReturnPiece.PieceType.BK);
		Piece whiteKing = Board.getPiece(ReturnPiece.PieceType.WK);
		if (current.getColor() == Player.white && Board.checkForCheck(Player.black, blackKing.getFile(), blackKing.getRank())){
			result.message = ReturnPlay.Message.CHECK;
			if (Board.checkForCheckmate(Player.black)){
				result.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
			}
		}
		else if (current.getColor() == Player.black && Board.checkForCheck(Player.white, whiteKing.getFile(), whiteKing.getRank())){
			result.message = ReturnPlay.Message.CHECK;
			if (Board.checkForCheckmate(Player.white)){
				result.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
			}
		}
		if (currPlayer == Player.white){
			currPlayer = Player.black;
		}
		else{
			currPlayer = Player.white;
		}
		result.piecesOnBoard.clear();
		result.piecesOnBoard = Board.getPieces();
		for (ReturnPiece p : result.piecesOnBoard){
			System.out.println(p);
		}
		return result;
	}
	
	
	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
        Board.initialize();
		Clock.reset();
        currPlayer = Player.white;
	}
}
