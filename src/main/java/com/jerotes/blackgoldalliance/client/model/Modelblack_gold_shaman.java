package com.jerotes.blackgoldalliance.client.model;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.animation.BlackGoldShamanAnimation;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldShamanEntity;
import com.jerotes.blackgoldalliance.init.BGAItems;
import com.jerotes.jerotes.client.model.Modelspecial_action;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class Modelblack_gold_shaman<T extends BlackGoldShamanEntity> extends Modelspecial_action<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BGA.MODID, "black_gold_shaman"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart left_ear;
	private final ModelPart right_ear;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart left_weapon;
	private final ModelPart right_arm;
	private final ModelPart right_weapon;
	private final ModelPart staff;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public Modelblack_gold_shaman(ModelPart root) {
		super(root);
		this.root = root;
		this.head = root.getChild("head");
		this.left_ear = this.head.getChild("left_ear");
		this.right_ear = this.head.getChild("right_ear");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.left_weapon = this.left_arm.getChild("left_weapon");
		this.right_arm = root.getChild("right_arm");
		this.right_weapon = this.right_arm.getChild("right_weapon");
		this.staff = this.right_weapon.getChild("staff");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("ear", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("cloak", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(36, 0).addBox(-5.0F, -8.0F, -4.0F, 10.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-5.0F, -8.0F, -4.0F, 10.0F, 10.0F, 8.0F, new CubeDeformation(0.35F))
				.texOffs(0, 102).addBox(-5.0F, -8.0F, -4.2F, 10.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(48, 67).addBox(-2.0F, -4.0F, -6.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(58, 64).addBox(3.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(58, 67).addBox(2.0F, -1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(58, 64).addBox(-4.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(58, 67).addBox(-3.0F, -1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, -6.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(48, 58).addBox(0.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -6.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(48, 58).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -6.0F, 0.0F, 0.0F, 0.0F, 0.6109F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 44).addBox(-7.0F, -5.0F, -3.0F, 14.0F, 28.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(70, 0).addBox(-10.0F, 22.9F, -7.0F, 20.0F, 0.0F, 18.0F, new CubeDeformation(0.0F))
				.texOffs(0, 18).addBox(-7.0F, -5.0F, -3.0F, 14.0F, 16.0F, 10.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 1.0F, -1.0F));

		PartDefinition jacket_r1 = body.addOrReplaceChild("jacket_r1", CubeListBuilder.create().texOffs(0, 82).addBox(-8.0F, -8.0F, -2.0F, 16.0F, 16.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 16.0F, 6.0F, 3.0543F, 0.0F, 3.1416F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(48, 24).mirror().addBox(-1.0F, -4.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(64, 24).mirror().addBox(-1.0F, -4.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(8.0F, 7.0F, 1.0F));

		PartDefinition left_weapon = left_arm.addOrReplaceChild("left_weapon", CubeListBuilder.create(), PartPose.offset(1.0F, 8.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(48, 24).addBox(-3.0F, -4.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(64, 24).addBox(-3.0F, -4.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-8.0F, 7.0F, 1.0F));

		PartDefinition right_weapon = right_arm.addOrReplaceChild("right_weapon", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 0.0F));

		PartDefinition staff = right_weapon.addOrReplaceChild("staff", CubeListBuilder.create().texOffs(72, 84).addBox(-3.5F, -18.45F, 5.5F, 3.0F, 13.0F, 3.0F, new CubeDeformation(-0.09F))
				.texOffs(72, 65).addBox(-2.01F, -17.25F, 7.5F, 0.0F, 13.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(72, 65).addBox(-1.99F, -17.25F, 7.5F, 0.0F, 13.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(86, 76).addBox(-3.0F, -18.25F, 1.75F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.01F))
				.texOffs(84, 84).addBox(-2.01F, -17.25F, 1.5F, 0.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(84, 84).addBox(-1.99F, -17.25F, 1.5F, 0.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(64, 64).addBox(-3.0F, -7.25F, 6.0F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.01F))
				.texOffs(64, 93).addBox(-3.0F, -0.25F, 6.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(2.0F, 7.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition staff_r1 = staff.addOrReplaceChild("staff_r1", CubeListBuilder.create().texOffs(86, 64).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2.0F, -13.25F, 8.5F, -0.3927F, 0.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(48, 42).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(64, 42).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(3.0F, 12.0F, 1.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(48, 42).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(64, 42).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-3.0F, 12.0F, 1.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}


	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(t, f4, f5, f3);
		//行走
		this.animateWalk(BlackGoldShamanAnimation.WALK, f, f2, 2.0f, 2.0f);
		if (!t.getMainHandItem().isEmpty()) {
			this.animate(t.idleAnimationState, BlackGoldShamanAnimation.IDLESTAFF, f3);
		}
		this.animate(t.idleAnimationState, BlackGoldShamanAnimation.IDLE, f3);
		this.animate(t.danceAnimationState, BlackGoldShamanAnimation.DANCE, f3);
		this.animate(t.longAttack1AnimationState, BlackGoldShamanAnimation.ATTACK1, f3);
		this.animate(t.longAttack2AnimationState, BlackGoldShamanAnimation.ATTACK2, f3);
		this.animate(t.deadAnimationState, BlackGoldShamanAnimation.DEAD, f3);

		this.staff.visible = t.getMainHandItem().is(BGAItems.BLACK_GOLD_SHAMAN_STAFF.get());

		this.hat.copyFrom(this.head);
		this.jacket.copyFrom(this.body);
		this.left_sleeve.copyFrom(this.left_arm);
		this.right_sleeve.copyFrom(this.right_arm);
		this.left_pants.copyFrom(this.left_leg);
		this.right_pants.copyFrom(this.right_leg);
	}

	private void applyHeadRotation(T t, float f, float f2, float f3) {
		f = Mth.clamp(f, -60.0f, 60.0f);
		f2 = Mth.clamp(f2, -20.0f, 20.0f);
		this.head.yRot = f * ((float) Math.PI / 180F);
		this.head.xRot = f2 * ((float) Math.PI / 180F);
	}

	public ModelPart root() {
		return this.root;
	}

	@Override
	public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
		this.getArm(humanoidArm).translateAndRotate(poseStack);
		this.getWeapon(humanoidArm).translateAndRotate(poseStack);
	}
	public void translateToHandOld(HumanoidArm humanoidArm, PoseStack poseStack) {
		this.getArm(humanoidArm).translateAndRotate(poseStack);
	}

	protected ModelPart getArm(HumanoidArm humanoidArm) {
		if (humanoidArm == HumanoidArm.LEFT) {
			return this.left_arm;
		}
		return this.right_arm;
	}
	protected ModelPart getWeapon(HumanoidArm humanoidArm) {
		if (humanoidArm == HumanoidArm.LEFT) {
			return this.left_weapon;
		}
		return this.right_weapon;
	}

	@Override
	public ModelPart getHead() {
		return this.head;
	}

	public void setAllVisible(boolean bl) {
		this.head.visible = bl;
		this.hat.visible = bl;
		this.body.visible = bl;
		this.right_arm.visible = bl;
		this.left_arm.visible = bl;
		this.right_leg.visible = bl;
		this.left_leg.visible = bl;
	}
}