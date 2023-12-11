import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class d11 {
	public static ArrayList<Integer> emptyRows(ArrayList<String> lines) {
		ArrayList<Integer> emptyRows = new ArrayList<>();
	
		for (int row = 0; row < lines.size(); row++)
			if (!lines.get(row).contains("#"))
				emptyRows.add(row);

		return emptyRows;
	}

	public static ArrayList<Integer> emptyCols(ArrayList<String> lines) {
		ArrayList<Integer> emptyCols = new ArrayList<>();

		for (int col = 0; col < lines.get(0).length(); col++) {
			boolean colHasGalaxy = false;

			for (int row = 0; row < lines.size() && !colHasGalaxy; row++)
				if (lines.get(row).charAt(col) == '#')
					colHasGalaxy = true;

			if (!colHasGalaxy)
				emptyCols.add(col);
		}

		return emptyCols;
	}

	public static long solve(ArrayList<String> lines, int galaxySize) {
		ArrayList<Point> points = new ArrayList<>();
		ArrayList<Integer> emptyRows = emptyRows(lines);
		ArrayList<Integer> emptyCols = emptyCols(lines);

		for (int row = 0; row < lines.size(); row++)
			for (int col = 0; col < lines.get(row).length(); col++)
				if (lines.get(row).charAt(col) == '#')
					points.add(new Point(row, col));

		long total = 0;
		for (int i = 0; i < points.size(); i++)
			for (int j = i + 1; j < points.size(); j++)
				total += points.get(i).distToPoint(points.get(j), emptyRows, emptyCols, galaxySize);

		return total;
	}
	public static long part1(ArrayList<String> lines) {
		return solve(lines, 2);
	}

	public static long part2(ArrayList<String> lines) {
		return solve(lines, 1000000);
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(new File("d11.txt"));
		ArrayList<String> lines = new ArrayList<String>();

		while (input.hasNextLine())
			lines.add(input.nextLine());

		System.out.print(String.format("Part 1: %d\n", part1(lines)));
		System.out.print(String.format("Part 2: %d\n", part2(lines)));
	}
}