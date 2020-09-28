
# Pythono3 code to rename multiple
# files in a directory or folder

# importing os module
import os

# Function to rename multiple files


def main():

    contents = []
    while True:
        try:
            line = input()
            if(line):
                pass
            else:
                break
        except EOFError:
            break
        contents.append(line)

    for line in contents:
        print(line[:line.rfind(" ")]+";"+line[line.rfind(" ")+1:])


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
