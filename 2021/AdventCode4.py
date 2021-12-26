import adventtools as atools
from tools import write
from tools import pause


class Board:
	def __init__(self):
		self.board = []
		self.markboard = []

	def __repr__(self): # string rep function for debug
		rep = ""

		rep+="\n"
		for h in range(len(self.board)):
			for n in self.board[h]:
				if n < 10: rep+=" "
				rep+=str(n)+" "
			rep+="  "
			for m in self.markboard[h]:
				rep+='X' if m else '-'
			rep+="\n"

		return rep

	def appendline(self, l: str): # parse function
		boardline = []
		for ns in l.replace("\n","").split(" "):
			if (not ns == ''):
				boardline.append(int(ns))
		self.board.append(boardline)
		self.markboard.append([False]*len(boardline))

	def mark(self, num): # mark num on board
			for h in range(len(self.board)):
				for w in range(len(self.board[h])):
					if self.board[h][w] == num:
						self.markboard[h][w] = True
						return # num marked. no need to check further

	def checkwin(self): # check if this board won
		colsums = [True]*len(self.markboard[0])
		H = len(self.markboard)
		W = len(self.markboard[0])
		for h in range(H):
			rowsum = True
			for w in range(W):
				colsums[w] = colsums[w] and self.markboard[h][w]
				rowsum = rowsum and self.markboard[h][w]
				if (h == H - 1 and colsums[w]): # on last row we're done summing cols, so check
					return True, w # True -> col
			if rowsum: return False, h # False -> row
		return None

	def calcscore(self, num):
		unmarkedsum = 0
		for h in range(len(self.markboard)):
			for w in range(len(self.markboard[h])):
				if not self.markboard[h][w]:
					unmarkedsum += self.board[h][w]
		return unmarkedsum * num



boards = []
nums = []


# parse file
with atools.openInputFile() as f:
	for ns in f.readline().split(","):
		nums.append(int(ns))
	board = None
	for l in f:
		if l == "\n":
			if not board == None:
				boards.append(board)
			board = Board()
		else:
			if not board == None:
				board.appendline(l)
print("nums: "+str(nums))
print(str(len(boards))+" boards:")
print(boards)
pause()

# play game
turn = 0
num = 0
firstwin = None
lastwin = None
while boards:

	num = nums[turn]
	print("["+str(turn)+"] drawn a "+str(num))
	for board in boards:
		board.mark(num)
	print(boards)

	for i in reversed(range(len(boards))):
		if (not boards[i].checkwin() == None):
			lastwin = boards[i]
			if firstwin == None: # declare winner for part 1
				firstwin = boards[i]
				print("#"+str(i+1)+" first won! score: "+str(firstwin.calcscore(num)))
				pause()
			boards.pop(i) # remove winners for part 2

	turn += 1
print("last won! score: "+str(lastwin.calcscore(num))) # declare last one for part 2