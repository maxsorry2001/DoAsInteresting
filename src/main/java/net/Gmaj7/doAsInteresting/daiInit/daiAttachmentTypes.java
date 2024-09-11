package net.Gmaj7.doAsInteresting.daiInit;

import com.mojang.serialization.Codec;
import net.Gmaj7.doAsInteresting.DoAsInteresting;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class daiAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> DAI_ATTCHMENT_TYPE = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, DoAsInteresting.MODID);

    public static final Supplier<AttachmentType<Integer>> DAI_TOTEM = DAI_ATTCHMENT_TYPE.register("dai_totem",
            () -> AttachmentType.builder(() -> 1).serialize(Codec.INT).build());

    public static final Supplier<AttachmentType<Float>> TEMPERATURE = DAI_ATTCHMENT_TYPE.register("temperature",
            () -> AttachmentType.builder(() -> 1.0F).serialize(Codec.FLOAT).build());

    public static final Supplier<AttachmentType<Integer>> FISSION_ARROW = DAI_ATTCHMENT_TYPE.register("fission_arrow",
            () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    public static void register(IEventBus eventBus){DAI_ATTCHMENT_TYPE.register(eventBus);}
}
