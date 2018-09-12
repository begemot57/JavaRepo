package tetris;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid {

	private static final int height = 20;
	private static final int width = 12;
	private static int[][] grid = new int[height][width];
	private static Piece curr_p, next_p;
	private int cp_r, cp_c;
	private Random r = new Random();
	private int points = 0;
	private String SYMBOL = "X";

	private void initialize() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				grid[i][j] = 0;
			}
		}
		addNewPiece(getRandomPiece());
		next_p = getRandomPiece();
	}

	public void draw() {
		StringBuffer board = new StringBuffer("");
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (j == 0)
					board.append(SYMBOL);
				if (grid[i][j] == 0) {
					board.append(" ");
				} else {
					board.append(SYMBOL);
				}
				if (j == width - 1) {
					board.append(SYMBOL);

					// draw next piece
					if (i < next_p.shape.length) {
						board.append(" ");
						for (int j2 = 0; j2 < next_p.getShape()[0].length; j2++) {
							if (next_p.getShape()[i][j2] == 1)
								board.append("X");
							else
								board.append(" ");
						}
					}
					// show points
					if(i==height-1)
						board.append(" POINTS: "+points);
					
					board.append("\n");
				}
			}
		}
		System.out.println(board);
	}

	/**
	 * Add new piece to the board. Returns false if piece can't be added - gave
	 * over!
	 * 
	 * @param p
	 * @return
	 */
	private void addNewPiece(Piece p) {
		resetCoordinates();
		curr_p = p;
		if (!canFit(cp_r, cp_c, p)) {
			draw();
			System.out.println("!!!!!!!!!!!!!!!!!!!!!GAME OVER!!!!!!!!!!!!!!!!!!!!!");
			System.exit(0);
		} else
			addToGrid(cp_r, cp_c, p);
	}
	
	private void resetCoordinates(){
		cp_r = 0;
		cp_c = 5;
	}

	private boolean canFit(int r, int c, Piece p) {
		// piece is outside of grid
		if (r < 0 || r >= height)
			return false;
		if (c < 0 || c >= width)
			return false;
		if ((height - r) < p.getShape().length || (width - c) < p.getShape()[0].length)
			return false;
		for (int i = r; i < r + p.getShape().length; i++) {
			for (int j = c; j < c + p.getShape()[0].length; j++) {
				// piece is on an occupied cell
				if (grid[i][j] == 1 && p.getShape()[i - r][j - c] == 1)
					return false;
			}
		}
		return true;
	}

	// new position for the piece is marked with "2"
	private void addToGrid(int r, int c, Piece p) {
		cp_r = r;
		cp_c = c;
		// wipe out old position of the piece
		wipeOldPiece();
		boolean canGoDown = canGoDown(r, c, p);
		for (int i = r; i < r + p.getShape().length; i++) {
			for (int j = c; j < c + p.getShape()[0].length; j++) {
				if (canGoDown) {
					// piece can move: 0 -> 0, 1 -> 2
					grid[i][j] = Math.max(grid[i][j], p.getShape()[i - r][j - c] * 2);
				} else {
					// piece can't move: 0 -> 0, 1 -> 1
					grid[i][j] = Math.max(grid[i][j], p.getShape()[i - r][j - c] * 1);
				}
			}
		}
		if (!canGoDown) {
			collectPoints();
			addNewPiece(next_p);
			next_p = getRandomPiece();
		}
	}
	
	private void collectPoints(){
		//find full lines
		List<Integer> fullLines = new ArrayList<Integer>();
		for (int i = 0; i < height; i++) {
			int counter = 0;
			for (int j = 0; j < width; j++) {
				if (grid[i][j] == 1)
					counter++;
			}
			if(counter==width){
				points++;
				fullLines.add(i);
			}
		}
		//drop hanging pieces
		if(!fullLines.isEmpty()){
			int[][] newGrid = new int[height][width];
			int new_row = height-1;
			for (int i = height-fullLines.size(); i >= 0; i--) {
				if(!fullLines.contains(i)){
					newGrid[new_row--] = grid[i];
				}
			}
			for (int i = 0; i < fullLines.size(); i++) {
				for (int j = 0; j < newGrid[i].length; j++) {
					newGrid[i][j] = 0;
				}
			}
			grid = newGrid;
		}
		
	}

	private boolean canGoDown(int r, int c, Piece p) {
		return canFit(r + 1, c, p);
	}

	private void wipeOldPiece() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[i][j] == 2)
					grid[i][j] = 0;
			}
		}
	}

	private Piece getRandomPiece() {
		// I,J,L,O,S,T,Z
		int Low = 0;
		int High = 6;
		int newP = r.nextInt(High - Low) + Low;
		switch (newP) {
		case 0:
			return new I();
		case 1:
			return new J();
		case 2:
			return new L();
		case 3:
			return new O();
		case 4:
			return new S();
		case 5:
			return new T();
		case 6:
			return new Z();
		}
		return null;
	}

	public void redraw(long mills) {
		StringBuffer clear = new StringBuffer("");
		for (int i = 0; i < height * 2; i++) {
			clear.append("\n");
		}
		while (true) {
			draw();
			try {
				Thread.sleep(mills);
				System.out.print(clear);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Grid() {
		System.out.println("*********************TETRIS*********************");
		System.out.println("e: turn counter clock wise");
		System.out.println("r: turn clock wise");
		System.out.println("d: move left");
		System.out.println("f: move right");
		System.out.println("enter or any other key: move down");
		System.out.println("p: I gonna be next");
		System.out.println("\n\n\n");
		initialize();
	}

	private void turnCW() {
		curr_p.turnCW();
		if (!canFit(cp_r, cp_c, curr_p))
			curr_p.turnCCW();
		else
			addToGrid(cp_r, cp_c, curr_p);
	}

	private void turnCCW() {
		curr_p.turnCCW();
		if (!canFit(cp_r, cp_c, curr_p))
			curr_p.turnCW();
		else
			addToGrid(cp_r, cp_c, curr_p);
	}

	private void moveRight() {
		if (canFit(cp_r, cp_c + 1, curr_p))
			addToGrid(cp_r, ++cp_c, curr_p);
	}

	private void moveLeft() {
		if (canFit(cp_r, cp_c - 1, curr_p))
			addToGrid(cp_r, --cp_c, curr_p);
	}

	private void moveDown() {
		if (canFit(cp_r + 1, cp_c, curr_p))
			addToGrid(++cp_r, cp_c, curr_p);
	}

	void run() {
		draw();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s;
		while (true) {
			try {
				s = br.readLine();
				if (s.contains("e")) {
//					System.out.println("turn counter clock wise");
					turnCCW();
				} else if (s.contains("r")) {
//					System.out.println("turn clock wise");
					turnCW();
				} else if (s.contains("d")) {
//					System.out.println("move left");
					moveLeft();
				} else if (s.contains("f")) {
//					System.out.println("move right");
					moveRight();
				} else if (s.contains("p")) {
					next_p = new I();
				} else {
//					System.out.println("move down");
					moveDown();
				}

				draw();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Grid g = new Grid();
		g.run();
	}

}
