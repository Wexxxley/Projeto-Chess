package BoardGame;

import Chess.ChessPiece;

//Classe casa do tabuleiro
public class House {
	private Board board;
	private Position position;
	private ChessPiece chesspiece;
	

	public House(Board board, Position position) {
		this.board = board;
		this.position = position;
		this.chesspiece = null;
	}
	
	public Board getBoard() {
		return board;
	}

	public Position getPosition() {
		return position;
	}

	public ChessPiece getChesspiece() {
		return chesspiece;
	}
	
	public void setChesspiece(ChessPiece chesspiece) {
		this.chesspiece = chesspiece;
	}

}
