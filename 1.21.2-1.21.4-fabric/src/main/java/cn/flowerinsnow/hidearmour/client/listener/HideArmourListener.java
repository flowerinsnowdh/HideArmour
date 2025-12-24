package cn.flowerinsnow.hidearmour.client.listener;

import cn.flowerinsnow.hidearmour.client.HideArmour;
import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerBlockHeadGetterEvent;
import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerEquipmentGetterEvent;
import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerSkullHeadGetterEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.SkullBlock;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public record HideArmourListener(HideArmour main) implements ClientPlayerEquipmentGetterEvent, ClientPlayerSkullHeadGetterEvent.Type, ClientPlayerSkullHeadGetterEvent.Profile, ClientPlayerBlockHeadGetterEvent {
    @Override
    public ItemStack onClientPlayerEquipmentGet(EquipmentSlot equipmentSlot, ItemStack originalGet) {
        if (!this.main.config().enable() || originalGet.getItem() == Items.AIR) {
            return originalGet;
        }

        switch (equipmentSlot) {
            case HEAD -> {
                if (this.main.config().headHelmet()) {
                    return ItemStack.EMPTY;
                }
            }
            case CHEST -> {
                if (originalGet.getItem() == Items.ELYTRA) {
                    if (this.main.config().chestElytra()) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (this.main.config().chestChestplate()) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            case LEGS -> {
                if (this.main.config().leggings()) {
                    return ItemStack.EMPTY;
                }
            }
            case FEET -> {
                if (this.main.config().boots()) {
                    return ItemStack.EMPTY;
                }
            }
        }

        return originalGet;
    }

    @Override
    public @Nullable SkullBlock.SkullType onClientPlayerWornHeadTypeGet(@Nullable SkullBlock.SkullType originalType) {
        if (this.main.config().enable() && this.main.config().headSkull()) {
            return null;
        }
        return originalType;
    }

    @Override
    public @Nullable ProfileComponent onClientPlayerWornHeadProfileGet(@Nullable ProfileComponent originalProfile) {
        if (this.main.config().enable() && this.main.config().headSkull()) {
            return null;
        }
        return originalProfile;
    }

    @Override
    public ActionResult onClientPlayerBlockHeadGet(ItemModelManager itemModelResolver, ItemRenderState output, ItemStack item) {
        if (this.main.config().enable() && this.main.config().headBlock()) {
            return ActionResult.FAIL;
        }
        return ActionResult.PASS;
    }
}


