package babydontherdme.client;

import babydontherdme.whistle.GoPayload;
import babydontherdme.whistle.RecallPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class BabyDontHerdMeClient implements ClientModInitializer {
    private KeyBinding whistle_recall;
    private boolean recallPressedBefore = false;
    private int recallCooldown = 0;

    private KeyBinding whistle_go;
    private boolean goPressedBefore = false;
    private int goCooldown = 0;

    @Override
    public void onInitializeClient() {
        whistle_recall = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.baby_dont_herd_me.recall",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                "category.baby_dont_herd_me.herding"
        ));
        whistle_go = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.baby_dont_herd_me.go",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_M,
            "category.baby_dont_herd_me.herding"
    ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            boolean pressedNow = whistle_recall.isPressed();
            if(pressedNow){
                if(!this.recallPressedBefore && this.recallCooldown==0){
                    this.recallCooldown = 12;
                    ClientPlayNetworking.send(new RecallPayload());
                }
            }
            this.recallPressedBefore = pressedNow;
            if (this.recallCooldown > 0) {
                --this.recallCooldown;
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            boolean pressedNow = whistle_go.isPressed();
            if(pressedNow){
                if(!this.goPressedBefore && this.goCooldown==0){
                    this.goCooldown = 12;
                    ClientPlayNetworking.send(new GoPayload());
                }
            }
            this.goPressedBefore = pressedNow;
            if (this.goCooldown > 0) {
                --this.goCooldown;
            }
        });
    }
}
