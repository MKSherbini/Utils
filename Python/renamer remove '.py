

# importing os module
import os

# Function to rename multiple files


def main():
    print("running")
    i = 0
    dir1 = "C:/DefinitelyNotWindows/Tracks/GameDev/OpenGL/Tuts/LearnOpenGL/"
    dir2 = "E:\\Tensei shitara Slime Datta Ken\\"
    for filename in os.listdir(dir1):
        src = dir1 + filename
       

        dst = dir1 + filename.replace("\'","")

        # rename() function will
        # rename all the files

        os.rename(src, dst)
        print(filename)


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
