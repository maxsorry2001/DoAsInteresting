package net.Gmaj7.funny_world.daiGui.hud;

import net.Gmaj7.funny_world.daiInit.daiUniqueData.daiUniqueDataGet;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ShowHumanityHud implements LayeredDraw.Layer {
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Player player = Minecraft.getInstance().player;
        if(!player.getMainHandItem().is(daiItems.HUMANITY_POCKET.get()) && !player.getOffhandItem().is(daiItems.HUMANITY_POCKET.get())) return;
        int humanity = ((daiUniqueDataGet)player).getHumanitySet().getHumanity();
        var screenWidth = guiGraphics.guiWidth();
        var screenHeight = guiGraphics.guiHeight();
        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("humanity").append(String.valueOf(humanity)), screenWidth / 2 - 91, screenHeight * 25 / 32, 0xFFFFFF);
    }
}
