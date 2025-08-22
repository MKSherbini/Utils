
import os
import sys
from inspect import getsourcefile
import subprocess


def main():

    dir1 = ""

    if len(sys.argv) == 1:
        dir1 = os.path.dirname(os.path.abspath(getsourcefile(lambda: None)))
    else:
        dir1 = sys.argv[1]

    # return_code = subprocess.run("echo Hello World", shell=True) shell=True for build in commands only
    # print(return_code)
    return_code = subprocess.run(
        ["python", os.path.join(dir1, "FixAndListAnimeNames.py")])


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
