@echo off
for /d %%I in (*) do (
    echo  ######################################################
    echo  == Encoding File == "%%I"
    echo  ......

    winrar.exe a "%%I.rar" "%%I" -df -ibck
    
    echo  ######################################################
    echo.
)
rem pause
