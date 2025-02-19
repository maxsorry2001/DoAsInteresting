package net.Gmaj7.funny_world.eventDispose;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiInit.daiPackets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = FunnyWorld.MODID)
public class daiPayLoadHandler {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar payloadRegistrar = event.registrar(FunnyWorld.MODID).versioned("1.0.0").optional();

        payloadRegistrar.playToServer(daiPackets.daiBellHelmetPacket.TYPE, daiPackets.daiBellHelmetPacket.STREAM_CODEC, daiPackets.daiBellHelmetPacket::handle);
    }
}
