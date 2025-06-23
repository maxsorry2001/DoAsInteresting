package net.Gmaj7.funny_world.daiEntities;

import net.Gmaj7.funny_world.FunnyWorld;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

public class daiModelLayers {
    public static ModelLayerLocation createIceBoatModelName(Boat.Type type) {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, type.getName());
        return new ModelLayerLocation(location.withPrefix("ice_boat/"), "main");
    }

    public static ModelLayerLocation createRaftModelName(Boat.Type type) {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, type.getName());
        return new ModelLayerLocation(location.withPrefix("ice_boat/"), "main");
    }
}
