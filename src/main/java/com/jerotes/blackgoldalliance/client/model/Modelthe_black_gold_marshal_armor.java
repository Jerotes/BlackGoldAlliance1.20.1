package com.jerotes.blackgoldalliance.client.model;

import com.jerotes.blackgoldalliance.BGA;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class Modelthe_black_gold_marshal_armor extends HumanoidModel<LivingEntity> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BGA.MODID, "the_black_gold_marshal_armor"), "main");
	public final ModelPart head;
	public final ModelPart body;
	private final ModelPart chestplate;
	private final ModelPart cloak;
	public final ModelPart left_arm;
	public final ModelPart right_arm;
	public final ModelPart left_leg;
	public final ModelPart right_leg;

	public Modelthe_black_gold_marshal_armor(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.chestplate = this.body.getChild("chestplate");
		this.cloak = this.chestplate.getChild("cloak");
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

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.5F, -6.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition right_ear_helmet = right_ear.addOrReplaceChild("right_ear_helmet", CubeListBuilder.create().texOffs(144, 0).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(4.5F, -6.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

		PartDefinition left_ear_helmet = left_ear.addOrReplaceChild("left_ear_helmet", CubeListBuilder.create().texOffs(144, 0).addBox(0.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition helmet = head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.5F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.3F))
				.texOffs(32, 0).addBox(-4.0F, -8.5F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = helmet.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(24, 0).addBox(-4.0F, -2.0F, 0.0F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.5F, 5.5F, -0.3927F, 0.0F, 0.0F));

		PartDefinition mouth2 = helmet.addOrReplaceChild("mouth2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.1542F, -2.85F, -3.8322F, -0.3927F, 0.0F, 0.0F));

		PartDefinition head_r2 = mouth2.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(77, 11).addBox(-4.0443F, -2.6907F, -4.0406F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(-0.3542F, 0.0F, 3.5322F, 0.0F, -0.829F, 0.0F));

		PartDefinition mouth = helmet.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offsetAndRotation(0.1542F, -1.15F, -4.9322F, 0.2182F, 0.0F, 0.0F));

		PartDefinition head_r3 = mouth.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(56, 0).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(-0.2F, -0.1879F, 0.0684F, 0.0F, -0.829F, 0.0F));

		PartDefinition horn = helmet.addOrReplaceChild("horn", CubeListBuilder.create(), PartPose.offset(6.2391F, -10.0637F, 0.4449F));

		PartDefinition horn_r1 = horn.addOrReplaceChild("horn_r1", CubeListBuilder.create().texOffs(64, 11).addBox(-1.5F, -1.5F, -4.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.9891F, 3.3137F, -0.1949F, 0.1309F, -0.1745F, -0.0698F));

		PartDefinition horn_r2 = horn.addOrReplaceChild("horn_r2", CubeListBuilder.create().texOffs(64, 11).mirror().addBox(-1.5F, -1.5F, -4.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.4891F, 3.3137F, -0.1949F, 0.1309F, 0.1745F, 0.0698F));

		PartDefinition horn_r3 = horn.addOrReplaceChild("horn_r3", CubeListBuilder.create().texOffs(78, 0).addBox(-1.5F, -3.5F, 2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(154, 0).addBox(-1.5F, -1.5F, -4.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.25F))
				.texOffs(64, 0).addBox(-1.5F, -1.5F, -4.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.4891F, -0.6863F, -1.4449F, 0.8874F, -0.6977F, 0.001F));

		PartDefinition horn_r4 = horn.addOrReplaceChild("horn_r4", CubeListBuilder.create().texOffs(64, 0).mirror().addBox(-1.5F, -1.5F, -4.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(154, 0).mirror().addBox(-1.5F, -1.5F, -4.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(78, 0).mirror().addBox(-1.5F, -3.5F, 2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0109F, -0.6863F, -1.4449F, 0.8874F, 0.6977F, -0.001F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition chestplate = body.addOrReplaceChild("chestplate", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(128, 15).addBox(-5.0F, 8.0F, -4.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.15F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cloak = chestplate.addOrReplaceChild("cloak", CubeListBuilder.create().texOffs(184, 0).addBox(-4.9F, -0.2867F, -2.3745F, 12.0F, 25.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(112, 8).addBox(2.5F, 0.1389F, -1.9829F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(3.0F, -2.0F, 2.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition jacket = chestplate.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition jacket_r1 = jacket.addOrReplaceChild("jacket_r1", CubeListBuilder.create().texOffs(64, 20).addBox(-4.0F, -3.5F, -2.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(1.105F)), PartPose.offsetAndRotation(0.0F, 2.7524F, -0.859F, 0.0873F, 0.0F, 0.0F));

		PartDefinition leggings = body.addOrReplaceChild("leggings", CubeListBuilder.create().texOffs(112, 16).addBox(-4.0F, -0.1F, -3.4F, 8.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(112, 16).addBox(-4.0F, -0.1F, 3.1F, 8.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition jacket_r2 = leggings.addOrReplaceChild("jacket_r2", CubeListBuilder.create().texOffs(96, 16).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(1.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, 3.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

		PartDefinition jacket_r3 = leggings.addOrReplaceChild("jacket_r3", CubeListBuilder.create().texOffs(96, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(-3.5F, 3.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition left_armor = left_arm.addOrReplaceChild("left_armor", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.775F))
				.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm_r1 = left_armor.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(128, 0).addBox(-0.4272F, -4.0F, -3.5F, 1.0F, 8.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.9272F, -0.4395F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition left_arm_r2 = left_armor.addOrReplaceChild("left_arm_r2", CubeListBuilder.create().texOffs(112, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.25F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition left_arm_r3 = left_armor.addOrReplaceChild("left_arm_r3", CubeListBuilder.create().texOffs(112, 8).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.15F)), PartPose.offsetAndRotation(1.4848F, -1.4236F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition left_weapon = left_arm.addOrReplaceChild("left_weapon", CubeListBuilder.create(), PartPose.offset(2.0F, 8.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition right_armor = right_arm.addOrReplaceChild("right_armor", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.775F))
				.texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_arm_r1 = right_armor.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(128, 0).addBox(-0.6772F, -4.0F, -3.5F, 1.0F, 8.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.8228F, -0.4395F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition right_arm_r2 = right_armor.addOrReplaceChild("right_arm_r2", CubeListBuilder.create().texOffs(96, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.25F)), PartPose.offsetAndRotation(-1.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition right_arm_r3 = right_armor.addOrReplaceChild("right_arm_r3", CubeListBuilder.create().texOffs(96, 8).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.15F)), PartPose.offsetAndRotation(-1.5F, -1.25F, 0.0F, 0.0F, 0.0F, -0.2182F));

		PartDefinition right_weapon = right_arm.addOrReplaceChild("right_weapon", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));

		PartDefinition left_boots = left_leg.addOrReplaceChild("left_boots", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_leggings = left_leg.addOrReplaceChild("left_leggings", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.505F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition right_boots = right_leg.addOrReplaceChild("right_boots", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.751F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_leggings = right_leg.addOrReplaceChild("right_leggings", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.51F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 128);
	}

	public Modelthe_black_gold_marshal_armor getArmor(LivingEntity entity){
		Minecraft minecraft = Minecraft.getInstance();
		float limbSwingAmount = entity.walkAnimation.speed(minecraft.getFrameTime());
		float limbSwing = entity.walkAnimation.position(minecraft.getFrameTime());
		float f = Mth.cos(limbSwing * 0.6662f) * limbSwingAmount;
		float f1 = Mth.cos(limbSwing * 0.6662f + (float) Math.PI) * limbSwingAmount;
		float f2 = Math.min(f, f1);
		this.cloak.xRot += f2 * -0.75f;
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