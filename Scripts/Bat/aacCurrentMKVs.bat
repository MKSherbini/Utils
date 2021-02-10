@echo off
for %%I in (*.mkv) do (
    echo  ######################################################
    echo  == Extracting audio == "%%I"
    echo Input file info 
    mediainfo --Inform="General;Duration=%%Duration/String3%%\nFile size=%%FileSize/String1%%" "%%I"
    echo  ......

    ffmpeg -v quiet -i "%%I" -c:a copy "%%~nI.aac" 

    echo Output file info 
    mediainfo --Inform="General;Duration=%%Duration/String3%%\nFile size=%%FileSize/String1%%" "%%~nI.aac"
    del "%%I"
    
    echo  ######################################################
    echo.
)
pause
