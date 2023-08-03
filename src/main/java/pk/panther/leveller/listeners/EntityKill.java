package pk.panther.leveller.listeners;

import com.alonsoaliaga.alonsolevels.api.AlonsoLevelsAPI;
import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import pk.panther.leveller.ActionMan;
import pk.panther.leveller.Utils;
import pk.panther.leveller.configurations.Config;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityKill implements Listener {
    private final Set<Mob> mobs;
    private final FileConfiguration animal;
    private final FileConfiguration lang;
    public EntityKill() {
        this.mobs = Config.ANIMALS.getFile().getConfigurationSection("Mobs").getKeys(false).stream().map(Mob::new).collect(Collectors.toSet());
        this.animal = Config.ANIMALS.getFile();
        this.lang = Config.LANG.getFile();
    }

    @EventHandler(ignoreCancelled = true)
    public void onMobKill(EntityDeathEvent e) {
        Entity killed = e.getEntity();
        Player killer = e.getEntity().getKiller();
        if(killer == null) return;
        if(!animal.getBoolean("Mobs.Exp-With-Creative-Gm") && killer.getGameMode().equals(GameMode.CREATIVE)) return;
        Optional<Mob> mob = mobs.stream().filter(m -> m.getEntity().contains(killed.getType())).findAny();
        if(mob.isEmpty()) return;
        int randomexp = Utils.getRandom(mob.get().getExpMin(), mob.get().getExpMax() + 1);
        AlonsoLevelsAPI.addExperience(killer.getUniqueId(), randomexp);
        ActionMan.send(killer, "killed_mob_and_got_exp", "{MOB};" + lang.getString("Mobs." + killed.getType().name().toUpperCase(), killed.getType().name()), "{EXP};" + randomexp);
    }

    @Getter
    static class Mob {
        private final Set<EntityType> entity = new HashSet<>();
        private final Integer expMin;
        private final Integer expMax;
        public Mob(String id) {
            String pt = "Mobs.";
            FileConfiguration pl = Config.ANIMALS.getFile();
            pl.getStringList(pt + id + ".entities").forEach(s -> entity.add(EntityType.valueOf(s)));
            expMax = pl.getInt(pt + id + ".exp.max");
            expMin = pl.getInt(pt + id + ".exp.min");
        }
    }
}
