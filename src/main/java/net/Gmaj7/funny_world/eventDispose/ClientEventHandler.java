package net.Gmaj7.funny_world.eventDispose;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEntities.model.IronShootEntityModel;
import net.Gmaj7.funny_world.daiEntities.model.MahjongEntityModel;
import net.Gmaj7.funny_world.daiEntities.model.ThunderBallModel;
import net.Gmaj7.funny_world.daiGui.hud.ShowHumanityHud;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

@EventBusSubscriber(modid = FunnyWorld.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(IronShootEntityModel.LAYER_LOCATION, IronShootEntityModel::createBodyLayer);
        event.registerLayerDefinition(ThunderBallModel.LAYER_LOCATION, ThunderBallModel::createBodyLayer);
        event.registerLayerDefinition(MahjongEntityModel.LAYER_LOCATION, MahjongEntityModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerHud(RegisterGuiLayersEvent event){
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "humanity"), new ShowHumanityHud());
    }
}
