for %%f in (*.bnk) do "D:\Tracks\GameDev\3D Ripping\league\bnkextr-master\bnkextr-master\bnkextr.exe" %%f
for %%f in (*.wem) do "D:\Tracks\GameDev\3D Ripping\league\ww2ogg022\ww2ogg.exe" %%f --pcb "D:\Tracks\GameDev\3D Ripping\league\ww2ogg022\packed_codebooks_aoTuV_603.bin"
for %%f in (*.ogg) do "D:\Tracks\GameDev\3D Ripping\league\ww2ogg022\revorb.exe" %%f
del (*.wem)
