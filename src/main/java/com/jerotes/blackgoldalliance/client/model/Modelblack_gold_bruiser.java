package com.jerotes.blackgoldalliance.client.model;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.animation.BlackGoldBruiserAnimation;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldBruiserEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldPiglinEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import org.joml.Vector3f;

public class Modelblack_gold_bruiser<T extends BlackGoldBruiserEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BGA.MODID, "black_gold_bruiser"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart up_body;
	private final ModelPart head;
	private final ModelPart left_ear;
	private final ModelPart right_ear;
	private final ModelPart left_arm;
	private final ModelPart left_hand;
	private final ModelPart right_arm;
	private final ModelPart right_hand;
	private final ModelPart down_body;
	private final ModelPart skirt;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public Modelblack_gold_bruiser(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.up_body = this.body.getChild("up_body");
		this.head = this.up_body.getChild("head");
		this.left_ear = this.head.getChild("left_ear");
		this.right_ear = this.head.getChild("right_ear");
		this.left_arm = this.up_body.getChild("left_arm");
		this.left_hand = this.left_arm.getChild("left_hand");
		this.right_arm = this.up_body.getChild("right_arm");
		this.right_hand = this.right_arm.getChild("right_hand");
		this.down_body = this.body.getChild("down_body");
		this.skirt = this.down_body.getChild("skirt");
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

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -20.0F, -1.75F));

		PartDefinition up_body = body.addOrReplaceChild("up_body", CubeListBuilder.create().texOffs(0, 31).addBox(-12.0F, -10.7778F, -5.5F, 24.0F, 13.0F, 14.0F, new CubeDeformation(0.05F))
				.texOffs(102, 24).addBox(0.1F, -14.7778F, 7.5F, 0.0F, 16.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(102, 24).addBox(-0.1F, -14.7778F, 7.5F, 0.0F, 16.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(64, 18).addBox(-12.5F, -8.7778F, -6.5F, 12.0F, 10.0F, 2.0F, new CubeDeformation(0.3F))
				.texOffs(64, 18).mirror().addBox(0.5F, -8.7778F, -6.5F, 12.0F, 10.0F, 2.0F, new CubeDeformation(0.3F)).mirror(false)
				.texOffs(0, 18).addBox(-11.0F, -13.7778F, -2.5F, 22.0F, 3.0F, 10.0F, new CubeDeformation(0.0025F))
				.texOffs(76, 45).addBox(-15.0F, -10.7778F, -1.5F, 30.0F, 8.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 4.7778F, 0.25F));

		PartDefinition hair_r1 = up_body.addOrReplaceChild("hair_r1", CubeListBuilder.create().texOffs(102, 24).addBox(-0.05F, -14.0F, -2.5F, 0.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.05F, -0.7778F, 9.0F, 0.0F, -0.1309F, 0.0F));

		PartDefinition hair_r2 = up_body.addOrReplaceChild("hair_r2", CubeListBuilder.create().texOffs(102, 24).addBox(0.05F, -9.0F, 0.25F, 0.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.05F, -5.7778F, 6.25F, 0.0F, 0.1309F, 0.0F));

		PartDefinition head = up_body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -9.0F, -8.0F, 12.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(42, 0).addBox(-6.0F, -10.0F, -6.0F, 12.0F, 10.0F, 8.0F, new CubeDeformation(0.1F))
				.texOffs(62, 17).addBox(0.0F, -13.0F, -8.0F, 0.0F, 13.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(92, 0).addBox(-2.0F, -5.0F, -13.0F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(80, 4).mirror().addBox(2.0F, -2.0F, -10.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(88, 3).mirror().addBox(2.0F, -4.0F, -12.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(88, 3).addBox(-3.0F, -4.0F, -12.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(74, 2).mirror().addBox(4.0F, -4.0F, -10.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(74, 2).addBox(-5.0F, -4.0F, -10.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(80, 4).addBox(-4.0F, -2.0F, -10.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.7778F, 4.5F));

		PartDefinition hair_r3 = head.addOrReplaceChild("hair_r3", CubeListBuilder.create().texOffs(62, 17).addBox(-0.05F, -17.5F, -10.25F, 0.0F, 13.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.05F, 5.0F, 1.75F, 0.0F, 0.1309F, 0.0F));

		PartDefinition hair_r4 = head.addOrReplaceChild("hair_r4", CubeListBuilder.create().texOffs(62, 17).addBox(0.0F, -6.5F, -7.5F, 0.0F, 13.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -6.0F, -1.0F, 0.0F, -0.1309F, 0.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(82, 8).mirror().addBox(0.0F, -1.0F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.5F, -7.0F, -1.0F, -0.7854F, -0.1396F, -0.6109F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(82, 8).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, -7.0F, -1.0F, -0.7854F, 0.1396F, 0.6109F));

		PartDefinition left_arm = up_body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(184, 0).mirror().addBox(-2.0F, -2.0F, -4.0F, 8.0F, 16.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(12.0F, -9.7778F, 1.5F));

		PartDefinition left_arm_r1 = left_arm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(184, 41).mirror().addBox(-5.0F, -6.5F, -3.5F, 10.0F, 13.0F, 7.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(4.0F, 2.5F, 0.5F, 0.0F, 1.5708F, 0.0F));

		PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(184, 61).mirror().addBox(-4.0F, -1.0F, -2.25F, 8.0F, 16.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(184, 41).mirror().addBox(-5.0F, -1.0F, 0.75F, 10.0F, 13.0F, 7.0F, new CubeDeformation(0.05F)).mirror(false)
				.texOffs(184, 25).mirror().addBox(-4.5F, -5.0F, -2.75F, 9.0F, 6.0F, 10.0F, new CubeDeformation(0.1F)).mirror(false)
				.texOffs(184, 25).mirror().addBox(-4.5F, -5.0F, -2.75F, 9.0F, 6.0F, 10.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(2.0F, 15.0F, -1.75F));

		PartDefinition left_shock = left_hand.addOrReplaceChild("left_shock", CubeListBuilder.create().texOffs(209, 31).mirror().addBox(-7.0F, 0.0F, -7.0F, 14.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.5F, 6.0F, 2.0F));

		PartDefinition right_arm = up_body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(184, 0).addBox(-6.0F, -2.0F, -4.0F, 8.0F, 16.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, -9.7778F, 1.5F));

		PartDefinition right_arm_r1 = right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(184, 41).addBox(-5.0F, -6.5F, -3.5F, 10.0F, 13.0F, 7.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(-4.0F, 2.5F, 0.5F, 0.0F, -1.5708F, 0.0F));

		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(184, 61).addBox(-4.0F, 0.0F, -2.25F, 8.0F, 16.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(184, 41).addBox(-5.0F, 0.0F, 0.75F, 10.0F, 13.0F, 7.0F, new CubeDeformation(0.05F))
				.texOffs(184, 25).addBox(-4.5F, -4.0F, -2.75F, 9.0F, 6.0F, 10.0F, new CubeDeformation(0.1F)), PartPose.offset(-2.0F, 14.0F, -1.75F));

		PartDefinition right_shock = right_hand.addOrReplaceChild("right_shock", CubeListBuilder.create().texOffs(209, 31).mirror().addBox(-7.0F, 0.0F, -7.0F, 14.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, 7.0F, 2.0F));

		PartDefinition down_body = body.addOrReplaceChild("down_body", CubeListBuilder.create().texOffs(0, 92).addBox(-14.0F, -0.5F, -8.5F, 28.0F, 17.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 58).addBox(-12.0F, -3.5F, -6.5F, 24.0F, 20.0F, 14.0F, new CubeDeformation(0.075F))
				.texOffs(92, 16).addBox(0.0F, -7.5F, 6.5F, 0.0F, 24.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.5F, 1.25F));

		PartDefinition down_body_r1 = down_body.addOrReplaceChild("down_body_r1", CubeListBuilder.create().texOffs(92, 16).addBox(-0.05F, -1.0F, 0.25F, 0.0F, 24.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.05F, -6.5F, 5.25F, 0.0F, 0.1309F, 0.0F));

		PartDefinition down_body_r2 = down_body.addOrReplaceChild("down_body_r2", CubeListBuilder.create().texOffs(92, 16).addBox(0.05F, -6.0F, -2.5F, 0.0F, 24.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.05F, -1.5F, 8.0F, 0.0F, -0.1309F, 0.0F));

		PartDefinition skirt = down_body.addOrReplaceChild("skirt", CubeListBuilder.create().texOffs(0, 125).addBox(-14.0F, 1.0F, -8.0F, 28.0F, 14.0F, 15.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 12.5F, 0.5F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(218, 0).mirror().addBox(-5.0F, 0.0F, -5.0F, 10.0F, 22.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(6.0F, 2.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(218, 0).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 22.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 2.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}


	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(t, f4, f5, f3);

		//行走
		float attack = 1 - t.attackAnimProgress / 10f;
		if (t.getRushUseTick() <= 0) {
			this.animateWalk(BlackGoldBruiserAnimation.WALK_POSE, f, f2 * attack, 2.0f, 2.0f);
			this.animateWalk(BlackGoldBruiserAnimation.WALK, f, f2 * (1 - attack), 2.0f, 2.0f);
		}
		//站立
		this.animate(t.idleAnimationState, BlackGoldBruiserAnimation.IDLE, f3);
		this.animateIdle3(t, t.idleBaseAnimationState, BlackGoldBruiserAnimation.IDLEBASE_POSE, f3);
		this.animateIdle2(t, t.idleBaseAnimationState, BlackGoldBruiserAnimation.IDLEBASE_NORMAL, f3);

		this.animate(t.stompAnimationState, BlackGoldBruiserAnimation.STOMP, f3);
		this.animate(t.fist1AnimationState, BlackGoldBruiserAnimation.FIST_ATTACK_LEFT, f3);
		this.animate(t.fist2AnimationState, BlackGoldBruiserAnimation.FIST_ATTACK_RIGHT, f3);
		this.animate(t.fist3AnimationState, BlackGoldBruiserAnimation.FIST_ATTACK_TWO, f3);
		this.animate(t.palm1AnimationState, BlackGoldBruiserAnimation.PALM_ATTACK_LEFT, f3);
		this.animate(t.palm2AnimationState, BlackGoldBruiserAnimation.PALM_ATTACK_RIGHT, f3);
		this.animate(t.palm3AnimationState, BlackGoldBruiserAnimation.PALM_ATTACK_TWO, f3);
		this.animate(t.rushAnimationState, BlackGoldBruiserAnimation.RUSH, f3);

		this.animate(t.danceAnimationState, BlackGoldBruiserAnimation.DANCE, f3);
		this.animate(t.deadAnimationState, BlackGoldBruiserAnimation.DEAD, f3);

		float f23 = Mth.clamp(f2, -120.0f, 120.0f);
		if (t.getAttackTick() <= 40 && t.getAttackTick() > 0 && t.getAttackUse() != 5) {
			if (t.getAttackTick() <= 10) {
				f23 *= 1 - (10 - t.getAttackTick()) / 10f;
			}
			if (t.getAttackTick() > 30) {
				f23 *= 1 - (t.getAttackTick() - 30) / 10f;
			}
			this.left_arm.xRot += f23 * Mth.DEG_TO_RAD;
			this.right_arm.xRot += f23 * Mth.DEG_TO_RAD;
		}
	}

	private void applyHeadRotation(T t, float f, float f2, float f3) {
		f = Mth.clamp(f, -60.0f, 60.0f);
		f2 = Mth.clamp(f2, -60.0f, 60.0f);
		this.head.resetPose();
		if (t.isAlive()) {
			this.head.yRot = f * Mth.DEG_TO_RAD;
			this.head.xRot = f2 * Mth.DEG_TO_RAD;
		}
	}
	protected void animateIdle2(BlackGoldPiglinEntity blackGoldPiglinEntity, AnimationState animationState,
								AnimationDefinition animation, float ageInTicks) {
		animationState.updateTime(ageInTicks, 1.0F);
		animationState.ifStarted((state) -> {
			KeyframeAnimations.animate(this, animation,
					animationState.getAccumulatedTime(),
					Math.min(1, blackGoldPiglinEntity.attackAnimProgress / 10f),
					new Vector3f());
		});
	}
	protected void animateIdle3(BlackGoldPiglinEntity blackGoldPiglinEntity, AnimationState animationState,
								AnimationDefinition animation, float ageInTicks) {
		animationState.updateTime(ageInTicks, 1.0F);
		animationState.ifStarted((state) -> {
			KeyframeAnimations.animate(this, animation,
					animationState.getAccumulatedTime(),
					Math.min(1, 1 - blackGoldPiglinEntity.attackAnimProgress / 10f),
					new Vector3f());
		});
	}


	public ModelPart root() {
		return this.root;
	}
}