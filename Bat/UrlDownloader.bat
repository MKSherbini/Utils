@REM credits: Mohammad khaled ElSherbini
@REM usage: UrlDownloader.bat "url"
@REM example: UrlDownloader.bat "http://localhost:7780/til/fileBrowse.jsp?file=/T3_EMS/Jan2022/31/13.CB1-log"
@REM output: file in director with name CB1-log.html

@echo off
set url=%1

for /F "tokens=3 delims=. " %%a in (%url%) do (
    echo downloading %%a ....
    curl -o %%a.html %url%
)