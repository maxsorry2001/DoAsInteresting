package net.Gmaj7.doAsInteresting.daiEntities;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.Gmaj7.doAsInteresting.daiEntities.custom.*;
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
                    .sized(0.5F,0.5F)
                    .clientTrackingRange(4)
                    .build("explode_storage_entity"));

    public static final Supplier<EntityType<SculkTntEntity>> SCULK_TNT =
            ENTITY_TYPES.register("sculk_tnt", () -> EntityType.Builder.<SculkTntEntity>of(SculkTntEntity::new, MobCategory.MISC)
                    .fireImmune()
                    .sized(0.98F, 0.98F)
                    .eyeHeight(0.15F)
                    .clientTrackingRange(10)
                    .build("sculk_tnt"));

    public static final Supplier<EntityType<JistgabburashEntity>> JISTGABBURASH_ENTITY =
            ENTITY_TYPES.register("jistgabburash_entity", () -> EntityType.Builder.<JistgabburashEntity>of(JistgabburashEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .build("jistgabburash_entity"));

    public static final Supplier<EntityType<ElectricChargeEntity>> ELECTRIC_CHARGE_ENTITY =
            ENTITY_TYPES.register("electric_charge_entity", () -> EntityType.Builder.<ElectricChargeEntity>of(ElectricChargeEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .build("electric_charge_entity"));

    public static final Supplier<EntityType<NegativeChargeEntity>> NEGATIVE_CHARGE_ENTITY =
            ENTITY_TYPES.register("negative_charge_entity", () -> EntityType.Builder.<NegativeChargeEntity>of(NegativeChargeEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .build("negative_charge_entity"));

    public static final Supplier<EntityType<IronShootEntity>> IRON_SHOOT_ENTITY =
            ENTITY_TYPES.register("iron_shoot_entity", () -> EntityType.Builder.<IronShootEntity>of(IronShootEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .build("iron_shoot_entity"));

    public static final Supplier<EntityType<ThunderWedgeEntity>> THUNDER_WEDGE_ENTITY =
            ENTITY_TYPES.register("thunder_wedge_entity", () -> EntityType.Builder.<ThunderWedgeEntity>of(ThunderWedgeEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .build("thunder_wedge_entity"));

    public static final Supplier<EntityType<ElectromagneticTntEntity>> ELECTROMAGNET_TNT_ENTITY =
            ENTITY_TYPES.register("electromagnetic_tnt_entity", () -> EntityType.Builder.<ElectromagneticTntEntity>of(ElectromagneticTntEntity::new, MobCategory.MISC)
                    .fireImmune()
                    .sized(0.98F, 0.98F)
                    .eyeHeight(0.15F)
                    .clientTrackingRange(10)
                    .build("electromagnetic_tnt_entity"));


    public static void register(IEventBus eventBus){ENTITY_TYPES.register(eventBus);}
}
