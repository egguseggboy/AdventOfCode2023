import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class d14 {
	public static long part1(Platform platform) {
		platform.uShift();
		return platform.getLoad();
	}

	public static long part2(Platform platform) {
		ArrayList<String> states = new ArrayList<>();
		states.add(platform.str());

		int maxCycles = 1000000000;
		for (int i = 0; i < maxCycles; i++) {
			platform.cycle();

			int stateFound = states.indexOf(platform.str());

			if (stateFound == -1)
				states.add(platform.str());
			else {
				int cycleLen = i - stateFound + 1;

				while (++i % cycleLen != maxCycles % cycleLen)
					platform.cycle();

				return platform.getLoad();
			}
		}

		return -1;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(new File("d14.txt"));
		ArrayList<String> lines = new ArrayList<String>();

		while (input.hasNextLine())
			lines.add(input.nextLine());

		Platform platform = new Platform(lines);

		System.out.print(String.format("Part 1: %d\n", part1(platform)));
		System.out.print(String.format("Part 2: %d\n", part2(platform)));
	}
}