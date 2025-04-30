package babydontherdme.whistle;

import babydontherdme.BabyDontHerdMe;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record RecallPayload() implements CustomPayload {

	public static final Identifier WHISTLE_PACKET_RECALL = BabyDontHerdMe.identify("recall");
    public static final RecallPayload.Id<RecallPayload> ID = new Id<>(WHISTLE_PACKET_RECALL);
    public static final PacketCodec<RegistryByteBuf, RecallPayload> CODEC = PacketCodec.unit(new RecallPayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
    
}
