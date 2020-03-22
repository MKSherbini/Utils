
# Pythono3 code to rename multiple
# files in a directory or folder

# importing os module
import os

# Function to rename multiple files


def main():
    print("running")
    i = 0
    dir1 = "I:/Anime/!/"
   
    for filename in os.listdir(dir1):
        src = dir1 + filename
       
        dst = filename
        dst = dst.replace("[AnimeKaizoku]","")
        dst = dst.replace("[AnimeKayo]","")
        dst = dst.replace("[Animekayo]","")
        dst = dst.replace("[AnimeRG]","")
        dst = dst.replace("[bonkai77]","")
        dst = dst.replace("[HorribleSubs]","")
        dst = dst.replace("[NP-COMPLETE]","")
        dst = dst.replace("[RyuujiTK]","")
        dst = dst.replace("[ZipArchive]","")
        dst = dst.replace("[Anime Kaizoku]","")
        dst = dst.replace("[HcLs]","")
        dst = dir1 + dst
       
        os.rename(src, dst)
        print(filename)


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
