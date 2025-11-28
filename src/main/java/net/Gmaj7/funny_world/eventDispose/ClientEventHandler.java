package net.Gmaj7.funny_world.eventDispose;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEntities.daiModelLayers;
import net.Gmaj7.funny_world.daiEntities.model.*;
import net.Gmaj7.funny_world.daiGui.hud.ShowHumanityHud;
import net.Gmaj7.funny_world.daiInit.daiKeyMapping;
import net.Gmaj7.funny_world.daiInit.daiKeySet;
import net.Gmaj7.funny_world.daiInit.daiPackets;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.RaftModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = FunnyWorld.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(IronShootEntityModel.LAYER_LOCATION, IronShootEntityModel::createBodyLayer);
        event.registerLayerDefinition(ThunderBallModel.LAYER_LOCATION, ThunderBallModel::createBodyLayer);
        event.registerLayerDefinition(MahjongEntityModel.LAYER_LOCATION, MahjongEntityModel::createBodyLayer);
        event.registerLayerDefinition(WaterKnifeModel.LAYER_LOCATION, WaterKnifeModel::createBodyLayer);
        event.registerLayerDefinition(WaterBombModel.LAYER_LOCATION, WaterBombModel::createBodyLayer);

        for (Boat.Type type : Boat.Type.values()){
            if(type == Boat.Type.BAMBOO) event.registerLayerDefinition(daiModelLayers.createRaftModelName(type), RaftModel::createBodyModel);
            else event.registerLayerDefinition(daiModelLayers.createIceBoatModelName(type), BoatModel::createBodyModel);
        }
    }
    @SubscribeEvent
    public static void registerHud(RegisterGuiLayersEvent event){
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "humanity"), new ShowHumanityHud());
    }

    @SubscribeEvent
    public static void registerKey(RegisterKeyMappingsEvent event){
        event.register(daiKeyMapping.SELECT_WATER_BOW);
    }

    @EventBusSubscriber(modid = FunnyWorld.MODID, value = Dist.CLIENT)
    public static class notBusEvent{
        private static final daiKeySet SELECT_WATER_BOW = new daiKeySet(daiKeyMapping.SELECT_WATER_BOW);

        @SubscribeEvent
        public static void keyInput(InputEvent.Key event){
            if(SELECT_WATER_BOW.wasReleased()){
                Player player = Minecraft.getInstance().player;
                for (InteractionHand hand : InteractionHand.values()){
                    if(player.getItemInHand(hand).is(daiItems.WATER_BOW.get()) && !player.isUsingItem()){
                        PacketDistributor.sendToServer(new daiPackets.daiWaterBowPacket(hand));
                        break;
                    }
                }
            }
            SELECT_WATER_BOW.update();
        }
    }
}
