package online.flowerinsnow.hidearmour.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import online.flowerinsnow.hidearmour.config.Config;

@SideOnly(Side.CLIENT)
public class RenderListener {
    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;

    @SubscribeEvent
    public void preRenderEntity(RenderLivingEvent.Pre<?> e) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityLivingBase entity = e.getEntity();
        if (!Config.enable.getBoolean() || !entity.equals(mc.player)) { // 未开启功能或渲染的不是当前玩家
            return;
        }
        EntityPlayerSP player = mc.player;
        InventoryPlayer inventory = player.inventory;
        NonNullList<ItemStack> armorInventory = inventory.armorInventory;
        // 在渲染玩家的数据前，先把玩家的护甲保存起来然后清空
        if (Config.helmet.getBoolean()) {
            helmet = armorInventory.get(3);
            armorInventory.set(3, ItemStack.EMPTY);
        }
        if (Config.chestplate.getBoolean()) {
            chestplate = armorInventory.get(2);
            armorInventory.set(2, ItemStack.EMPTY);
        }
        if (Config.leggings.getBoolean()) {
            leggings = armorInventory.get(1);
            armorInventory.set(1, ItemStack.EMPTY);
        }
        if (Config.boots.getBoolean()) {
            boots = armorInventory.get(0);
            armorInventory.set(0, ItemStack.EMPTY);
        }
    }

    @SubscribeEvent
    public void postRenderEntity(RenderLivingEvent.Post<?> e) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityLivingBase entity = e.getEntity();
        if (!entity.equals(Minecraft.getMinecraft().player)) { // 未开启功能或渲染的不是当前玩家
            return;
        }
        if ((helmet == null || helmet == ItemStack.EMPTY)
                && (chestplate == null || chestplate == ItemStack.EMPTY)
                && (leggings == null || chestplate == ItemStack.EMPTY)
                && (boots == null || boots == ItemStack.EMPTY)) { // 全为null，直接不管
            return;
        }
        EntityPlayerSP player = mc.player;
        InventoryPlayer inventory = player.inventory;
        NonNullList<ItemStack> armourInventory = inventory.armorInventory;
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
    }
}
