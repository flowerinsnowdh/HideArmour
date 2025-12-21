package cn.flowerinsnow.hidearmour.client.listener;

import cn.flowerinsnow.hidearmour.client.HideArmour;
import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerChestGetterEvent;
import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerArmourGetterEvent;
import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerHeadGetterEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;

@Environment(EnvType.CLIENT)
public record HideArmourListener(HideArmour main) implements ClientPlayerArmourGetterEvent, ClientPlayerHeadGetterEvent, ClientPlayerChestGetterEvent {
    @Override
    public ItemStack onClientPlayerEquipmentGet(EquipmentSlot equipmentSlot, ItemStack originalGet) {
        if (!this.main.config().enable() || originalGet.isEmpty()) {
            return originalGet;
        }

        switch (equipmentSlot) {
            case HEAD -> {
                return this.main.config().headHelmet() ? ItemStack.EMPTY : originalGet;
            }
            case CHEST -> {
                return this.main.config().chestChestplate() ? ItemStack.EMPTY : originalGet;
            }
            case LEGS -> {
                return this.main.config().leggings() ? ItemStack.EMPTY : originalGet;
            }
            case FEET -> {
                return this.main.config().boots() ? ItemStack.EMPTY : originalGet;
            }
        }

        return originalGet;
    }

    @Override
    public ItemStack onClientPlayerHeadGet(ItemStack originalGet) {
        if (!this.main.config().enable() || originalGet.isEmpty()) {
            return originalGet;
        }
        Item item = originalGet.getItem();
        if (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof AbstractSkullBlock) {
            return this.main.config().headSkull() ? ItemStack.EMPTY : originalGet;
        } else if (!(item instanceof ArmorItem armorItem && armorItem.getSlotType() == EquipmentSlot.HEAD)) {
            return this.main.config().headBlock() ? ItemStack.EMPTY : originalGet;
        }
        return originalGet;
    }

    @Override
    public ItemStack onClientPlayerChestGet(ItemStack originalGet) {
        if (!this.main.config().enable()) {
            return originalGet;
        }
        if (originalGet.isOf(Items.ELYTRA)) {
            return this.main.config().chestElytra() ? ItemStack.EMPTY : originalGet;
        } else {
            return this.main.config().chestChestplate() ? ItemStack.EMPTY : originalGet;
        }
    }
}
