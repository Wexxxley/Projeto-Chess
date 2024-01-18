package Chess.Pieces;

import BoardGame.Board;
import BoardGame.House;
import BoardGame.Position;
import Chess.ChessPiece;
import Chess.Color;

public class Queen extends ChessPiece{

	public Queen(Board board, Color color, House house) {
		super(board, color, house);
	}
	
	@Override
	public String toString() {
		return "Q";
	}
	
	@Override
	public boolean[][] possibleMovies(){
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position position = new Position(getHouse().getPosition().getRow(),getHouse().getPosition().getColum()); //posicao da minha peça
		
		//verificando acima
		Position aux = new Position(position.getRow()-1, position.getColum()); //posição auxiliar
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColum()] = true;
			aux.setValues(aux.getRow()-1, aux.getColum());
		}
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColum()] = true;
		}
		
		//verificando abaixo
		aux.setValues(position.getRow()+1, position.getColum()); //posição auxiliar
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColum()] = true;
			aux.setValues(aux.getRow()+1, aux.getColum());
		}
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColum()] = true;
		}
		
		//verificando a esquerda
		aux.setValues(position.getRow(), position.getColum()-1); //posição auxiliar
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColum()] = true;
			aux.setValues(aux.getRow(), aux.getColum()-1);
		}
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColum()] = true;
		}
		
		//verificando a direita
		aux.setValues(position.getRow(), position.getColum()+1); //posição auxiliar
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColum()] = true;
			aux.setValues(aux.getRow(), aux.getColum()+1);
		}
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColum()] = true;
		}
		
		//verificando acima esquerda
		aux.setValues(position.getRow()-1, position.getColum()-1);
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColum()] = true;
			aux.setValues(aux.getRow()-1, aux.getColum()-1);
		}
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColum()] = true;
		}
		
		//verificando acima direita
		aux.setValues(position.getRow()-1, position.getColum()+1); //posição auxiliar
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColum()] = true;
			aux.setValues(aux.getRow()-1, aux.getColum()+1);
		}
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColum()] = true;
		}
		
		//verificando em baixo esquerda
		aux.setValues(position.getRow()+1, position.getColum()-1); //posição auxiliar
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColum()] = true;
			aux.setValues(aux.getRow()+1, aux.getColum()-1);
		}
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColum()] = true;
		}
		
		//verificando em baixo direita
		aux.setValues(position.getRow()+1, position.getColum()+1); //posição auxiliar
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColum()] = true;
			aux.setValues(aux.getRow()+1, aux.getColum()+1);
		}
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColum()] = true;
		}

		
		return mat;
	}
}
