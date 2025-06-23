package net.Gmaj7.funny_world.daiEntities.renderer;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEntities.daiModelLayers;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ChestRaftModel;
import net.minecraft.client.model.RaftModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

import javax.swing.*;
import java.util.Map;
import java.util.stream.Stream;

public class IceBoatRender extends BoatRenderer {
    private final Map<Boat.Type, Pair<ResourceLocation, ListModel<Boat>>> resource;

    public IceBoatRender(EntityRendererProvider.Context context, boolean chestBoat) {
        super(context, chestBoat);
        this.resource = (Map)Stream.of(Boat.Type.values()).collect(ImmutableMap.toImmutableMap(type -> type, type -> Pair.of(getTextureLocation(type, chestBoat), this.createBoatModel(context, type, chestBoat))));
    }

    private net.minecraft.client.model.ListModel<Boat> createBoatModel(EntityRendererProvider.Context context, Boat.Type type, boolean chestBoat) {
        ModelLayerLocation modellayerlocation = chestBoat ? ModelLayers.createChestBoatModelName(type) : daiModelLayers.createIceBoatModelName(type);
        ModelPart modelpart = context.bakeLayer(modellayerlocation);
        if (type.isRaft()) {
            return (net.minecraft.client.model.ListModel)(chestBoat ? new ChestRaftModel(modelpart) : new RaftModel(modelpart));
        } else {
            return (net.minecraft.client.model.ListModel)(chestBoat ? new ChestBoatModel(modelpart) : new BoatModel(modelpart));
        }
    }

    private static ResourceLocation getTextureLocation(Boat.Type type, boolean chestBoat) {
        return chestBoat ? ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, type.getName()).withPrefix("textures/entity/chest_boat/").withSuffix(".png") : ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, type.getName()).withPrefix("textures/entity/ice_boat/").withSuffix(".png");
    }

    @Override
    public Pair<ResourceLocation, net.minecraft.client.model.ListModel<Boat>> getModelWithLocation(Boat boat) {
        return (Pair)this.resource.get(boat.getVariant());
    }
}
