package net.Gmaj7.funny_world.daiEnchantments.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public record EntropyEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<EntropyEnchantmentEffect> CODEC = MapCodec.unit(EntropyEnchantmentEffect::new);
    @Override
    public void apply(ServerLevel pLevel, int pEnchantmentLevel, EnchantedItemInUse pItem, Entity pEntity, Vec3 pOrigin) {
        BlockPos blockPos = BlockPos.containing(pOrigin);
        List<BlockState> list = new ArrayList<>();
        int px = blockPos.getX(),py = blockPos.getY(), pz = blockPos.getZ();
        int a = 2 * pEnchantmentLevel;
        int dx = -a, dy = -a, dz = -a;
        while (dx < a + 1){
            BlockPos blockPos1 = new BlockPos(px + dx, py + dy, pz + dz);
            list.add(pLevel.getBlockState(blockPos1));
            pLevel.destroyBlock(blockPos1, false);
            if (dz == a) {
                dz = -a;
                if(dy == a){
                    dy = -a;
                    dx ++;
                }
                else dy ++;
            }
            else dz++;
        }
        dx = -a; dy = -a; dz = -a;
        while (dx < a + 1){
            BlockPos blockPos1 = new BlockPos(px + dx, py + dy, pz + dz);
            int num = new Random().nextInt(list.size());
            pLevel.setBlockAndUpdate(blockPos1, list.get(num));
            list.remove(num);
            if (dz == a) {
                dz = -a;
                if(dy == a){
                    dy = -a;
                    dx ++;
                }
                else dy ++;
            }
            else dz++;
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
