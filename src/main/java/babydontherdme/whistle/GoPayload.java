package babydontherdme.whistle;

import babydontherdme.BabyDontHerdMe;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record GoPayload() implements CustomPayload {

	public static final Identifier WHISTLE_PACKET_GO = BabyDontHerdMe.identify("go");
    public static final GoPayload.Id<GoPayload> ID = new Id<>(WHISTLE_PACKET_GO);
    public static final PacketCodec<RegistryByteBuf, GoPayload> CODEC = PacketCodec.unit(new GoPayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
