#include <fstream>
#include <math.h>
#include <iostream>
#include <string>
#include <vector>

bool isSymbol(char c) {
	return !std::isdigit(c) && (c != '.');
}

bool checkAdjacentToNum(std::vector<std::string> lines, int i, int j) {
	int minY = std::max(0, i - 1), maxY = std::min(int(lines.size()) - 1, i + 1);
	int minX = std::max(0, j - 1), maxX = std::min(int(lines[i].size()) - 1, j + 1);

	for (int y = minY; y <= maxY; y++)
		for (int x = minX; x <= maxX; x++)
			if ((x != 0 || y != 0) && isSymbol(lines[y][x]))
				return true;

	return false;
}

int part1(std::vector<std::string> lines) {
	int sum = 0;

	for (int i = 0; i < lines.size(); i++) {
		bool parsingNum = false;
		bool includeInSum = false;
		int currNum = 0;

		for (int j = 0; j < lines[i].size(); j++) {
			if (!parsingNum) {
				if (std::isdigit(lines[i][j])) {
					currNum = int(lines[i][j]) - '0';

					if (checkAdjacentToNum(lines, i, j))
						includeInSum = true;
					parsingNum = true;
				}
			} else {
				if (std::isdigit(lines[i][j])) {
					currNum *= 10;
					currNum += int(lines[i][j]) - '0';

					if (checkAdjacentToNum(lines, i, j))
						includeInSum = true;
				} else {
					sum += includeInSum * currNum;
					parsingNum = false;
					includeInSum = false;
					currNum = 0;
				}
			}
		}
		if (parsingNum) {
			sum += includeInSum * currNum;
		}
	}
	return sum;
}

int getNumFromLeft(std::string line, int j) {
	int num = 0;
	bool parsingNum = false;

	while (j <= line.size() - 1) {
		if (std::isdigit(line[j])) {
			num += int(line[j]) - '0';
			num *= 10;
			parsingNum = true;
		} else if (parsingNum)
			break;
		j++;
	}

	return num /= 10;
}

int getNumFromRight(std::string line, int j) {
	int digit = 0;
	int num = 0;
	bool parsingNum = false;
	
	while (j >= 0) {
		if (std::isdigit(line[j])) {
			num += pow(10, digit++) * (int(line[j]) - '0');
			parsingNum = true;
			
		} else if (parsingNum)
			break;
		j--;
	}

	return num;
}

std::vector<int> checkUpOrDownAdj(std::string line, int j) {
	std::vector<int> adj;

	bool LDigit = std::isdigit(line[j-1]);
	bool CDigit = std::isdigit(line[j]);
	bool RDigit = std::isdigit(line[j+1]);

	// DDD
	if (LDigit && CDigit && RDigit)
		adj.push_back(getNumFromLeft(line, j-1));
	// D.D
	else if (LDigit && RDigit) {
		adj.push_back(getNumFromRight(line, j-1));
		adj.push_back(getNumFromLeft(line, j+1));
	}
	// DX.
	else if (LDigit)
		adj.push_back(getNumFromRight(line, j));
	// .XD
	else if (RDigit)
		adj.push_back(getNumFromLeft(line, j));
	// .D.
	else if (CDigit)
		adj.push_back(int(line[j]) - '0');

	return adj;
}

std::vector<int> checkAdjacentToGear(std::vector<std::string> lines, int i, int j) {
	std::vector<int> adjacent;

	// Left, right
	if (std::isdigit(lines[i][j-1]))
		adjacent.push_back(getNumFromRight(lines[i], j-1));
	if (std::isdigit(lines[i][j+1]))
		adjacent.push_back(getNumFromLeft(lines[i], j+1));

	// Up, down
	for (int e : checkUpOrDownAdj(lines[i-1], j))
		adjacent.push_back(e);
	for (int e : checkUpOrDownAdj(lines[i+1], j))
		adjacent.push_back(e);

	return adjacent;
}

int part2(std::vector<std::string> lines) {
	int sum = 0;

	for (int i = 0; i < lines.size(); i++) {
		for (int j = 0; j < lines[i].size(); j++) {
			if (lines[i][j] == '*') {
				std::vector<int> adjacent = checkAdjacentToGear(lines, i, j);

				if (adjacent.size() == 2)
					sum += adjacent[0] * adjacent[1];
			}
		}
	}

	return sum;
}

int main() {
	std::fstream file; file.open("d03.txt");
	std::vector<std::string> lines;

	std::string line;
	while (getline(file, line))
		lines.push_back(line);

	std::cout << "Part 1: " << part1(lines) << '\n';
	std::cout << "Part 2: " << part2(lines) << '\n';

	return 0;
}