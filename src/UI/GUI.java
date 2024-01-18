package UI;
import java.util.InputMismatchException;
import java.util.Scanner;

import BoardGame.Board;
import BoardGame.House;
import BoardGame.Position;
import Chess.ChessPosition;
import Chess.Color;

public class GUI {
	Scanner scan = new Scanner(System.in);
	
	//Metodo que printa o tabuleiro
	public void printBoard(Board board) {
		System.out.println("==========================");
        System.out.print("\u001B[46m"); // Configura a cor de fundo para azul
        for(int i = 0;i < 8; i++) {
			System.out.print(8-i + " ");
			for(int j =0; j < 8; j++) {
				printHouse(board.getHouse(new Position(i, j)));
			}
			System.out.print("\n");
		}
		System.out.print("  a  b  c  d  e  f  g  h  \n");
		
        System.out.println("\u001B[0m"); // Restaura a cor de fundo para o padrão

	}
	
	//Metodo que printa o tabuleiro com os movimentos possiveis
	public void printBoard(Board board, boolean[][] possibleMovies) {
        System.out.print("\u001B[46m"); // Configura a cor de fundo para azul
        for(int i = 0;i < 8; i++) {
			System.out.print(8-i + " ");
			for(int j =0; j < 8; j++) {
				if(possibleMovies[i][j] == true) {
					printHouse(board.getHouse(new Position(i, j)), true);
				}else {
					printHouse(board.getHouse(new Position(i, j)), false);
				}
			}
			System.out.print("\n");
		}
		System.out.print("  a  b  c  d  e  f  g  h  \n");
		
        System.out.println("\u001B[0m"); // Restaura a cor de fundo para o padrão

	}
	
	//Metodo que printa uma casa do tabuleiro
	private void printHouse(House house) {
		if(house.getChesspiece() != null) {
			if(house.getChesspiece().getColor() == Color.BLACK) {
				System.out.print("\u001B[30m" + house.getChesspiece() + "\u001B[0m");
		        System.out.print("\u001B[46m");

			}else {
				System.out.print(house.getChesspiece());
			}
		}else {
			System.out.print("-");
		}
		System.out.print("  ");	
	}
	
	//Metodo que printa uma casa do tabuleiro
	private void printHouse(House house, boolean aux) {
		if(aux == true) {
	        System.out.print("\u001B[45m"); // Fundo amarelo
		}
		if(house.getChesspiece() != null) {
			if(house.getChesspiece().getColor() == Color.BLACK) {
				System.out.print("\u001B[30m" + house.getChesspiece() + "\u001B[0m");
		        System.out.print("\u001B[45m");

			}else {
				System.out.print(house.getChesspiece());
			}
		}else {
			System.out.print("-");
		}
		System.out.print("\u001B[46m  ");	
	}
	
	//Metodo que lê uma posição de xadrez
	public ChessPosition readChessPosition() {
		try {
			String ss = scan.nextLine();
			char column = ss.charAt(0);
			int row = Integer.parseInt(ss.substring(1));
			return new ChessPosition(column, row);
		}catch(RuntimeException e) {
			throw new InputMismatchException("erro: Essa posição não existe no tabuleiro de xadrez");
		}
	
	}
	
}

