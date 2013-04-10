public class Utils {
	/**
	 * Returns the position on the one-dimensional structure of the board, 
	 * given the x and y parameters, and the size of the board(n)
	 * @param x
	 * @param y
	 * @param n
	 * @return
	 */
	public static int CalcXY(int x, int y, int n){return x+y*n;}
	
	
	/**
	 * Returns the position on the two-dimensional structure of the board,
	 * given the xth position on a one-dimensional structure, and size of board(n).
	 * @param x
	 * @param n
	 * @return
	 */
	public static int[] CalcPos(int pos, int n){
		int[] xy = new int[2];
		xy[0] = pos%n;
		xy[1] = pos/n;
		return xy;
	}
}