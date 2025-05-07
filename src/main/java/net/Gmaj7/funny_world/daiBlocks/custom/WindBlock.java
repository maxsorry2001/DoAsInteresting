package net.Gmaj7.funny_world.daiBlocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WindBlock extends Block {
    public WindBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if(!(level.getBlockState(pos.above()).getBlock() instanceof AnvilBlock))
            this.makeParticle(ParticleTypes.CLOUD, level, pos);
        super.animateTick(state, level, pos, random);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    public void makeParticle(ParticleOptions options, Level level, BlockPos pos){
        for (int i = 1; i <= 2; i++) {
            for (int j = 0; j < 6 * i; j++) {
                double r = j * 2 * Math.PI / (4 * i);
                level.addParticle(options, pos.getX() + 0.5 + 2 * i * Math.sin(r), pos.getY() + 0.5, pos.getZ() + 0.5 + 2 * i * Math.cos(r), 0.1 * Math.cos(r), 0, - 0.1 * Math.sin(r));
            }
        }
    }
}
