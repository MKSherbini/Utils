@echo off
for %%I in (*.mkv) do (
    echo  ######################################################
    echo  == Original File == "%%I"
    echo  ......
    echo  == New File == "%%~nI.mp4" 
    echo  ######################################################
    echo.
    ffmpeg -i "%%I" "%%~nI.mp4" 
    del "%%I"
)
pause