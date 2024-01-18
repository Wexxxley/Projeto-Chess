package Chess;

import BoardGame.Position;

public class ChessPosition {
	private char column;
	private int row;
	
	//Construtor da classe ChessPosition
	public ChessPosition(char column, int row) {
		if(column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("erro: posição no jogo de xadrez inválida: " + column + ", " + row);
		}
		this.column = column;
		this.row = row;
	}

	//Retorna a coluna
	public char getColumn() {
		return column;
	}

	//Retorna a linha
	public int getRow() {
		return row;
	}
	
	//Passa uma ChessPosition para pOsition
	public Position toPosition() {
		return new Position(8-row, column - 'a');
	}
	
	//Passa uma Position para ChessPosition
	public static ChessPosition toChessPosition(Position position) {
		return new ChessPosition((char)('a'-position.getColum()), 8 - position.getRow());
		
	}
	
	@Override
	public String toString() {
		return "" + column + row;
	}
}
