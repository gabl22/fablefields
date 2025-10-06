package me.gabl.fablefields.util;

import me.gabl.common.log.Logger;

public class GdxLogger {

    public static Logger get(Class<?> sender) {
        return new Logger(sender.getCanonicalName(), GdxLoggerPublisher.centralPublisher);
    }

    public static Logger get() {
        return new Logger("GdxLogger", GdxLoggerPublisher.centralPublisher);
    }
}
