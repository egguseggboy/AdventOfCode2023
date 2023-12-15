#include <fstream>
#include <iostream>
#include <regex>
#include <string>
#include <vector>
#include "../cppUtils.cpp"

long long solve(std::vector<std::string> lines, int part) {
	std::vector<long long> allExt;

	for (auto line : lines) {
		auto nums = regexSplit(line, std::regex(" "));

		if (part == 2) std::reverse(nums.begin(), nums.end());
		
		int n = nums.size();
		long long numTri[n][n] = {0};

		for (int i = 0; i < n; i++)
			numTri[0][i] = stoll(nums[i]);

		int i = 1;
		for (; i < n; i++) {
			bool allZeros = true;
			for (int j = i; j < n; j++) {
				numTri[i][j] = numTri[i-1][j] - numTri[i-1][j-1];

				if (numTri[i][j] != 0)
					allZeros = false;
			}
			if (allZeros)
				break;
		}

		long long ext = 0;
		for (; i >= 0; i--)
			if (i != 0)
				ext += numTri[i-1][n-1];

		allExt.push_back(ext);
	}

	long long sum = 0;
	for (auto e : allExt) sum += e;

	return sum;
}

long long part1(std::vector<std::string> lines) {
	return solve(lines, 1);
}

long long part2(std::vector<std::string> lines) {
	return solve(lines, 2);
}

int main() {
	std::fstream file; file.open("d09.txt");
	std::vector<std::string> lines;

	std::string line;
	while (getline(file, line))
		lines.push_back(line);

	std::cout << "Part 1: " << part1(lines) << '\n';
	std::cout << "Part 2: " << part2(lines) << '\n';

	return 0;
}