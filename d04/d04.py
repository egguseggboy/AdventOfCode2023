def part1(lines):
	total = 0

	for line in lines:
		numWinning = 0

		line = line.replace("  ", " ").split(": ")[1]
		numbers = line.split(" | ")
		winning = numbers[0].split(' ')
		have = numbers[1].split(' ')

		for num in winning:
			if num in have:
				numWinning += 1

		total += int(pow(2, numWinning - 1))

	return total

def part2(lines):
	copies = [1] * len(lines)

	for i in range(len(lines)):
		numWinning = 0

		line = lines[i].replace("  ", " ").split(": ")[1]
		numbers = line.split(" | ")
		winning = numbers[0].split(' ')
		have = numbers[1].split(' ')

		for num in winning:
			if num in have:
				numWinning += 1

		for j in range(i + 1, i + numWinning + 1):
			copies[j] += copies[i]

	return sum(copies)

if __name__ == "__main__":
	lines = open("d04.txt").read().splitlines()
	
	print(f'Part 1: {part1(lines)}')
	print(f'Part 2: {part2(lines)}')