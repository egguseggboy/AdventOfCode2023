connections = {
	'|': ('u', 'd'),
	'-': ('l', 'r'),
	'L': ('u', 'r'),
	'J': ('u', 'l'),
	'7': ('d', 'l'),
	'F': ('d', 'r'),
	'.': (), 'S': ()
}

opposites = {
	'u': 'd', 'd': 'u',
	'l': 'r', 'r': 'l'
}

def calcNextDir(lines, pos, fromDir):
	row, col = pos
	currcoords = lines[row][col]

	dirPair = connections[currcoords]
	if fromDir in dirPair:
		newFromDir = opposites[dirPair[dirPair.index(fromDir) - 1]]

		newRow = row + (newFromDir == 'u') - (newFromDir == 'd')
		newCol = col + (newFromDir == 'l') - (newFromDir == 'r')

		return newRow, newCol, newFromDir
	
	return None

def traversePath(lines, part):
	dists = [[-1] * len(lines[0]) for _ in range(len(lines))]

	for row in range(len(lines)):
		if "S" in lines[row]:
			col = lines[row].index("S")
			break

	dists[row][col] = 0
	
	# row, col = pos

	# For part 2
	path = set()

	# consider up
	if row > 0 and lines[row-1][col] in ['|', '7', 'F']:
		path.update(movePath(lines, dists, (row-1, col), 'd'))
	# consider down
	if row < len(lines) - 1 and lines[row+1][col] in ['|', 'L', 'J']:
		path.update(movePath(lines, dists, (row+1, col), 'u'))
	# consider left
	if col > 0 and lines[row][col-1] in ['-', 'L', 'F']:
		path.update(movePath(lines, dists, (row, col-1), 'r'))
	# consider right
	if col < len(lines[0]) - 1 and lines[row][col+1] in ['-', 'J', '7']:
		path.update(movePath(lines, dists, (row, col+1), 'l'))

	if part == 1:
		return max(max(row) for row in dists)
	
	return path

def movePath(lines, dists, pos, fromDir):
	stepsAway = 1
	row, col = pos

	path = set()

	# Keep moving until invalid connection is reached or currDist >= prevDist
	while True:
		path.add((row, col))

		if stepsAway >= dists[row][col] and dists[row][col] != -1:
			break

		dists[row][col] = stepsAway
		nextPoint = calcNextDir(lines, (row, col), fromDir)

		# Break if valid connection is not made
		if nextPoint is None:
			break

		row, col, fromDir = nextPoint
		stepsAway += 1

	return path


def part1(lines):
	return traversePath(lines, 1)

def pipeTranslate(pipe):
	match pipe:
		case '|': return [".p."] * 3
		case '-': return ["...", "ppp", "..."]
		case 'L': return [".p.", ".pp", "..."]
		case 'J': return [".p.", "pp.", "..."]
		case '7': return ["...", "pp.", ".p."]
		case 'F': return ["...", ".pp", ".p."]
		case 'S': return [".p.", "ppp", ".p."]

def addZeros(map):
	zeroQueue = [(0, 0)]

	while len(zeroQueue) > 0:
		row, col = zeroQueue.pop()

		map[row][col] = 'o'

		if row > 0 and map[row-1][col] in ['i', '.']:
			zeroQueue.append((row-1, col))
		if row < len(map) - 1 and map[row+1][col] in ['i', '.']:
			zeroQueue.append((row+1, col))
		if col > 0 and map[row][col-1] in ['i', '.']:
			zeroQueue.append((row, col-1))
		if col < len(map[0]) - 1 and map[row][col+1] in ['i', '.']:
			zeroQueue.append((row, col+1))


def part2(lines):
	tilesMap = [['i'] * 3 * len(lines[0]) for _ in range(3 * len(lines))]
	
	path = traversePath(lines, 2)

	for coords in path:
		row, col = coords
		mapRow = row * 3; mapCol = col * 3;

		tileMap = pipeTranslate(lines[row][col])

		for r in range(3):
			for c in range(3):
				tilesMap[mapRow + r][mapCol + c] = tileMap[r][c]

	addZeros(tilesMap)

	total = 0
	for row in tilesMap:
		total += row.count('i')

	return int(total / 9)

if __name__ == "__main__":
	lines = open("d10.txt").read().splitlines()
	
	print(f'Part 1: {part1(lines)}')
	print(f'Part 2: {part2(lines)}')