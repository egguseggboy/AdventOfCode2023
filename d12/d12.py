from functools import lru_cache

@lru_cache
def solve(pattern, groups):
	total = 0

	# If no groups left
	if len(groups) == 0:
		# If no # found, 1 permutation added
		return (pattern.find("#") == -1)

	if len(pattern) < sum(groups) + len(groups) - 1:
		return 0
	
	# If first group can be inserted at beginning
	if pattern[0:groups[0]].find('.') == -1 and (len(pattern) == groups[0] or pattern[groups[0]] != "#"):
		total += solve(pattern[(groups[0] + 1):], groups[1:])
	# If dot can be inserted at beginning
	if pattern[0] != '#':
		total += solve(pattern[1:], groups[0:])
	
	return total

def part1(lines):
	total = 0

	for line in lines:
		pattern, rawGroups = line.split()
		pattern = pattern.strip('.')
		groups = [int(g) for g in rawGroups.split(',')]

		total += solve(pattern, tuple(groups))

	return total

def part2(lines):
	total = 0

	for line in lines:
		pattern, rawGroups = line.split()
		pattern = "?".join([pattern] * 5).strip('.')
		groups = [int(g) for g in rawGroups.split(',')] * 5

		total += solve(pattern, tuple(groups))

	return total

if __name__ == "__main__":
	lines = open("d12.txt").read().splitlines()
	
	print(f'Part 1: {part1(lines)}')
	print(f'Part 2: {part2(lines)}')