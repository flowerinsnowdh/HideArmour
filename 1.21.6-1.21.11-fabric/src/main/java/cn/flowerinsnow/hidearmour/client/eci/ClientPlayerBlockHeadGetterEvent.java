package cn.flowerinsnow.hidearmour.client.eci;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;

public interface ClientPlayerBlockHeadGetterEvent {
    Event<ClientPlayerBlockHeadGetterEvent> EVENT = EventFactory.createArrayBacked(ClientPlayerBlockHeadGetterEvent.class, listeners -> (itemModelResolver, output, item) -> {
        for (ClientPlayerBlockHeadGetterEvent listener : listeners) {
            InteractionResult interactionResult = listener.onClientPlayerBlockHeadGet(itemModelResolver, output, item);
            if (interactionResult != InteractionResult.PASS) {
                return interactionResult;
            }
        }
        return InteractionResult.PASS;
    });

    InteractionResult onClientPlayerBlockHeadGet(ItemModelResolver itemModelResolver, ItemStackRenderState output, ItemStack item);
}
