
import os
import re
import io
from inspect import getsourcefile
import sys
import pyperclip

extraBadWords = []


def main():
    i = 0

    # dir1 = "I:/Anime/!/"
    # dir1 = "I:/Anime/"
    # dir1 = "D:/_tempSlow/_Ready/"
    if len(sys.argv) == 1:
        dir1 = os.path.dirname(os.path.abspath(getsourcefile(lambda: None)))
    else:
        dir1 = sys.argv[1]
        if len(sys.argv) > 2:
            for bw in sys.argv[2:]:
                extraBadWords.append(bw)

    FixAnimeNames(dir1)
    files = set()
    for (dirpath, dirnames, filenames) in os.walk(dir1):
        for filename in filenames:
            dst = filename
            match = re.findall(r"-[\Sa-zA-Z0-9]{16}-[0-9]{3}", dst)
            if match:
                dst = dst.replace(match[0], "")

            fileNameSplits = os.path.splitext(dst)
            if fileNameSplits[1] == ".bat":
                continue

            dst = (fileNameSplits[0]).strip(". _")
            # if(fileNameSplits[1] == ".zip" or fileNameSplits[1] == ".rar"):
            files.add(dst)
    sortedFiles = list(files)
    sortedFiles.sort()

    clipCopy = ""
    for fi in sortedFiles:
        clipCopy += fi + "\n"
        print(fi)

    pyperclip.copy(clipCopy)
    # dst = os.path.dirname(os.path.abspath(getsourcefile(lambda: None)))

    # with open(os.path.join(dir1, "out.txt"), "a", encoding="utf-8") as f:
    # with open("out.txt", "a", encoding="utf-8") as f:
    # with open(os.path.join(dst, "out.txt"), "w", encoding="utf-8") as f:
    #     for fi in files:
    #         print(fi)
    #         print(fi, file=f)


def FixAnimeNames(dir1):
    i = 0
    # dir1 = "I:/Anime/!/"
    # dir1 = "I:/Anime/"
    # dir1 = "D:/_tempSlow/_Ready/"

    for (dirpath, dirnames, filenames) in os.walk(dir1):
        FixAndRename(dirpath, dirnames)
    for (dirpath, dirnames, filenames) in os.walk(dir1):
        FixAndRename(dirpath, filenames)


def FixAndRename(dirpath, filenames):
    for filename in filenames:
        src = os.path.join(dirpath, filename)
        # perf sux but to lazy to do smt else
        dst = filename
        dst = dst.replace("[AnimeKaizoku]", "")
        dst = dst.replace("[AnimeKayo]", "")
        dst = dst.replace("[Animekayo]", "")
        dst = dst.replace("[AnimeRG]", "")
        dst = dst.replace("[bonkai77]", "")
        dst = dst.replace("[HorribleSubs]", "")
        dst = dst.replace("[NP-COMPLETE]", "")
        dst = dst.replace("[RyuujiTK]", "")
        dst = dst.replace("[ZipArchive]", "")
        dst = dst.replace("[Anime Kaizoku]", "")
        dst = dst.replace("[HcLs]", "")
        dst = dst.replace("[AHQ]", "")
        dst = dst.replace("[Neel]", "")
        dst = dst.replace("[AnimeOut]", "")
        dst = dst.replace("[22nowyyblack]", "")
        dst = dst.replace("[bluely8]", "")
        dst = dst.replace("[DemonAlpha]", "")
        dst = dst.replace("[Patwari]", "")
        dst = dst.replace("[Marshall]", "")
        dst = dst.replace("[Kaycee]", "")
        dst = dst.replace("[AKS]", "")
        dst = dst.replace("[tlacatlc6]", "")
        dst = dst.replace("[dedsec]", "")
        dst = dst.replace("[Darklord]", "")
        dst = dst.replace("[Judas]", "")
        dst = dst.replace("[Cleo]", "")
        dst = dst.replace("[AniDL]", "")
        dst = dst.replace("[Commie]", "")
        dst = dst.replace("[Anime-Koi]", "")
        dst = dst.replace("[Hadena]", "")
        dst = dst.replace("[st0ned]", "")
        dst = dst.replace("[Mori]", "")
        dst = dst.replace("[Nuke]", "")
        dst = dst.replace("[LBB]", "")
        dst = dst.replace("[UberAnime]", "")
        dst = dst.replace("[zgg]", "")
        dst = dst.replace("[Waku]", "")
        dst = dst.replace("[Zimabdk]", "")
        dst = dst.replace("[SHINIGAMI_KIRA]", "")
        dst = dst.replace("[DhruboZ]", "")
        dst = dst.replace("[DashWatson]", "")
        dst = dst.replace("[LoliKiller]", "")
        dst = dst.replace("[Lucy]", "")
        dst = dst.replace("[architdate]", "")
        dst = dst.replace("[Erai-raws]", "")
        dst = dst.replace("[dn92]", "")
        dst = dst.replace("[FFF]", "")
        dst = dst.replace("[HS]", "")
        dst = dst.replace("[Zii]", "")
        dst = dst.replace("[DeadFish]", "")
        dst = dst.replace("[Core]", "")
        dst = dst.replace("[Ace]", "")
        dst = dst.replace("[Apognwsi]", "")
        dst = dst.replace("[Yuu]", "")
        dst = dst.replace("[King Of Darkness]", "")
        dst = dst.replace("[Mugiwara]", "")

        for bw in extraBadWords:
            dst = dst.replace(bw, "")

        # match = re.findall(r"-[\Sa-zA-Z0-9]{16}-[0-9]{3}", dst)
        # if match:
        #     dst = dst.replace(match[0], "")

        fileNameSplits = os.path.splitext(dst)
        dst = dst.strip(". _")
        dst = (fileNameSplits[0]).strip(". _") + fileNameSplits[1]
        dst = os.path.join(dirpath, dst)

        # print(filename)
        if(src != dst):
            os.rename(src, dst)
            print("O", os.path.basename(src), "N", os.path.basename(dst))


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
