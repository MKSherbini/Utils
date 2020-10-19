
import os
import re
import io
from inspect import getsourcefile
import sys
import pyperclip

extraBadWords = []


def main():
    i = 0
    dir1 = r"C:\DefinitelyNotWindows\Games\Epic Games\rocketleague"
    files = []
    for (dirpath, dirnames, filenames) in os.walk(dir1):
        for filename in filenames:
            files.append(os.path.join(dirpath[23:], filename))

    clipCopy = ""
    for fi in files:
        clipCopy += fi + "\n"
        print(fi)

    pyperclip.copy(clipCopy)
    # dst = os.path.dirname(os.path.abspath(getsourcefile(lambda: None)))


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
