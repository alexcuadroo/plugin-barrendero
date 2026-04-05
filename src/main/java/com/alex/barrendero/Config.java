package com.alex.barrendero;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Config {
    private final int intervalSeconds;
    private final int ageSeconds;
    private final double radius;
    private final List<String> worlds;

    private final boolean messagesEnabled;
    private final int warnBeforeSeconds;
    private final String prefix;
    private final String cleanupMessage;
    private final String warningMessage;

    public Config(FileConfiguration cfg) {
        this.intervalSeconds = cfg.getInt("interval-seconds", 60);
        this.ageSeconds = cfg.getInt("age-seconds", 30);
        this.radius = cfg.getDouble("radius", 0.0);
        this.worlds = cfg.getStringList("worlds");

        this.messagesEnabled = cfg.getBoolean("messages.enabled", true);
        this.warnBeforeSeconds = cfg.getInt("messages.warn-before-seconds", 30);
        this.prefix = cfg.getString("messages.prefix", "[PYTHECUS]");
        this.cleanupMessage = cfg.getString("messages.cleanup-message",
                "&a{prefix} &fEliminados &e{removed} &fitems del suelo.");
        this.warningMessage = cfg.getString("messages.warning-message",
                "&6{prefix} &eEn &c{seconds} &esegundos se realizará una limpieza del suelo.");
    }

    public int getIntervalSeconds() { return intervalSeconds; }
    public int getAgeSeconds() { return ageSeconds; }
    public double getRadius() { return radius; }
    public boolean isWorldAllowed(String name) {
        return worlds.isEmpty() || worlds.contains(name);
    }

    public boolean isMessagesEnabled() { return messagesEnabled; }
    public int getWarnBeforeSeconds() { return warnBeforeSeconds; }
    public String getPrefix() { return prefix; }
    public String getCleanupMessage() { return cleanupMessage; }
    public String getWarningMessage() { return warningMessage; }
}
