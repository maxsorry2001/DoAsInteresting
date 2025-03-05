package net.Gmaj7.funny_world.mixin;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.Gmaj7.funny_world.daiInit.daiUniqueData.HoneyAbsorbEffect;
import net.Gmaj7.funny_world.daiInit.daiUniqueData.daiUniqueDataGet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.common.extensions.IBlockStateExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockState.class)
public abstract class BlockStateMixin extends BlockBehaviour.BlockStateBase implements IBlockStateExtension, daiUniqueDataGet {
    @Unique
    HoneyAbsorbEffect honeyAbsorbEffect = new HoneyAbsorbEffect();
    protected BlockStateMixin(Block owner, Reference2ObjectArrayMap<Property<?>, Comparable<?>> values, MapCodec<BlockState> propertiesCodec) {
        super(owner, values, propertiesCodec);
    }

    @Override
    public HoneyAbsorbEffect getHoneyAbsorbEffect() {
        return honeyAbsorbEffect;
    }
}
