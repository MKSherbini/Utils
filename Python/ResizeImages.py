
import os
from inspect import getsourcefile
import sys
import cv2


def main():
    dir1 = ""

    if len(sys.argv) == 1:
        dir1 = os.path.dirname(os.path.abspath(getsourcefile(lambda: None)))
    else:
        dir1 = sys.argv[1]
    i = 0
    for (dirpath, dirnames, filenames) in os.walk(dir1):
        for filename in filenames:
            fileNameSplits = os.path.splitext(filename)
            if fileNameSplits[1] == ".jpg":
                print(dirpath+'\\'+filename)
                image = cv2.imread(dirpath+'\\'+filename)
                newimage = image[130:1080, :]
                # cv2.imshow(image)
                cv2.imwrite(
                    dirpath+'\\'+"img"+str(i)+fileNameSplits[1], newimage)
                i = i + 1
                # cv2.imshow("crop", newimage)


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
