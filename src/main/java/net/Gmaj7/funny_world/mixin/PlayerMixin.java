package net.Gmaj7.funny_world.mixin;


import net.Gmaj7.funny_world.daiInit.daiPackets;
import net.Gmaj7.funny_world.daiInit.daiUniqueData.HumanitySet;
import net.Gmaj7.funny_world.daiInit.daiUniqueData.daiUniqueDataGet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.extensions.IPlayerExtension;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IPlayerExtension, daiUniqueDataGet {
    @Unique
    private HumanitySet humanitySet = new HumanitySet(50);

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    @Unique
    public HumanitySet getHumanitySet() {
        return humanitySet;
    }

    @Override
    public LivingEntity self() {
        return super.self();
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    public void addAdditionalSaveDataMixin(CompoundTag compound, CallbackInfo ci){
        compound.putInt("dai_humanity", humanitySet.getHumanity());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    public void readAdditionalSaveDataMixin(CompoundTag compound, CallbackInfo ci){
        if (compound.contains("dai_humanity")) {
            this.humanitySet.setHumanity(compound.getInt("dai_humanity"));
        }
    }
}
