package chess;


/**
 * Contains the set of rules for king's movement
 * @author Abdulellah Abualshour
 * @author Fahad Syed
 * 
 */
public class Pawn extends ChessPiece {

	private boolean hasMoved = false; 
	
	/**
	 * default constructor for Pawn.
	 */
	public Pawn() {}

	/**
	 * one arg constructor for Pawn.
	 * @param colorNew color of instance
	 */
	public Pawn(String colorNew) {
		super(colorNew);
	}
	
	/**
	 * convert instance to string.
	 */
	public String toString(){
		return this.getColor().substring(0, 1).toLowerCase()+"p ";
	}
	
	/**
	 * method to see if the Pawn moved at least once.
	 * @return true or false
	 */
	public boolean getHasMoved(){
		return hasMoved; 
	}
	
	/**
	 * sets the Pawn first movement indicator.
	 * @param newHasMoved true or false
	 */
	public void setHasMoved(boolean newHasMoved){
		hasMoved = newHasMoved; 
	}
	
	/**
	 * method that contains the rules for pawn's movement. Tests if a move is legal.
	 * @param board board
	 * @param locRow original row
	 * @param locCol original column
	 * @param destRow destination row
	 * @param destCol destination column
	 * @return true or false
	 */
	public boolean canMove(ChessBoard board, int locRow, int locCol, int destRow, int destCol) {
		if(board.doesCoordExist(destRow, destCol) && board.doesCoordExist(locRow, locCol)){
			if(this.getColor().trim().equalsIgnoreCase("black")){
				//below
				if(locRow == destRow+1 && locCol == destCol){
					if(board.getPiece(destRow, destCol) == null){
						return true;
					}
					else{
						return false;
					}
				}
				//en passant right
				else if(board.doesCoordExist(locRow, locCol+1) 
						&& board.getPiece(locRow, locCol+1) != null 
						&& board.getPiece(locRow, locCol+1).equals(board.getEnpassantWhite()[0])  
						&& destRow == locRow-1 && destCol == locCol+1){
					board.setPiece(null, locRow, locCol+1);
					return true; 
				} 
				//en passant left
				else if(board.doesCoordExist(locRow, locCol-1) 
						&& board.getPiece(locRow, locCol-1) != null
						&& board.getPiece(locRow, locCol-1).equals(board.getEnpassantWhite()[0])
						&& destRow == locRow-1 && destCol == locCol-1){
					board.setPiece(null, locRow, locCol-1);
					return true;
				}
				//diagonals
				else if((locRow == destRow+1) && (locCol == destCol+1 || locCol == destCol-1)){
					if(board.getPiece(destRow, destCol) == null || board.getPiece(destRow, destCol).getColor().equals("black")){ //same color, do not attack
						return false; 
					}
					else{
						return true; 
					}
				}
				//double move
				else if(hasMoved == false && locRow == destRow+2 && locCol == destCol){
					if(board.getPiece(destRow, destCol) == null && board.getPiece(destRow+1, destCol) == null){
						Pawn[] enpassant = board.getEnpassantBlack();
						enpassant[0] = this; 
						board.setEnpassantBlack(enpassant);
						return true; 
					}
					else{
						return false; 
					}
				}
				
			}
			else{
				//above
				if(locRow == destRow-1 && locCol == destCol){
					if(board.getPiece(destRow, destCol) == null){
						return true;
					}
					else{
						return false;
					}
				}
				//en passant right
				else if(board.doesCoordExist(locRow, locCol+1) 
						&& board.getPiece(locRow, locCol+1) != null 
						&& board.getPiece(locRow, locCol+1).equals(board.getEnpassantBlack()[0])  
						&& destRow == locRow+1 && destCol == locCol+1){
					board.setPiece(null, locRow, locCol+1);
					return true; 
				} 
				//en passant left
				else if(board.doesCoordExist(locRow, locCol-1) 
						&& board.getPiece(locRow, locCol-1) != null
						&& board.getPiece(locRow, locCol-1).equals(board.getEnpassantBlack()[0])
						&& destRow == locRow+1 && destCol == locCol-1){
					board.setPiece(null, locRow, locCol-1);
					return true;
				}
				//diagonals
				else if((locRow == destRow-1) && (locCol == destCol+1 || locCol == destCol-1)){
					if(board.getPiece(destRow, destCol) == null || board.getPiece(destRow, destCol).getColor().equals("white")){ //same color, do not attack
						return false; 
					}
					else{
						return true; 
					}
				}
				//double move
				else if(hasMoved == false && locRow == destRow-2 && locCol == destCol){
					if(board.getPiece(destRow,destCol) == null && board.getPiece(destRow-1, destCol) == null){
						Pawn[] enpassant = board.getEnpassantWhite();
						enpassant[0] = this; 
						board.setEnpassantWhite(enpassant);
						return true; 
					}
					else{
						return false; 
					}
				}
			}
		}
		return false;
	}

}
