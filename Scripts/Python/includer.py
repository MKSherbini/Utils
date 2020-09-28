from os import walk
from inspect import getsourcefile
from os.path import abspath
from os.path import dirname

##import os,sys
##def we_are_frozen():
##    # All of the modules are built-in to the interpreter, e.g., by py2exe
##    return hasattr(sys, "frozen")
##
##def module_path():
##    encoding = sys.getfilesystemencoding()
##    if we_are_frozen():
##        return os.path.dirname(unicode(sys.executable, encoding))
##    return os.path.dirname(unicode(__file__, encoding))


#path="D:\Embedded\stm\Projects\KeilTest\_used"
path = abspath(getsourcefile(lambda:None))
print(dirname(path))
outName = "ChessGameAssets.h"
out = open(dirname(path) + "\\" + outName,"w+")
f = []
for (dirpath, dirnames, filenames) in walk(dirname(path)):
    f.extend(filenames)
    break
p = ""
s = "#include \""
e = "\" \n"
for i in f:
    if(i[-2:] == ".h" and i != outName):
        p+=s + i + e
        out.write(s + i + e)
print(p)
out.close() 

