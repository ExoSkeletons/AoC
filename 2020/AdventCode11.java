
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.Character;
import java.util.HashMap;
import java.io.File;
import java.util.Scanner;

public class AdventCode11 {
	public static final char FLOOR = '.', EMPTY = 'L', OCCUPIED = '#';

	public static class Vector2 {
		public int x = 0, y = 0;

		Vector2(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Please specify the input file path");
			return;
		}

		try {
			char[][] board = readFile(args[0], 97, 95); // row, col
			int step = -1, changes;

			long tStart = System.currentTimeMillis();

			do {
				step++;

				//System.out.println("Step: " + step);
				//printBoard(board);

				changes = stepBoard(board, 0, 5);

				//try { System.in.read(); } catch(IOException ignored) {}
			} while(changes > 0);

			long t = System.currentTimeMillis() - tStart;

			System.out.println("----------");

			int occCount = 0, empCount = 0;
			for (int x = 0; x < board.length; x++)
				for (int y = 0; y < board[x].length; y++)
					if (board[x][y] == OCCUPIED)
						occCount++;
					else if (board[x][y] == EMPTY)
						empCount++;
			System.out.println("Time: " + t + "ms Total Stepped: " + step + ", Occupied: " + occCount + ", Empty: " + empCount);
			printBoard(board);
		} catch (FileNotFoundException e) {
			System.out.println("File \"" + args[0] + "\" not found.");
		}
	}

	public static void printBoard(char[][] board) {
		System.out.print("  ");
		for (int i = 0; i < board[0].length; i++)
			System.out.print("" + i + " ");
		System.out.println();
		for (int x = 0; x < board.length; x++) {
			System.out.print("" + x + " ");
			for (int y = 0; y < board[x].length; y++) {
				if (y > 0) System.out.print(' ');
				System.out.print(board[x][y]);
			}
			System.out.println();
		}
	}

	public static int stepBoard(char[][] board, int mostToOccupy, int leastToEmpty) {
		HashMap<Vector2, Character> changes = new HashMap<>();

		for (int x = 0; x < board.length; x++)
			for (int y = 0; y < board[x].length; y++)
				if (board[x][y] == EMPTY) {
					if (countVis(board, x, y, OCCUPIED) <= mostToOccupy)
						changes.put(new Vector2(x, y), OCCUPIED);
				} else if (board[x][y] == OCCUPIED) {
					if (countVis(board, x, y, OCCUPIED) >= leastToEmpty)
						changes.put(new Vector2(x, y), EMPTY);
				}

		//System.out.println("Total changes: " + changes.size());
		for (Vector2 v : changes.keySet())
			board[v.x][v.y] = changes.get(v).charValue();
		return changes.size();
	}

	// Part #1
	public static int countAdjecent(char[][] board, int sx, int sy, char search) {
		int count = 0;

		if (sx > 0 && board[sx - 1][sy] == search)
			count++;
		if (sx < board.length - 1 && board[sx + 1][sy] == search)
			count++;
		if (sy > 0 && board[sx][sy - 1] == search)
			count++;
		if (sy < board[sx].length - 1 && board[sx][sy + 1] == search)
			count++;

		if (sx > 0 && sy > 0 && board[sx - 1][sy - 1] == search)
			count++;
		if (sx < board.length - 1 && sy > 0 && board[sx + 1][sy - 1] == search)
			count++;
		if (sx > 0 && sy < board[sx].length - 1 && board[sx - 1][sy + 1] == search)
			count++;
		if (sx < board.length - 1 && sy < board[sx].length - 1 && board[sx + 1][sy + 1] == search)
			count++;

		return count;
	}

	// Part #2
	public static int countVis(char[][] board, int sx, int sy, char search) {
		int count = 0;

		//System.out.println("vis from " + sx + "," + sy);

		//System.out.print("x- ");
		for (int i = 1; sx - i >= 0; i++) {
			//System.out.print("" + (sx - i) + "," + sy + ":" + board[sx - i][sy] + " ");
			if (board[sx - i][sy] != FLOOR) {
				if (board[sx - i][sy] == search)
					count++;
				break;
			}
		}
		//System.out.println();
		//System.out.print("x+ ");
		for (int i = 1; sx + i < board.length; i++) {
			//System.out.print("" + (sx + i) + "," + sy + ":" + board[sx + i][sy] + " ");
			if (board[sx + i][sy] != FLOOR) {
				if (board[sx + i][sy] == search)
					count++;
				break;
			}
		}
		//System.out.println();
		//System.out.print("y- ");
		for (int i = 1; sy - i >= 0; i++) {
			//System.out.print("" + sx + "," + (sy - i) + ":" + board[sx][sy - i] + " ");
			if (board[sx][sy - i] != FLOOR) {
				if (board[sx][sy - i] == search)
					count++;
				break;
			}
		}
		//System.out.println();
		//System.out.print("y+ ");
		for (int i = 1; sy + i < board[sx].length; i++) {
			//System.out.print("" + sx + "," + (sy + i) + ":" + board[sx][sy + i] + " ");
			if (board[sx][sy + i] != FLOOR) {
				if (board[sx][sy + i] == search)
					count++;
				break;
			}
		}
		//System.out.println();

		//System.out.print("x-y- ");
		for (int i = 1; (sx - i >= 0) && (sy - i >= 0); i++) {
			//System.out.print("" + (sx - i) + "," + (sy - i) + ":" + board[sx - i][sy - i] + " ");
			if (board[sx - i][sy - i] != FLOOR) {
				if (board[sx - i][sy - i] == search)
					count++;
				break;
			}
		}
		//System.out.println();
		//System.out.print("x-y+ ");
		for (int i = 1; (sx - i >= 0) && (sy + i < board[sx].length); i++) {
			//System.out.print("" + (sx - i) + "," + (sy + i) + ":" + board[sx - i][sy + i] + " ");
			if (board[sx - i][sy + i] != FLOOR) {
				if (board[sx - i][sy + i] == search)
					count++;
				break;
			}
		}
		//System.out.println();
		//System.out.print("x+y- ");
		for (int i = 1; (sx + i < board.length) && (sy - i >= 0); i++) {
			//System.out.print("" + (sx + i) + "," + (sy - i) + ":" + board[sx + i][sy - i] + " ");
			if (board[sx + i][sy - i] != FLOOR) {
				if (board[sx + i][sy - i] == search)
					count++;
				break;
			}
		}
		//System.out.println();
		//System.out.print("x+y+ ");
		for (int i = 1; (sx + i < board.length) && (sy + i < board[sx].length); i++) {
			//System.out.print("" + (sx + i) + "," + (sy + i) + ":" + board[sx + i][sy + i] + " ");
			if (board[sx + i][sy + i] != FLOOR) {
				if (board[sx + i][sy + i] == search)
					count++;
				break;
			}
		}
		//System.out.println();

		//System.out.println();

		return count;
	}

	public static char[][] readFile(String path, int totalRow, int totalColumn) throws FileNotFoundException {
		char[][] res = new char[totalRow][totalColumn];
		File file = new File(path);
		Scanner scanner = new Scanner(file);

		for (int row = 0; scanner.hasNextLine() && row < totalRow; row++) {
    		char[] chars = scanner.nextLine().toCharArray();
    		for (int i = 0; i < totalColumn && i < chars.length; i++) {
        		res[row][i] = chars[i];
    		}
		}

		return res;
	}
}