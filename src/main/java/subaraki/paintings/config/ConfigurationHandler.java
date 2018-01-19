package subaraki.paintings.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Level;
import subaraki.paintings.mod.Paintings;
import subaraki.paintings.mod.PaintingsPattern;
import subaraki.paintings.mod.PaintingsUtilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static subaraki.paintings.mod.Paintings.MODID;

public class ConfigurationHandler {
    private static ConfigurationHandler instance = new ConfigurationHandler();
    private File configurationFile;
    private File patternsDirectory;
    private String patternSource;
    private PaintingsPattern pattern;
    public String configuredPatternName;
    public String activePatternName;

    private static String assetsRoot = "/assets/subaraki";

    public ConfigurationHandler() {
        this.configuredPatternName = "gibea";
    }

    // Called directly by main Paintings class, no need to make it an event listener
    public void preInit(FMLPreInitializationEvent event) {
        patternsDirectory = new File(event.getModConfigurationDirectory(), MODID + "/patterns");
        configurationFile = event.getSuggestedConfigurationFile();
        loadConfig();
    }

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

        this.configuredPatternName = config.getString("name", "Painting Mode", "gibea", "Texture").toLowerCase();
    }

    /**
     * Generates the pattern field from the patternSource field.
     */
    private void buildPattern() {
        Gson gson = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
        JsonElement element = gson.fromJson(this.patternSource, JsonElement.class);
        JsonObject json = element.getAsJsonObject();

        this.pattern = gson.fromJson(json, PaintingsPattern.class);

        PaintingsUtilities.ignoreVanillaPaintings();
        this.pattern.parse();
        PaintingsUtilities.debugDumpEnumArt(Level.DEBUG);
    }

    /**
     * Loads the source named under activePatternName.
     */
    public void loadPatternSource(Side side) {

        this.activePatternName = configuredPatternName;
        File activePatternFile = new File(patternsDirectory, activePatternName + ".json");

        try {
            InputStream inputStream;

            if (activePatternFile.exists()) {

                // A pattern file for the specified texture exists in the config/morepaintings/patterns folder
                inputStream = new FileInputStream(activePatternFile);

            } else {

                if (side.isServer()) {

                    // Running on a server, check for pattern in the mod's JAR file
                    inputStream = ConfigurationHandler.class.getResourceAsStream("/assets/subaraki/patterns/" + this.activePatternName + ".json");

                } else {

                    // Single player session, find the file as a resource
                    ResourceLocation location = new ResourceLocation("subaraki:patterns/" + activePatternName + ".json");
                    IResourceManager resourceManager = FMLClientHandler.instance().getClient().getResourceManager();
                    IResource resource = resourceManager.getResource(location);
                    inputStream = resource.getInputStream();

                }

            }

            if (inputStream != null) {
                this.patternSource = IOUtils.toString(inputStream, Charset.defaultCharset());
                this.buildPattern();
            }

        } catch (IOException e) {
            Paintings.log.error(e.getMessage());
        }
    }

    /**
     * This is meant to be called on the client after having received the pattern from the server.
     *
     * @param patternSource
     */
    public void setPattern(String patternSource) {

        // Only rebuild the pattern if the source is different. They will be the same is Single Player mode or (usually) when the client is reconnecing to the server in the same session.
        if (!patternSource.equals(this.patternSource)) {
            this.patternSource = patternSource;
            this.buildPattern();
            this.activePatternName = this.pattern.getName();
        } else {
            Paintings.log.info("Client skipped loading duplicate pattern.");
        }

    }

    public static ConfigurationHandler getInstance() {
        return instance;
    }

    public PaintingsPattern getPattern() {
        return this.pattern;
    }

    public String getPatternSource() {
        return this.patternSource;
    }
}
