package Chess.Pieces;

import BoardGame.Board;
import BoardGame.House;
import BoardGame.Position;
import Chess.ChessPiece;
import Chess.Color;

public class King extends ChessPiece {

	public King(Board board, Color color, House house) {
		super(board, color, house);
	}
	
	@Override
	public String toString() {
		return "K";
	}
	
	@Override
	public boolean[][] possibleMovies(){
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position position = new Position(getHouse().getPosition().getRow(), getHouse().getPosition().getColum()); //posicao da minha peça
		
		//verificando acima
		Position aux = new Position(position.getRow()-1, position.getColum()); 
		if(validatePosition(aux)) {mat[aux.getRow()][aux.getColum()] = true;}
		
		//verificando diagonal cima direita
		aux.setValues(position.getRow()-1, position.getColum()+1); 
		if(validatePosition(aux)) {mat[aux.getRow()][aux.getColum()] = true;}
		
		//verificando direita
		aux.setValues(position.getRow(), position.getColum()+1); 
		if(validatePosition(aux)) {mat[aux.getRow()][aux.getColum()] = true;}
		
		//verificando diagonal baixo direita
		aux.setValues(position.getRow() + 1, position.getColum()+1); 
		if(validatePosition(aux)) {mat[aux.getRow()][aux.getColum()] = true;}
		
		//verificando em baixo
		aux.setValues(position.getRow() + 1, position.getColum()); 
		if(validatePosition(aux)) {mat[aux.getRow()][aux.getColum()] = true;}
		
		//verificando diagonal baixo esquerda
		aux.setValues(position.getRow() + 1, position.getColum()-1); 
		if(validatePosition(aux)) {mat[aux.getRow()][aux.getColum()] = true;}
		
		//verificando esquerda
		aux.setValues(position.getRow(), position.getColum() - 1); 
		if(validatePosition(aux)) {mat[aux.getRow()][aux.getColum()] = true;}
		
		//verificando diagonal cima esquerda
		aux.setValues(position.getRow() - 1, position.getColum() - 1); 
		if(validatePosition(aux)) {mat[aux.getRow()][aux.getColum()] = true;}
		
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
