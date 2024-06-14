package net.Gmaj7.doAsInteresting.daiEnchantments;

import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.Gmaj7.doAsInteresting.daiEnchantments.custom.*;
import net.Gmaj7.doAsInteresting.daiInit.daiTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class daiEnchantments {

    public static final DeferredRegister<Enchantment> dai_ENCHANTMENTS = DeferredRegister.create(Registries.ENCHANTMENT, DoAsInteresting.MODID);

    public static final DeferredHolder< Enchantment, Enchantment> EXPLOSION_GET = dai_ENCHANTMENTS.register("explosion_get",
            () -> new ExplosionGet(
                    Enchantment.definition(
                            daiTags.daiItemTags.CHEST_ARMOR_ENCHANTABLE,
                            1,
                            1,
                            Enchantment.dynamicCost(1, 10),
                            Enchantment.dynamicCost(10,10),
                            1,
                            EquipmentSlot.CHEST
                    )
            )
    );
    public static final DeferredHolder<Enchantment, Enchantment> SHIELD_STRIKE = dai_ENCHANTMENTS.register("shield_strike",
            () -> new ShieldStrike(
                    Enchantment.definition(
                            daiTags.daiItemTags.SHIELD_ENCHANTABLE,
                            1,
                            1,
                            Enchantment.dynamicCost(1, 10),
                            Enchantment.dynamicCost(10,10),
                            1,
                            EquipmentSlot.values()
                    )
            )
    );
    public static final DeferredHolder<Enchantment, Enchantment> PLUNDER = dai_ENCHANTMENTS.register("plunder",
            () -> new Plunder(
                    Enchantment.definition(
                            daiTags.daiItemTags.TOTEM_ENCHANTABLE,
                            1,
                            1,
                            Enchantment.dynamicCost(1,10),
                            Enchantment.dynamicCost(10,10),
                            1,
                            EquipmentSlot.values()
                    )
            )
    );
    public static final DeferredHolder<Enchantment, Enchantment> ELECTRIFICATION_BY_FRICTION = dai_ENCHANTMENTS.register("electrification_by_friction",
            () -> new ElectrificationByFriction(
                    Enchantment.definition(
                            daiTags.daiItemTags.CHEST_ARMOR_ENCHANTABLE,
                            1,
                            1,
                            Enchantment.dynamicCost(1,10),
                            Enchantment.dynamicCost(10, 10),
                            1,
                            EquipmentSlot.CHEST
                    )
            )
    );
    public static final DeferredHolder<Enchantment, Enchantment> FISSION = dai_ENCHANTMENTS.register("fission",
            () -> new Fission(
                    Enchantment.definition(
                            daiTags.daiItemTags.BOW_ENCHANTABLE,
                            1,
                            3,
                            Enchantment.dynamicCost(1, 10),
                            Enchantment.dynamicCost(10, 10),
                            1,
                            EquipmentSlot.values()
                    )
            )
    );


    public static void register(IEventBus eventBus){dai_ENCHANTMENTS.register(eventBus);}
}