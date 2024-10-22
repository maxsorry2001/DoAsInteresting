package net.Gmaj7.funny_world.eventDispose;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEntities.model.IronShootEntityModel;
import net.Gmaj7.funny_world.daiEntities.model.ThunderBallModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = FunnyWorld.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(IronShootEntityModel.LAYER_LOCATION, IronShootEntityModel::createBodyLayer);
        event.registerLayerDefinition(ThunderBallModel.LAYER_LOCATION, ThunderBallModel::createBodyLayer);
    }
}
