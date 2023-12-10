import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class d05 {
	public static ArrayList<Long> parseSeeds(String seedsStr) {
		ArrayList<Long> seeds = new ArrayList<>();

		Scanner seedScanner = new Scanner(seedsStr);
		while (seedScanner.hasNextLong())
			seeds.add(seedScanner.nextLong());
		seedScanner.close();

		return seeds;
	}
	
	public static ArrayList<ResourceMap> parseMaps(ArrayList<String> lines) {
		lines.removeAll(Arrays.asList(""));

		ArrayList<ResourceMap> resourceMaps = new ArrayList<>();

		for (int i = 0; i < lines.size();) {
			String line = lines.get(i++);

			if (line.contains("map")) {
				ResourceMap currMap = new ResourceMap(line);

				while (i < lines.size() && !lines.get(i).contains("map"))
					currMap.addMap(lines.get(i++));

				resourceMaps.add(currMap);
			}
		}

		return resourceMaps;
	}
	public static long part1(ArrayList<String> lines) {
		String seedsStr = lines.get(0).substring(7);
		
		ArrayList<Long> seeds = parseSeeds(seedsStr);
		ArrayList<ResourceMap> resourceMaps = parseMaps(lines);
		ArrayList<Long> locations = new ArrayList<>();

		for (long seed : seeds) {
			long currValue = seed;

			for (ResourceMap currMap : resourceMaps)
				currValue = currMap.translateAll(currValue, false);

			locations.add(currValue);
		}

		return Collections.min(locations);
	}

	public static long part2(ArrayList<String> lines) {
		String seedsStr = lines.get(0).substring(7);

		ArrayList<Long> seeds = parseSeeds(seedsStr);
		ArrayList<ResourceMap> resourceMaps = parseMaps(lines);
		Collections.reverse(resourceMaps);

		Long minLocationVal = (long) 0;

		while (minLocationVal++ != Long.MAX_VALUE) {
			long currValue = minLocationVal;

			for (ResourceMap currMap : resourceMaps)
				currValue = currMap.translateAll(currValue, true);

			for (int i = 0; i < seeds.size(); i += 2) {
				long offset = currValue - seeds.get(i);
				
				if (offset >= 0 && offset < seeds.get(i+1))
					return minLocationVal;
			}
		}

		return minLocationVal;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(new File("d05.txt"));
		ArrayList<String> lines = new ArrayList<String>();

		while (input.hasNextLine())
			lines.add(input.nextLine());

		System.out.print("Part 1: ");
		System.out.println(part1(lines));
		System.out.print("Part 2: ");
		System.out.println(part2(lines));
	}
}