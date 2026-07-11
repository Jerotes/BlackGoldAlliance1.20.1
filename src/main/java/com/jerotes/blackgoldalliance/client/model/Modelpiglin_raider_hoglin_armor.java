package com.jerotes.blackgoldalliance.client.model;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.entity.Animal.PiglinRaiderHoglinEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelpiglin_raider_hoglin_armor<T extends PiglinRaiderHoglinEntity> extends Modelpiglin_raider_hoglin<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BGA.MODID, "piglin_raider_hoglin_armor"), "main");

	public Modelpiglin_raider_hoglin_armor(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(148, 0).addBox(-7.0F, -7.0F, -1.25F, 14.0F, 14.0F, 14.0F, new CubeDeformation(1.05F))
				.texOffs(92, 0).addBox(-7.0F, -7.0F, -12.75F, 14.0F, 14.0F, 14.0F, new CubeDeformation(1.25F))
				.texOffs(92, 28).addBox(-7.0F, -7.0F, -6.25F, 14.0F, 14.0F, 14.0F, new CubeDeformation(1.1F)), PartPose.offset(0.0F, 7.0F, 4.5F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -13.0F));

		PartDefinition head_rotation = head.addOrReplaceChild("head_rotation", CubeListBuilder.create().texOffs(0, 28).addBox(-7.0F, -0.1F, -18.75F, 14.0F, 3.0F, 14.0F, new CubeDeformation(0.55F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

		PartDefinition head_armor_r1 = head_rotation.addOrReplaceChild("head_armor_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -7.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.45F)), PartPose.offsetAndRotation(0.0F, 4.0F, -6.5F, -1.5708F, 0.0F, 0.0F));

		PartDefinition head_rotation_r1 = head_rotation.addOrReplaceChild("head_rotation_r1", CubeListBuilder.create().texOffs(42, 5).mirror().addBox(-2.0F, -3.5F, -1.0F, 4.0F, 7.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(7.0F, -2.8481F, -12.2302F, 0.0F, -1.5708F, 0.0F));

		PartDefinition head_rotation_r2 = head_rotation.addOrReplaceChild("head_rotation_r2", CubeListBuilder.create().texOffs(42, 5).addBox(-2.0F, -3.5F, -1.0F, 4.0F, 7.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-7.0F, -2.8481F, -12.2302F, 0.0F, -1.5708F, 0.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(6.0F, -2.0F, -3.0F));

		PartDefinition left_ear_rotation = left_ear.addOrReplaceChild("left_ear_rotation", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.5F, 1.75F, 3.0F, 0.8727F, 0.0F, 0.0F));

		PartDefinition left_ear_rotation2 = left_ear_rotation.addOrReplaceChild("left_ear_rotation2", CubeListBuilder.create(), PartPose.offsetAndRotation(6.5F, -1.75F, -3.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(-6.0F, -2.0F, -3.0F));

		PartDefinition right_ear_rotation = right_ear.addOrReplaceChild("right_ear_rotation", CubeListBuilder.create(), PartPose.offsetAndRotation(6.5F, 1.75F, 3.0F, 0.8727F, 0.0F, 0.0F));

		PartDefinition right_ear_rotation2 = right_ear_rotation.addOrReplaceChild("right_ear_rotation2", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.5F, -1.75F, -3.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition mane = body.addOrReplaceChild("mane", CubeListBuilder.create(), PartPose.offset(0.0F, -14.0F, -7.0F));

		PartDefinition neck_armor_r1 = mane.addOrReplaceChild("neck_armor_r1", CubeListBuilder.create().texOffs(204, 0).addBox(-2.0F, -16.0F, -2.0F, 4.0F, 32.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.95F, 5.9F, 1.5708F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(32, 69).mirror().addBox(-4.0F, -5.5F, -3.65F, 8.0F, 22.0F, 8.0F, new CubeDeformation(-0.7F)).mirror(false), PartPose.offset(4.0F, 10.0F, -4.75F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(32, 69).addBox(-4.0F, -5.5F, -3.9F, 8.0F, 22.0F, 8.0F, new CubeDeformation(-0.7F)), PartPose.offset(-4.0F, 10.0F, -4.5F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(96, 69).mirror().addBox(-4.0F, -8.5F, -3.75F, 8.0F, 22.0F, 8.0F, new CubeDeformation(-1.05F)).mirror(false), PartPose.offset(4.5F, 13.0F, 14.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(96, 69).addBox(-4.0F, -8.5F, -3.75F, 8.0F, 22.0F, 8.0F, new CubeDeformation(-1.05F)), PartPose.offset(-4.5F, 13.0F, 14.0F));

		return LayerDefinition.create(meshdefinition, 256, 128);
	}
}
