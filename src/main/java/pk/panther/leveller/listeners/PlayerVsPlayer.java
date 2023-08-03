package pk.panther.leveller.listeners;

import com.alonsoaliaga.alonsolevels.api.AlonsoLevelsAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pk.panther.leveller.ActionMan;
import pk.panther.leveller.Utils;
import pk.panther.leveller.configurations.Config;

public class PlayerVsPlayer implements Listener {
    private final int expMin;
    private final int expMax;
    public PlayerVsPlayer() {
        String[] exp = Config.MAIN.getFile().getString("PlayerVsPlayer.exp", "10;20").split(";");
        this.expMin = Integer.parseInt(exp[0]);
        this.expMax = Integer.parseInt(exp[1]);
    }
    @EventHandler(ignoreCancelled = true)
    public void kill(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        Player victim = e.getEntity().getPlayer();
        if(killer == null || victim == null) return;
        int randomExp = Utils.getRandom(expMin, expMax + 1);
        if (AlonsoLevelsAPI.getExperience(victim.getUniqueId()) < randomExp) return;
        AlonsoLevelsAPI.addExperience(killer.getUniqueId(), randomExp);
        AlonsoLevelsAPI.removeExperience(victim.getUniqueId(), randomExp);
        ActionMan.send(killer, "player_vs_player_kill_killer", "{VICTIM};" + victim.getName(), "{EXP};" + randomExp);
        ActionMan.send(victim, "player_vs_player_kill_victim", "{KILLER};" + killer.getName(), "{EXP};" + randomExp);
    }
}
