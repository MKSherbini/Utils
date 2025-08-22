
import os
import re
from inspect import getsourcefile
import sys



def main():
    dir1 = ""

    if len(sys.argv) == 1:
        dir1 = os.path.dirname(os.path.abspath(getsourcefile(lambda: None)))
    else:
        dir1 = sys.argv[1]

    for (dirpath, dirnames, filenames) in os.walk(dir1):
        for filename in filenames:
            fileNameSplits = os.path.splitext(filename)
            if fileNameSplits[1] == ".mp4":
                cnt = cnt + 1

 


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
