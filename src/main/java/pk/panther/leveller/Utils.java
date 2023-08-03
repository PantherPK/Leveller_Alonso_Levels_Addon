package pk.panther.leveller;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import pk.panther.leveller.configurations.Config;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Utils {
    public static FileConfiguration pl = Config.MAIN.getFile();
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();
    public static String chatprx = pl.getString("Chat-Prefix");
    public static String fixa(String t) {
        return ChatColor.translateAlternateColorCodes('&', t);
    }
    public static void sendMessage(Player p, String t) {
        p.sendMessage(fixa(chatprx + t));
    }
    public static void sendBar(Player p, String t) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(fixa(t)));
    }
    public static void sendTitle(Player p, String t, String ts) {
        p.sendTitle(fixa(t), fixa(ts), 10, 20, 10);
    }
    public static Integer getRandom(Integer size1, Integer size2) {
        return random.nextInt(size1, size2);
    }
    public static String formatter(String text, String... PlaceholderAndValue) {
        if(PlaceholderAndValue.length == 0) return text;
        return Arrays.stream(PlaceholderAndValue).reduce(text, (str, pav) -> {String[] PAV = pav.split(";");return str.replace(PAV[0], PAV[1]);});
    }
    public static Set<String> formatter(Set<String> actions, String... PlaceholderAndValue) {
        if(PlaceholderAndValue.length == 0) return actions;
        return actions.stream().map(action -> formatter(action, PlaceholderAndValue)).collect(Collectors.toSet());
    }
    public static List<String> formatter(List<String> actions, String... PlaceholderAndValue) {
        if(PlaceholderAndValue.length == 0) return actions;
        return actions.stream().map(action -> formatter(action, PlaceholderAndValue)).collect(Collectors.toList());
    }
}
