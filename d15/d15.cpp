#include <algorithm>
#include <fstream>
#include <iostream>
#include <regex>
#include <string>
#include <vector>
#include "../cppUtils.cpp"

#define hashMapLen 256

struct lens {
	std::string label; int focalLen;

	lens(std::string label, int focalLen) {
		this->label = label;
		this->focalLen = focalLen;
	}
};

int hash(std::string str) {
	int startVal = 0;

	for (auto c : str)
		startVal = ((startVal + int(c)) * 17) % hashMapLen;

	return startVal;
}

long part1(std::string line) {
	long sum = 0;

	for (auto step : regexSplit(line, std::regex(",")))
		sum += hash(step);

	return sum;
}

long part2(std::string line) {
	long power = 0;
	std::vector<lens> boxes[hashMapLen];

	for (auto step : regexSplit(line, std::regex(","))) {
		auto tokens = regexSplit(step, std::regex("[-=]"));
		std::string label = tokens.at(0);

		// Remove lens with label
		if (step.ends_with('-'))
			for (auto &box : boxes)
				box.erase(std::remove_if(box.begin(), box.end(),
				[label](const lens &l) { return l.label == label; }), box.end());
		// Add/update lens with focal length
		else {
			int focalLen = stoi(tokens.at(1));
			int labelFound = false;

			// Look for existing lens with label, update
			for (auto &box : boxes)
				for (auto &l : box)
					if (l.label == label) {
						labelFound = true;
						l.focalLen = focalLen;
						break;
					}

			// No existing label, add
			if (!labelFound)
				boxes[hash(label)].push_back(lens(label, focalLen));
		}
	}

	for (int i = 0; i < hashMapLen; i++)
		for (int j = 0; j < boxes[i].size(); j++)
			power += (i + 1) * (j + 1) * boxes[i].at(j).focalLen;

	return power;
}

int main() {
	std::fstream file; file.open("d15.txt");

	std::string line; getline(file, line);

	std::cout << "Part 1: " << part1(line) << '\n';
	std::cout << "Part 2: " << part2(line) << '\n';

	return 0;
}