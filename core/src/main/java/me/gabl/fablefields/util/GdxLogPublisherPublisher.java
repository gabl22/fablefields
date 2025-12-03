package me.gabl.fablefields.util;

import com.badlogic.gdx.Gdx;
import me.gabl.fablefields.util.log.LogPublisher;
import me.gabl.fablefields.util.log.SeverityLevel;

public class GdxLogPublisherPublisher implements LogPublisher {

    public static final LogPublisher centralPublisher = new GdxLogPublisherPublisher();

    @Override
    public void publish(String sender, String message, SeverityLevel severityLevel, Object... objects) {
        Throwable throwable = null;
        StringBuilder mb = new StringBuilder(message);
        if (objects != null && objects.length > 0) {
            mb.append("\n");
            for (Object object : objects) {
                if (object instanceof Throwable && throwable == null) {
                    throwable = (Throwable) object;
                } else {
                    mb.append(object.toString()).append("\n");
                }
            }
        }

        String finalMessage = mb.toString();

        switch (severityLevel) {
            case INFO:
                if (throwable != null) {
                    Gdx.app.log(sender, finalMessage, throwable);
                } else {
                    Gdx.app.log(sender, finalMessage);
                }
                return;
            case DEBUG:
                if (throwable != null) {
                    Gdx.app.debug(sender, finalMessage, throwable);
                } else {
                    Gdx.app.debug(sender, finalMessage);
                }
            default:
                if (throwable != null) {
                    Gdx.app.error(sender, finalMessage, throwable);
                } else {
                    Gdx.app.error(sender, finalMessage);
                }
                return;
        }
    }
}
