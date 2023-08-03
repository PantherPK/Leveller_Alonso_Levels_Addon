package pk.panther.leveller.configurations;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pk.panther.leveller.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public class File {

    private FileConfiguration config;
    private java.io.File configFile;
    private final String filename;
    public File(String filename) {
        this.filename = filename;
    }
    public void reloadConfig() {
        if (configFile == null) configFile = new java.io.File(Main.getInstance().getDataFolder(), filename);
        config = YamlConfiguration.loadConfiguration(configFile);
        InputStream defConfigStream = Main.getInstance().getResource(filename);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(configFile);
            config.setDefaults(defConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (config == null) reloadConfig();
        return config;
    }

    public void saveConfig() {
        if (config == null || configFile == null) return;
        try {
            getConfig().save(configFile);
        }
        catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
        }
    }

    public void saveDefaultConfig() {
        if (configFile == null) configFile = new java.io.File(Main.getInstance().getDataFolder(), filename);
        if (!configFile.exists()) Main.getInstance().saveResource(filename, false);
    }
}
