package me.gabl.fablefields.util;

import com.badlogic.gdx.Gdx;
import me.gabl.common.log.Publisher;
import me.gabl.common.log.SeverityLevel;

public class GdxLoggerPublisher extends Publisher {

    public static final Publisher centralPublisher = new GdxLoggerPublisher();

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

        if (severityLevel.getLevel() >= SeverityLevel.ERROR.getLevel()) {
            if (throwable != null) {
                Gdx.app.error(sender, finalMessage, throwable);
            } else {
                Gdx.app.error(sender, finalMessage);
            }
            return;
        }

        if (severityLevel.getLevel() >= SeverityLevel.INFO.getLevel()) {
            if (throwable != null) {
                Gdx.app.log(sender, finalMessage, throwable);
            } else {
                Gdx.app.log(sender, finalMessage);
            }
            return;
        }

        if (severityLevel.getLevel() >= SeverityLevel.TRACE.getLevel()) {
            if (throwable != null) {
                Gdx.app.debug(sender, finalMessage, throwable);
            } else {
                Gdx.app.debug(sender, finalMessage);
            }
        }
    }
}
