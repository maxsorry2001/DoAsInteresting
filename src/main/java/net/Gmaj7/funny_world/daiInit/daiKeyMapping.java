package net.Gmaj7.funny_world.daiInit;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class daiKeyMapping {
    public static final String KEY_CATEGORY_DAI = "key.category.funny_world.dai";
    public static final String KEY_SWITCH_WATER_BOW = "key.funny_world.switch_water_bow";

    public static final KeyMapping SELECT_WATER_BOW = new KeyMapping(KEY_SWITCH_WATER_BOW, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY_DAI);
}
