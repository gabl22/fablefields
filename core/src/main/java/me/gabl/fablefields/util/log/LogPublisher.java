package me.gabl.fablefields.util.log;

public interface LogPublisher {

    default void info(String sender, String message, Object ... objects) {
        publish(sender, message, SeverityLevel.INFO, objects);
    }

    default void error(String sender, String message, Object ... objects) {
        publish(sender, message, SeverityLevel.ERROR, objects);
    }

    default void debug(String sender, String message, Object ... objects) {
        publish(sender, message, SeverityLevel.DEBUG, objects);
    }

    void publish(String sender, String message, SeverityLevel level, Object... objects);
}
