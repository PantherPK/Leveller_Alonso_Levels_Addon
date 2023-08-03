package pk.panther.leveller.listeners;

import com.alonsoaliaga.alonsolevels.api.AlonsoLevelsAPI;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pk.panther.leveller.ActionMan;
import pk.panther.leveller.Utils;
import pk.panther.leveller.configurations.Config;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BlockBreak implements Listener {
    private final Set<Block> blocks;
    private final FileConfiguration bl;
    private final FileConfiguration lang;
    public BlockBreak() {
        this.bl = Config.BLOCKS.getFile();
        this.lang = Config.LANG.getFile();
        this.blocks = this.bl.getConfigurationSection("Blocks").getKeys(false).stream().map(Block::new).collect(Collectors.toSet());
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        if(!bl.getBoolean("Blocks.Exp-With-Creative-Gm") && e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        Optional<Block> block = blocks.stream().filter(b -> b.material.contains(e.getBlock().getType())).findAny();
        if(block.isEmpty()) return;
        int randomexp = Utils.getRandom(block.get().expMin, block.get().expMax + 1);
        AlonsoLevelsAPI.addExperience(e.getPlayer().getUniqueId(), randomexp);
        ActionMan.send(e.getPlayer(), "block_break_and_got_exp", "{EXP};" + randomexp,"{BLOCK};" + lang.getString("Blocks." + e.getBlock().getType().name().toUpperCase(), e.getBlock().getType().name()));
    }
    static class Block {
        private final Set<Material> material;
        private final int expMin;
        private final int expMax;
        public Block(String id) {
            String pt = "Blocks.";
            FileConfiguration pl = Config.BLOCKS.getFile();
            this.material = pl.getStringList(pt + id + ".materials").stream().map(Material::valueOf).collect(Collectors.toSet());
            expMax = pl.getInt(pt + id + ".exp.max");
            expMin = pl.getInt(pt + id + ".exp.min");
        }
    }
}
