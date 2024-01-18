package BoardGame;

import Chess.ChessPiece;

public class Board {
	private int rows;
	private int columns;
	private House[][] matriz;
	
	//Construtor da classe Board
	public Board(int rows, int columns) {
		if(rows < 1 || columns <1) {
			throw new BoardException("erro: é preciso que tenha pelo menos uma linha e uma coluna na criação do tauleiro");
		}
		this.rows = rows;
		this.columns = columns;
		matriz = new House[rows][columns];
		
		for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matriz[i][j] = new House(this, new Position(i, j)); //A referência do tabuleiro está sendo passada para cada casa
            }
        }
	}

	//Retorna a quantidade de linhas do tabuleiro
	public int getRows() {
		return rows;
	}

	//Retorna a quantidade de colunas do tabuleiro
	public int getColumns() {
		return columns;
	}
	
	//Retorna um casa em determinada posicao do tabuleiro
	public House getHouse(Position p) {
		if(!positionExists(p)) {
			throw new BoardException("erro: posição não existe no tabuleiro: " + p);
		}
		return matriz[p.getRow()][p.getColum()];
	}
	
	//Retorna o tabuleiro completo
	public House[][] getMatriz(){
		return matriz;
	}
	
	//Coloca uma peça em determinada posição do tabuleiro
	public void placeChessPiece(ChessPiece piece, Position position) {
		if(!positionExists(position)) {
			throw new BoardException("erro: posição não existe no tabuleiro: " + position);
		}
		if(thereIsAPiece(position)) {
			throw new BoardException("erro: ja existe uma peça nessa posição: " + position);
		}
		
		piece.setHouse(matriz[position.getRow()][position.getColum()]);      //Seta a casa da peça
		matriz[position.getRow()][position.getColum()].setChesspiece(piece); //Seta a peca da cass
	}
	
	//Metodo que remove uma peça do tabuleiro
	public ChessPiece removeChessPiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("erro: posição não existe no tabuleiro: " + position);
		}
		if(!thereIsAPiece(position)) {
			return null;
		}
		ChessPiece chesspiece = getHouse(position).getChesspiece();
		getHouse(position).setChesspiece(null); //Seto a peça da casa
		chesspiece.setHouse(null);              //Seto a casa da peça
		return chesspiece;
		
	}
	
	//Metodo que verifica se existe um determinada posicao no tabuleiro
	public boolean positionExists(Position p) {
		if(p.getRow() >= 0 && p.getRow() < rows && p.getColum() >= 0 && p.getColum() < columns) {return true;}
		else {return false;}
	}
	
	//Metodo que verifica se existe alguma peça em determinada posição
	public boolean thereIsAPiece(Position p) {
		if(getHouse(p).getChesspiece() != null) {return true;}
		else {return false;}
	}
}
