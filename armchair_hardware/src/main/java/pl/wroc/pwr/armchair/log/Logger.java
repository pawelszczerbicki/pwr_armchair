package pl.wroc.pwr.armchair.log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pawel on 02.01.14.
 */
public class Logger {
    private static Map<Class, Logger> loggers = new HashMap<>();
    private Class clazz;

    private Logger() {
    }

    private Logger(Class clazz) {
        this.clazz = clazz;
    }

    public static Logger getInstance(Class clazz) {
        if (loggers.containsKey(clazz))
            return loggers.get(clazz);
        loggers.put(clazz, new Logger(clazz));
        return loggers.get(clazz);
    }

    public void error(String m) {
        print(m, LogType.ERROR);
    }

    public void warning(String m) {
        print(m, LogType.WARNING);
    }

    public void info(String m) {
        print(m, LogType.INFO);
    }

    private void print(String m, LogType t) {
        System.out.println(String.format("[%s] [%s] %s",clazz.getName(), t, m));
        sendMessage();
    }

    private static void sendMessage() {
        //TODO Implement method sending log to server
    }
}
