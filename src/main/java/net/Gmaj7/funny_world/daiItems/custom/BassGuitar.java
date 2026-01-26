package net.Gmaj7.funny_world.daiItems.custom;

import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NoteBlock;

import java.util.List;

public class BassGuitar extends MusicalInstrument {

    public BassGuitar(Properties properties) {
        super(properties);
        this.isBass = true;
    }

    @Override
    public void playSound(Level level, Player player, int note) {
        level.playSeededSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_BASS, SoundSource.PLAYERS, 3, NoteBlock.getPitchFromNote(note), level.random.nextLong());
        super.playSound(level, player, note);
    }

    @Override
    public void dealEcho(Level level, Player player, int note) {
        if(level.isClientSide()) return;
        List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(getRange(note)));
        list.remove(player);
        for(LivingEntity entity : list) {
            entity.hurt(new DamageSource(daiFunctions.getHolder(level, Registries.DAMAGE_TYPE, DamageTypes.SONIC_BOOM), player), getDamage(note));
        }
    }
}
