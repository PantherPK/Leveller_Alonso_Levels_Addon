package pk.panther.leveller;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import pk.panther.leveller.configurations.Config;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public enum Action {
    SOUND("[sound]") {
        @Override
        public void execute(Player player, String action) {
            String[] sound = action.split(" ");
            player.playSound(player.getLocation(), Sound.valueOf(sound[1]), Float.parseFloat(sound[2]), Float.parseFloat(sound[3]));
        }
    },
    MESSAGE_LIST("[message-list]") {
        @Override
        public void execute(Player player, String action) {
            List<String> toSend = Config.MAIN.getFile().getStringList("actions-mess-lists." + action.split(" ")[1]);
            if(toSend.isEmpty()) return;
            for(String send : toSend) Utils.sendMessage(player, send);
        }
    },
    MESSAGE("[message]") {
        @Override
        public void execute(Player player, String action) {
            Utils.sendMessage(player, action.substring(this.getPrefix().length()));
        }
    },
    CONSOLE("[console]") {
        @Override
        public void execute(Player player, String action) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), action.substring(this.getPrefix().length()));
        }
    },
    PLAYER("[player]") {
        @Override
        public void execute(Player player, String action) {
            Bukkit.getServer().dispatchCommand(player, action.substring(this.getPrefix().length()));
        }
    },
    TITLE("[title]") {
        @Override
        public void execute(Player player, String action) {
            String[] title = action.substring(8).split(";");
            Utils.sendTitle(player, title[0], title[1]);
        }
    },
    ACTIONBAR("[actionbar]") {
        @Override
        public void execute(Player player, String action) {
            Utils.sendBar(player, action.substring(this.getPrefix().length()));
        }
    };

    private final String prefix;

    Action(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public abstract void execute(Player player, String action);

    public static void executeAction(Player player, String action) {
        Arrays.stream(Action.values()).filter(actionType -> action.startsWith(actionType.getPrefix())).forEach(actionType -> actionType.execute(player, action));
    }

    public static void executeActions(Player player, Set<String> actions, String... PlaceholderAndValue) {
        if(actions.isEmpty()) return;
        actions.forEach(action -> Action.executeAction(player, Utils.formatter(action, PlaceholderAndValue)));
    }
}

