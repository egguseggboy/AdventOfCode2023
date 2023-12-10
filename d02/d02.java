import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class d02 {
	public static int solve(ArrayList<String> lines, int part) {
		int result = 0;
		int lineNum = 0;

		for (String line : lines) {
			lineNum++;
			int maxR = 0; int maxG = 0; int maxB = 0;

			// Better matching
			/*
			line = line.split(": ")[1];
			for (String elem : line.split("[,;] ")) {
				String[] elemCount = ...
			}
			*/

			line = line.split(": ")[1];
			for (String set : line.split("; ")) {
				for (String elem : set.split(", ")) {
					String[] elemCount = elem.split(" ");

					switch (elemCount[1]) {
						case "red":
							maxR = Math.max(maxR, Integer.parseInt(elemCount[0]));
							break;
						case "green":
							maxG = Math.max(maxG, Integer.parseInt(elemCount[0]));
							break;
						case "blue":
							maxB = Math.max(maxB, Integer.parseInt(elemCount[0]));
							break;
					}
				}
			}

			switch (part) {
				case 1:
					if (maxR <= 12 && maxG <= 13 && maxB <= 14)
						result += lineNum;
					break;
				case 2:
					result += maxR * maxG * maxB;
			}
		}

		return result;
	}

	public static int part1(ArrayList<String> lines) {
		return solve(lines, 1);
	}

	public static int part2(ArrayList<String> lines) {
		return solve(lines, 2);
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(new File("d02.txt"));
		ArrayList<String> lines = new ArrayList<String>();

		while (input.hasNextLine())
			lines.add(input.nextLine());

		System.out.print("Part 1: ");
		System.out.println(part1(lines));
		System.out.print("Part 2: ");
		System.out.println(part2(lines));
	}
}