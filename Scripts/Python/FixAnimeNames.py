
import os
import re
import zlib

def main():
    i = 0
    # dir1 = "I:/Anime/!/"
    # dir1 = "I:/Anime/"
    dir1 = "D:/_tempSlow/_Ready/"

    for (dirpath, dirnames, filenames) in os.walk(dir1):
        FixAndRename(dirpath, dirnames)
    for (dirpath, dirnames, filenames) in os.walk(dir1):
        FixAndRename(dirpath, filenames)


def FixAndRename(dirpath, filenames):
    for filename in filenames:
        src = os.path.join(dirpath, filename)

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
