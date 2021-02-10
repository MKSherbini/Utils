@echo off
for %%I in (*.mkv) do (
    echo  ######################################################
    echo  == Encoding File == "%%I"
     echo Input file info 
    mediainfo --Inform="General;Duration=%%Duration/String3%%\nFile size=%%FileSize/String1%%" "%%I"
    echo  ......
    echo  ######################################################

    ffmpeg -v quiet -stats  -threads 4 -hwaccel cuda -i "%%I"  -c:v libx265 -filter:v fps=fps=30 -max_muxing_queue_size 9999 -f matroska "%%~nI x265%%~xI" 
    @REM 2>NUL

    mediainfo --Inform="General;Duration=%%Duration/String3%%\nFile size=%%FileSize/String1%%" "%%~nI x265%%~xI"
    
    echo  ######################################################
    echo.
)
pause
