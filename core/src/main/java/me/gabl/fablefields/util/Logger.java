package me.gabl.fablefields.util;

import me.gabl.fablefields.util.log.LogPublisher;
import me.gabl.fablefields.util.log.SeverityLevel;

public class Logger {

    private final LogPublisher publisher;
    private final String senderName;

    public Logger(LogPublisher publisher, String senderName) {
        this.publisher = publisher;
        this.senderName = senderName;
    }

    public void info(String message, Object ... objects) {
        publisher.info(senderName, message, objects);
    }

    public void error(String message, Object ... objects) {
        publisher.error(senderName, message, objects);
    }

    public void debug(String message, Object ... objects) {
        publisher.debug(senderName, message, objects);
    }

    public void log(String message, SeverityLevel level, Object ... objects) {
        publisher.publish(senderName, message, level, objects);
    }

    public static Logger get(Class<?> sender) {
        return new Logger(GdxLogPublisherPublisher.centralPublisher, sender.getCanonicalName());
    }

    public static Logger get() {
        return new Logger(GdxLogPublisherPublisher.centralPublisher, "GdxLogger");
    }
}
