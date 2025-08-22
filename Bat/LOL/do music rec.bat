REM @echo off
call :treeProcess
goto :eof

:treeProcess 
if exist *.bnk ( if not exist "%CD%\OGGFiles" mkdir "%CD%\OGGFiles"
for %%f in (*.bnk) do "I:\Tracks\GameDev\3D Ripping\league\bnkextr-master\bnkextr-master\bnkextr.exe" %%f
for %%f in (*.wem) do "I:\Tracks\GameDev\3D Ripping\league\ww2ogg022\ww2ogg.exe" %%f --pcb "I:\Tracks\GameDev\3D Ripping\league\ww2ogg022\packed_codebooks_aoTuV_603.bin"   
for %%f in (*.ogg) do "I:\Tracks\GameDev\3D Ripping\league\ww2ogg022\revorb.exe" %%f
move *.ogg "%CD%\OGGFiles"
del "%CD%\*.wem"
rem echo "%CD%\OGGFiles"
)

if exist *.wpk ( if not exist "%CD%\OGGFiles2" mkdir "%CD%\OGGFiles2"
for %%f in (*.wpk) do "I:\Tracks\GameDev\3D Ripping\league\RavioliGameTools_v2.10\RExtractorConsole.exe" %%f OGGFiles2
cd OGGFiles2
for %%f in (*.wem) do "I:\Tracks\GameDev\3D Ripping\league\ww2ogg022\ww2ogg.exe" %%f --pcb "I:\Tracks\GameDev\3D Ripping\league\ww2ogg022\packed_codebooks_aoTuV_603.bin"   
for %%f in (*.ogg) do "I:\Tracks\GameDev\3D Ripping\league\ww2ogg022\revorb.exe" %%f
del "%CD%\OGGFiles2\*.wem"
del "%CD%\*.wem"
REM echo %CD%
cd ..
REM echo %CD%
REM move *.ogg "%CD%\OGGFiles2"
REM echo "%CD%\OGGFiles2"
pause 
)

for /D %%d in (*) do (
	 rem echo "%%d"
	 rem echo "OGGFiles"
	 rem echo %%d != OGGFiles
	
	if not %%d == OGGFiles (
		if not %%d == OGGFiles2 (
			cd %%d
			call :treeProcess
			cd ..
		)
	)
)
exit /b