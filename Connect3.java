import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;



public class Connect3 {

	public static void main(String[] argv) throws FileNotFoundException {
		Connect3 board = new Connect3(argv[0]);
		decision(board);
	}
	
	char[][] board;
	char toMove;

	public Connect3(String filename) throws FileNotFoundException {
		File file = new File(filename);
		Scanner scanner = new Scanner(file);
		board = new char[4][5];
		int count = 0;
		for(int i = 0; i < 4; i++) {
			String s = scanner.nextLine();
			for(int j = 0; j < 5; j++) {
				board[i][j] = s.charAt(j);
				if(s.charAt(j) != '.') count++;
			}
		}
		if(count % 2 == 0) toMove = 'X';
		else toMove = 'O';
		scanner.close();
	}
	
	public Connect3(Connect3 prev, int move) {
		if(prev.toMove == 'X') toMove = 'O';
		else toMove = 'X';
		board = new char[4][5];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 5; j++) {
				board[i][j] = prev.board[i][j];
			}
		}
		for(int x = 3; x>=0; x--) {
			if(board[x][move] == '.') {
				board[x][move] = prev.toMove;
				break;
			}
		}
		
	}
	public void printBoard() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 5; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}
	public char win() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 5; j++) {
				char turn = board[i][j];
				if(i - 1 >= 0 && board[i-1][j] == turn && i - 2 >= 0 && board[i-2][j] == turn) return turn;
				if(i - 1 >= 0 && j - 1 >= 0 && board[i-1][j-1] == turn && i - 2 >= 0 && j - 2 >= 0 && board[i-2][j-2] == turn) return turn;
				if(j - 1 >= 0 && board[i][j-1] == turn && j - 2 >= 0 && board[i][j-2] == turn) return turn;
				if(i + 1 < 4 && j - 1 >= 0 && board[i+1][j-1] == turn && i + 2 < 4 && j - 2 >= 0 && board[i+2][j-2] == turn) return turn;
				
			}
		}
		return '.';
	}
	public static void decision(Connect3 init) {
		int[] moves = init.possibleMoves();
		int bestMove = moves[0];
		int val;
		if(init.toMove == 'X') {
			val = -1;
			for(int move: moves) {
				Connect3 newBoard = new Connect3(init, move);
				int thisVal = maxVal(newBoard);
				if(thisVal > val) {
					bestMove = move;
					val = thisVal;
				}
			}
		}
		else {
			val = 1;
			for(int move: moves) {
				Connect3 newBoard = new Connect3(init, move);
				int thisVal = minVal(newBoard);
				if(thisVal < val) {
					bestMove = move;
					val = thisVal;
				}
			}
		}
		System.out.println(val + " " + init.toMove + bestMove);
	}
	public static int maxVal(Connect3 state) {
		char c = state.win();
		if(c == 'O') return -1;
		if(c == 'X') return 1;
		if(state.possibleMoves().length == 0) return 0;
		int v = Integer.MIN_VALUE;
		for(int move: state.possibleMoves()) {
			Connect3 newBoard = new Connect3(state, move);
			v = Math.max(v, minVal(newBoard));
		}
		return v;
		
		
	}
	public static int minVal(Connect3 state) {
		char c = state.win();
		if(c == 'O') return -1;
		if(c == 'X') return 1;
		if(state.possibleMoves().length == 0) return 0;
		int v = Integer.MAX_VALUE;
		for(int move: state.possibleMoves()) {
			Connect3 newBoard = new Connect3(state, move);
			v = Math.min(v, maxVal(newBoard));
		}
		return v;
	}
	
	public int[] possibleMoves() {
		int size = 0;
		for(int i = 0; i < 5; i++) {
			if(board[0][i] == '.') size++;
		}
		int[] moves = new int[size];
		int pos = 0;
		for(int i = 0; i < 5; i++) {
			if(board[0][i] == '.') {
				moves[pos] = i;
				pos++;
			}
		}
		return moves;
	}
}
