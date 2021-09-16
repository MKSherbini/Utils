@echo on

set task=%1
setlocal enableextensions enabledelayedexpansion

set /a count = 1


call %task%

    @REM FOR /F "tokens=* USEBACKQ" %%F IN (`dir /s /b *.part ^| find "" /c /v /i`) DO (
:loop
    FOR /F "tokens=* USEBACKQ" %%F IN (`dir /b/s ^| findstr "[wp][ea][br][mt]" ^| find "" /c /v /i`) DO (
        echo iteration !count!
        echo Number of parts left: %%F
        SET var=%%F
        if !var! == 0 (
            goto done
        )   

        call %task%
        
        set /a count += 1
        goto loop 
    )
    
:done
pause
