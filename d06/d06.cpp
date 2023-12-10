#include <algorithm>
#include <fstream>
#include <iostream>
#include <sstream>
#include <string>
#include <vector>

long long part1(std::vector<std::string> lines) {
	std::vector<long long> times, distances;

	std::stringstream tsstream(lines[0].substr(9));
	std::stringstream dsstream(lines[1].substr(9));

	long long currT, currD;
	std::vector<long long> recordCounts;

	while (!tsstream.eof() && !dsstream.eof()) {
		long long recordCount = 0;
		tsstream >> currT;
		dsstream >> currD;

		for (int i = 1; i <= currT; i++)
			if (i * (currT - i) > currD)
				recordCount++;

		recordCounts.push_back(recordCount);
	}

	long long total = 1;
	for (long long recordCount : recordCounts)
		total *= recordCount;

	return total;
}

long long part2(std::vector<std::string> lines) {
	std::string timeStr = lines[0], distStr = lines[1];

	timeStr.erase(std::remove_if(timeStr.begin(), timeStr.end(), isspace), timeStr.end());
	distStr.erase(std::remove_if(distStr.begin(), distStr.end(), isspace), distStr.end());

	timeStr = timeStr.substr(timeStr.find(':') + 1);
	distStr = distStr.substr(distStr.find(':') + 1);

	long long time = stoll(timeStr), dist = stoll(distStr);
	long long minHold, maxHold;

	for (minHold = 1; minHold <= time; minHold++)
		if (minHold * (time - minHold) > dist)
			break;

	for (maxHold = time; maxHold >= minHold; maxHold--)
		if (maxHold * (time - maxHold) > dist)
			break;

	return maxHold - minHold + 1;
}

int main() {
	std::fstream file; file.open("d06.txt");
	std::vector<std::string> lines;

	std::string line;
	while (getline(file, line))
		lines.push_back(line);

	std::cout << "Part 1: " << part1(lines) << '\n';
	std::cout << "Part 2: " << part2(lines) << '\n';

	return 0;
}