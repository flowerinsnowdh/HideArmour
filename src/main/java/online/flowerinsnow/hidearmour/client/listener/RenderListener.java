package online.flowerinsnow.hidearmour.client.listener;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.collection.DefaultedList;
import online.flowerinsnow.hidearmour.client.config.Config;
import online.flowerinsnow.hidearmour.client.eci.RenderPlayerEntityCallback;

@Environment(EnvType.CLIENT)
public class RenderListener implements RenderPlayerEntityCallback.Pre, RenderPlayerEntityCallback.Post {
    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;

    @Override
    public ActionResult preRenderPlayer(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (abstractClientPlayerEntity.equals(mc.player)) {
            if (!Config.getConfig().getBooleanValue("enable") || !abstractClientPlayerEntity.equals(mc.player)) { // 未开启功能或渲染的不是当前玩家
                return ActionResult.PASS;
            }
            ClientPlayerEntity player = mc.player;
            PlayerInventory inventory = player.getInventory();
            DefaultedList<ItemStack> armourInventory = inventory.armor;
            // 在渲染玩家的数据前，先把玩家的护甲保存起来然后清空
            if (Config.getConfig().getBooleanValue("helmet")) {
                helmet = armourInventory.get(3);
                armourInventory.set(3, ItemStack.EMPTY);
            }
            if (Config.getConfig().getBooleanValue("chestplate")) {
                chestplate = armourInventory.get(2);
                armourInventory.set(2, ItemStack.EMPTY);
            }
            if (Config.getConfig().getBooleanValue("leggings")) {
                leggings = armourInventory.get(1);
                armourInventory.set(1, ItemStack.EMPTY);
            }
            if (Config.getConfig().getBooleanValue("boots")) {
                boots = armourInventory.get(0);
                armourInventory.set(0, ItemStack.EMPTY);
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public ActionResult postRenderPlayer(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (!abstractClientPlayerEntity.equals(mc.player)) { // 未开启功能或渲染的不是当前玩家
            return ActionResult.PASS;
        }
        if ((helmet == null || helmet == ItemStack.EMPTY)
                && (chestplate == null || chestplate == ItemStack.EMPTY)
                && (leggings == null || chestplate == ItemStack.EMPTY)
                && (boots == null || boots == ItemStack.EMPTY)) { // 全为null，直接不管
            return ActionResult.PASS;
        }
        ClientPlayerEntity player = mc.player;
        PlayerInventory inventory = player.getInventory();
        DefaultedList<ItemStack> armourInventory = inventory.armor;
        // 在渲染完成玩家后，再将护甲加回来
        if (helmet != null && helmet != ItemStack.EMPTY) {
            armourInventory.set(3, helmet);
        }
        if (chestplate != null && chestplate != ItemStack.EMPTY) {
            armourInventory.set(2, chestplate);
        }
        if (leggings != null && leggings != ItemStack.EMPTY) {
            armourInventory.set(1, leggings);
        }
        if (boots != null && boots != ItemStack.EMPTY) {
            armourInventory.set(0, boots);
        }
        helmet = chestplate = leggings = boots = null;
        return ActionResult.PASS;
    }
}
