package pl.wroc.pwr.armchair.logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static pl.wroc.pwr.armchair.logger.Type.ERROR;
import static pl.wroc.pwr.armchair.logger.Type.INFO;
import static pl.wroc.pwr.armchair.logger.Type.WARNING;

/**
 * Created by Pawel on 02.01.14.
 */
public class Logger {

    private static Map<Class, Logger> loggers = new ConcurrentHashMap<Class, Logger>();
    private Class clazz;

    private Logger() {
    }

    private Logger(Class clazz) {
        this.clazz = clazz;
    }

    public static Logger getInstance(Class clazz) {
        loggers.put(clazz, new Logger(clazz));
        return loggers.get(clazz);
    }

    public void error(String m) {
        print(m, ERROR);
    }

    public void warning(String m) {
        print(m, WARNING);
    }

    public void info(String m) {
        print(m, INFO);
    }

    private void print(String m, Type t) {
        System.out.println(String.format("[%s] [%s] %s", clazz.getName(), t, m));
        sendMessage();
    }

    private static void sendMessage() {
        //TODO Implement method sending log to server
    }
}
