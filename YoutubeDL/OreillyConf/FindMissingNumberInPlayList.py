# Pythono3 code to rename multiple
# files in a directory or folder

# importing os module
import os
import collections
from os import walk
from inspect import getsourcefile
from os.path import abspath
from os.path import dirname
import sys

# Function to rename multiple files


def main():

    # path = "C:/DefinitelyNotWindows/Tracks/GameDev/Unity/Unity tut/PluralSight/Unreal/Unreal Engine 4 Fundamentals"
    # path = "C:/DefinitelyNotWindows/Tracks/GameDev/Unity/Unity tut/PluralSight/Unreal/"
    if len(sys.argv) == 1:
        path = os.path.dirname(os.path.abspath(getsourcefile(lambda: None)))
    else:
        path = sys.argv[1]
    
    print(path)

    for (dirpathr, dirnamesr, filenamesr) in walk(path):
        for target in dirnamesr:
            print(target)
            target = os.path.join(dirpathr, target)
            files = []
            subs = []
            part = []
            for (dirpath, dirnames, filenames) in walk(target):
                # files.extend(filenames)
                # print(dirpath)
                for item in filenames:
                    # print(item, item[-3:])
                    if item[-3:] == "srt":
                        subs.append(item)
                    elif item[-3:] == "mp4":
                        files.append(item)
                    elif item[-3:] == "art":
                        part.append(item)
            print("mp4")
            FindMissing(files)
            print("srt")
            FindMissing(subs)
            if len(part)>0:
                print("part")
                for item in part:
                  print(str(item.split(".")[0]) + ",", end="")

        break


def FindMissing(files):
    if len(files) == 0:
        return []
    numbers = []
    for file in files:
        try:
            numbers.append(int(file.split(".")[0]))
        except ValueError:
            print("value error")
    
    if len(numbers) == 0:
        return []
        
    numbers = list(collections.OrderedDict.fromkeys(numbers).keys())
    numbers.sort()

    for i in range(0, len(numbers) - 1, 1):
        if numbers[i + 1] - numbers[i] > 1:
            # print("range: ", range(numbers[i], numbers[i + 1]))
            for item in list(range(numbers[i], numbers[i + 1]))[1:]:
                print(str(item) + ",", end="")
    if numbers[-1] != len(numbers):
        print()
    print(numbers[-1], len(numbers), numbers[-1] - len(numbers))


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
