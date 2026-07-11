package com.jerotes.blackgoldalliance.mixin;

import com.jerotes.blackgoldalliance.entity.Boss.TheBlackGoldMarshalEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldRuntEntity;
import com.jerotes.blackgoldalliance.init.BGAItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

    public HumanoidArmorLayerMixin(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Inject(method = "renderArmorPiece*", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;setPartVisibility(Lnet/minecraft/client/model/HumanoidModel;Lnet/minecraft/world/entity/EquipmentSlot;)V"), cancellable = true)
    public void renderArmorPieceAdd(PoseStack poseStack, MultiBufferSource multiBufferSource, T t, EquipmentSlot equipmentSlot, int n, A a, CallbackInfo ci) {
        ItemStack itemstack = t.getItemBySlot(equipmentSlot);
        Item item = itemstack.getItem();
        if (t instanceof TheBlackGoldMarshalEntity) {
            if (item == BGAItems.THE_BLACK_GOLD_MARSHAL_HELMET.get()) {
                ci.cancel();
            }
            if (item == BGAItems.THE_BLACK_GOLD_MARSHAL_CHESTPLATE.get()) {
                ci.cancel();
            }
            if (item == BGAItems.THE_BLACK_GOLD_MARSHAL_LEGGINGS.get()) {
                ci.cancel();
            }
            if (item == BGAItems.THE_BLACK_GOLD_MARSHAL_BOOTS.get()) {
                ci.cancel();
            }
        }
        if (t instanceof BlackGoldRuntEntity) {
            a.body.yScale *= 0.75f;
            a.leftArm.xScale *= 0.75f;
            a.rightArm.xScale *= 0.75f;
            a.leftArm.yScale *= 0.75f;
            a.rightArm.yScale *= 0.75f;
            a.leftArm.zScale *= 0.75f;
            a.rightArm.zScale *= 0.75f;
            a.leftLeg.yScale *= 0.5f;
            a.rightLeg.yScale *= 0.5f;
        }
    }
    @Inject(method = "renderTrim*", at = @At("HEAD"), cancellable = true)
    public void renderTrim1(ArmorMaterial armorMaterial, PoseStack poseStack, MultiBufferSource multiBufferSource, int n, ArmorTrim armorTrim, A model, boolean bl, CallbackInfo ci) {
        if (specialArmor().contains(armorMaterial.getName())) {
            ci.cancel();
        }
    }

    @Unique
    public List<String> specialArmor() {
        List<String> list = new ArrayList<>();
        list.add("black_gold_runt_armor");
        list.add("black_gold_warrior_armor");
        list.add("black_gold_mauler_armor");
        list.add("black_gold_hunter_armor");
        list.add("black_gold_cavalry_armor");
        list.add("the_black_gold_marshal_armor");

        list.add("oath_wither_squire_armor");
        return list;
    }
}