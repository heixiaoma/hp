package net.hserver.hp.client.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.LongAdder;

public class NamedThreadFactory implements ThreadFactory {
    private final String prefix;
    private final LongAdder threadNumber = new LongAdder();

    public NamedThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    public Thread newThread(Runnable runnable) {
        this.threadNumber.add(1L);
        return new Thread(runnable, this.prefix + "@" + this.threadNumber.intValue());
    }
}
