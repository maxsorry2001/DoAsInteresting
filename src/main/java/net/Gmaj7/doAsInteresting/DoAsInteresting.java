package net.Gmaj7.doAsInteresting;

import com.mojang.logging.LogUtils;
import net.Gmaj7.doAsInteresting.daiBlocks.daiBlocks;
import net.Gmaj7.doAsInteresting.daiEffects.daiMobEffects;
import net.Gmaj7.doAsInteresting.daiEnchantments.daiEnchantments;
import net.Gmaj7.doAsInteresting.daiEntities.daiEntities;
import net.Gmaj7.doAsInteresting.daiEntities.renderer.SculkTntRenderer;
import net.Gmaj7.doAsInteresting.daiInit.daiArmorMaterials;
import net.Gmaj7.doAsInteresting.daiInit.daiAttachmentTypes;
import net.Gmaj7.doAsInteresting.daiInit.daiDataComponentTypes;
import net.Gmaj7.doAsInteresting.daiItems.daiItems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(DoAsInteresting.MODID)
public class DoAsInteresting
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "doasinteresting";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public DoAsInteresting(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        daiItems.DAI_ITEMS.register(modEventBus);
        daiBlocks.DAI_BLOCKS.register(modEventBus);
        DoAsInterestingTabs.DAI_CREATIVE_MODE_TABS.register(modEventBus);
        daiEntities.register(modEventBus);
        daiEnchantments.register(modEventBus);
        daiMobEffects.register(modEventBus);
        daiAttachmentTypes.register(modEventBus);
        daiDataComponentTypes.register(modEventBus);
        daiArmorMaterials.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(daiEntities.EXPLODE_STORAGE_ENTITY.get(), ThrownItemRenderer::new);
            EntityRenderers.register(daiEntities.NEGATIVE_CHARGE_ENTITY.get(), ThrownItemRenderer::new);
            EntityRenderers.register(daiEntities.ELECTRIC_CHARGE_ENTITY.get(), ThrownItemRenderer::new);
            EntityRenderers.register(daiEntities.SCULK_TNT.get(), SculkTntRenderer::new);
            EntityRenderers.register(daiEntities.JISTGABBURASH_ENTITY.get(), ThrownItemRenderer::new);
        }
    }
}
