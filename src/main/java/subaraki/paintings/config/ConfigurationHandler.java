package subaraki.paintings.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler {
    public static ConfigurationHandler instance = new ConfigurationHandler();
    public File configurationFile = null;
    public File patternsDirectory = null;

    public String texture = "gibea";

    public void loadConfig() {
        Configuration config = new Configuration(configurationFile);
        config.load();
        loadSettings(config);
        config.save();

        // Create patterns directory if it doesn't exist
        if (!patternsDirectory.exists()) {
            patternsDirectory.mkdirs();
        }
    }

    private void loadSettings(Configuration config) {
        config.addCustomCategoryComment("Painting Mode", "The layout pattern of your paintings texture. Built-in patterns include gibea, sphax, insane, tinypics, mediumpics, new_insane, and massive. See the online documentation for information on custom templates.");

        this.texture = config.getString("name", "Painting Mode", "gibea", "Texture").toLowerCase();
    }
}
