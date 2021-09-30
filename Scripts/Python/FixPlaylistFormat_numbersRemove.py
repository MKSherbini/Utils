
import os
import re
from inspect import getsourcefile
import sys

def main():
    idx = 0
    dir1 = ""

    if len(sys.argv) == 1:
        dir1 = os.path.dirname(os.path.abspath(getsourcefile(lambda: None)))
    else:
        dir1 = sys.argv[1]
        if len(sys.argv) == 3:
            idx = int(sys.argv[2]) - 1


    for (dirpath, dirnames, filenames) in os.walk(dir1):
        for filename in filenames:
            fileNameSplits = os.path.splitext(filename)
            if fileNameSplits[1] != ".bat":
                # print(filename.split('. ')[1])
                os.rename(filename,filename.split('. ')[1])



# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
