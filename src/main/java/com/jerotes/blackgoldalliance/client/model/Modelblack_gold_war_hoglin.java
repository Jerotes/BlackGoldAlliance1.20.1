package com.jerotes.blackgoldalliance.client.model;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.animation.BlackGoldWarHoglinAnimation;
import com.jerotes.blackgoldalliance.entity.Animal.BlackGoldWarHoglinEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

public class Modelblack_gold_war_hoglin<T extends BlackGoldWarHoglinEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BGA.MODID, "black_gold_war_hoglin"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;

	public Modelblack_gold_war_hoglin(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.body = root.getChild("body");
		this.head = this.body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -10.5F, -19.5F, 24.0F, 21.0F, 39.0F, new CubeDeformation(0.0F))
				.texOffs(87, 0).addBox(-9.0F, -16.0F, -18.5F, 18.0F, 8.0F, 17.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -1.5F, 2.75F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(157, 0).addBox(-9.0F, -4.0F, -8.5F, 18.0F, 8.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.5271F, 4.1926F, -0.48F, 0.0F, 0.0F));

		PartDefinition mane = body.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(0, 98).addBox(0.0F, 0.0F, -13.5F, 0.0F, 15.0F, 29.0F, new CubeDeformation(0.0F))
				.texOffs(0, 98).addBox(-3.0F, 2.0F, -9.5F, 0.0F, 15.0F, 29.0F, new CubeDeformation(0.0F))
				.texOffs(0, 98).addBox(3.0F, 2.0F, -9.5F, 0.0F, 15.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -21.0F, -10.5F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, -19.5F));

		PartDefinition head_rotation = head.addOrReplaceChild("head_rotation", CubeListBuilder.create().texOffs(0, 60).addBox(-10.5F, -4.5F, -28.5F, 21.0F, 9.0F, 29.0F, new CubeDeformation(0.0F))
				.texOffs(58, 128).mirror().addBox(9.0F, -14.0F, -19.5F, 3.0F, 17.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(70, 128).mirror().addBox(9.0F, -11.0F, -24.5F, 3.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(58, 128).addBox(-12.0F, -14.0F, -19.5F, 3.0F, 17.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(70, 128).addBox(-12.0F, -11.0F, -24.5F, 3.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(9.0F, -3.0F, -4.5F));

		PartDefinition left_ear_rotation = left_ear.addOrReplaceChild("left_ear_rotation", CubeListBuilder.create(), PartPose.offsetAndRotation(-9.75F, 2.625F, 4.5F, 0.8727F, 0.0F, 0.0F));

		PartDefinition left_ear_rotation2 = left_ear_rotation.addOrReplaceChild("left_ear_rotation2", CubeListBuilder.create().texOffs(100, 85).addBox(0.0F, -2.0F, -3.0F, 9.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.75F, -2.625F, -4.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(-9.0F, -3.0F, -4.5F));

		PartDefinition right_ear_rotation = right_ear.addOrReplaceChild("right_ear_rotation", CubeListBuilder.create(), PartPose.offsetAndRotation(9.75F, 2.625F, 4.5F, 0.8727F, 0.0F, 0.0F));

		PartDefinition right_ear_rotation2 = right_ear_rotation.addOrReplaceChild("right_ear_rotation2", CubeListBuilder.create().texOffs(100, 85).addBox(-9.0F, -2.0F, -3.0F, 9.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.75F, -2.625F, -4.5F, 0.0F, 0.0F, -0.7854F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(58, 98).mirror().addBox(-4.5F, 0.0F, -4.125F, 9.0F, 21.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(6.0F, 3.0F, -11.125F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(58, 98).addBox(-4.5F, 0.0F, -4.5F, 9.0F, 21.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 3.0F, -10.75F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(94, 99).mirror().addBox(-4.25F, -0.5F, -3.75F, 8.0F, 17.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(6.75F, 7.5F, 17.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(94, 99).addBox(-4.25F, -0.5F, -3.75F, 8.0F, 17.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.75F, 7.5F, 17.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
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
		this.head.yRot = f4 * ((float)Math.PI / 180F);
		this.head.xRot = f5 * ((float)Math.PI / 180F);
		if (t.isVehicle() && t.getControllingPassenger() == t.getOwner()) {
			this.animateWalk(BlackGoldWarHoglinAnimation.RIDERUN, f, f2, 2.0f, 2.0f);
		}
		else if (t.isVehicle() && t.getControllingPassenger() instanceof Mob mob && mob.isAggressive()) {
			this.animateWalk(BlackGoldWarHoglinAnimation.RUN, f, f2, 2.0f, 2.0f);
		}
		else if (t.isAggressive()) {
			this.animateWalk(BlackGoldWarHoglinAnimation.RUN, f, f2, 2.0f, 2.0f);
		}
		else {
			this.animateWalk(BlackGoldWarHoglinAnimation.WALK, f, f2, 2.0f, 2.0f);
		}
		this.animate(t.idleAnimationState, BlackGoldWarHoglinAnimation.IDLE, f3);
		this.animate(t.attack1AnimationState, BlackGoldWarHoglinAnimation.ATTACK1, f3);
		this.animate(t.attack2AnimationState, BlackGoldWarHoglinAnimation.ATTACK2, f3);
		this.animate(t.attack3AnimationState, BlackGoldWarHoglinAnimation.ATTACK3, f3);
		this.animate(t.sitAnimationState, BlackGoldWarHoglinAnimation.SIT, f3);
		this.animate(t.toSitAnimationState, BlackGoldWarHoglinAnimation.TOSIT, f3);
		this.animate(t.stopSitAnimationState, BlackGoldWarHoglinAnimation.STOPSIT, f3);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
