package skull.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Timer {
    private long time;

    public long mark() {
        long last = time;
        time = System.currentTimeMillis();
        return (time - last);
    }

    public void log() {
        log.info("Time since last mark: {}", mark());
    }
}
