package cn.flowerinsnow.hidearmour.client.eci;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public interface ClientPlayerHeadGetterEvent {
    Event<ClientPlayerHeadGetterEvent> EVENT = EventFactory.createArrayBacked(ClientPlayerHeadGetterEvent.class, listeners -> originalGet -> {
        for (ClientPlayerHeadGetterEvent listener : listeners) {
            ItemStack itemStack = listener.onClientPlayerHeadGet(originalGet);
            if (itemStack != originalGet) {
                return itemStack;
            }
        }
        return originalGet;
    });

    ItemStack onClientPlayerHeadGet(ItemStack originalGet);
}
