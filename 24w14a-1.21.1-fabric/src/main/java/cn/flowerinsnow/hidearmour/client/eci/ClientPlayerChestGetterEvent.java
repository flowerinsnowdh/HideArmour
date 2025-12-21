package cn.flowerinsnow.hidearmour.client.eci;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public interface ClientPlayerChestGetterEvent {
    Event<ClientPlayerChestGetterEvent> EVENT = EventFactory.createArrayBacked(ClientPlayerChestGetterEvent.class, listeners -> originalGet -> {
        for (ClientPlayerChestGetterEvent listener : listeners) {
            ItemStack itemStack = listener.onClientPlayerChestGet(originalGet);
            if (itemStack != originalGet) {
                return itemStack;
            }
        }
        return originalGet;
    });

    ItemStack onClientPlayerChestGet(ItemStack originalGet);
}
