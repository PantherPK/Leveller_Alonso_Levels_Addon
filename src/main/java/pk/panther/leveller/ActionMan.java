package pk.panther.leveller;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ActionMan {
    @Getter
    private static final Map<String, Set<String>> actions = new HashMap<>();
    public static void loadActions(FileConfiguration main) {
        main.getConfigurationSection("actions").getKeys(false).forEach(key -> {
            List<String> action = main.getStringList("actions." + key);
            if(!action.isEmpty()) actions.put(key, new HashSet<>(action));
        });
    }
    public static void send(Player p, String identifier, String... PlaceholderAndValue) {
        CompletableFuture.runAsync(() -> {
            Set<String> action = actions.get(identifier);
            if(action == null || action.isEmpty()) return;
            if(PlaceholderAndValue.length >= 1) Action.executeActions(p, Utils.formatter(action, PlaceholderAndValue)); else Action.executeActions(p, action);
        });
    }
}