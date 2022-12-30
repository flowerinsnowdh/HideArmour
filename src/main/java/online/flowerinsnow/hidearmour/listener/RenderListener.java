package online.flowerinsnow.hidearmour.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
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
        if (!Config.enable.getBoolean() || !e.entity.equals(Minecraft.getMinecraft().thePlayer)) { // 未开启功能或渲染的不是当前玩家
            return;
        }
        EntityPlayerSP player = mc.thePlayer;
        InventoryPlayer inventory = player.inventory;
        ItemStack[] armorInventory = inventory.armorInventory;
        // 在渲染玩家的数据前，先把玩家的护甲保存起来然后清空
        if (Config.helmet.getBoolean()) {
            helmet = armorInventory[3];
            armorInventory[3] = null;
        }
        if (Config.chestplate.getBoolean()) {
            chestplate = armorInventory[2];
            armorInventory[2] = null;
        }
        if (Config.leggings.getBoolean()) {
            leggings = armorInventory[1];
            armorInventory[1] = null;
        }
        if (Config.boots.getBoolean()) {
            boots = armorInventory[0];
            armorInventory[0] = null;
        }
    }

    @SubscribeEvent
    public void postRenderEntity(RenderLivingEvent.Post<?> e) {
        Minecraft mc = Minecraft.getMinecraft();
        if (!e.entity.equals(Minecraft.getMinecraft().thePlayer)) { // 未开启功能或渲染的不是当前玩家
            return;
        }
        if (helmet == null && chestplate == null && leggings == null && boots == null) { // 全为null，直接不管
            return;
        }
        EntityPlayerSP player = mc.thePlayer;
        InventoryPlayer inventory = player.inventory;
        ItemStack[] armourInventory = inventory.armorInventory;
        // 在渲染完成玩家后，再将护甲加回来
        if (helmet != null) {
            armourInventory[3] = helmet;
        }
        if (chestplate != null) {
            armourInventory[2] = chestplate;
        }
        if (leggings != null) {
            armourInventory[1] = leggings;
        }
        if (boots != null) {
            armourInventory[0] = boots;
        }
        helmet = chestplate = leggings = boots = null;
    }
}
