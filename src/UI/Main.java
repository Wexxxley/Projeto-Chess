package UI;

import java.util.InputMismatchException;
import java.util.Scanner;
import BoardGame.BoardException;
import Chess.*; 

public class Main {
	public static void main(String[] args) {
		GUI gui = new GUI();
		ChessMatch chessmatch = new ChessMatch();
		Scanner scan = new Scanner(System.in);
		
		while(chessmatch.getCheckMate() != true) {
			try {
				gui.printBoard(chessmatch.getChessBoard());
				if(chessmatch.getCheck()) {
					System.out.println("VOCÊ ESTÁ EM CHECK!");
				}
				System.out.print("Jogador: " + chessmatch.getPlayer() +"; Turno: " + chessmatch.getTurn() + "\n");
				System.out.print("Posição inicial: ");
				ChessPosition initial = gui.readChessPosition();
				System.out.print("\n"); 
				
				boolean[][] possibleMovies = chessmatch.possibleMovies(initial);
				gui.printBoard(chessmatch.getChessBoard(), possibleMovies);
				
				System.out.print("Posição final: ");
				ChessPosition end = gui.readChessPosition();
				
				chessmatch.moveChessPiece(initial, end);				
				System.out.print("\n");
				
				if(chessmatch.getPromoted() != null) {
					System.out.print("Promoção de peça. Digite sua nova peça(B, H, Q, T):");
					String t = scan.nextLine();
					chessmatch.promotedPiece(t);
				}

			}catch(BoardException e){
				System.out.println(e.getMessage());
			}catch(InputMismatchException e){
				System.out.println(e.getMessage());
			}
		}
		gui.printBoard(chessmatch.getChessBoard());
		System.out.println("CHECKMATE!\nO Jogador " + chessmatch.getPlayer() + " é o campeão. ");
		scan.close();
		
		
	}
}
