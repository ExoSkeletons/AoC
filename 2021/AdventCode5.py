import adventtools as atools
from tools import write
from tools import pause


class Path(object):
	def __init__(self, x1, y1, x2, y2):
		# cache points in path
		points = []
		if x1 == x2: # verticals
			if y1 > y2:
				y1, y2 = y2, y1
			for y in range(y1, y2 + 1):
				points.append((x1, y))
		elif y1 == y2: # horizontals
			if x1 > x2:
				x1, x2 = x2, x1
			for x in range(x1, x2 + 1):
				points.append((x, y1))
		else: #diagonals
			if x1 > x2:
				x1, x2 = x2, x1
				y1, y2 = y2, y1
			for i in range(x2 - x1 + 1):
				points.append(
					 (x1 + i,
					 (y1 - i) if y1 > y2 else (y1 + i))
				)

		self.x1 = x1
		self.y1 = y1
		self.x2 = x2
		self.y2 = y2
		self.points = points

	def __repr__(self):
		return "("+str(self.x1)+","+str(self.y1)+") -> ("+str(self.x2)+","+str(self.y2)+")"


paths = []
W = 0
H = 0

#parse file
with atools.openInputFile() as f:
	for l in f:
		split = l.split(" ")
		point1 = split[0].split(",")
		point2 = split[2].split(",")
		path = Path(
			int(point1[0]), int(point1[1]),
			int(point2[0]), int(point2[1])
		)
		W = max(W, path.x1, path.x2)
		H = max(H, path.y1, path.y2)
		paths.append(path)
print(paths)

# overlap board
board = []
for __ in range(H+1):
	board.append([0]*(W+1))

# apply paths
for path in paths:
	# if path.x1 == path.x2 or path.y1 == path.y2: # only stright lines
		for x,y in path.points:
			board[y][x] += 1
for row in board:
	print(row)

# count overlaps
overlapCount = 0
for row in board:
	for overlap in row:
		if overlap >= 2:
			overlapCount += 1
print("overlap count: "+str(overlapCount))