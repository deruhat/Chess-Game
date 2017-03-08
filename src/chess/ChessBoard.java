package chess;


/**
 * Chessboard matrix implementation.
 * @author Abdulellah Abualshour
 * @author Fahad Syed
 * 
 */
public class ChessBoard {
	
	private ChessPiece[][] board = new ChessPiece[8][8];
	private Pawn[] enpassantWhite = new Pawn[1];
	private Pawn[] enpassantBlack = new Pawn[1];
	
	/**
	 * no argument constructor for creating a chessboard. Sets the board as soon as it's created.
	 */
	public ChessBoard(){
		this.setBoard(); 
	}
	
	/**
	 * one argument constructor for debugging purposes.
	 * @param s "debugging"
	 */
	public ChessBoard(String s){
		
		for(int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				board[i][j] = null; 
			}
		}
		
		board[0][4] = new King("white");
		board[7][4] = new King("black");
		board[0][0] = new Rook("white");
		board[7][7] = new Rook("black");
		board[0][7] = new Rook("white");
		board[7][0] = new Rook("black");
	}
	
	/**
	 * method for setting the chess pieces initially.
	 * 
	 */
	public void setBoard(){
		
		for(int i = 0; i < 8; i++){
			board[1][i] = new Pawn("white");
			board[6][i] = new Pawn("black");
			if(i ==0 || i == 7){
				board[0][i] = new Rook("white");
				board[7][i] = new Rook("black");
			}
			else if(i == 2 || i == 5){
				board[0][i] = new Bishop("white");
				board[7][i] = new Bishop("black");
			}
			else if(i == 3){
				board[0][i] = new Queen("white");
				board[7][i] = new Queen("black");
			}
			else if(i == 4){
				board[0][i] = new King("white");
				board[7][i] = new King("black");
			}
			else if(i == 1 || i == 6){
				board[0][i] = new Knight("white");
				board[7][i] = new Knight("black");
			}
		}		
	}
	
	/**
	 * method for printing out the matrix in user-friendly interface.
	 */
	public void printBoard(){
		boolean squareColor = true;
		for(int rows = 8; rows >= 0; rows--){
			for(int cols = 0; cols <= 8; cols++){
				if(rows > 0){
					if(cols < 8){
						if(board[rows-1][cols] == null){
							if(squareColor == true){
								System.out.print("## ");
							}
							else{
								System.out.print("   ");
							}
						}
						else{
							ChessPiece x = board[rows-1][cols];
							System.out.print(x);
						}
					}
					else{
						System.out.println(rows);
					}
					squareColor = !squareColor;
				}
				else{
					System.out.println(" a  b  c  d  e  f  g  h ");
					cols = 9; 
				}
			}
		}
	}
	
	/**
	 * method for checking if the coordinate exists in the matrix.
	 * @param x row
	 * @param y column
	 * @return true or false
	 */
	public boolean doesCoordExist(int x, int y){
		return (x < 8 && x >= 0 && y < 8 && y >= 0);
	}
	
	/**
	 * method for getting a piece from a coordinate
	 * @param row row 
	 * @param col col
	 * @return chess piece
	 */
	public ChessPiece getPiece(int row, int col){
		return board[row][col];
	}
	
	/**
	 * method for assigning a piece to a coordinate.
	 * @param piece piece instance
	 * @param row row
	 * @param col col
	 */
	public void setPiece(ChessPiece piece, int row, int col){
		board[row][col] = piece; 
	}
	
	/**
	 * method for getting the en passant white piece.
	 * @return enpassantWhite
	 */
	public Pawn[] getEnpassantWhite(){
		return enpassantWhite;
	}
	
	/**
	 * method for setting the en passant white piece when en passant is possible.
	 * @param newEnpassant Piece that jumped twice next to another piece of different color
	 */
	public void setEnpassantWhite(Pawn[] newEnpassant){
		this.enpassantWhite = newEnpassant;
	}
	
	/**
	 * method for getting the en passant black piece.
	 * @return enpassantBlack
	 */
	public Pawn[] getEnpassantBlack(){
		return enpassantBlack;
	}
	
	/**
	 * method for setting the en passant black piece when en passant is possible.
	 * @param newEnpassant Piece that jumped twice next to another piece of different color
	 */
	public void setEnpassantBlack(Pawn[] newEnpassant){
		this.enpassantBlack = newEnpassant;
	}
	
	/**
	 * method that uses the rules of a specific pice the given coordinates and move it if legal.
	 * @param locRow original row
	 * @param locCol original column
	 * @param destRow destination row
	 * @param destCol destination column
	 * @return true or false
	 */
	public boolean movePiece(int locRow, int locCol, int destRow, int destCol){
		ChessPiece piece = board[locRow][locCol];
		if(piece != null && piece.canMove(this, locRow, locCol, destRow, destCol)){
			ChessPiece destPiece = board[destRow][destCol]; //possibly used later. 
			board[locRow][locCol] = null;
			board[destRow][destCol] = piece; 
			if(piece instanceof Pawn){ //to keep track of move 2 on first turn. 
				((Pawn)piece).setHasMoved(true);
			}
			else if(piece instanceof King){//to keep track of castling.
				((King)piece).setHasMoved(true);
			}
			else if(piece instanceof Rook){// to keep track of castling.
				((Rook)piece).setHasMoved(true);
			}
			return true; 
		}
		else{
			return false; 
		}
	}

	/**
	 * implementation of check in chess.
	 * @param pieceColor colr of the piece
	 * @return true or false
	 * @throws IllegalArgumentException not white or black, illegal
	 */
	public boolean check(String pieceColor)
	throws IllegalArgumentException{
		
		//Checks to make sure color was correctly given. 
		if(!pieceColor.trim().toLowerCase().equals("black") && !pieceColor.trim().toLowerCase().equals("white")){
			throw new IllegalArgumentException("");
		} 
		//Finds king. 
		int kingRow = -1;
		int kingCol = -1; 
		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 8; col++){
				ChessPiece piece = board[row][col];
				if(piece != null && piece instanceof King && piece.getColor().trim().toLowerCase().equals(pieceColor)){
					kingRow = row; 
					kingCol = col; 
					row = 9;
					col = 9; 
				}
			}
		}
		//Makes sure king exists. 
		if(kingRow == -1 || kingCol == -1){
			return false; 
		}
		
		//Searches to see if the king is in check. 
		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 8; col++){
				ChessPiece piece = board[row][col];
				if(piece != null && !piece.getColor().trim().equals(pieceColor) && piece.canMove(this, row, col, kingRow, kingCol)){
					return true; 
				}
			}
		}
		return false; 
	}
}
