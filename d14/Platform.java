import java.util.ArrayList;
import java.util.Collections;

public class Platform {
	public ArrayList<ArrayList<Integer>> platform = new ArrayList<>();

	public Platform(ArrayList<String> lines) {
		for (int row = 0; row < lines.size(); row++) {
			ArrayList<Integer> intRow = new ArrayList<>();

			for (int col = 0; col < lines.size(); col++)
				intRow.add(translateChar(lines.get(row).charAt(col)));
			platform.add(intRow);
		}
	}

	public char translateInt(int tile) {
		switch (tile) {
			case 0: return '.';
			case 1: return 'O';
			case 2: return '#';
		}
		return '?';
	}

	public int translateChar(char tile) {
		switch (tile) {
			case '.': return 0;
			case 'O': return 1;
			case '#': return 2;
		}
		return -1;
	}

	// arr[x][y] = arr[y][x]
	public void transpose() {
		for (int row = 0; row < platform.size(); row++) {
			for (int col = row+1; col < platform.size(); col++) {
				int temp = platform.get(row).get(col);
				platform.get(row).set(col, platform.get(col).get(row));
				platform.get(col).set(row, temp);
			}
		} 
	}

	// Shift rocks right
	public void rShift() {
		for (int row = 0; row < platform.size(); row++) {
			// from: 0 or last index of #
			// to: next index of # or -1
			int from = 0, to = -1;
			do {
				// Next index of #
				int relTo = platform.get(row).subList(from, platform.size()).indexOf(2);

				// No # found, sort from last #
				if (relTo == -1) {
					platform.get(row).subList(from, platform.size()).sort(null);
					break;
				// # found, sort from last # to next #
				} else {
					to = relTo + from;
					platform.get(row).subList(from, to).sort(null);
					from = to + 1;
				}
			// Repeat until no # found
			} while (to != -1);
		}
	}

	// lShift with reversed rows
	public void lShift() {
		for (ArrayList<Integer> row : platform)	Collections.reverse(row);
		rShift();
		for (ArrayList<Integer> row : platform)	Collections.reverse(row);
	}

	// rShift with transposed array
	public void dShift() {
		transpose(); rShift(); transpose();
	}

	// lShift with transposed array
	public void uShift() {
		transpose(); lShift(); transpose();
	}

	public void cycle() {
		uShift(); lShift(); dShift(); rShift();
	}

	public long getLoad() {
		long load = 0;

		for (int row = 0; row < platform.size(); row++)
			load += (platform.size() - row) * Collections.frequency(platform.get(row), 1);

		return load;
	}

	public String str() {
		String str = "";
		for (int row = 0; row < platform.size(); row++) {
			for (int col = 0; col < platform.size(); col++)
				str += translateInt(platform.get(row).get(col));
			str += '\n';
		}
		return str;
	}
}