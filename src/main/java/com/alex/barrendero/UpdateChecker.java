package com.alex.barrendero;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class UpdateChecker {

    private static final String GITHUB_API_URL =
            "https://api.github.com/repos/alexcuadroo/plugin-barrendero/releases/latest";
    private static final String RELEASES_URL =
            "https://github.com/alexcuadroo/plugin-barrendero/releases/tag/";

    private final JavaPlugin plugin;
    private final LoggerHelper logger;

    public UpdateChecker(JavaPlugin plugin, LoggerHelper logger) {
        this.plugin = plugin;
        this.logger = logger;
    }

    public void checkForUpdates() {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URL url = URI.create(GITHUB_API_URL).toURL();
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/vnd.github+json");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                int responseCode = conn.getResponseCode();
                if (responseCode != 200) {
                    logger.warn("No se pudo verificar actualizaciones (HTTP " + responseCode + ").");
                    return;
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                conn.disconnect();

                String latestTag = parseTagName(response.toString());
                if (latestTag == null) {
                    logger.warn("No se pudo obtener la version mas reciente de GitHub.");
                    return;
                }

                String latestVersion = latestTag.startsWith("v.") ? latestTag.substring(2) : latestTag;
                String currentVersion = plugin.getPluginMeta().getVersion();

                if (!currentVersion.equals(latestVersion)) {
                    logger.info("========================================");
                    logger.info("¡Nueva version disponible! " + currentVersion + " -> " + latestVersion);
                    logger.info("Descargala en: " + RELEASES_URL + latestTag);
                    logger.info("========================================");
                }

            } catch (Exception e) {
                logger.warn("Error al verificar actualizaciones: " + e.getMessage());
            }
        });
    }

    private String parseTagName(String json) {
        String key = "\"tag_name\"";
        int index = json.indexOf(key);
        if (index == -1) return null;
        int colonIndex = json.indexOf(':', index + key.length());
        if (colonIndex == -1) return null;
        int firstQuote = json.indexOf('"', colonIndex + 1);
        if (firstQuote == -1) return null;
        int secondQuote = json.indexOf('"', firstQuote + 1);
        if (secondQuote == -1) return null;
        return json.substring(firstQuote + 1, secondQuote);
    }
}
