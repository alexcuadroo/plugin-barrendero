package com.alex.barrendero;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public class BarrenderoPlugin extends JavaPlugin {
    private CleanupManager cleanupManager;
    private LoggerHelper logger;
    private Config config;

    @Override
    public void onEnable() {
        this.logger = new LoggerHelper(this);
        saveDefaultConfig();
        this.config = new Config(getConfig());
        this.cleanupManager = new CleanupManager(this, config, logger);
        this.cleanupManager.start();
        logger.info("Barrendero habilitado.");
        new UpdateChecker(this, logger).checkForUpdates();
    }

    @Override
    public void onDisable() {
        if (cleanupManager != null) cleanupManager.stop();
        if (logger != null) logger.info("Barrendero deshabilitado.");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("barrendero") && args.length == 1) {
            return List.of("reload");
        }
        return Collections.emptyList();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("barrendero")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                reloadConfig();
                this.config = new Config(getConfig());
                this.cleanupManager.reloadConfig(this.config);
                sender.sendMessage(Component.text("[Barrendero] Configuración recargada."));
                return true;
            }
            sender.sendMessage(Component.text("Uso: /barrendero reload"));
            return true;
        }
        return false;
    }
}
