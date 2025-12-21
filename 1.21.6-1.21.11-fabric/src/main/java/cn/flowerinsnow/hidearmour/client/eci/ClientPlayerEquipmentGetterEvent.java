package cn.flowerinsnow.hidearmour.client.eci;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public interface ClientPlayerEquipmentGetterEvent {
    Event<ClientPlayerEquipmentGetterEvent> EVENT = EventFactory.createArrayBacked(ClientPlayerEquipmentGetterEvent.class, listeners -> ((equipmentSlot, originalGet) -> {
        for (ClientPlayerEquipmentGetterEvent listener : listeners) {
            ItemStack itemStack = listener.onClientPlayerEquipmentGet(equipmentSlot, originalGet);
            if (itemStack != originalGet) {
                return itemStack;
            }
        }
        return originalGet;
    }));

    ItemStack onClientPlayerEquipmentGet(EquipmentSlot equipmentSlot, ItemStack originalGet);
}
