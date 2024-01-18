package Chess;

import BoardGame.Board;
import BoardGame.House;
import BoardGame.Position;

public abstract class ChessPiece {
	private Board board;
	private Color color;
	private House house;
	private int numberOfMovements;

	//Construtor da classe ChessPiece
	public ChessPiece(Board board, Color color, House house) {
		this.color = color;
		this.board = board;
		this.house = house;
	}

	//Retornar a cor da peça
	public Color getColor() {
		return color;
	}
	
	//Retorna o tabuleiro da peça
	public Board getBoard() {
		return board;
	}
	
	//Metodo que retorna a posição da peça
	public House getHouse() {
		return house;
	}
	
	//Metodo que seta a casa da peça
	public void setHouse(House house) {
		this.house = house;
	}
	
	//Metodo que retorna a quantidade de movimentos da peça
	public int getNumberOfMovements() {
		return numberOfMovements;
	}
	
	//Metodo que incrementa o numero de movimentos da peça
	public void increaseNumberOfMovements() {
		numberOfMovements++;
	}
	
	//Metodo que decrementa o numero de movimentos da peça
	public void decrementNumberOfMovements() {
		numberOfMovements--;
	}
	
	//Metodo abstrato que retorna uma matriz de boolean. Essa matriz informa quais os possiveis movimentos da peça
	public abstract boolean[][] possibleMovies();
	
	//Metodo que verifica se é possivel mover a peça para determinada posição
	public boolean possibleMove(Position position) {
		return possibleMovies()[position.getRow()][position.getColum()];
	}
	
	//Verifica se é possivel fazer pelo menos um movimento
	public boolean isThreAnyPossibleMovie() {
		boolean [][] mat = possibleMovies();
		for(int i=0; i<mat.length; i++) {
			for(int j=0; j<mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	//Metodo que verifica se a peça em determinada posicao é uma peça oponente
	public boolean isThereOpponentPiece(Position position) {
		if(board.getHouse(position).getChesspiece() != null) {
			if(color != board.getHouse(position).getChesspiece().getColor()) {return true;}
		}
		return false;
	}
}
