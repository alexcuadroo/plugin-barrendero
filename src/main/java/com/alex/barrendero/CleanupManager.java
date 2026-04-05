package com.alex.barrendero;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class CleanupManager {
    private static final MiniMessage MINI = MiniMessage.miniMessage();

    private final JavaPlugin plugin;
    private final LoggerHelper logger;
    private Config config;
    private BukkitTask task;
    private BukkitTask warningTask;

    public CleanupManager(JavaPlugin plugin, Config config, LoggerHelper logger) {
        this.plugin = plugin;
        this.config = config;
        this.logger = logger;
    }

    public void start() {
        stop();
        long intervalTicks = Math.max(1, config.getIntervalSeconds()) * 20L;
        task = plugin.getServer().getScheduler().runTaskTimer(plugin, this::runCleanup, intervalTicks, intervalTicks);

        if (config.isMessagesEnabled() && config.getWarnBeforeSeconds() > 0
                && config.getWarnBeforeSeconds() < config.getIntervalSeconds()) {
            long warnDelayTicks = intervalTicks - (config.getWarnBeforeSeconds() * 20L);
            warningTask = plugin.getServer().getScheduler().runTaskTimer(plugin, this::sendWarning, warnDelayTicks, intervalTicks);
        }

        logger.info("Tarea de limpieza programada cada " + config.getIntervalSeconds() + "s.");
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (warningTask != null) {
            warningTask.cancel();
            warningTask = null;
        }
    }

    public void reloadConfig(Config config) {
        this.config = config;
        start();
    }

    private void sendWarning() {
        if (!config.isMessagesEnabled()) return;
        String raw = config.getWarningMessage()
                .replace("{prefix}", config.getPrefix())
                .replace("{seconds}", String.valueOf(config.getWarnBeforeSeconds()));
        Bukkit.broadcast(MINI.deserialize(raw));
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
        if (removed > 0) {
            logger.info("Eliminados " + removed + " items del suelo.");
            if (config.isMessagesEnabled()) {
                String raw = config.getCleanupMessage()
                        .replace("{prefix}", config.getPrefix())
                        .replace("{removed}", String.valueOf(removed));
                Bukkit.broadcast(MINI.deserialize(raw));
            }
        }
    }
}
