package com.alex.barrendero;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class CleanupManager {
    private final JavaPlugin plugin;
    private final LoggerHelper logger;
    private Config config;
    private BukkitTask task;

    public CleanupManager(JavaPlugin plugin, Config config, LoggerHelper logger) {
        this.plugin = plugin;
        this.config = config;
        this.logger = logger;
    }

    public void start() {
        stop();
        long intervalTicks = Math.max(1, config.getIntervalSeconds()) * 20L;
        task = plugin.getServer().getScheduler().runTaskTimer(plugin, this::runCleanup, intervalTicks, intervalTicks);
        logger.info("Tarea de limpieza programada cada " + config.getIntervalSeconds() + "s.");
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    public void reloadConfig(Config config) {
        this.config = config;
        start();
    }

    private void runCleanup() {
        int removed = 0;
        for (World world : plugin.getServer().getWorlds()) {
            if (!config.isWorldAllowed(world.getName())) continue;
            List<Entity> entities = world.getEntities();
            for (Entity e : entities) {
                if (e instanceof Item item) {
                    int ticksLived = item.getTicksLived();
                    if (ticksLived >= config.getAgeSeconds() * 20L) {
                        item.remove();
                        removed++;
                    }
                }
            }
        }
        if (removed > 0) logger.info("Eliminados " + removed + " items del suelo.");
    }
}
