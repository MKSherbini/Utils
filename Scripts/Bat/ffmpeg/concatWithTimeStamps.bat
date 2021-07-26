@REM init
@echo off
setlocal enableextensions enabledelayedexpansion
set file=%1
set filename=%~n1
set fileext=%~x1
set /a count = 0

@REM generate parts
for /F "tokens=1,2 delims= " %%i in (timestamps.txt) do (
    set /a count += 1
    @REM ffmpeg -hwaccel cuda -i "%filename%%fileext%" -ss %%i -to %%j -c:a copy -c:v copy "%filename%x!count!%fileext%" 
    IF not %%i EQU -1 if not %%j EQU -1 (ffmpeg -hwaccel cuda -i "%filename%%fileext%" -ss %%i -to %%j -c:a copy -c:v copy "%filename%x!count!%fileext%")
    IF %%i EQU -1 if not %%j EQU -1 (ffmpeg -hwaccel cuda -i "%filename%%fileext%" -to %%j -c:a copy -c:v copy "%filename%x!count!%fileext%")
    IF not %%i EQU -1 if %%j EQU -1 (ffmpeg -hwaccel cuda -i "%filename%%fileext%" -ss %%i -c:a copy -c:v copy "%filename%x!count!%fileext%")
   
    @REM if !count! equ 1 goto :eof
    @REM echo !count! %filename%
    @REM echo input file name is ^<%filename%^> and extension is ^<%fileext%^>
    @REM echo %%i %%j

)  

@REM clean original file
del "%filename%%fileext%"

@REM concat parts
FOR /L %%f IN (1,1,!count!) DO (
  ECHO file %filename%x%%f%fileext% >> concatlist.txt
)

ffmpeg -f concat -safe 0 -i concatlist.txt -c copy "%filename%%fileext%"

@REM clean temp files
del concatlist.txt
FOR /L %%f IN (1,1,!count!) DO (
  DEL %filename%x%%f%fileext%
)

@REM wait and quit
pause
exit