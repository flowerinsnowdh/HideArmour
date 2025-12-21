package cn.flowerinsnow.hidearmour.client.listener;

import cn.flowerinsnow.hidearmour.client.HideArmour;
import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerBlockHeadGetterEvent;
import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerEquipmentGetterEvent;
import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerWornHeadGetterEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.SkullBlock;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public record HideArmourListener(HideArmour main) implements ClientPlayerEquipmentGetterEvent, ClientPlayerWornHeadGetterEvent.Type, ClientPlayerWornHeadGetterEvent.Profile, ClientPlayerBlockHeadGetterEvent {
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
    public @Nullable SkullBlock.Type onClientPlayerWornHeadTypeGet(@Nullable SkullBlock.Type originalType) {
        if (this.main.config().enable() && this.main.config().headSkull()) {
            return null;
        }
        return originalType;
    }

    @Override
    public @Nullable ResolvableProfile onClientPlayerWornHeadProfileGet(@Nullable ResolvableProfile originalProfile) {
        if (this.main.config().enable() && this.main.config().headSkull()) {
            return null;
        }
        return originalProfile;
    }

    @Override
    public InteractionResult onClientPlayerBlockHeadGet(ItemModelResolver itemModelResolver, ItemStackRenderState output, ItemStack item) {
        if (this.main.config().enable() && this.main.config().headBlock()) {
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }
}
