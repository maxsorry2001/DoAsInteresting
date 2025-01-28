package net.Gmaj7.funny_world.mixin;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.Gmaj7.funny_world.daiSounds.daiJukeboxSongs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.commands.WeatherCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Clearable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.JukeboxSongPlayer;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.ticks.ContainerSingleItem;
import org.apache.commons.compress.harmony.pack200.IntList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin(JukeboxBlockEntity.class)
public abstract class JukeboxBlockEntityMixin extends BlockEntity implements Clearable, ContainerSingleItem.BlockContainerSingleItem {
    public JukeboxBlockEntityMixin(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private static void tickMixin(Level pLevel, BlockPos pPos, BlockState pState, JukeboxBlockEntity pJukebox, CallbackInfo info){
        if(pJukebox.getSongPlayer().isPlaying() && pJukebox.getSongPlayer().getSong() == pLevel.registryAccess().registryOrThrow(Registries.JUKEBOX_SONG).getHolderOrThrow(daiJukeboxSongs.SPRING_FESTIVAL_OVERTURE).value()){
            List<LivingEntity> list = pLevel.getEntitiesOfClass(LivingEntity.class, new AABB(pPos).inflate(14));
            List<Player> listPlayer = pLevel.getEntitiesOfClass(Player.class, new AABB(pPos).inflate(14));
            for (LivingEntity livingEntity : list){
                if(livingEntity instanceof Player) continue;
                if (livingEntity.onGround()) livingEntity.setDeltaMovement(0,0.3,0);
                if(livingEntity instanceof Villager villager){
                    for (Player pTarget : listPlayer){
                        villager.getGossips().add(pTarget.getUUID(), GossipType.MAJOR_POSITIVE, 20);
                    }
                }
                if(pLevel instanceof ServerLevel serverLevel){
                    serverLevel.getServer().overworld().setWeatherParameters(65535, 65535, false, false);
                }
            }
            if(pJukebox.getSongPlayer().getTicksSinceSongStarted() % 34 == 0){
                ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET);
                it.unimi.dsi.fastutil.ints.IntList intListColor = new IntArrayList();
                intListColor.add(255 << 16);
                if(new Random().nextBoolean())
                    intListColor.add((255 << 16) + (new Random().nextInt(25) + 230) << 8);
                else
                    intListColor.add((255 << 16) + (new Random().nextInt((int) Math.pow(2, 4)) + 1) << 4);
                intListColor.add(0xFFFFFF);
                FireworkExplosion fireworkExplosion = new FireworkExplosion(FireworkExplosion.Shape.LARGE_BALL, intListColor, new IntArrayList(), true, false);
                List<FireworkExplosion> list1 = new ArrayList<>();
                list1.add(fireworkExplosion);
                itemStack.set(DataComponents.FIREWORKS, new Fireworks(new Random().nextInt(3) + 1, list1));
                double dx = new Random().nextDouble(3) - 1.5;
                double dz = new Random().nextDouble(3) - 1.5;
                FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(pLevel, pPos.getX() + dx, pPos.getY() + 2.5, pPos.getZ() + dz, itemStack);
                pLevel.addFreshEntity(fireworkRocketEntity);
            }
        }
    }
}
