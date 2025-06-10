package net.Gmaj7.funny_world.daiInit;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiInit.daiUniqueData.HumanitySet;
import net.Gmaj7.funny_world.daiInit.daiUniqueData.daiUniqueDataGet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
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
}
