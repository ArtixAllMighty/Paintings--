package subaraki.paintings.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {
    public static ConfigurationHandler instance = new ConfigurationHandler();

    public String texture = "gibea";

    public void loadConfig(File file) {
        Configuration config = new Configuration(file);
        config.load();
        loadSettings(config);
    }

    private void loadSettings(Configuration config) {
        config.addCustomCategoryComment("Painting Mode", "The layout pattern of your paintings texture. Built-in patterns include gibea, sphax, insane, tinypics, mediumpics, new_insane, and massive. See the online documentation for information on custom templates.");

        this.texture = config.getString("name", "Painting Mode", "gibea", "Texture").toLowerCase();
    }
}
