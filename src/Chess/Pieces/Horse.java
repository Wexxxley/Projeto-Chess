package Chess.Pieces;

import BoardGame.Board;
import BoardGame.House;
import BoardGame.Position;
import Chess.ChessPiece;
import Chess.Color;

public class Horse extends ChessPiece {

	public Horse(Board board, Color color, House house) {
		super(board, color, house);
	}
	
	@Override
	public String toString() {
		return "H";
	}
	
	@Override
	public boolean[][] possibleMovies(){
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position position = new Position(getHouse().getPosition().getRow(), getHouse().getPosition().getColum()); //posicao da minha peça
		Position aux = new Position(0, 0); //Posição auxiliar
		
		int[][] possibleOffsets = { {-2, -1}, {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2} };

	    for (int[] offset : possibleOffsets) {
	        aux.setValues(position.getRow() + offset[0], position.getColum() + offset[1]);
	        if (validatePosition(aux)) {
	            mat[aux.getRow()][aux.getColum()] = true;
	        }
	    }


		return mat;
	}
	
	public boolean validatePosition(Position aux) {
		if(!getBoard().positionExists(aux)) {return false;}
		if(getBoard().thereIsAPiece(aux)) {
			if(isThereOpponentPiece(aux)) {return true;}
			return false;
		}
		return true;
	}

}
