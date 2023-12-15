#include <fstream>
#include <iostream>
#include <string>
#include <vector>
#include "../cppUtils.cpp"

int mismatches(std::string s1, std::string s2) {
	int mismatches = 0;
	for (int i = 0; i < s1.length(); i++)
		if (s1[i] != s2[i])
			mismatches++;

	return mismatches;
}

long horizSymmetry(std::vector<std::string> pattern, int part) {
	for (int row = 0; row < pattern.size() - 1; row++) {
		int totalMismatches = 0, i = 0;

		while (row - i >= 0 && row+1 + i < pattern.size()) {
			totalMismatches += mismatches(pattern.at(row-i), pattern.at(row+1+i));
			i++;
		}

		// Check for 0 mismatches in part 1, 1 in part 2
		if (totalMismatches == part - 1)
			return row + 1;
	}
	return 0;
}

long vertSymmetry(std::vector<std::string> patterns, int part) {
	std::vector<std::string> transposed;
	
	for (int col = 0; col < patterns[0].size(); col++) {
		std::string s = "";
		
		for (int row = 0; row < patterns.size(); row++)
			s += (patterns[row][col]);
		
		transposed.push_back(s);
	}

	return horizSymmetry(transposed, part);
}

long solve(std::vector<std::vector<std::string>> patterns, int part) {
	long sum = 0;

	for (auto pattern : patterns)
		sum += horizSymmetry(pattern, part) * 100 + vertSymmetry(pattern, part);

	return sum;
}

long part1(std::vector<std::vector<std::string>> patterns) {
	return solve(patterns, 1);
}

long part2(std::vector<std::vector<std::string>> patterns) {
	return solve(patterns, 2);
}

int main() {
	std::fstream file; file.open("d13.txt");

	std::vector<std::string> pattern;
	std::vector<std::vector<std::string>> patterns;

	std::string line;
	while (getline(file, line)) {
		if (line.empty()) {
			patterns.push_back(pattern);
			pattern.clear();
		} else
			pattern.push_back(line);
	}
	patterns.push_back(pattern);

	std::cout << "Part 1: " << part1(patterns) << '\n';
	std::cout << "Part 2: " << part2(patterns) << '\n';

	return 0;
}