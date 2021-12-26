from os.path import exists
from tools import write

def openInputFile():
	intputFilePath = ""
	while True:
		write("enter input file path: ")
		intputFilePath = input().replace("\"","")

		if exists(intputFilePath):
			return open(intputFilePath)

		print("file " + intputFilePath + " does not exist")

def getFileLines(f):
	return [l for l in f]