package net.Gmaj7.funny_world.daiInit;

import net.Gmaj7.funny_world.FunnyWorld;
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
}
