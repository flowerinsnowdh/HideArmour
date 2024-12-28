package cn.flowerinsnow.hidearmour.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

import java.util.Objects;

public class PreRenderOwnArmourEvent extends Event implements ICancellableEvent {
    private HumanoidArmorLayer<? extends HumanoidRenderState, ? extends HumanoidModel<? extends HumanoidRenderState>, ? extends HumanoidModel<? extends HumanoidRenderState>> renderer;
    private PoseStack matrices;
    private MultiBufferSource vertexConsumers;
    private ItemStack stack;
    private EquipmentSlot slot;
    private int light;
    private HumanoidModel<? extends HumanoidRenderState> armorModel;

    public PreRenderOwnArmourEvent(HumanoidArmorLayer<? extends HumanoidRenderState, ? extends HumanoidModel<? extends HumanoidRenderState>, ? extends HumanoidModel<? extends HumanoidRenderState>> renderer, PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, EquipmentSlot slot, int light, HumanoidModel<? extends HumanoidRenderState> armorModel) {
        this.renderer = renderer;
        this.matrices = matrices;
        this.vertexConsumers = vertexConsumers;
        this.stack = stack;
        this.slot = slot;
        this.light = light;
        this.armorModel = armorModel;
    }

    public HumanoidArmorLayer<? extends HumanoidRenderState, ? extends HumanoidModel<? extends HumanoidRenderState>, ? extends HumanoidModel<? extends HumanoidRenderState>> getRenderer() {
        return this.renderer;
    }

    public void setRenderer(HumanoidArmorLayer<? extends HumanoidRenderState, ? extends HumanoidModel<? extends HumanoidRenderState>, ? extends HumanoidModel<? extends HumanoidRenderState>> renderer) {
        this.renderer = renderer;
    }

    public PoseStack getMatrices() {
        return this.matrices;
    }

    public void setMatrices(PoseStack matrices) {
        this.matrices = matrices;
    }

    public MultiBufferSource getVertexConsumers() {
        return this.vertexConsumers;
    }

    public void setVertexConsumers(MultiBufferSource vertexConsumers) {
        this.vertexConsumers = vertexConsumers;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public EquipmentSlot getSlot() {
        return this.slot;
    }

    public void setSlot(EquipmentSlot slot) {
        this.slot = slot;
    }

    public int getLight() {
        return this.light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public HumanoidModel<? extends HumanoidRenderState> getArmorModel() {
        return this.armorModel;
    }

    public void setArmorModel(HumanoidModel<? extends HumanoidRenderState> armorModel) {
        this.armorModel = armorModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreRenderOwnArmourEvent that = (PreRenderOwnArmourEvent) o;
        return this.light == that.light && Objects.equals(this.renderer, that.renderer) && Objects.equals(this.matrices, that.matrices) && Objects.equals(this.vertexConsumers, that.vertexConsumers) && Objects.equals(this.stack, that.stack) && this.slot == that.slot && Objects.equals(this.armorModel, that.armorModel);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.renderer != null ? renderer.hashCode() : 0);
        result = 31 * result + (this.matrices != null ? matrices.hashCode() : 0);
        result = 31 * result + (this.vertexConsumers != null ? vertexConsumers.hashCode() : 0);
        result = 31 * result + (this.stack != null ? stack.hashCode() : 0);
        result = 31 * result + (this.slot != null ? slot.hashCode() : 0);
        result = 31 * result + this.light;
        result = 31 * result + (this.armorModel != null ? armorModel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PreRenderArmourEvent{" +
                "renderer=" + this.renderer +
                ", matrices=" + this.matrices +
                ", vertexConsumers=" + this.vertexConsumers +
                ", stack=" + this.stack +
                ", slot=" + this.slot +
                ", light=" + this.light +
                ", armorModel=" + this.armorModel +
                '}';
    }
}
