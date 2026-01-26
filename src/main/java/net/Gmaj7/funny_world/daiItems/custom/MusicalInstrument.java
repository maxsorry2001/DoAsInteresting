package net.Gmaj7.funny_world.daiItems.custom;

import net.Gmaj7.funny_world.daiEffects.daiMobEffects;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NoteBlock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicalInstrument extends Item {
    protected final float baseRange = 8;
    protected final float baseDamage = 4;
    protected Holder.Reference<SoundEvent> instrument;
    private static final Map<Holder.Reference<SoundEvent>, Float> OCTAVE_SHIFT = new HashMap<>();

    static {
        OCTAVE_SHIFT.put(SoundEvents.NOTE_BLOCK_BASS, 4F);
        OCTAVE_SHIFT.put(SoundEvents.NOTE_BLOCK_DIDGERIDOO, 4F);

        OCTAVE_SHIFT.put(SoundEvents.NOTE_BLOCK_GUITAR, 2F);

        OCTAVE_SHIFT.put(SoundEvents.NOTE_BLOCK_BANJO, 1F);
        OCTAVE_SHIFT.put(SoundEvents.NOTE_BLOCK_HARP, 1F);
        OCTAVE_SHIFT.put(SoundEvents.NOTE_BLOCK_PLING, 1F);
        OCTAVE_SHIFT.put(SoundEvents.NOTE_BLOCK_IRON_XYLOPHONE, 1F);
        OCTAVE_SHIFT.put(SoundEvents.NOTE_BLOCK_BIT, 1F);

        OCTAVE_SHIFT.put(SoundEvents.NOTE_BLOCK_FLUTE, 0.5F);
        OCTAVE_SHIFT.put(SoundEvents.NOTE_BLOCK_COW_BELL, 0.5F);

        OCTAVE_SHIFT.put(SoundEvents.NOTE_BLOCK_BELL, 0.25F);
        OCTAVE_SHIFT.put(SoundEvents.NOTE_BLOCK_CHIME, 0.25F);
        OCTAVE_SHIFT.put(SoundEvents.NOTE_BLOCK_XYLOPHONE, 0.25F);
    }
    public MusicalInstrument(Holder.Reference<SoundEvent> instrument, Properties properties) {
        super(properties);
        this.instrument = instrument;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        int note = level.random.nextInt(24) + 1;
        playSound(level, player, note);
        return super.use(level, player, usedHand);
    }

    public void playSound(Level level, Player player, int note){
        level.playSeededSound(player, player.getX(), player.getY(), player.getZ(), instrument, SoundSource.PLAYERS, 3, NoteBlock.getPitchFromNote(note), level.random.nextLong());
        if (player.hasEffect(daiFunctions.getHolder(level, Registries.MOB_EFFECT, daiMobEffects.ECHO.getKey()))){
            dealEcho(level, player, note);
        }
    }

    public void dealEcho(Level level, Player player, int note){
        if(level.isClientSide()) return;
        List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(getRange(note)));
        list.remove(player);
        for(LivingEntity entity : list) {
            entity.hurt(new DamageSource(daiFunctions.getHolder(level, Registries.DAMAGE_TYPE, DamageTypes.SONIC_BOOM), player), getDamage(note));
        }
    };

    protected float getRange(int note) {
        return getRate() * baseRange * (float) Math.pow(0.5, note / 12F);
    }

    protected float getDamage(int note) {
        return (1 / getRate()) * baseDamage * (float) Math.pow(2, note / 12F);
    }

    private float getRate(){
        return OCTAVE_SHIFT.getOrDefault(instrument, 1.0F);
    }
}
