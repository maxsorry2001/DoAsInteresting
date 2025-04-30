package net.Gmaj7.funny_world.daiEntities.custom;

import net.Gmaj7.funny_world.daiEntities.daiEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class SlimeFishingHookEntity extends FishingHook {
    protected boolean inGround;
    double posX;
    double posY;
    double posZ;
    public SlimeFishingHookEntity(EntityType<? extends FishingHook> entityType, Level level) {
        super(entityType, level);
    }

    public SlimeFishingHookEntity(Player player, Level level) {
        this(daiEntities.SLIME_FISHING_HOOK_ENTITY.get(), level);
        this.setOwner(player);
        this.teleportTo(player.getX(), player.getY(), player.getZ());
    }

    @Override
    public void tick() {
        super.tick();
        if(inGround){
            this.teleportTo(posX, posY, posZ);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (!inGround){
            this.inGround = true;
            this.posX = this.getX();
            this.posY = this.getY();
            this.posZ = this.getZ();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
    }

}
