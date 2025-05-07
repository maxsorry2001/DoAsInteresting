package net.Gmaj7.funny_world.daiBlocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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
        for (int i = 1; i <= 2; i++) {
            for (int j = 0; j < 4 * i; j++) {
                level.addParticle(ParticleTypes.CLOUD, pos.getX() + random.nextFloat(), pos.getY() + random.nextFloat(), pos.getZ() + random.nextFloat(), 0, 0.2, 0);
                double r = j * 2 * Math.PI / (4 * i);
                for (int k = 0; k < 4; k++)
                    level.addParticle(ParticleTypes.CLOUD, pos.getX() + 0.5 + 2 * i * Math.sin(r), pos.getY() + k, pos.getZ() + 0.5 + 2 * i * Math.cos(r), 0.2 * Math.cos(r), 0, - 0.2 * Math.sin(r));
            }
        }
        super.animateTick(state, level, pos, random);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }
}
