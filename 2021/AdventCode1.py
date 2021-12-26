import adventtools as atools
from tools import write
from tools import pause

# read input file
deepMeasures = []
with atools.openInputFile() as f:
	for l in f : deepMeasures.append(int(l))

# count increases
mIncreaseCount = 0
for i,m in enumerate(deepMeasures,0):
	if (i > 0 and deepMeasures[i - 1] < m):
			mIncreaseCount += 1
print("measure increased " + str(mIncreaseCount) + " times")

pause()

# measure increase of sliding sum
size = len(deepMeasures)
slideSum = 0
slideSumPrev = 0
sumIncreaseCount = 0

write("enter slide size: ")
slideSize = int(input())

for i,m in enumerate(deepMeasures,0):
	if (i + slideSize - 1 < size):
		slideSumPrev = slideSum
		slideSum = 0
		for n in range(slideSize) : slideSum += deepMeasures[i + n]
		if (i > 0 and slideSum > slideSumPrev):
				sumIncreaseCount += 1
print("sum increased " + str(sumIncreaseCount) + " times")
