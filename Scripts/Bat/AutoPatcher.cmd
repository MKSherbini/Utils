@echo off
pushd %~dp0
for /f %%A in ('dir /b *.exe') do (
echo == Installing Updates == "%%A" ...
Start /wait %%A /passive /norestart
)
echo.
echo ########################################
echo.
