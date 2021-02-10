
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
            
    imgs = []
    for (dirpath, dirnames, filenames) in os.walk(dir1):
        for filename in filenames:
            fileNameSplits = os.path.splitext(filename)
            if fileNameSplits[1] == ".jpg":
                imgs.append(cv2.imread(filename) )

    cv2.imwrite('vconcat.jpg', cv2.vconcat(imgs))


 


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
