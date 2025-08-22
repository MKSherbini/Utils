

# importing os module
import os
from os import walk
# Function to rename multiple files


def main():

    i = 0
    dir1 = "C:/Users/mh-sh/Deezloader Music"

    files = []
    i = 0
    f = []
    for (dirpath, dirnames, filenames) in walk(dir1):
        files.extend(filenames)

        # for filename in filenames:
            #    filename.split(" ")

            # dst = filename.replace(",", " ")
            # dst = dst.replace("-", " ")
            # dst = dst.replace("&", " ")
            # dst = dst.replace("_", " ")
            # dst = dst.replace("[", " ")
            # dst = dst.replace("[", " ")
            # dst = dst.replace("(", " ")
            # dst = dst.replace(")", " ")
            # dst = dst.replace(".", " ")
            # dst = dst.replace(".", " ")
            # dst = dst.replace("feat", " ")
            # dst = dst.replace("Lossless", " ")
            # dst = dst.replace("    ", " ")
            # dst = dst.replace("   ", " ")
            # dst = dst.replace("  ", " ")

            # files.append(dst.split(" "))
            # files.append(dst.split(" "))
            # print(files[i])
            # i+=1

    duplicates = []
    flag = 0
    idx = 1

    for it1 in range(len(files)):
        for it2 in range(it1+1, len(files)):
            f1 = files[it1]
            f2 = files[it2]
            # if compare2(f1, f2) > 5:
            if f1 == f2 and f1 != "folder.png"and f1 != "playlist.m3u":
                if flag == 0:
                    duplicates.append([])
                    idx += 1
                    flag = 1
                    # print(len(duplicates))
                    duplicates[idx-2].append(f1)
                duplicates[idx-2].append(f2)
        flag = 0
    print(len(duplicates))
    for dup in duplicates:
        print(dup)

    # print(duplicates)
    # print(files[0])
    # print(files[1])
    # print(compare2(files[0], files[1]))


def compare2(f1, f2):

    matches = 0

    for w1 in f1:
        for w2 in f2:
            if w1 == w2 and w1 != " "and w1 != ""and w1 != "mp3"and w1 != "flac"and w1 != "FLAC":
                matches += 1

    return matches


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
