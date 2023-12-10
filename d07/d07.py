def cardVal(card, part):
	if card.isdigit():
		return int(card)
	match card:
		case 'T': return 10
		case 'J': return 11 if (part == 1) else 1
		case 'Q': return 12
		case 'K': return 13
		case 'A': return 14

def handMatch(hand):
	counts = [hand.count(card) for card in set(hand)]

	match (len(counts)):
		# 5 of a kind
		case 1: return 6
		case 2:
			# 4 of a kind
			if (counts.count(4) == 1): return 5
			# Full house
			return 4
		case 3:
			# 3 of a kind
			if (counts.count(3) == 1): return 3
			# 2 pair
			return 2
		# 1 pair
		case 4: return 1
		# High card
		case 5: return 0

def handVal(hand, part):
	if (part == 2):
		counts = [card for card in set(hand)]
		counts.sort(key = lambda c : hand.count(c), reverse=True)

		for card in counts:
			if card != 'J':
				hand = hand.replace('J', card)
				break

	return handMatch(hand)

def sortVal(hand, part):
	cardsVal = 0
	for i in range(len(hand)):
		cardsVal += cardVal(hand[i], part) / pow(10.0, (i + 1) * 2)

	# sortVal: hand.C1C2C3C4C5
	return handVal(hand, part) + cardsVal

def solve(lines, part):
	winnings = 0

	plays = [line.split() for line in lines]
	plays.sort(key=lambda p : sortVal(p[0], part))

	for i in range(len(plays)):
		winnings += int(plays[i][1]) * (i + 1)

	return winnings

def part1(lines):
	return solve(lines, 1)

def part2(lines):
	return solve(lines, 2)

if __name__ == "__main__":
	lines = open("d07.txt").read().splitlines()
	
	print(f'Part 1: {part1(lines)}')
	print(f'Part 2: {part2(lines)}')