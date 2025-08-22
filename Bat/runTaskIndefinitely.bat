@echo off

set task=%1


FOR /L %%N IN () DO call %task%

@REM :loop
@REM %task%
@REM goto loop
