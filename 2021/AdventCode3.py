import adventtools as atools
from tools import write
from tools import pause



def intFromBitList(bits):
	res = 0
	for bit in bits:
		res = (res << 1) | bit
	return res



# parse file
bits = []
with atools.openInputFile() as f:
	for l in f:
		bl = []
		for c in l:
			if (c != '\n'):
				bl.append(0 if c == '0' else 1)
		bits.append(bl)


gamma = 0
epsilon = 0
possibleOxygenIndices = [*range(len(bits))]
possibleCO2Indices = [*range(len(bits))]
for coli in range(len(bits[0])):
	rating = 0
	# rating < 0 => [rating] more 0s
	# rating > 0 => [rating] more 1s
	# rating == 0 => equal 1s & 0s

	# find global common bit
	for rowi in range(len(bits)):
		rating += 1 if bits[rowi][coli] == 1 else -1
	bit = 1 if rating > 0 else 0

	# build up gamma & epsilon from bits for part 1
	gamma = (gamma << 1) | bit
	epsilon = (epsilon << 1) | (bit - 1) * -1

	# part 2
	if (len(possibleOxygenIndices) > 1):
		# find oxygen common bit
		oxygenRating = 0
		for rowi in possibleOxygenIndices:
			oxygenRating += 1 if bits[rowi][coli] == 1 else -1
		oxygenBit = 1 if oxygenRating > 0 else 0
		# filter oxygen list for part 2
		for rowi in reversed(possibleOxygenIndices):
			if (oxygenRating == 0):
				if (bits[rowi][coli] == 0):
					possibleOxygenIndices.remove(rowi)
			else:
				if (not bits[rowi][coli] == oxygenBit):
					possibleOxygenIndices.remove(rowi)
	if (len(possibleCO2Indices) > 1):
		# find co2 common bit
		co2Rating = 0
		for rowi in possibleCO2Indices:
			co2Rating += 1 if bits[rowi][coli] == 1 else -1
		co2Bit = 1 if co2Rating > 0 else 0
		# filter co2 list for part 2
		for rowi in reversed(possibleCO2Indices):
			if (co2Rating == 0):
				if (bits[rowi][coli] == 1):
					possibleCO2Indices.remove(rowi)
			else:
				if (bits[rowi][coli] == co2Bit):
					possibleCO2Indices.remove(rowi)

oxygen = intFromBitList(bits[possibleOxygenIndices[0]])
co2 = intFromBitList(bits[possibleCO2Indices[0]])

print("gamma: "+str(gamma)+" epsilon: "+str(epsilon)+" power (mul): "+str(gamma*epsilon))

print("oxygens: "+str(bits[possibleOxygenIndices[0]]) + " = " + str(oxygen))
print("co2s: "+str(bits[possibleCO2Indices[0]]) + " = " + str(co2))
print("life support (mul): "+str(oxygen*co2))