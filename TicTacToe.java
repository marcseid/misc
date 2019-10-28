package tictactoe;

import java.util.Scanner;

public class TicTacToe {

	int n = 3;
	Character[][] table = new Character[n][n];
	static int moveCount, row, col;
	static final char EMPTY = ' ';
	static final char PLAYER_1 = 'o';
	static final char PLAYER_2 = 'x';
	static Character player = PLAYER_1;
	static Scanner k = new Scanner(System.in);

	public static void main(String[] args) {
		TicTacToe g = new TicTacToe();
		g.initializeGame();
		g.displayBoard();
		g.gameLogic();
		k.close();
	}
	
	public void gameLogic() {
		while (true) {
			player = changePlayer(player);
			chooseLocation();
			while (isIllegal(row, col)) {
				displayBoard();
				chooseLocation();
			}
			moveCount++;
			changeBoardEntry(player, row, col);
			displayBoard();

			if (checkIfWinner(row, col)) {
				System.out.println("\nThe winner is " + player + " !");
				break;
			}
			if (checkIfTie()) {
				System.out.println("\nThe game is a tie !");
				break;
			}
		}
	}
	
	public void chooseLocation() {
		System.out.print("\n" + player + " ,choose your location (row): ");
		row = k.nextInt();
		System.out.print(player + " ,choose your location (column): ");
		col = k.nextInt();
	}

	public boolean isIllegal(int row, int column) {
		if (row > 2 || column > 2 || row < 0 || column < 0) {
			System.out.println("That slot is out of bounds, please try again.");
			return true;
		} else if (table[row][column] != EMPTY) {
			System.out.println("That slot is already taken, please try again.");
			return true;
		}
		return false;
	}

	public void initializeGame() {
		for (int i = 0; i < n; i++) {
			for (int p = 0; p < n; p++) {
				table[i][p] = EMPTY;
			}
		}
		System.out.println("Let's play!\n");
	}

	public void changeBoardEntry(char player, int row, int column) {
		table[row][column] = player;
	}

	public void displayBoard() {
		System.out.println("  0  " + table[0][0] + "|" + table[0][1] + "|" + table[0][2] 
						+ "\n    --+-+--" + "\n  1  "
				+ table[1][0] + "|" + table[1][1] + "|" + table[1][2] 
						+ "\n    --+-+--" + "\n  2  " + table[2][0] + "|"
				+ table[2][1] + "|" + table[2][2]
						+ "\n     0 1 2 ");
	}

	public char changePlayer(char player) {
		return player == PLAYER_1 ? PLAYER_2 : PLAYER_1;
	}

	public boolean checkIfWinner(int row, int col){
	    boolean onDiagonal = (row == col) || (col == -1 * row + (table.length-1));
	    boolean HorizontalWin = true, VerticalWin = true;
	    boolean DiagonalWinOne = true, DiagonalWinTwo = true;

	    for(int n = 0; n < table.length; n++){
	        if(!table[row][n].equals(player))
	            HorizontalWin = false;
	        if(!table[n][col].equals(player))
	            VerticalWin = false;
	    }

	    if(onDiagonal){
	        for(int n = 0; n < table.length; n++){
	            if(!table[n][n].equals(player))
	                DiagonalWinOne = false;
	            if(!table[n][-1*n+(table.length-1)].equals(player))
	                DiagonalWinTwo = false;
	        }
	    }
	    else{
	        DiagonalWinOne = false;
	        DiagonalWinTwo = false;
	    }
	    boolean winner = (HorizontalWin || VerticalWin || DiagonalWinOne || DiagonalWinTwo);
	    return winner;
	}
	
	public boolean checkIfTie() {
		if (moveCount == (Math.pow(n, 2)))
			return true;
		return false;
	}
}
