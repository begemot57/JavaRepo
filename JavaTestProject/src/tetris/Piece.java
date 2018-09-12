package tetris;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class Piece {
	protected int[][] shape;

	public void turnCW() {
		int[][] newShape = new int[shape[0].length][shape.length];
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[0].length; j++) {
				// System.out.println(i + "," + j + " -> " + (j) + "," +
				// (shape.length-1-i));
				newShape[j][shape.length - 1 - i] = shape[i][j];
			}
		}
		shape = newShape;
	}

	public void turnCCW() {
		int[][] newShape = new int[shape[0].length][shape.length];
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[0].length; j++) {
				// System.out.println(i + "," + j + " -> " + (j) + "," +
				// (shape.length-1-i));
				newShape[shape[0].length - 1 - j][i] = shape[i][j];
			}
		}
		shape = newShape;
	}

	public int[][] getShape() {
		return shape;
	}

	public void draw() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j] == 1)
					sb.append("X");
				else
					sb.append(" ");
				if (j == shape[i].length - 1)
					sb.append("\n");
			}
		}
		System.out.println(sb);
	}

	public static void main(String[] str) {
//		L p = new L();
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		String s;
//		while (true) {
//			try {
//				s = br.readLine();
//				if(s.equals("f")){
//					System.out.println("detected f"); 
//					p.turnCW();
//				}
//				if(s.equals("d")){
//					System.out.println("detected d");
//					p.turnCCW();
//				}
//				p.draw();
//				Thread.sleep(1000);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

		 Piece p = new I();
		 p.draw();
		 p.turnCW();
		 p.draw();
		 p.turnCW();
		 p.draw();
		 p.turnCW();
		 p.draw();
		 p.turnCW();
		 p.draw();
	}

}
