
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
            dst = filename
            # if dst[4:6]=="  ":
                # print(dst)
                # print(dst[:4]+"X"+dst[5:])

                # if dst[-11:] == "description":
                #     dst=dst[:4]+dst[5:]+"O"
                # else:
                #     dst=dst[:4]+dst[5:]

                # os.rename(os.path.join(dirpath, filename),
                #           os.path.join(dirpath, dst))
            match = re.findall(r"(^[0-9]+[.-])[^ ]", dst)
            if match:
                dst = dst.replace(match[0], match[0][:-1]+". ")
                print("O", filename, "N", dst)

                os.rename(os.path.join(dirpath, filename),
                          os.path.join(dirpath, dst))


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
