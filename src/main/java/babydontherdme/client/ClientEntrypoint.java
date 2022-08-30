package babydontherdme.client;

import babydontherdme.Entrypoint;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ClientEntrypoint implements ClientModInitializer {
    private KeyBinding whistle;
    private boolean pressedBefore = false;
    private int whistleCooldown = 0;
    @Override
    public void onInitializeClient() {
        whistle = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.baby_dont_herd_me.whistle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                "category.baby_dont_herd_me.herding"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            boolean pressedNow = whistle.isPressed();
            if(pressedNow){
                if(!this.pressedBefore && this.whistleCooldown==0){
                    this.whistleCooldown = 12;
                    ClientPlayNetworking.send(Entrypoint.WHISTLE_PACKET, PacketByteBufs.empty());
                }
            }
            this.pressedBefore = pressedNow;
            if (this.whistleCooldown > 0) {
                --this.whistleCooldown;
            }
        });
    }
}
