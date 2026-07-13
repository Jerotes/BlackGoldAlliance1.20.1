package com.jerotes.blackgoldalliance.client.model;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.animation.PiglinRaiderHoglinAnimation;
import com.jerotes.blackgoldalliance.entity.Animal.PiglinRaiderHoglinEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;

public class Modelpiglin_raider_hoglin<T extends PiglinRaiderHoglinEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BGA.MODID, "piglin_raider_hoglin"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;

	public Modelpiglin_raider_hoglin(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.body = root.getChild("body");
		this.head = this.body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(1, 1).addBox(-8.0F, -7.0F, -13.0F, 16.0F, 14.0F, 26.0F, new CubeDeformation(0.0F))
				.texOffs(0, 64).addBox(-6.0F, -10.5F, -12.25F, 12.0F, 6.0F, 13.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 7.0F, 4.5F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(0, 83).addBox(-6.5F, 0.0F, 0.0F, 12.0F, 6.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -10.5F, 0.75F, -0.48F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -13.0F));

		PartDefinition head_rotation = head.addOrReplaceChild("head_rotation", CubeListBuilder.create().texOffs(61, 1).addBox(-7.0F, -3.0F, -19.0F, 14.0F, 6.0F, 19.0F, new CubeDeformation(0.0F))
				.texOffs(1, 13).addBox(6.0F, -9.0F, -13.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(10, 13).addBox(-8.0F, -9.0F, -13.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(6.0F, -2.0F, -3.0F));

		PartDefinition left_ear_rotation = left_ear.addOrReplaceChild("left_ear_rotation", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.5F, 1.75F, 3.0F, 0.8727F, 0.0F, 0.0F));

		PartDefinition left_ear_rotation2 = left_ear_rotation.addOrReplaceChild("left_ear_rotation2", CubeListBuilder.create().texOffs(1, 6).addBox(0.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, -1.75F, -3.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(-6.0F, -2.0F, -3.0F));

		PartDefinition right_ear_rotation = right_ear.addOrReplaceChild("right_ear_rotation", CubeListBuilder.create(), PartPose.offsetAndRotation(6.5F, 1.75F, 3.0F, 0.8727F, 0.0F, 0.0F));

		PartDefinition right_ear_rotation2 = right_ear_rotation.addOrReplaceChild("right_ear_rotation2", CubeListBuilder.create().texOffs(1, 1).addBox(-6.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, -1.75F, -3.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition mane = body.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(90, 33).addBox(0.0F, 0.0F, -9.0F, 0.0F, 10.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, -7.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(41, 42).addBox(-3.0F, 0.0F, -2.75F, 6.0F, 14.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 10.0F, -4.75F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(66, 42).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 10.0F, -4.5F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 45).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 13.0F, 14.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(21, 45).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, 13.0F, 14.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t.isBaby()) {
			this.head.xScale = 1.2f;
			this.head.yScale = 1.2f;
			this.head.zScale = 1.2f;
		}
		else {
			this.head.xScale = 1f;
			this.head.yScale = 1f;
			this.head.zScale = 1f;
		}
		this.applyHeadRotation(t, f4, f5, f3);
		if (t.isVehicle() && t.getControllingPassenger() == t.getOwner()) {
			this.animateWalk(PiglinRaiderHoglinAnimation.RIDERUN, f, f2, 2.0f, 2.0f);
		}
		else if (t.isVehicle() && t.getControllingPassenger() instanceof Mob mob && mob.isAggressive()) {
			this.animateWalk(PiglinRaiderHoglinAnimation.RUN, f, f2, 2.0f, 2.0f);
		}
		else if (t.isAggressive()) {
			this.animateWalk(PiglinRaiderHoglinAnimation.RUN, f, f2, 2.0f, 2.0f);
		}
		else {
			this.animateWalk(PiglinRaiderHoglinAnimation.WALK, f, f2, 2.0f, 2.0f);
		}
		this.animate(t.idleAnimationState, PiglinRaiderHoglinAnimation.IDLE, f3);
		this.animate(t.attack1AnimationState, PiglinRaiderHoglinAnimation.ATTACK1, f3);
		this.animate(t.attack2AnimationState, PiglinRaiderHoglinAnimation.ATTACK2, f3);
		this.animate(t.attack3AnimationState, PiglinRaiderHoglinAnimation.ATTACK3, f3);
		this.animate(t.sitAnimationState, PiglinRaiderHoglinAnimation.SIT, f3);
		this.animate(t.toSitAnimationState, PiglinRaiderHoglinAnimation.TOSIT, f3);
		this.animate(t.stopSitAnimationState, PiglinRaiderHoglinAnimation.STOPSIT, f3);
	}
	private void applyHeadRotation(T t, float f, float f2, float f3) {
		f = Mth.clamp(f, -60.0f, 60.0f);
		f2 = Mth.clamp(f2, -60.0f, 60.0f);
		this.head.yRot = f * ((float) Math.PI / 180F);
		this.head.xRot = f2 * ((float) Math.PI / 180F);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
