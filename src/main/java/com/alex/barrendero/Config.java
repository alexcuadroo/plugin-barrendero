package com.alex.barrendero;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Config {
    private final int intervalSeconds;
    private final int ageSeconds;
    private final double radius;
    private final List<String> worlds;

    public Config(FileConfiguration cfg) {
        this.intervalSeconds = cfg.getInt("interval-seconds", 60);
        this.ageSeconds = cfg.getInt("age-seconds", 30);
        this.radius = cfg.getDouble("radius", 0.0);
        this.worlds = cfg.getStringList("worlds");
    }

    public int getIntervalSeconds() { return intervalSeconds; }
    public int getAgeSeconds() { return ageSeconds; }
    public double getRadius() { return radius; }
    public boolean isWorldAllowed(String name) {
        return worlds.isEmpty() || worlds.contains(name);
    }
}
