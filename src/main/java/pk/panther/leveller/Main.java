package pk.panther.leveller;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pk.panther.leveller.configurations.Config;
import pk.panther.leveller.listeners.BlockBreak;
import pk.panther.leveller.listeners.EntityKill;
import pk.panther.leveller.listeners.LevelUp;
import pk.panther.leveller.listeners.PlayerVsPlayer;

import java.util.Arrays;
import java.util.List;

public final class Main extends JavaPlugin {
    @Getter
    private static Main instance;
    @Override
    public void onEnable() {
        instance = this;
        if(!Bukkit.getPluginManager().isPluginEnabled("AlonsoLevels")) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        Arrays.stream(Config.values()).forEach(Config::saveDefaultFile);
        ActionMan.loadActions(Config.MAIN.getFile());
        List.of(new LevelUp(), new BlockBreak(), new PlayerVsPlayer(), new EntityKill()).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }
}
