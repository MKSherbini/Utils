
# importing os module
import os

from os import walk


def main():
    i = 0
    dir1 = "C:/Users/mh-sh/Deezloader Music"
    dir2 = "C:/Users/mh-sh/Deezloader Music remix"

    for (dirpath, dirnames, filenames) in walk(dir1):

        # for filename in filenames:
        #     src = dir1 + filename
        #     dst = dir2 + filename[filename.find("Tensei"):]

        # os.rename(dirpath+filenames, dir2+dirpath[len(dir1):]+filenames)
        list1 = [dirpath + "/" + s for s in filenames]
        list2 = [dir2+dirpath[len(dir1):]+"/"+s for s in filenames]
        dst = ""
        for t in range(len(list1)):
            if list1[t].lower().find("remix") != -1 or list1[t].lower().find("acoustic") != -1 or list1[t].lower().find("(live version") != -1 or list1[t].lower().find("live at mtv") != -1 or list1[t].lower().find("(live)") != -1 or list1[t].lower().find("live from") != -1:
                src = (list1[t].replace("\\", "/"))
                dst = (list2[t].replace("\\", "/"))
                # src = src.replace("/", "\\")
                # dst = dst.replace("/", "\\")
                try:
                    os.makedirs(os.path.dirname(dst))
                except OSError:
                    pass
                    # print("Creation of the directory %s failed" % dst)
                else:
                    pass
                    # print("Successfully created the directory %s" % dst)
                print(src, dst)

                os.rename(src, dst)
        # if not os.listdir(dirpath):
        #     os.rmdir(dirpath)
    for (dirpath, dirnames, filenames) in walk(dir1):
        if not dirnames and len(filenames) == 1 and filenames[0] == "folder.png":
            src = ((dirpath + "/" + "folder.png").replace("\\", "/"))
            dst = ((dir2+dirpath[len(dir1):]+"/" +
                    "folder.png").replace("\\", "/"))
            os.rename(src, dst)
            os.rmdir(dirpath)
            continue

        if not dirnames and not filenames:
            os.rmdir(dirpath)

    # print('\\\\'.lower().replace('\\\\','\\'))
        # os.rename(src, dst)
        # print(dirpath) # path
        # print(dirnames) # folders in path
        # print(filenames) # files in path
        # print("level")


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
