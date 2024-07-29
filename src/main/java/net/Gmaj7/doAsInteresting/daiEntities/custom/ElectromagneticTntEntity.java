package net.Gmaj7.doAsInteresting.daiEntities.custom;

import net.Gmaj7.doAsInteresting.daiBlocks.custom.ElectromagneticTNT;
import net.Gmaj7.doAsInteresting.daiBlocks.daiBlocks;
import net.Gmaj7.doAsInteresting.daiEffects.daiMobEffects;
import net.Gmaj7.doAsInteresting.daiEntities.daiEntities;
import net.Gmaj7.doAsInteresting.daiInit.daiDamageTypes;
import net.Gmaj7.doAsInteresting.daiInit.daiTags;
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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ElectromagneticTntEntity extends Entity implements TraceableEntity {
    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(ElectromagneticTntEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<BlockState> DATA_BLOCK_STATE_ID = SynchedEntityData.defineId(ElectromagneticTntEntity.class, EntityDataSerializers.BLOCK_STATE);
    private static final int DEFAULT_FUSE_TIME = 80;
    private static final String TAG_BLOCK_STATE = "block_state";
    public static final String TAG_FUSE = "fuse";
    @Nullable
    private LivingEntity owner;
    public ElectromagneticTntEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ElectromagneticTntEntity(Level pLevel, double pX, double pY, double pZ, @javax.annotation.Nullable LivingEntity pOwner) {
        this(daiEntities.ELECTROMAGNET_TNT_ENTITY.get(), pLevel);
        this.setPos(pX, pY, pZ);
        double d0 = pLevel.random.nextDouble() * (float) (Math.PI * 2);
        this.setDeltaMovement(-Math.sin(d0) * 0.02, 0.2F, -Math.cos(d0) * 0.02);
        this.setFuse(80);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
        this.owner = pOwner;
    }

    @Override
    public void tick() {
        this.applyGravity();
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
        }
        int x = this.getFuse() - 1;
        this.setFuse(x);
        if(x <= 2){
            for (int i = -3; i < 4; i++){
                for (int j = -3; j < 4; j++){
                    for (int k = -3; k < 4; k++){
                        this.level().addParticle(ParticleTypes.FALLING_OBSIDIAN_TEAR, this.getX() + i, this.getY() + j, this.getZ() + k,0, 0, 0);
                        this.level().playSound(this,  new BlockPos(this.blockPosition()), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 1.0F, 1.0F);
                    }
                }
            }
        }
        if (x <= 0) {
            this.discard();
            if (!this.level().isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level().isClientSide) {
                this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    protected void explode() {
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, new AABB(this.blockPosition()).inflate(7));
        for (LivingEntity target : list){
            target.hurt(new DamageSource(this.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(daiDamageTypes.RADIATION), this.owner), 10F);
            target.addEffect(new MobEffectInstance(daiMobEffects.RADIATION, 300, 0));
        }
        for (int i = -3; i < 4; i++){
            for (int j = -3; j < 4; j++){
                for (int k = -3; k < 4; k++){
                    BlockPos blockPos = new BlockPos(this.blockPosition().getX() + i, this.blockPosition().getY() + j, this.blockPosition().getZ() + k);
                    BlockState blockState = this.level().getBlockState(blockPos);
                    Block block = blockState.getBlock();
                    if(blockState.is(daiTags.daiBlockTags.ELECTROMAGNETIC_BREAK))
                        this.level().destroyBlock(blockPos, true);
                    else if(block instanceof ElectromagneticTNT)
                        ((ElectromagneticTNT) block).beExploded(this.level(), blockPos, this.owner);
                }
            }
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        pBuilder.define(DATA_FUSE_ID, 80);
        pBuilder.define(DATA_BLOCK_STATE_ID, daiBlocks.ELECTROMAGNETIC_TNT.get().defaultBlockState());
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

    @Override
    public void restoreFrom(Entity pEntity) {
        super.restoreFrom(pEntity);
        if (pEntity instanceof ElectromagneticTntEntity electromagneticTntEntity) {
            this.owner = electromagneticTntEntity.owner;
        }
    }

    @Override
    protected double getDefaultGravity() {
        return 0.04;
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return this.owner;
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
