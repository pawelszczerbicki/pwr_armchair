package pl.wroc.pwr.armchair.logger;

import pl.wroc.pwr.armchair.ws.AtmosphereService;
import pl.wroc.pwr.armchair.ws.Message;
import pl.wroc.pwr.armchair.ws.MessageType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static pl.wroc.pwr.armchair.logger.Type.*;

/**
 * Created by Pawel on 02.01.14.
 */
public class Logger {

    private static Map<Class, Logger> loggers = new ConcurrentHashMap<Class, Logger>();
    private Class clazz;
    private AtmosphereService atmosphereService;

    private Logger() {
    }

    private Logger(Class clazz, AtmosphereService atmosphereService) {
        this.atmosphereService = atmosphereService;
        this.clazz = clazz;
    }

    public static Logger getInstance(Class clazz) {
        return getInstance(clazz, null);
    }

    public static Logger getInstance(Class clazz, AtmosphereService atm) {
        loggers.put(clazz, new Logger(clazz, atm));
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
        String log = String.format("[%s] [%s] %s", clazz.getName(), t, m);
        System.out.println(log);
        sendMessage(log);
    }

    private void sendMessage(String m) {
        if (atmosphereService != null)
            atmosphereService.send(new Message(MessageType.LOG, m));
    }
}
