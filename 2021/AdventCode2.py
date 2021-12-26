import adventtools as atools
from tools import write
from tools import pause

FORWARD = 'f'
BACKWARDS = 'b'
UP = 'u'
DOWN = 'd'
HORIZONTAL = 'H'
DEPTH = 'D'

class Instruction:
	"""class for keeping track of an intruction"""

	axis: bool # horizonal: True
	amount: int = 0

	def __init__(self, axis: bool, amount: int):
		self.axis = axis
		self.amount = amount

	def __repr__(self):
		return "["+(HORIZONTAL if self.axis else DEPTH)+","+str(self.amount)+"]"


# parse file
instructions = []
with atools.openInputFile() as f:
	for l in f:
		direction = l[0]
		instructions.append(Instruction(
			direction == FORWARD or direction == BACKWARDS,
			int(l[-2]) * (-1 if direction == UP or direction == BACKWARDS else 1)
		))
print(instructions)

# part 1
horizontal = 0
depth = 0
for ins in instructions:
	if (ins.axis): horizontal += ins.amount
	else: depth += ins.amount
print("part 1 final position: ("+str(horizontal)+","+str(depth)+") mul: "+str(horizontal*depth))

pause()

# part 2
horizontal = 0
depth = 0
aim = 0
for ins in instructions:
	if (ins.axis):
		horizontal += ins.amount
		depth += aim * ins.amount
	else:
		aim += ins.amount
print("part 2 final position: ("+str(horizontal)+","+str(depth)+")["+str(aim)+"] mul: "+str(horizontal*depth))