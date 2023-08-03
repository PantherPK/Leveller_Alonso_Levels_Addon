package pk.panther.leveller.configurations;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public enum Config {
    MAIN("configuration.yml"),
    ANIMALS("mobs.yml"),
    BLOCKS("blocks.yml"),
    LANG("translation.yml");
    @Getter
    private final String filename;
    Config(String filename) {
        this.filename = filename;
    }
    public FileConfiguration getFile() {
        return new File(this.filename).getConfig();
    }
    public void saveDefaultFile() {
        new File(this.filename).saveDefaultConfig();
    }
}
