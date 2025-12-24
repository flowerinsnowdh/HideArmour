package cn.flowerinsnow.hidearmour.client.mixin;

import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerBlockHeadGetterEvent;
import cn.flowerinsnow.hidearmour.client.eci.ClientPlayerSkullHeadGetterEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.SkullBlock;
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
            method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;",
                    ordinal = 0
            )
    )
    public void captureVar1Entity(T entity, S state, float partialTicks, CallbackInfo ci) {
        this.clientPlayer = entity == Minecraft.getInstance().player;
    }

    @Redirect(
            method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;wornHeadType:Lnet/minecraft/world/level/block/SkullBlock$Type;",
                    ordinal = 0,
                    opcode = Opcodes.PUTFIELD
            )
    )
    public void overwriteWornHeadType(LivingEntityRenderState instance, SkullBlock.Type value) {
        if (!this.clientPlayer) {
            instance.wornHeadType = value;
            return;
        }
        instance.wornHeadType = ClientPlayerSkullHeadGetterEvent.TYPE.invoker().onClientPlayerWornHeadTypeGet(value);
    }

    @Redirect(
            method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;wornHeadProfile:Lnet/minecraft/world/item/component/ResolvableProfile;",
                    ordinal = 0,
                    opcode = Opcodes.PUTFIELD
            )
    )
    public void overwriteWornHeadProfile(LivingEntityRenderState instance, ResolvableProfile value) {
        if (!this.clientPlayer) {
            instance.wornHeadProfile = value;
            return;
        }
        instance.wornHeadProfile = ClientPlayerSkullHeadGetterEvent.PROFILE.invoker().onClientPlayerWornHeadProfileGet(value);
    }

    @Redirect(
            method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/item/ItemModelResolver;updateForLiving(Lnet/minecraft/client/renderer/item/ItemStackRenderState;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lnet/minecraft/world/entity/LivingEntity;)V"
            )
    )
    public void overwriteItemModelResolverUpdateForLiving(ItemModelResolver instance, ItemStackRenderState output, ItemStack item, ItemDisplayContext displayContext, LivingEntity entity) {
        if (!this.clientPlayer) {
            instance.updateForLiving(output, item, displayContext, entity);
            return;
        }
        InteractionResult interactionResult = ClientPlayerBlockHeadGetterEvent.EVENT.invoker().onClientPlayerBlockHeadGet(instance, output, item);
        if (interactionResult == InteractionResult.FAIL) {
            output.clear();
        } else {
            instance.updateForLiving(output, item, displayContext, entity);
        }
    }
}
