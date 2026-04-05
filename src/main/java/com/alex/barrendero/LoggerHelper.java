package com.alex.barrendero;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerHelper {
    private final Logger logger;

    public LoggerHelper(JavaPlugin plugin) {
        this.logger = plugin.getLogger();
    }

    public void info(String msg) { logger.info(msg); }
    public void warn(String msg) { logger.warning(msg); }
    public void error(String msg, Throwable t) { logger.log(Level.SEVERE, msg, t); }
}
