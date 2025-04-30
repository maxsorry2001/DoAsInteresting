package net.Gmaj7.funny_world;

import com.mojang.logging.LogUtils;
import net.Gmaj7.funny_world.daiBlocks.daiBlockEntities;
import net.Gmaj7.funny_world.daiBlocks.daiBlocks;
import net.Gmaj7.funny_world.daiEffects.daiMobEffects;
import net.Gmaj7.funny_world.daiEnchantments.daiEnchantmentEffects;
import net.Gmaj7.funny_world.daiEntities.daiEntities;
import net.Gmaj7.funny_world.daiEntities.renderer.*;
import net.Gmaj7.funny_world.daiFluids.BaseFluidType;
import net.Gmaj7.funny_world.daiFluids.daiFluidTypes;
import net.Gmaj7.funny_world.daiFluids.daiFluids;
import net.Gmaj7.funny_world.daiFluids.BaseFluidType;
import net.Gmaj7.funny_world.daiFluids.daiFluidTypes;
import net.Gmaj7.funny_world.daiFluids.daiFluids;
import net.Gmaj7.funny_world.daiInit.daiArmorMaterials;
import net.Gmaj7.funny_world.daiInit.daiAttachmentTypes;
import net.Gmaj7.funny_world.daiInit.daiDataComponentTypes;
import net.Gmaj7.funny_world.daiInit.daiItemProperties;
import net.Gmaj7.funny_world.daiItems.daiItems;
import net.Gmaj7.funny_world.daiItems.daiPotions;
import net.Gmaj7.funny_world.daiSounds.daiSounds;
import net.Gmaj7.funny_world.villager.daiVillagers;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
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
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(FunnyWorld.MODID)
public class FunnyWorld
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "funny_world";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public FunnyWorld(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        daiItems.DAI_ITEMS.register(modEventBus);
        daiBlocks.DAI_BLOCKS.register(modEventBus);
        daiPotions.register(modEventBus);
        daiBlockEntities.register(modEventBus);
        FunnyWorldTabs.DAI_CREATIVE_MODE_TABS.register(modEventBus);
        daiEntities.register(modEventBus);
        daiMobEffects.register(modEventBus);
        daiAttachmentTypes.register(modEventBus);
        daiDataComponentTypes.register(modEventBus);
        daiArmorMaterials.register(modEventBus);
        daiVillagers.register(modEventBus);
        daiSounds.register(modEventBus);
        daiEnchantmentEffects.register(modEventBus);
        daiFluidTypes.register(modEventBus);
        daiFluids.register(modEventBus);

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
            EntityRenderers.register(daiEntities.BRICK_ENTITY.get(), ThrownItemRenderer::new);
            EntityRenderers.register(daiEntities.NETHER_BRICK_ENTITY.get(), ThrownItemRenderer::new);
            EntityRenderers.register(daiEntities.THROWN_HYDROGEN_ENTITY.get(), ThrownItemRenderer::new);
            EntityRenderers.register(daiEntities.THROWN_OXYGEN_ENTITY.get(), ThrownItemRenderer::new);
            EntityRenderers.register(daiEntities.THROWN_CARBON_DIOXIDE_ENTITY.get(), ThrownItemRenderer::new);
            EntityRenderers.register(daiEntities.THROWN_CARBON_MONOXIDE_ENTITY.get(), ThrownItemRenderer::new);
            EntityRenderers.register(daiEntities.JISTGABBURASH_ENTITY.get(), ThrownItemRenderer::new);
            EntityRenderers.register(daiEntities.SCULK_TNT_ENTITY.get(), SculkTntRenderer::new);
            EntityRenderers.register(daiEntities.ELECTROMAGNET_TNT_ENTITY.get(), ElectromagneticTntRenderer::new);
            EntityRenderers.register(daiEntities.IRON_SHOOT_ENTITY.get(), IronShootEntityRenderer::new);
            EntityRenderers.register(daiEntities.THUNDER_WEDGE_ENTITY.get(), ThunderWedgeRender::new);
            EntityRenderers.register(daiEntities.THUNDER_BALL_ENTITY.get(), ThunderBallRender::new);
            EntityRenderers.register(daiEntities.MAHJONG_ENTITY.get(), MahjongEntityRenderer::new);
            EntityRenderers.register(daiEntities.MOMENTUM_ARROW_ENTITY.get(), MomentumArrowRenderer::new);
            EntityRenderers.register(daiEntities.ENTITIES_ARROW_ENTITY.get(), EntitiesArrowRenderer::new);
            EntityRenderers.register(daiEntities.SLIME_FISHING_HOOK_ENTITY.get(), FishingHookRenderer::new);

            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(daiFluids.EXTRACTANT_FLOW.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(daiFluids.EXTRACTANT_STILL.get(), RenderType.translucent());
            });

            daiItemProperties.addCustomItemProperties();
        }

        @SubscribeEvent
        public static void fluidRegister(RegisterClientExtensionsEvent event){
            event.registerFluidType(daiFluidTypes.EXTRACTANT_FLUID.get().getExtensions(), daiFluidTypes.EXTRACTANT_FLUID);
        }
    }
}
