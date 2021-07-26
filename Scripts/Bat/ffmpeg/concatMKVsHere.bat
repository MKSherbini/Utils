for %%f in (*.mkv) do (
    echo file %%f >> list.txt
)
ffmpeg -f concat -safe 0 -i list.txt -c copy output.mkv
del list.txt