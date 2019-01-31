package subaraki.paintings.mod.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import subaraki.paintings.config.ConfigurationHandler;
import subaraki.paintings.mod.Paintings;
import subaraki.paintings.mod.PaintingsPattern;

public class PatternLoader {

	public PatternLoader() {
		MinecraftForge.EVENT_BUS.register(this);
		loadPatterns();
	}

	private static final String CLASS_LOC = "com.mcf.davidee.paintinggui.gui.PaintingButton";

	public void loadPatterns() {

		// Open a reader to the pattern
		ResourceLocation loc = new ResourceLocation("subaraki:patterns/" + ConfigurationHandler.instance.texture+".json");

		InputStream in = PatternLoader.class.getResourceAsStream("/assets/"+loc.getResourceDomain()+"/"+loc.getResourcePath());

		if(in == null)
		{
			Paintings.log.warn("Could not read painting data. paintings will be unusable");
			return;
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		// Read the JSON into a loader object
		Gson gson = new Gson();
		JsonElement je = gson.fromJson(reader, JsonElement.class);
		JsonObject json = je.getAsJsonObject();

		// Rebuild painting list in EnumArt
		PaintingsPattern.instance = gson.fromJson(json, PaintingsPattern.class);
		PaintingsPattern.instance.loadPatterns();

		// Replace texture used by Painting Gui Selection Revamped

		if(!ConfigurationHandler.instance.texture.equals("vanilla"))
			try {
				Class altClass = Class.forName(CLASS_LOC);
				paintingGuiTextureHelper(altClass, "TEXTURE", new ResourceLocation("subaraki:art/" + ConfigurationHandler.instance.texture + ".png"));
				paintingGuiHelper(altClass, "KZ_WIDTH", PaintingsPattern.instance.getSize().width * 16);
				paintingGuiHelper(altClass, "KZ_HEIGHT", PaintingsPattern.instance.getSize().height * 16);
			} catch (Exception e) {
				Paintings.log.warn(e.getLocalizedMessage());
			}
	}

	private void paintingGuiHelper(Class c, String field, int value)
			throws Exception {
		Field f = c.getField(field);
		f.setAccessible(true);
		f.set(null, value);
	}

	private void paintingGuiTextureHelper(Class c, String field, ResourceLocation loc)
			throws Exception {
		Field f = c.getField(field);
		f.setAccessible(true);
		f.set(null, loc);
	}
}