package net.Gmaj7.funny_world.daiEntities.custom;

import net.Gmaj7.funny_world.daiBlocks.daiBlocks;
import net.Gmaj7.funny_world.daiEntities.daiEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

public class WindTntEntity extends Entity implements TraceableEntity {

    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(WindTntEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<BlockState> DATA_BLOCK_STATE_ID = SynchedEntityData.defineId(WindTntEntity.class, EntityDataSerializers.BLOCK_STATE);
    private static final int DEFAULT_FUSE_TIME = 80;
    private static final String TAG_BLOCK_STATE = "block_state";
    public static final String TAG_FUSE = "fuse";
    @Nullable
    private LivingEntity owner;
    public WindTntEntity(EntityType<WindTntEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.blocksBuilding = true;
    }

    public WindTntEntity(Level pLevel, double pX, double pY, double pZ, @Nullable LivingEntity pOwner) {
        this(daiEntities.WIND_TNT_ENTITY.get(), pLevel);
        this.setPos(pX, pY, pZ);
        double d0 = pLevel.random.nextDouble() * (float) (Math.PI * 2);
        this.setDeltaMovement(-Math.sin(d0) * 0.02, 0.2F, -Math.cos(d0) * 0.02);
        this.setFuse(80);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
        this.owner = pOwner;
    }
    public WindTntEntity(Level pLevel) {
        super(daiEntities.WIND_TNT_ENTITY.get(), pLevel);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        pBuilder.define(DATA_FUSE_ID, 80);
        pBuilder.define(DATA_BLOCK_STATE_ID, daiBlocks.WIND_TNT.get().defaultBlockState());
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.04;
    }

    @Override
    public void tick() {
        this.applyGravity();
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
        }

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if(i % 10 == 0){
            RandomSource randomSource = RandomSource.create();
            for (int dx = this.blockPosition().getX() - 6; dx <= this.blockPosition().getX() + 6; dx ++){
                for (int dz = this.blockPosition().getZ() - 6; dz <= this.blockPosition().getZ() + 6; dz++){
                    level().addParticle(ParticleTypes.CLOUD, dx + randomSource.nextFloat(), this.blockPosition().getY() + randomSource.nextFloat(), dz + randomSource.nextFloat(), 0, 0.5, 0);
                }
            }
        }
        if (i <= 0) {
            this.discard();
            if (!this.level().isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
        }
    }

    protected void explode() {
        int dy, i = 6;
        for (; i >= 1; i --){
            int startX = this.blockPosition().getX() - i, endX = this.blockPosition().getX() + i,
                    startZ = this.blockPosition().getZ() - i, endZ = this.blockPosition().getZ() + i;
            for (int dx = startX; dx <= endX; dx ++){
                for (int dz = startZ; dz <= endZ; dz++){
                    dy = this.blockPosition().getY() + i;
                    BlockPos upPos = new BlockPos(dx, dy, dz), downPos = new BlockPos(dx, dy - 6, dz);
                    if(!level().getBlockState(downPos).is(Blocks.AIR)) {
                        level().destroyBlock(upPos, true);
                        level().setBlockAndUpdate(upPos, level().getBlockState(downPos));
                        level().setBlockAndUpdate(downPos, Blocks.AIR.defaultBlockState());
                    }
                    if(isStartOrEnd(dx, startX, endX) && isStartOrEnd(dz, startZ, endZ) && i == 4){
                        upPos = upPos.below();
                        level().destroyBlock(upPos, true);
                        level().setBlockAndUpdate(upPos, daiBlocks.WIND_BLOCK.get().defaultBlockState());
                    }
                }
            }
        }
        level().explode(this, null, new SimpleExplosionDamageCalculator(true, false, Optional.of(1.22F), BuiltInRegistries.BLOCK.getTag(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity())), this.getX(), this.getY(), this.getZ(), 6, false, Level.ExplosionInteraction.TRIGGER, ParticleTypes.GUST_EMITTER_SMALL, ParticleTypes.GUST_EMITTER_LARGE, SoundEvents.WIND_CHARGE_BURST);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putShort("fuse", (short)this.getFuse());
        pCompound.put("block_state", NbtUtils.writeBlockState(this.getBlockState()));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.setFuse(pCompound.getShort("fuse"));
        if (pCompound.contains("block_state", 10)) {
            this.setBlockState(NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), pCompound.getCompound("block_state")));
        }
    }

    @Nullable
    public LivingEntity getOwner() {
        return this.owner;
    }

    @Override
    public void restoreFrom(Entity pEntity) {
        super.restoreFrom(pEntity);
        if (pEntity instanceof WindTntEntity windTntEntity) {
            this.owner = windTntEntity.owner;
        }
    }

    public void setFuse(int pLife) {
        this.entityData.set(DATA_FUSE_ID, pLife);
    }

    public int getFuse() {
        return this.entityData.get(DATA_FUSE_ID);
    }

    public void setBlockState(BlockState pBlockState) {
        this.entityData.set(DATA_BLOCK_STATE_ID, pBlockState);
    }

    public BlockState getBlockState() {
        return this.entityData.get(DATA_BLOCK_STATE_ID);
    }

    private boolean isStartOrEnd(int n, int start, int end){
        return n == start || n == end;
    }
}