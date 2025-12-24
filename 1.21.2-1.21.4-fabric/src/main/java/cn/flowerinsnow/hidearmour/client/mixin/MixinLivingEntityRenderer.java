package cn.flowerinsnow.hidearmour.client.mixin;

import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerBlockHeadGetterEvent;
import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerSkullHeadGetterEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.SkullBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
@Environment(EnvType.CLIENT)
public class MixinLivingEntityRenderer<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {
    @Unique
    private boolean clientPlayer;

    @Inject(
            method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;",
                    ordinal = 0
            )
    )
    public void captureVar1Entity(T entity, S state, float partialTicks, CallbackInfo ci) {
        this.clientPlayer = entity == MinecraftClient.getInstance().player;
    }

    @Redirect(
            method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;wearingSkullType:Lnet/minecraft/block/SkullBlock$SkullType;",
                    ordinal = 0,
                    opcode = Opcodes.PUTFIELD
            )
    )
    public void overwriteWornHeadType(LivingEntityRenderState instance, SkullBlock.SkullType value) {
        if (!this.clientPlayer) {
            instance.wearingSkullType = value;
            return;
        }
        instance.wearingSkullType = ClientPlayerSkullHeadGetterEvent.TYPE.invoker().onClientPlayerWornHeadTypeGet(value);
    }

    @Redirect(
            method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;wearingSkullProfile:Lnet/minecraft/component/type/ProfileComponent;",
                    ordinal = 0,
                    opcode = Opcodes.PUTFIELD
            )
    )
    public void overwriteWornHeadProfile(LivingEntityRenderState instance, ProfileComponent value) {
        if (!this.clientPlayer) {
            instance.wearingSkullProfile = value;
            return;
        }
        instance.wearingSkullProfile = ClientPlayerSkullHeadGetterEvent.PROFILE.invoker().onClientPlayerWornHeadProfileGet(value);
    }

    @Redirect(
            method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/item/ItemModelManager;updateForLivingEntity(Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/entity/LivingEntity;)V"
            )
    )
    public void overwriteItemModelResolverUpdateForLiving(ItemModelManager instance, ItemRenderState output, ItemStack item, ItemDisplayContext displayContext, LivingEntity entity) {
        if (!this.clientPlayer) {
            instance.updateForLivingEntity(output, item, displayContext, entity);
            return;
        }
        ActionResult interactionResult = ClientPlayerBlockHeadGetterEvent.EVENT.invoker().onClientPlayerBlockHeadGet(instance, output, item);
        if (interactionResult == ActionResult.FAIL) {
            output.clear();
        } else {
            instance.updateForLivingEntity(output, item, displayContext, entity);
        }
    }
}
