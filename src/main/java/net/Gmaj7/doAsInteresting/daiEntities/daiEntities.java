package net.Gmaj7.doAsInteresting.daiEntities;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.Gmaj7.doAsInteresting.daiEntities.custom.ExplosionStorageEntity;
import net.Gmaj7.doAsInteresting.daiEntities.custom.SculkTntEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class daiEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, DoAsInteresting.MODID);

    public static final Supplier<EntityType<ExplosionStorageEntity>> EXPLODE_STORAGE_ENTITY =
            ENTITY_TYPES.register("explode_storage_entity",() -> EntityType.Builder.<ExplosionStorageEntity>of(ExplosionStorageEntity::new, MobCategory.MISC)
                    .sized(0.5F,0.5F).clientTrackingRange(4)
                    .build("explode_storage_entity"));

    public static final Supplier<EntityType<SculkTntEntity>> SCULK_TNT =
            ENTITY_TYPES.register("sculk_tnt", () -> EntityType.Builder.<SculkTntEntity>of(SculkTntEntity::new, MobCategory.MISC)
                    .fireImmune()
                    .sized(0.98F, 0.98F)
                    .eyeHeight(0.15F)
                    .clientTrackingRange(10)
                    .build("sculk_tnt"));

    public static void register(IEventBus eventBus){ENTITY_TYPES.register(eventBus);}
}
