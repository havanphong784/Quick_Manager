package com.quickmanager.debug;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class AppLogger {

    private static final Level DEFAULT_LEVEL = Level.ALL;
    private static boolean initialized = false;

    private AppLogger() {
    }

    public static Logger getLogger(Class<?> clazz) {
        initRootLogger();
        return Logger.getLogger(clazz.getName());
    }


    private static synchronized void initRootLogger() {
        if (initialized) return;
        initialized = true;

        Logger rootLogger = Logger.getLogger("com.quickmanager");
        rootLogger.setLevel(DEFAULT_LEVEL);

        rootLogger.setUseParentHandlers(false);

        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(DEFAULT_LEVEL);
        handler.setFormatter(new SimpleFormatter());
        rootLogger.addHandler(handler);
    }
}
