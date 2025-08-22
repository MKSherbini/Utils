
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
    
    print(f"removing desktop.ini in: {dir1}")
    for root, dirs, files in os.walk(dir1):
        for filename in files:
            if filename.lower() == "desktop.ini":
                file_path = os.path.join(root, filename)
                try:
                    os.remove(file_path)
                    print(f"Deleted {file_path}")
                except Exception as e:
                    print(f"Error deleting {file_path}: {e}")


 


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
