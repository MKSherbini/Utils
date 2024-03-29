package skull.shopping.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Timer {
    private long time;

    public Timer() {
        mark();
    }

    public long mark() {
        long last = time;
        time = System.currentTimeMillis();
        return (time - last);
    }

    public void log() {
        log.info("Time since last mark: {}", mark());
    }

    public void log(String id) {
        log.info("Time since last mark for {}: {}", id, mark());
    }
}
