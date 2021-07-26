@echo off
for %%I in (*.mp4) do (
    echo  ######################################################
    echo  == Original File == "%%I"
    echo  ......
    echo  == New File == "%%I.part" 
    echo  ######################################################
    echo.
    rename "%%I" "%%I.part"
)
pause
