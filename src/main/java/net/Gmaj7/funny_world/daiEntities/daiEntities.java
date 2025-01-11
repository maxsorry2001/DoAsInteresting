package net.Gmaj7.funny_world.daiEntities;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEntities.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class daiEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, FunnyWorld.MODID);

    public static final Supplier<EntityType<ExplosionStorageEntity>> EXPLODE_STORAGE_ENTITY =
            ENTITY_TYPES.register("explode_storage_entity",() -> EntityType.Builder.<ExplosionStorageEntity>of(ExplosionStorageEntity::new, MobCategory.MISC)
                    .sized(0.5F,0.5F)
                    .clientTrackingRange(4)
                    .build("explode_storage_entity"));


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

    public static final Supplier<EntityType<BrickEntity>> BRICK_ENTITY =
            ENTITY_TYPES.register("nether_brick_entity", () -> EntityType.Builder.<BrickEntity>of(BrickEntity::new, MobCategory.MISC)
                    .fireImmune()
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(4)
                    .build("nether_brick_entity"));

    public static final Supplier<EntityType<NetherBrickEntity>> NETHER_BRICK_ENTITY =
            ENTITY_TYPES.register("brick_entity", () -> EntityType.Builder.<NetherBrickEntity>of(NetherBrickEntity::new, MobCategory.MISC)
                    .fireImmune()
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(4)
                    .build("brick_entity"));

    public static final Supplier<EntityType<ThrownHydrogenEntity>> THROWN_HYDROGEN_ENTITY =
            ENTITY_TYPES.register("thrown_hydrogen_entity", () -> EntityType.Builder.<ThrownHydrogenEntity>of(ThrownHydrogenEntity::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(4)
                    .build("thrown_hydrogen_entity"));

    public static final Supplier<EntityType<ThrownOxygenEntity>> THROWN_OXYGEN_ENTITY =
            ENTITY_TYPES.register("thrown_oxygen_entity", () -> EntityType.Builder.<ThrownOxygenEntity>of(ThrownOxygenEntity::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(4)
                    .build("thrown_oxygen_entity"));

    public static final Supplier<EntityType<ThrownCarbonDioxideEntity>> THROWN_CARBON_DIOXIDE_ENTITY =
            ENTITY_TYPES.register("thrown_carbon_dioxide_entity", () -> EntityType.Builder.<ThrownCarbonDioxideEntity>of(ThrownCarbonDioxideEntity::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .fireImmune()
                    .clientTrackingRange(4)
                    .build("thrown_carbon_dioxide_entity"));

    public static final Supplier<EntityType<ThrownCarbonMonoxideEntity>> THROWN_CARBON_MONOXIDE_ENTITY =
            ENTITY_TYPES.register("thrown_carbon_monoxide_entity", () -> EntityType.Builder.<ThrownCarbonMonoxideEntity>of(ThrownCarbonMonoxideEntity::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .fireImmune()
                    .clientTrackingRange(4)
                    .build("thrown_carbon_monoxide_entity"));

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

    public static final Supplier<EntityType<SculkTntEntity>> SCULK_TNT_ENTITY =
            ENTITY_TYPES.register("sculk_tnt", () -> EntityType.Builder.<SculkTntEntity>of(SculkTntEntity::new, MobCategory.MISC)
                    .fireImmune()
                    .sized(0.98F, 0.98F)
                    .eyeHeight(0.15F)
                    .clientTrackingRange(10)
                    .build("sculk_tnt"));

    public static final Supplier<EntityType<ThunderBallEntity>> THUNDER_BALL_ENTITY =
            ENTITY_TYPES.register("thunder_ball", () -> EntityType.Builder.<ThunderBallEntity>of(ThunderBallEntity::new, MobCategory.MISC)
                    .fireImmune()
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(2)
                    .build("thunder_ball"));

    public static final Supplier<EntityType<MahjongEntity>> MAHJONG_ENTITY =
            ENTITY_TYPES.register("mahjong_entity", () -> EntityType.Builder.<MahjongEntity>of(MahjongEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .build("mahjong_entity"));



    public static void register(IEventBus eventBus){ENTITY_TYPES.register(eventBus);}
}
