package com.jerotes.blackgoldalliance.client.model;

import com.jerotes.blackgoldalliance.BGA;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class Modelblack_gold_cavalry_armor extends HumanoidModel<LivingEntity> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BGA.MODID, "black_gold_cavalry_armor"), "main");
	public final ModelPart head;
	public final ModelPart body;
	public final ModelPart left_arm;
	public final ModelPart right_arm;
	public final ModelPart left_leg;
	public final ModelPart right_leg;

	public Modelblack_gold_cavalry_armor(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.5F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.1F))
				.texOffs(96, 13).addBox(0.0F, -16.5F, -4.0F, 0.0F, 14.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(112, 20).addBox(-1.0F, -13.6F, -0.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(32, 0).addBox(-4.0F, -9.5F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.5F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offsetAndRotation(0.1542F, -2.15F, -4.9322F, 0.3491F, 0.0F, 0.0F));

		PartDefinition head_r1 = mouth.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(56, 0).addBox(-3.9496F, -1.6879F, -3.9538F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(-0.1542F, 0.0F, 3.5322F, 0.0F, -0.829F, 0.0F));

		PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -7.28F, 1.1218F, -0.8727F, 0.0F, 0.0F));

		PartDefinition horn_r1 = horn.addOrReplaceChild("horn_r1", CubeListBuilder.create().texOffs(78, 0).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.2654F, -0.53F, 1.1218F, 0.4363F, -0.1745F, -0.0698F));

		PartDefinition horn_r2 = horn.addOrReplaceChild("horn_r2", CubeListBuilder.create().texOffs(64, 0).addBox(-1.5F, -1.5F, -4.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.25F, 0.53F, -1.1218F, 0.4363F, -0.1745F, -0.0698F));

		PartDefinition horn_r3 = horn.addOrReplaceChild("horn_r3", CubeListBuilder.create().texOffs(64, 0).mirror().addBox(-1.5F, -1.5F, -4.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(78, 0).mirror().addBox(1.5F, -1.5F, 2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.25F, 0.53F, -1.1218F, 0.4363F, 0.1745F, 0.0698F));

		PartDefinition mouth2 = head.addOrReplaceChild("mouth2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.1542F, -4.35F, -4.5322F, -0.3054F, 0.0F, 0.0F));

		PartDefinition head_r2 = mouth2.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(64, 11).addBox(-4.0443F, -2.6907F, -4.0406F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(-0.1542F, 0.0F, 3.5322F, 0.0F, -0.829F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition jacket = body.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition jacket_r1 = jacket.addOrReplaceChild("jacket_r1", CubeListBuilder.create().texOffs(64, 20).addBox(-4.0F, -3.5F, -2.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(1.105F)), PartPose.offsetAndRotation(0.0F, 2.7524F, -0.359F, 0.0873F, 0.0F, 0.0F));

		PartDefinition jacket_r2 = jacket.addOrReplaceChild("jacket_r2", CubeListBuilder.create().texOffs(96, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(-3.5F, 11.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition jacket_r3 = jacket.addOrReplaceChild("jacket_r3", CubeListBuilder.create().texOffs(96, 16).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(1.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, 11.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

		PartDefinition jacket_r4 = jacket.addOrReplaceChild("jacket_r4", CubeListBuilder.create().texOffs(112, 8).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.5F, 2.85F, 0.0436F, 0.0F, 0.0F));

		PartDefinition jacket_r5 = jacket.addOrReplaceChild("jacket_r5", CubeListBuilder.create().texOffs(112, 8).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.5F, -2.9F, -0.0436F, 0.0F, 0.0F));

		PartDefinition front = jacket.addOrReplaceChild("front", CubeListBuilder.create(), PartPose.offset(0.0F, 9.5F, -2.75F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.775F))
				.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.2F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition left_arm_r1 = left_arm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(84, 5).addBox(-0.4272F, -4.0F, -3.5F, 1.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9272F, -0.4395F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition left_arm_r2 = left_arm.addOrReplaceChild("left_arm_r2", CubeListBuilder.create().texOffs(112, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.25F)), PartPose.offsetAndRotation(0.5F, -0.2F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.775F))
				.texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.2F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition right_arm_r1 = right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(84, 5).addBox(-0.6772F, -4.0F, -3.5F, 1.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8228F, -0.4395F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition right_arm_r2 = right_arm.addOrReplaceChild("right_arm_r2", CubeListBuilder.create().texOffs(96, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.25F)), PartPose.offsetAndRotation(-1.5F, -0.2F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.505F)), PartPose.offset(1.9F, 12.0F, 0.0F));

		PartDefinition left_pants = left_leg.addOrReplaceChild("left_pants", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.51F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition right_pants = right_leg.addOrReplaceChild("right_pants", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.751F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	public Modelblack_gold_cavalry_armor getArmor(LivingEntity entity){
		return this;
	}



	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}