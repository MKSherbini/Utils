@echo off
for %%I in (*.mkv *avi *.flv *.rmvb) do (
    echo  ######################################################
    echo  == Moving File == "%%I"
    echo  ......

    md "%%~nI"
    move "%%I" "%%~nI/%%I"


    echo == New File == "%%~nI/%%I"
    echo  ######################################################    
    echo.
)
pause