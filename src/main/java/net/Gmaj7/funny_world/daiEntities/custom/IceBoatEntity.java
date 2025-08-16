package net.Gmaj7.funny_world.daiEntities.custom;

import net.Gmaj7.funny_world.daiEntities.daiEntities;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.Gmaj7.funny_world.daiInit.daiPackets;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class IceBoatEntity extends Boat {
    public IceBoatEntity(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
    }

    public IceBoatEntity(Level level, double x, double y, double z){
        this(daiEntities.ICE_BOAT_ENTITY.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public void push(Entity entity) {
        if (entity instanceof Boat) {
            if (entity.getBoundingBox().minY < this.getBoundingBox().maxY) {
                entity.setDeltaMovement(this.getDeltaMovement());
                this.setDeltaMovement(0, 0,0);
            }
        }
        else if (entity instanceof LivingEntity && entity.getBoundingBox().minY <= this.getBoundingBox().minY && !this.hasPassenger(entity)) {
            entity.hurt(new DamageSource(daiFunctions.getHolder(this.level(), Registries.DAMAGE_TYPE, DamageTypes.GENERIC)), ((LivingEntity) entity).getMaxHealth() < 50 ? ((LivingEntity) entity).getMaxHealth() : ((LivingEntity) entity).getMaxHealth() / 3);
        }
    }

    @Override
    public void tick() {
        if(level().isClientSide() && !level().noBlockCollision(this, this.getBoundingBox().inflate(1, 0, 1)) && this.getDeltaMovement().length() * 10 > 5){
            PacketDistributor.sendToServer(new daiPackets.daiIceBoatPacket(this.getX(), this.getY(), this.getZ(), (float) this.getDeltaMovement().length() * 10));
            this.setDeltaMovement(0, 0, 0);
        }
        super.tick();
    }

    @Override
    public Item getDropItem() {
        Item var10000;
        switch (this.getVariant().ordinal()) {
            case 0:
                var10000 = daiItems.OAK_ICE_BOAT.get();
                break;
            case 1:
                var10000 = daiItems.SPRUCE_ICE_BOAT.get();
                break;
            case 2:
                var10000 = daiItems.BIRCH_ICE_BOAT.get();
                break;
            case 3:
                var10000 = daiItems.JUNGLE_ICE_BOAT.get();
                break;
            case 4:
                var10000 = daiItems.ACACIA_ICE_BOAT.get();
                break;
            case 5:
                var10000 = daiItems.CHERRY_ICE_BOAT.get();
                break;
            case 6:
                var10000 = daiItems.DARK_OAK_ICE_BOAT.get();
                break;
            case 7:
                var10000 = daiItems.MANGROVE_ICE_BOAT.get();
                break;
            case 8:
                var10000 = daiItems.BAMBOO_ICE_RAFT.get();
                break;
            default:
                var10000 = daiItems.OAK_ICE_BOAT.get();
        }

        return var10000;
    }

    @Override
    public float getGroundFriction() {
        return 0.99F;
    }
}
