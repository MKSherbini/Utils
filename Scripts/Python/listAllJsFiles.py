
import os
import re
from inspect import getsourcefile
import sys


def main():
    i = 0
    dir1 = ""
    if len(sys.argv) == 1:
        dir1 = os.path.dirname(os.path.abspath(getsourcefile(lambda: None)))
    else:
        dir1 = sys.argv[-1]

    for (dirpath, dirnames, filenames) in os.walk(dir1):
        for filename in filenames:
            if filename[-3:] == ".js":
                print(os.path.join(dirpath, filename))


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
