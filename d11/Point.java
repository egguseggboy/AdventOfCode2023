import java.util.ArrayList;

public class Point {
	public int row; public int col;

	public Point(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int emptyRowsBetween(int otherRow, ArrayList<Integer> emptyRows) {
		int emptyRowsBetween = 0;

		int rLower = (this.row <= otherRow) ? this.row : otherRow;
		int rUpper = (this.row > otherRow) ? this.row : otherRow;

		for (int r = rLower + 1; r < rUpper; r++)
			if (emptyRows.contains(r))
				emptyRowsBetween++;

		return emptyRowsBetween;
	}

	public int emptyColsBetween(int otherCol, ArrayList<Integer> emptyCols) {
		int emptyColsBetween = 0;

		int cLower = (this.col <= otherCol) ? this.col : otherCol;
		int cUpper = (this.col > otherCol) ? this.col : otherCol;

		for (int c = cLower + 1; c < cUpper; c++)
			if (emptyCols.contains(c))
				emptyColsBetween++;

		return emptyColsBetween;
	}

	public long distToPoint(Point otherPt, ArrayList<Integer> emptyRows, ArrayList<Integer> emptyCols, long galaxySize) {
		return Math.abs(this.row - otherPt.row) + emptyRowsBetween(otherPt.row, emptyRows) * (galaxySize - 1) + 
		       Math.abs(this.col - otherPt.col) + emptyColsBetween(otherPt.col, emptyCols) * (galaxySize - 1);
	}
}