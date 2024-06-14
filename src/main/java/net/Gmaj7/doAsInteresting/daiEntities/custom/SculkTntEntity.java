package net.Gmaj7.doAsInteresting.daiEntities.custom;

import net.Gmaj7.doAsInteresting.daiBlocks.custom.SculkTNT;
import net.Gmaj7.doAsInteresting.daiBlocks.daiBlocks;
import net.Gmaj7.doAsInteresting.daiEntities.daiEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class SculkTntEntity extends Entity implements TraceableEntity {

    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(SculkTntEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<BlockState> DATA_BLOCK_STATE_ID = SynchedEntityData.defineId(SculkTntEntity.class, EntityDataSerializers.BLOCK_STATE);
    private static final int DEFAULT_FUSE_TIME = 80;
    private static final String TAG_BLOCK_STATE = "block_state";
    public static final String TAG_FUSE = "fuse";
    @Nullable
    private LivingEntity owner;
    public SculkTntEntity(EntityType<SculkTntEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.blocksBuilding = true;
    }

    public SculkTntEntity(Level pLevel, double pX, double pY, double pZ, @Nullable LivingEntity pOwner) {
        this(daiEntities.SCULK_TNT.get(), pLevel);
        this.setPos(pX, pY, pZ);
        double d0 = pLevel.random.nextDouble() * (float) (Math.PI * 2);
        this.setDeltaMovement(-Math.sin(d0) * 0.02, 0.2F, -Math.cos(d0) * 0.02);
        this.setFuse(80);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
        this.owner = pOwner;
    }
    public SculkTntEntity(Level pLevel) {
        super(daiEntities.SCULK_TNT.get(), pLevel);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        pBuilder.define(DATA_FUSE_ID, 80);
        pBuilder.define(DATA_BLOCK_STATE_ID, daiBlocks.SCULK_TNT.get().defaultBlockState());
    }

    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
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
        if(i <= 2){
            level().playSound(null, blockPosition(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.BLOCKS, 3.0F, 1.0F);
            for (int dx = this.blockPosition().getX() - 1; dx <= this.blockPosition().getX() + 1; dx ++){
                for (int dy = this.blockPosition().getY() - 1; dy <= this.blockPosition().getY() + 1; dy ++){
                    for (int dz = this.blockPosition().getZ() - 1; dz <= this.blockPosition().getZ() + 1; dz++){
                        BlockPos blockPos = new BlockPos(dx, dy, dz);
                        if(this.level().getBlockState(blockPos).is(Blocks.AIR)) continue;
                        if(this.level().getBlockState(blockPos).is(BlockTags.SCULK_REPLACEABLE)){
                            this.level().setBlock(blockPos, Blocks.SCULK.defaultBlockState(), 1);
                        }
                    }
                }
            }
            List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(6D));
            Vec3 vec3Start = getEyePosition();
            for (LivingEntity target : list){
                Vec3 vec3End = target.getEyePosition();
                Vec3 vec3Throw = vec3Start.vectorTo(vec3End);
                Vec3 vec3Per = vec3Throw.normalize();
                int x = Mth.floor(vec3Throw.length()) + 3;
                for (int j = 1; j < x; j++){
                    Vec3 vec3Point = vec3Start.add(vec3Per.scale(j));
                    level().addParticle(ParticleTypes.SONIC_BOOM, vec3Point.x, vec3Point.y, vec3Point.z, 1, 0.0, 0.0);
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
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(8D));
        for (LivingEntity target : list){
            target.hurt(new DamageSource(level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.SONIC_BOOM), owner), 20F);
            target.knockback(2, this.getX() - target.getX(), this.getZ() - target.getZ());
        }
        for (int dx = this.blockPosition().getX() - 4; dx <= this.blockPosition().getX() + 4; dx ++){
            for (int dy = this.blockPosition().getY() - 4; dy <= this.blockPosition().getY() + 4; dy ++){
                for (int dz = this.blockPosition().getZ() - 4; dz <= this.blockPosition().getZ() + 4; dz++){
                    BlockPos blockPos = new BlockPos(dx, dy, dz);
                    if(this.level().getBlockState(blockPos).getBlock() instanceof SculkTNT) {
                        ((SculkTNT) this.level().getBlockState(blockPos).getBlock()).beExploded(this.level(), blockPos, this.owner);
                    }
                }
            }
        }
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
        if (pEntity instanceof SculkTntEntity sculkTntEntity) {
            this.owner = sculkTntEntity.owner;
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
}