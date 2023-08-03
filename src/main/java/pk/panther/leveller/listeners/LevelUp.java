package pk.panther.leveller.listeners;

import com.alonsoaliaga.alonsolevels.api.AlonsoLevelsAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class LevelUp implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void minecraftUp(PlayerExpChangeEvent e) {
        AlonsoLevelsAPI.addExperience(e.getPlayer().getUniqueId(), e.getAmount());
    }
}
