package Chess.Pieces;

import BoardGame.Board;
import BoardGame.House;
import BoardGame.Position;
import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.Color;

public class Pawn extends ChessPiece {

	private ChessMatch chessmatch;
	public Pawn(Board board, Color color, House house, ChessMatch chessmatch) {
		super(board, color, house);
		this.chessmatch = chessmatch;
	}
	
	@Override
	public String toString() {
		return "P";
	}
	
	@Override
	public boolean[][] possibleMovies(){
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position position = new Position(getHouse().getPosition().getRow(), getHouse().getPosition().getColum()); //posicao da minha peça
		Position aux = new Position(0, 0); //Posição auxiliar

		if(getColor() == Color.WHITE) {
							
			//Andar para frente
			aux.setValues(position.getRow()-1, position.getColum());
			if(getNumberOfMovements()==0) {
				mat[position.getRow()-1][position.getColum()] = true;
				mat[position.getRow()-2][position.getColum()] = true;
			}
			else if(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
				mat[aux.getRow()][aux.getColum()] = true;
			}
			
			//Verificando se tem inimigo na diagonal esquerda
			aux.setValues(position.getRow()-1, position.getColum()-1);
			if(getBoard().positionExists(aux) && getBoard().thereIsAPiece(aux) && isThereOpponentPiece(aux)) {
				mat[aux.getRow()][aux.getColum()] = true;
			}
			
			//Verificando se tem inimigo na diagonal direita
			aux.setValues(position.getRow()-1, position.getColum()+1);
			if(getBoard().positionExists(aux) && getBoard().thereIsAPiece(aux) && isThereOpponentPiece(aux)) {
				mat[aux.getRow()][aux.getColum()] = true;
			}
			
			//EnPassant
			if(position.getRow() == 3) {
				//Verificando se esta vazio na diagonal esquerda
				aux.setValues(position.getRow()-1, position.getColum()-1);
				if(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
					//verifico se tem um peao do lado e se ele esta na salvo na var enPassant
					aux.setValues(position.getRow(), position.getColum()-1);
					if(getBoard().positionExists(aux) && getBoard().thereIsAPiece(aux) && getBoard().getHouse(aux).getChesspiece() == chessmatch.getEnPassant()) {
						mat[position.getRow()-1][position.getColum()-1] = true;
					}
				}
				
				//Verificando se esta vazio na diagonal direita
				aux.setValues(position.getRow()-1, position.getColum()+1);
				if(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
					//verifico se tem um peao do lado e se ele esta na salvo na var enPassant
					aux.setValues(position.getRow(), position.getColum()+1);
					if(getBoard().positionExists(aux) && getBoard().thereIsAPiece(aux) && getBoard().getHouse(aux).getChesspiece() == chessmatch.getEnPassant()) {
						mat[position.getRow()-1][position.getColum()+1] = true;
					}
				}
				
			}
		}else {
			
			//Andar para frente
			aux.setValues(position.getRow()+1, position.getColum());
			if(getNumberOfMovements()==0) {
				mat[position.getRow()+1][position.getColum()] = true;
				mat[position.getRow()+2][position.getColum()] = true;
			}
			else if(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
				mat[aux.getRow()][aux.getColum()] = true;
			}
			
			//Verificando se tem inimigo na diagonal esquerda
			aux.setValues(position.getRow()+1, position.getColum()-1);
			if(getBoard().positionExists(aux) && getBoard().thereIsAPiece(aux) && isThereOpponentPiece(aux)) {
				mat[aux.getRow()][aux.getColum()] = true;
			}
			
			//Verificando se tem inimigo na diagonal direita
			aux.setValues(position.getRow()+1, position.getColum()+1);
			if(getBoard().positionExists(aux) && getBoard().thereIsAPiece(aux) && isThereOpponentPiece(aux)) {
				mat[aux.getRow()][aux.getColum()] = true;
			}
			
			//EnPassant
			if(position.getRow() == 4) {
				//Verificando se esta vazio na diagonal esquerda
				aux.setValues(position.getRow()+1, position.getColum()-1);
				if(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
					//verifico se tem um peao do lado e se ele esta na salvo na var enPassant
					aux.setValues(position.getRow(), position.getColum()-1);
					if(getBoard().positionExists(aux) && getBoard().thereIsAPiece(aux) && getBoard().getHouse(aux).getChesspiece() == chessmatch.getEnPassant()) {
						mat[position.getRow()+1][position.getColum()-1] = true;
					}
				}
				
				//Verificando se esta vazio na diagonal direita
				aux.setValues(position.getRow()+1, position.getColum()+1);
				if(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
					//verifico se tem um peao do lado e se ele esta na salvo na var enPassant
					aux.setValues(position.getRow(), position.getColum()+1);
					if(getBoard().positionExists(aux) && getBoard().thereIsAPiece(aux) && getBoard().getHouse(aux).getChesspiece() == chessmatch.getEnPassant()) {
						mat[position.getRow()+1][position.getColum()+1] = true;
					}
				}
				
			}
		}
		return mat;
	}
}
