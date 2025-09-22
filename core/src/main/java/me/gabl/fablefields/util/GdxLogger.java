package me.gabl.fablefields.util;

import me.gabl.common.log.Logger;

public class GdxLogger {

    public static <T> Logger get(Class<T> sender) {
        return new Logger(sender.getCanonicalName(), GdxLoggerPublisher.centralPublisher);
    }
}
