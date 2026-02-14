package net.Gmaj7.funny_world.daiInit;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiInit.daiUniqueData.HumanitySet;
import net.Gmaj7.funny_world.daiInit.daiUniqueData.daiUniqueDataGet;
import net.Gmaj7.funny_world.daiItems.custom.MusicalInstrument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class daiPackets {
    public static class daiBellHelmetPacket implements CustomPacketPayload {
        private final BlockPos blockPos;

        public static final CustomPacketPayload.Type<daiBellHelmetPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "magic_select"));
        public static final StreamCodec<RegistryFriendlyByteBuf, daiBellHelmetPacket> STREAM_CODEC = CustomPacketPayload.codec(daiBellHelmetPacket::write, daiBellHelmetPacket::new);

        public daiBellHelmetPacket(BlockPos blockPos){
            this.blockPos = blockPos;
        }

        public daiBellHelmetPacket(FriendlyByteBuf buf){
            this.blockPos = buf.readBlockPos();
        }

        public void write(FriendlyByteBuf buf){
            buf.writeBlockPos(blockPos);
        }

        public static void handle(daiBellHelmetPacket packet, IPayloadContext context){
            context.enqueueWork(() -> {
                if (context.player() instanceof ServerPlayer serverPlayer){
                    serverPlayer.level().destroyBlock(packet.blockPos, true);
                }
            });
        }
        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static class daiHumanityPacket implements CustomPacketPayload {
        private final int dHumanity;

        public static final CustomPacketPayload.Type<daiHumanityPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "humanity"));
        public static final StreamCodec<RegistryFriendlyByteBuf, daiHumanityPacket> STREAM_CODEC = CustomPacketPayload.codec(daiHumanityPacket::write, daiHumanityPacket::new);

        public daiHumanityPacket(int humanity){
            this.dHumanity = humanity;
        }

        public daiHumanityPacket(FriendlyByteBuf buf){
            this.dHumanity = buf.readInt();
        }

        public void write(FriendlyByteBuf buf){
            buf.writeInt(dHumanity);
        }

        public static void handle(daiHumanityPacket packet, IPayloadContext context){
            context.enqueueWork(() -> {
                if (context.player().level().isClientSide()){
                    HumanitySet humanitySet = ((daiUniqueDataGet)context.player()).getHumanitySet();
                    humanitySet.setHumanity(packet.dHumanity);
                }
            });
        }
        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static class daiIceBoatPacket implements CustomPacketPayload {
        double x;
        double y;
        double z;
        float r;

        public static final CustomPacketPayload.Type<daiIceBoatPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "ice_boat"));
        public static final StreamCodec<RegistryFriendlyByteBuf, daiIceBoatPacket> STREAM_CODEC = CustomPacketPayload.codec(daiIceBoatPacket::write, daiIceBoatPacket::new);

        public daiIceBoatPacket(double x, double y, double z, float r){
            this.x = x;
            this.y = y;
            this.z = z;
            this.r = r;
        }

        public daiIceBoatPacket(FriendlyByteBuf buf){
            this.x = buf.readDouble();
            this.y = buf.readDouble();
            this.z = buf.readDouble();
            this.r = buf.readFloat();
        }

        public void write(FriendlyByteBuf buf){
            buf.writeDouble(x);
            buf.writeDouble(y);
            buf.writeDouble(z);
            buf.writeFloat(r);
        }

        public static void handle(daiIceBoatPacket packet, IPayloadContext context){
            context.enqueueWork(() -> {
                if (context.player() instanceof ServerPlayer serverPlayer){
                    serverPlayer.level().explode(null, packet.x, packet.y, packet.z, packet.r, false, Level.ExplosionInteraction.BLOCK);
                }
            });
        }
        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static class daiWaterBowPacket implements CustomPacketPayload {
        InteractionHand hand;

        public static final CustomPacketPayload.Type<daiWaterBowPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "water_bow"));
        public static final StreamCodec<RegistryFriendlyByteBuf, daiWaterBowPacket> STREAM_CODEC = CustomPacketPayload.codec(daiWaterBowPacket::write, daiWaterBowPacket::new);

        public daiWaterBowPacket(InteractionHand hand){
            this.hand = hand;
        }

        public daiWaterBowPacket(FriendlyByteBuf buf){
            this.hand = buf.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        }

        public void write(FriendlyByteBuf buf){
            buf.writeBoolean(hand == InteractionHand.MAIN_HAND);
        }

        public static void handle(daiWaterBowPacket packet, IPayloadContext context){
            context.enqueueWork(() -> {
                if (context.player() instanceof ServerPlayer serverPlayer){
                    ItemStack itemStack = serverPlayer.getItemInHand(packet.hand);
                    itemStack.set(daiDataComponentTypes.WATER_BOW_MODEL, (itemStack.get(daiDataComponentTypes.WATER_BOW_MODEL) + 1) % 3);
                }
            });
        }
        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static class musicalInstrumentPacket implements CustomPacketPayload {
        int note;

        public static final CustomPacketPayload.Type<musicalInstrumentPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(FunnyWorld.MODID, "musical_instrument_packet"));
        public static final StreamCodec<RegistryFriendlyByteBuf, musicalInstrumentPacket> STREAM_CODEC = CustomPacketPayload.codec(musicalInstrumentPacket::write, musicalInstrumentPacket::new);

        public musicalInstrumentPacket(int note){
            this.note = note;
        }

        public musicalInstrumentPacket(FriendlyByteBuf buf){
            this.note = buf.readInt();
        }

        public void write(FriendlyByteBuf buf){
            buf.writeInt(note);
        }

        public static void handle(musicalInstrumentPacket packet, IPayloadContext context){
            context.enqueueWork(() -> {
                if (context.player() instanceof ServerPlayer serverPlayer && serverPlayer.getMainHandItem().getItem() instanceof MusicalInstrument musicalInstrument){
                    musicalInstrument.playSound(serverPlayer.level(), serverPlayer, packet.note);
                }
            });
        }
        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}
