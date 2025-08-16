package net.Gmaj7.funny_world.daiBlocks.blockEntity;

import net.Gmaj7.funny_world.daiBlocks.daiBlockEntities;
import net.Gmaj7.funny_world.daiEffects.daiMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class LavaChickenCoreBE extends BlockEntity {
    private int time = 1;
    private int lava_chicken_ingot = 0;
    public LavaChickenCoreBE(BlockPos pos, BlockState blockState) {
        super(daiBlockEntities.LAVA_CHICKEN_BE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, LavaChickenCoreBE lavaChickenCoreBE){
        if(!(level instanceof ServerLevel)) return;
        lavaChickenCoreBE.time ++;
        if(lavaChickenCoreBE.time == 100){
            lavaChickenCoreBE.time = 0;
            List<Player> listPlayer = level.getEntitiesOfClass(Player.class, new AABB(pos).inflate(10));
            List<Chicken> listChicken = level.getEntitiesOfClass(Chicken.class, new AABB(pos).inflate(5, 1, 5));
            if(lavaChickenCoreBE.getIngot() > 0) {
                for (Chicken chicken : listChicken) {
                    if (chicken.isBaby()) continue;
                    ItemStack itemStack = new ItemStack(Items.COOKED_CHICKEN);
                    ItemEntity itemEntity = new ItemEntity(level, chicken.getX(), chicken.getY(), chicken.getZ(), itemStack);
                    level.addFreshEntity(itemEntity);
                    ((ServerLevel) level).sendParticles(ParticleTypes.LAVA, chicken.getX(), chicken.getY(), chicken.getZ(), 5, 0, 0, 0, 0);
                    chicken.remove(Entity.RemovalReason.DISCARDED);
                }
            }for (Player player : listPlayer){
                if(lavaChickenCoreBE.getIngot() > 1){
                    player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300));
                }
                if(lavaChickenCoreBE.getIngot() > 2) {
                    player.addEffect(new MobEffectInstance(daiMobEffects.LAVA_CHICKEN_POWER, 300));
                }
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        System.out.println(lava_chicken_ingot);
        tag.putInt("ingot", this.lava_chicken_ingot);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        System.out.println(tag.getInt("ingot"));
        this.lava_chicken_ingot = tag.getInt("ingot");
    }

    public boolean ingotAdd(){
        boolean flag;
        if(lava_chicken_ingot >= 3) flag = false;
        else {
            lava_chicken_ingot ++;
            flag = true;
        }
        return flag;
    }

    public boolean ingotReduce(){
        boolean flag;
        if(lava_chicken_ingot == 0) flag = false;
        else {
            lava_chicken_ingot --;
            flag = true;
        }
        return flag;
    }

    public int getIngot() {
        return lava_chicken_ingot;
    }
}
