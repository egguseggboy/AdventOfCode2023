# NOTE: PART 2'S SOLUTION IS VERY SLOW. THIS WILL NOT BE FINAL (I HOPE)

def mover(lines, startPtDir):
	powered = set()
	visited = []
	moveQueue = [startPtDir]

	while len(moveQueue) > 0:
		toExplore = moveQueue.pop()

		if toExplore in visited:
			continue

		visited.append(toExplore)
		(_, currDir) = (row, col), (dRow, dCol) = toExplore
		nextPoint = (nextRow, nextCol) = (row + dRow), (col + dCol)

		if not 0 <= nextRow < len(lines) or \
		   not 0 <= nextCol < len(lines[0]):
			continue;

		powered.add(nextPoint)

		nextDirDict = {
			'.': [currDir],
			# Rotate left
			'/': [(-dCol, -dRow)],
			# Rotate right
			'\\': [(dCol, dRow)],
			# Split vertically if moving left/right
			'|': [currDir] if dRow != 0 else [(-1, 0), (1, 0)],
			# Split horizontally if moving up/down
			'-': [currDir] if dCol != 0 else [(0, -1), (0, 1)]
		}

		nextDirs = nextDirDict[lines[nextRow][nextCol]]

		for nextDir in nextDirs:
			moveQueue.append((nextPoint, nextDir))

	return len(powered)

def part1(lines):
	return mover(lines, ((0, -1), (0, 1)))

def part2(lines):
	maxPowered = 0

	for i in range(len(lines)):
		maxPowered = max(maxPowered, 
			# From left moving right
			mover(lines, ((i, -1), (0, 1))),
			# From right moving left
			mover(lines, ((i, len(lines)), (0, -1))),
			# From top moving down
			mover(lines, ((-1, i), (1, 0))),
			# From bottom moving up
			mover(lines, ((len(lines), i), (-1, 0))),
		)

		print(i, maxPowered)

	return maxPowered

if __name__ == "__main__":
	lines = open("d16.txt").read().splitlines()
	
	print(f'Part 1: {part1(lines)}')
	print(f'Part 2: {part2(lines)}')