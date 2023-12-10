def part1(lines):
	sum = 0

	for line in lines:
		digits = ''.join(i for i in line if i.isdigit())
		num = int(digits[0] + digits[-1])
		sum = sum + num

	return sum

def part2(lines):
	sum = 0

	numbers = ['one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine']

	for line in lines:
		# Loop until no number strings found
		while True:
			minInd = -1
			firstNumFound = ''

			# Finds earliest instance of number string
			for number in numbers:
				numInd = line.find(number)

				if numInd != -1 and (minInd == -1 or numInd < minInd):
					firstNumFound = number
					minInd = numInd

			# No number string found, exit loop
			if minInd == -1:
				break

			# Replace with number, leave last character for 'twone' case
			intNum = numbers.index(firstNumFound) + 1
			line = line.replace(firstNumFound, f'{intNum}{firstNumFound[-1]}')

		digits = ''.join(i for i in line if i.isdigit())
		num = int(digits[0] + digits[-1])
		sum = sum + num

	return sum

if __name__ == "__main__":
	lines = open("d01.txt").read().splitlines()
	
	print(f'Part 1: {part1(lines)}')
	print(f'Part 2: {part2(lines)}')