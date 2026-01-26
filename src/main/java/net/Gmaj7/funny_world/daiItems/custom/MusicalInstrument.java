package net.Gmaj7.funny_world.daiItems.custom;

import net.Gmaj7.funny_world.daiEffects.daiMobEffects;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class MusicalInstrument extends Item {
    protected final float baseRange = 12;
    protected final float baseDamage = 4;
    protected  boolean isBass = false;
    public MusicalInstrument(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        int note = level.random.nextInt(24) + 1;
        playSound(level, player, note);
        return super.use(level, player, usedHand);
    }

    public void playSound(Level level, Player player, int note){
        if (player.hasEffect(daiFunctions.getHolder(level, Registries.MOB_EFFECT, daiMobEffects.ECHO.getKey()))){
            dealEcho(level, player, note);
        }
    }

    public abstract void dealEcho(Level level, Player player, int note);

    protected float getRange(int note) {
        return isBass ? 2 * baseRange * (float) Math.pow(0.5, note / 12F) : baseRange * (float) Math.pow(0.5, note / 12F);
    }

    protected float getDamage(int note) {
        return isBass ? 0.5F * baseDamage * (float) Math.pow(2, note / 12F) : baseDamage * (float) Math.pow(2, note / 12F);
    }
}
