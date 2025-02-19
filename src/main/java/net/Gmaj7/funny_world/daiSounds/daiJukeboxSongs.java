package net.Gmaj7.funny_world.daiSounds;

import net.Gmaj7.funny_world.FunnyWorld;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

public interface daiJukeboxSongs {
    ResourceKey<JukeboxSong> SPRING_FESTIVAL_OVERTURE = create("spring_festival_overture");

    private static ResourceKey<JukeboxSong> create(String name){
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, name));
    }

    public static void bootstrap(BootstrapContext<JukeboxSong> bootstrapContext){
        register(bootstrapContext, SPRING_FESTIVAL_OVERTURE, daiSounds.SPRING_FESTIVAL_OVERTURE,295, 15);
    }

    private static void register(BootstrapContext<JukeboxSong> bootstrapContext, ResourceKey<JukeboxSong> key, Holder<SoundEvent> holder, float length, int output){
        bootstrapContext.register(key, new JukeboxSong(holder, Component.translatable(Util.makeDescriptionId("daisongs_spring_festival_overture", key.location())), length, output));
    }
}
