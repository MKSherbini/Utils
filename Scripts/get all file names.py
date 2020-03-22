
# Pythono3 code to rename multiple
# files in a directory or folder

# importing os module
import os
import io
# Function to rename multiple files


def main():
    outName = "animelist.txt"
    out = io.open("D:/"+outName, "w+", encoding="utf-8")

    print("running")
    i = 0
    dir1 = "F:/Series"
    dir2 = "I:/Series/English"
    dir3 = "I:/Series/Animated"
    dir4 = "I:/Movies"
    dir5 = "F:/Movies/English"
    dir6 = "F:/Movies/Anim"

    # print((os.listdir(dir1)+os.listdir(dir2)))

    for filename in (os.listdir(dir1)+os.listdir(dir2)):
        print(filename)
        out.write(filename+"\n")
    for filename in (os.listdir(dir3)+os.listdir(dir4)):
        print(filename)
        out.write(filename+"\n")
    for filename in (os.listdir(dir5)+os.listdir(dir6)):
        print(filename)
        out.write(filename+"\n")

    out.close()


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
