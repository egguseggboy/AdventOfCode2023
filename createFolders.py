import os

if __name__ == "__main__":
	for i in range(1, 26):
		day = f'd{i:02}'
		path = os.path.join(os.getcwd(), day)

		os.mkdir(day)
		open(f'{day}/{day}.txt', 'w')