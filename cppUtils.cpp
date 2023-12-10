#include <regex>
#include <string>
#include <vector>

std::vector<std::string> regexSplit(std::string toSplit, std::regex splitBy) {
	std::sregex_token_iterator iter(toSplit.begin(), toSplit.end(), splitBy, -1), end;

	return {iter, end};
}