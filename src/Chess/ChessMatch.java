package Chess;

import java.util.ArrayList;
import BoardGame.*;
import Chess.Pieces.*;

public class ChessMatch {
	private Board ChessBoard;                     //Tabuleiro da partida
	private int turn;							  //Turno da partida
	private Color player;                         //Jogador atual
	private ArrayList<ChessPiece> capturedPieces; //paças capturadas
	private ArrayList<ChessPiece> boardPieces;    //peças no tabuleiro
	private boolean check;                        //Indica se a partida esta em check
	private boolean checkMate;                    //Indica se é checkMate
	private ChessPiece enPassant;                 //Indica se tem uma peça com perigo de sofre a jogada especial enPassant
	private ChessPiece promoted;                  //Indica se tem um peao a ser promovido
	
	//Construtor
	public ChessMatch() {
		this.ChessBoard = new Board(8, 8);
		this.turn = 1;
		this.player = Color.WHITE;
		this.capturedPieces = new ArrayList<ChessPiece>();
		this.boardPieces = new ArrayList<ChessPiece>();
		this.check = false;
		this.checkMate = false;
		this.enPassant = null;
		SetupInitial();
	}
	
	//Retorna checkMate
	public boolean getCheckMate() {
		return checkMate;
	}
	
	//Retorna se o jogo esta em check
	public boolean getCheck() {
		return check;
	}
	
	//Retorna uma peça promovida
	public ChessPiece getPromoted() {
		return promoted;
	}
	
	//Metodo que retorna o turno atual
	public int getTurn() {
		return turn;
	}
	
	//Metodo que incrementa o turno
	private void nextTurn() {
		this.turn++;
		nextPlayer();
	}
	
	//Metodo que retorna o player atual
	public Color getPlayer() {
		return player;
	}
	
	//Metodo que muda o player atual
	private void nextPlayer() {
		if(player == Color.BLACK) {player = Color.WHITE;}
		else {player = Color.BLACK;}
	}

	//Retorna o tabuleiro da partida de xadrez
	public Board getChessBoard() {
		return ChessBoard;
	}
	
	//Retorna uma peça que está vulnerável a sofrer o enPassant
	public ChessPiece getEnPassant() {
		return enPassant;
	}
	
	//Metodo que filtra e retorna as peças de terminada cor de uma lista
	private ArrayList<ChessPiece> filterPieces(ArrayList<ChessPiece> list, Color color){
		ArrayList<ChessPiece> pieces = new ArrayList<ChessPiece>();
		for(ChessPiece p : list) {
			if(p.getColor() == color) {
				pieces.add(p);
			}
		}
		return pieces;
	}
	
	//Metodo que retorna o rei de determinada cor
	private ChessPiece returnTheking(Color color) {
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				ChessPiece p = ChessBoard.getHouse(new Position(i, j)).getChesspiece();
				if(p instanceof King && p.getColor() == color) {
					return p;
				}
			}
		}
		throw new ChessException("erro: não ha rei da cor " + color);
	}
	
	//Metodo que verifica se o rei esta em check
	private boolean testCheck(Color color) {
		Position positionKing = this.returnTheking(color).getHouse().getPosition();
		ArrayList<ChessPiece> opponentPieces = this.filterPieces(boardPieces, (color == Color.BLACK)? Color.WHITE: Color.BLACK);
		for(ChessPiece p: opponentPieces) {
			boolean[][] mat = p.possibleMovies();
			if(mat[positionKing.getRow()][positionKing.getColum()] == true) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if(testCheck(color) == false) {return false;}
		
		ArrayList<ChessPiece> list = filterPieces(boardPieces, color);
		for(ChessPiece p: list) {
			boolean[][] mat = p.possibleMovies();
			for(int i=0; i<8; i++) {
				for(int j=0; j<8; j++) {
					if(mat[i][j] == true) {
						Position initial = p.getHouse().getPosition();
						Position end = new Position(i, j);
						
						//Fazendo o movimento
						ChessPiece piece = ChessBoard.removeChessPiece(initial);
						ChessPiece capturedpiece = ChessBoard.removeChessPiece(end);
						ChessBoard.placeChessPiece(piece, end);
						if(capturedpiece != null) {
							boardPieces.remove(capturedpiece);
							capturedPieces.add(capturedpiece);
						}
						
						//Testando es ainda está em check
						boolean testcheck = testCheck(color);
						
						//desfazendo movimento
						undoMovement(initial, end, capturedpiece);
						
						//Se nao estiver em check significa que nao esta em situação de checkMate
						if(testcheck == false) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	//Metodo que move uma peça para determinada posicao e retorna uma possivel peça capturada
	public ChessPiece moveChessPiece(ChessPosition initial, ChessPosition target) {
		Position i = initial.toPosition();
		Position t = target.toPosition();
		
		//Valida a posicao inicial
		if(!ChessBoard.thereIsAPiece(i)) {
			throw new ChessException("Erro: não há peça para ser movida nessa posição: " + initial);
		}
		if(player != ChessBoard.getHouse(i).getChesspiece().getColor()) {
			throw new ChessException("Erro: A peça escolhida não é sua ");
		}
		if(!ChessBoard.getHouse(i).getChesspiece().isThreAnyPossibleMovie())
		{
			throw new ChessException("Erro: não ha movimentos possíveis para fazer ");
		}
		
		//Valida posicao final
		if(ChessBoard.thereIsAPiece(t) && !ChessBoard.getHouse(i).getChesspiece().possibleMove(t)) {
			throw new ChessException("Erro: não é possivel mover a peça para a posição " + t);
		}
		
		//Fazendo o movimento
		ChessPiece piece = ChessBoard.removeChessPiece(i);
		ChessPiece capturedpiece = ChessBoard.removeChessPiece(t);
		ChessBoard.placeChessPiece(piece, t);
		piece.increaseNumberOfMovements();
		if(capturedpiece != null) {
			boardPieces.remove(capturedpiece);
			capturedPieces.add(capturedpiece);
		}
		
		//Se o player se colocou em check
		if(testCheck(player)) {
			undoMovement(i, t, capturedpiece);
			throw new ChessException("Erro: você se colocou em check");
		}
		
		ChessPiece p = ChessBoard.getHouse(t).getChesspiece();
		
		//Promovendo a peça
		promoted = null;
		if(p instanceof Pawn) {
			if(p.getColor() == Color.WHITE && t.getRow() == 0 || p.getColor() == Color.BLACK && t.getRow() == 7) {
				promoted = p;
				promoted = promotedPiece("Q");
				
			}
		}
		
		//Se meu oponente ficou em check
		check = (testCheck((player==Color.BLACK)? Color.WHITE: Color.BLACK))? true : false; 
		
		//Verifica se a partida acabou ou se vai para um próximo turno
		if(testCheckMate((player == Color.BLACK)? Color.WHITE: Color.BLACK)) {
			checkMate = true;
		}else {
			nextTurn();	
		}
		
		//Verifica se uma peça esta vulneravel ao moviment onPassant
		if(p instanceof Pawn && (t.getRow() == i.getRow() - 2) || (t.getRow() == i.getRow() + 2)) {
			enPassant = p;
		}else {
			enPassant = null;
		}
		
		if(piece instanceof Pawn) {
			if(i.getColum() != t.getColum() && capturedpiece == null) {
				Position pawnp;
				if(piece.getColor() == Color.WHITE) {
					pawnp = new Position(t.getRow()+1, t.getColum());
				}else {
					pawnp = new Position(t.getRow()-1, t.getColum());					
				}
				capturedpiece = ChessBoard.removeChessPiece(pawnp);
				capturedPieces.add(capturedpiece);
				boardPieces.remove(capturedpiece);
			}
		}
		
		return capturedpiece;
	}
	
	//Metodo que promove uma peça
	public ChessPiece promotedPiece(String t){
		if(promoted==null) {
			throw new ChessException("Erro: nao ha peça a ser promovida");
		}
		if(t.equals("B") || t.equals("Q") || t.equals("T") || t.equals("H")) {
			Position pos = promoted.getHouse().getPosition();
			ChessPiece pp = ChessBoard.removeChessPiece(pos);
			boardPieces.remove(pp);
			
			ChessPiece newpiece;
			if(t.equals("B")) {newpiece = new Bishop(ChessBoard, promoted.getColor(), pp.getHouse());}
			else if(t.equals("Q")) {newpiece = new Queen(ChessBoard, promoted.getColor(), pp.getHouse());}
			else if(t.equals("T")) {newpiece = new Tower(ChessBoard,  promoted.getColor(), pp.getHouse());}
			else {newpiece = new Horse(ChessBoard,  promoted.getColor(), pp.getHouse());}
			
			ChessBoard.placeChessPiece(newpiece, pos);
			boardPieces.add(newpiece);
			return newpiece;

		}
		throw new ChessException("Erro: digite uma peça válida");
	}
	
	//Metodo que desfaz movimentos(quando o player faz um movimento que deixa o rei em check)
	private void undoMovement(Position initial, Position target, ChessPiece captured) {
		ChessPiece piece = ChessBoard.removeChessPiece(target);
		ChessBoard.placeChessPiece(piece, initial);
		piece.decrementNumberOfMovements();
		if(captured != null) {
			ChessBoard.placeChessPiece(captured, target);
			boardPieces.add(captured);
			capturedPieces.remove(captured);
		}
	}
	
	//Metodo que coloca uma nova peça no tabuleiro com o posição do tipo ChessPosition
	private void placeNewChessPiece(ChessPiece piece, ChessPosition chessposition) {
		boardPieces.add(piece);
		ChessBoard.placeChessPiece(piece, chessposition.toPosition());
	}
	
	//Metodo que retorna os possiveis movimentos de uma peça
	public boolean[][] possibleMovies(ChessPosition position){
		Position p = position.toPosition();
		if(!ChessBoard.thereIsAPiece(p)) {
			throw new ChessException("erro: não há peça para ser movida nessa posição");
		}
		if(player != ChessBoard.getHouse(p).getChesspiece().getColor()) {
			throw new ChessException("Erro: A peça escolhida não é sua ");
		}
		if(!ChessBoard.getHouse(p).getChesspiece().isThreAnyPossibleMovie())
		{
			throw new ChessException("Erro: não ha movimentos possíveis para fazer ");
		}
		return ChessBoard.getHouse(p).getChesspiece().possibleMovies();
		
	}
	
	//Metodo que inicia o tabuleiro com as peças alocadas nas casas corretas
	public void SetupInitial() {
	    // Posicionamento das peças brancas
	    placeNewChessPiece(new King(ChessBoard, Color.WHITE, ChessBoard.getHouse(new Position(7, 4))), new ChessPosition('e', 1));
	    placeNewChessPiece(new Tower(ChessBoard, Color.WHITE, ChessBoard.getHouse(new Position(7, 0))), new ChessPosition('a', 1));
	    placeNewChessPiece(new Tower(ChessBoard, Color.WHITE, ChessBoard.getHouse(new Position(7, 7))), new ChessPosition('h', 1));
	    placeNewChessPiece(new Bishop(ChessBoard, Color.WHITE, ChessBoard.getHouse(new Position(7, 2))), new ChessPosition('c', 1));
	    placeNewChessPiece(new Bishop(ChessBoard, Color.WHITE, ChessBoard.getHouse(new Position(7, 5))), new ChessPosition('f', 1));
	    placeNewChessPiece(new Horse(ChessBoard, Color.WHITE, ChessBoard.getHouse(new Position(7, 1))), new ChessPosition('b', 1));
	    placeNewChessPiece(new Horse(ChessBoard, Color.WHITE, ChessBoard.getHouse(new Position(7, 6))), new ChessPosition('g', 1));
	    placeNewChessPiece(new Queen(ChessBoard, Color.WHITE, ChessBoard.getHouse(new Position(7, 3))), new ChessPosition('d', 1));

	    // Adição de peões brancos com um loop for
	    for (char file = 'a'; file <= 'h'; file++) {
	        placeNewChessPiece(new Pawn(ChessBoard, Color.WHITE, ChessBoard.getHouse(new Position(6, file - 'a')), this), new ChessPosition(file, 2));
	    }

	    // Posicionamento das peças pretas
	    placeNewChessPiece(new King(ChessBoard, Color.BLACK, ChessBoard.getHouse(new Position(0, 4))), new ChessPosition('e', 8));
	    placeNewChessPiece(new Tower(ChessBoard, Color.BLACK, ChessBoard.getHouse(new Position(0, 0))), new ChessPosition('a', 8));
	    placeNewChessPiece(new Tower(ChessBoard, Color.BLACK, ChessBoard.getHouse(new Position(0, 7))), new ChessPosition('h', 8));
	    placeNewChessPiece(new Bishop(ChessBoard, Color.BLACK, ChessBoard.getHouse(new Position(0, 2))), new ChessPosition('c', 8));
	    placeNewChessPiece(new Bishop(ChessBoard, Color.BLACK, ChessBoard.getHouse(new Position(0, 5))), new ChessPosition('f', 8));
	    placeNewChessPiece(new Horse(ChessBoard, Color.BLACK, ChessBoard.getHouse(new Position(0, 1))), new ChessPosition('b', 8));
	    placeNewChessPiece(new Horse(ChessBoard, Color.BLACK, ChessBoard.getHouse(new Position(0, 6))), new ChessPosition('g', 8));
	    placeNewChessPiece(new Queen(ChessBoard, Color.BLACK, ChessBoard.getHouse(new Position(0, 3))), new ChessPosition('d', 8));

	    // Adição de peões pretos com um loop for
	    for (char file = 'a'; file <= 'h'; file++) {
	        placeNewChessPiece(new Pawn(ChessBoard, Color.BLACK, ChessBoard.getHouse(new Position(1, file - 'a')), this), new ChessPosition(file, 7));
	    }
	}
}
