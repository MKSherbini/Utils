
import os
import re
from inspect import getsourcefile
import sys


def atof(text):
    try:
        retval = float(text)
    except ValueError:
        retval = text
    return retval


def natural_keys(text):
    '''
    alist.sort(key=natural_keys) sorts in human order
    http://nedbatchelder.com/blog/200712/human_sorting.html
    (See Toothy's implementation in the comments)
    float regex comes from https://stackoverflow.com/a/12643073/190597
    '''
    return [atof(c) for c in re.split(r'[+-]?([0-9]+(?:[.][0-9]*)?|[.][0-9]+)', text)]


def main():
    idx = 0
    dir1 = ""

    if len(sys.argv) == 1:
        dir1 = os.path.dirname(os.path.abspath(getsourcefile(lambda: None)))
    else:
        dir1 = sys.argv[1]
        if len(sys.argv) == 3:
            idx = int(sys.argv[2]) - 1

    cnt = 0
    for (dirpath, dirnames, filenames) in os.walk(dir1):
        for filename in filenames:
            fileNameSplits = os.path.splitext(filename)
            if fileNameSplits[1] == ".mp4":
                cnt = cnt + 1

    print(cnt)
    for (dirpath, dirnames, filenames) in os.walk(dir1):
        dirnames.sort(key=natural_keys)
        filenames.sort(key=natural_keys)

        fileNameSplits = []
        for filename in filenames:
            dst = filename
            fileNameSplits = os.path.splitext(dst)
            match = re.findall(r"^[0-9]+[.-][ ]*", dst)
            if match:
                if fileNameSplits[1] == ".mp4":
                    idx = idx + 1
                dst = str(idx).zfill(len(str(cnt)))+". "+dst[len(match[0]):]
                print("O", filename, "N", dst)

                os.rename(os.path.join(dirpath, filename),
                          os.path.join(dirpath, dst))


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
