package net.Gmaj7.funny_world.daiInit;

import com.mojang.serialization.Codec;
import net.Gmaj7.funny_world.FunnyWorld;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class daiAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> DAI_ATTCHMENT_TYPE = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, FunnyWorld.MODID);

    public static final Supplier<AttachmentType<Integer>> DAI_TOTEM = DAI_ATTCHMENT_TYPE.register("dai_totem",
            () -> AttachmentType.builder(() -> 1).serialize(Codec.INT).build());

    public static final Supplier<AttachmentType<Float>> TEMPERATURE = DAI_ATTCHMENT_TYPE.register("temperature",
            () -> AttachmentType.builder(() -> 1.0F).serialize(Codec.FLOAT).build());

    public static final Supplier<AttachmentType<Integer>> FISSION_ARROW = DAI_ATTCHMENT_TYPE.register("fission_arrow",
            () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    public static final Supplier<AttachmentType<Integer>> THUNDER_HIT = DAI_ATTCHMENT_TYPE.register("thunder_hit",
            () -> AttachmentType.builder(() -> 1).serialize(Codec.INT).build());

    public static void register(IEventBus eventBus){DAI_ATTCHMENT_TYPE.register(eventBus);}
}