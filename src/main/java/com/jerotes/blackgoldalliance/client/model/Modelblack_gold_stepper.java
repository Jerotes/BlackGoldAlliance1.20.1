package com.jerotes.blackgoldalliance.client.model;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.animation.BlackGoldStepperAnimation;
import com.jerotes.blackgoldalliance.entity.Animal.BlackGoldStepperEntity;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelblack_gold_stepper<T extends BlackGoldStepperEntity> extends AgeableHierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BGA.MODID, "black_gold_stepper"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart wood;

	public Modelblack_gold_stepper(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root;
		this.body = root.getChild("body");
		this.wood = body.getChild("wood");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(161, 0).addBox(-12.0F, -19.0F, -11.0F, 24.0F, 19.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -50.0F, 0.0F));

		PartDefinition wood = body.addOrReplaceChild("wood", CubeListBuilder.create().texOffs(161, 112).addBox(-12.8276F, -20.6207F, -13.4655F, 24.0F, 19.0F, 22.0F, new CubeDeformation(0.5F))
				.texOffs(42, 125).mirror().addBox(6.1724F, -5.6207F, -17.4655F, 19.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(52, 59).mirror().addBox(21.1724F, -5.6207F, -13.4655F, 4.0F, 4.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 59).mirror().addBox(37.1724F, -5.6207F, -13.4655F, 4.0F, 4.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 59).mirror().addBox(37.1724F, 21.3793F, -13.4655F, 4.0F, 4.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(52, 59).addBox(-26.8276F, -5.6207F, -13.4655F, 4.0F, 4.0F, 22.0F, new CubeDeformation(0.0F))
				.texOffs(0, 59).addBox(-42.8276F, -5.6207F, -13.4655F, 4.0F, 4.0F, 22.0F, new CubeDeformation(0.0F))
				.texOffs(0, 115).addBox(-49.8276F, -5.6207F, -9.4655F, 7.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(0, 115).mirror().addBox(41.1724F, 21.3793F, -9.4655F, 7.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 115).mirror().addBox(41.1724F, -5.6207F, -9.4655F, 7.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 115).addBox(-49.8276F, 21.3793F, -9.4655F, 7.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(0, 59).addBox(-42.8276F, 21.3793F, -13.4655F, 4.0F, 4.0F, 22.0F, new CubeDeformation(0.0F))
				.texOffs(0, 133).addBox(-42.8276F, -5.6207F, 8.5345F, 35.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(28, 115).addBox(-42.8276F, 21.3793F, 8.5345F, 20.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 133).mirror().addBox(6.1724F, -5.6207F, 8.5345F, 35.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(28, 115).mirror().addBox(21.1724F, 21.3793F, 8.5345F, 20.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 85).mirror().addBox(21.1724F, -1.6207F, 8.5345F, 4.0F, 23.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 85).addBox(-26.8276F, -1.6207F, 8.5345F, 4.0F, 23.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(42, 125).addBox(-26.8276F, -5.6207F, -17.4655F, 19.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 137).addBox(-24.8276F, -18.6207F, -14.4655F, 0.0F, 74.0F, 24.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-24.8276F, -21.6207F, -14.4655F, 3.0F, 3.0F, 24.0F, new CubeDeformation(0.0F))
				.texOffs(0, 13).addBox(-23.8276F, -25.6207F, -6.4655F, 5.0F, 11.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-18.8276F, -25.6207F, -6.4655F, 12.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).mirror().addBox(5.1724F, -25.6207F, -6.4655F, 12.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 13).mirror().addBox(17.1724F, -25.6207F, -6.4655F, 5.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 32).mirror().addBox(20.1724F, -21.6207F, -14.4655F, 3.0F, 3.0F, 24.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 137).mirror().addBox(23.1724F, -18.6207F, -14.4655F, 0.0F, 74.0F, 24.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 141).addBox(-8.8276F, -20.6207F, 8.5345F, 16.0F, 3.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.8276F, 1.6207F, 2.4655F));

		PartDefinition hair_left_top = body.addOrReplaceChild("hair_left_top", CubeListBuilder.create(), PartPose.offset(12.0F, -19.0F, -8.0F));

		PartDefinition hair_left_top_rotation = hair_left_top.addOrReplaceChild("hair_left_top_rotation", CubeListBuilder.create().texOffs(212, 0).addBox(0.0F, 0.0F, -10.0F, 12.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.0F, 0.0F, 1.0472F));

		PartDefinition hair_left_middle = body.addOrReplaceChild("hair_left_middle", CubeListBuilder.create(), PartPose.offset(12.0F, -15.0F, -8.0F));

		PartDefinition hair_left_middle_rotation = hair_left_middle.addOrReplaceChild("hair_left_middle_rotation", CubeListBuilder.create().texOffs(216, 41).addBox(0.0F, 0.0F, -8.0F, 12.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.0F, 0.0F, 1.0472F));

		PartDefinition hair_left_bottom = body.addOrReplaceChild("hair_left_bottom", CubeListBuilder.create(), PartPose.offset(12.0F, -10.0F, -8.0F));

		PartDefinition hair_left_bottom_rotation = hair_left_bottom.addOrReplaceChild("hair_left_bottom_rotation", CubeListBuilder.create().texOffs(216, 57).addBox(0.0F, 0.0F, -8.0F, 12.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.0F, 0.0F, 1.0472F));

		PartDefinition hair_right_top = body.addOrReplaceChild("hair_right_top", CubeListBuilder.create(), PartPose.offset(-12.0F, -18.0F, -8.0F));

		PartDefinition hair_right_top_rotation = hair_right_top.addOrReplaceChild("hair_right_top_rotation", CubeListBuilder.create().texOffs(212, 0).mirror().addBox(-12.0F, 0.0F, -10.0F, 12.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.0F, 8.0F, 0.0F, 0.0F, -1.0472F));

		PartDefinition hair_right_middle = body.addOrReplaceChild("hair_right_middle", CubeListBuilder.create(), PartPose.offset(-12.0F, -14.0F, -8.0F));

		PartDefinition hair_right_middle_rotation = hair_right_middle.addOrReplaceChild("hair_right_middle_rotation", CubeListBuilder.create().texOffs(216, 41).mirror().addBox(-12.0F, 0.0F, -8.0F, 12.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.0F, 0.0F, -1.0472F));

		PartDefinition hair_right_bottom = body.addOrReplaceChild("hair_right_bottom", CubeListBuilder.create(), PartPose.offset(-12.0F, -9.0F, -8.0F));

		PartDefinition hair_right_bottom_rotation = hair_right_bottom.addOrReplaceChild("hair_right_bottom_rotation", CubeListBuilder.create().texOffs(216, 57).mirror().addBox(-12.0F, 0.0F, -8.0F, 12.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.0F, 0.0F, -1.0472F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(161, 41).mirror().addBox(-3.0F, 0.0F, -3.0F, 6.0F, 52.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(161, 153).mirror().addBox(-3.0F, 0.0F, -3.0F, 6.0F, 52.0F, 6.0F, new CubeDeformation(1.0F)).mirror(false)
				.texOffs(185, 41).mirror().addBox(-4.0F, 52.0F, -4.0F, 8.0F, 22.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(185, 153).mirror().addBox(-4.0F, 52.0F, -4.0F, 8.0F, 22.0F, 8.0F, new CubeDeformation(1.0F)).mirror(false), PartPose.offset(-6.0F, -50.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(161, 41).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 52.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(161, 153).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 52.0F, 6.0F, new CubeDeformation(1.0F))
				.texOffs(185, 41).addBox(-4.0F, 52.0F, -4.0F, 8.0F, 22.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(185, 153).addBox(-4.0F, 52.0F, -4.0F, 8.0F, 22.0F, 8.0F, new CubeDeformation(1.0F)), PartPose.offset(6.0F, -50.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (t.isBaby()) {
			this.body.xScale = 1.2f;
			this.body.yScale = 1.2f;
			this.body.zScale = 1.2f;
		}
		else {
			this.body.xScale = 1f;
			this.body.yScale = 1f;
			this.body.zScale = 1f;
		}
		if (!t.isVehicle()) {
			this.body.yRot = f4 * ((float) Math.PI / 180F);
			this.body.xRot = f5 * ((float) Math.PI / 180F);
		}
		if (t.isVehicle()) {
			this.animateWalk(BlackGoldStepperAnimation.WALK, f, f2, 1.0f, 2.0f);
		}
		else {
			this.animateWalk(BlackGoldStepperAnimation.WALK, f, f2, 2.0f, 2.0f);
		}
		this.animate(t.idleAnimationState, BlackGoldStepperAnimation.IDLE, f3);
		this.animate(t.attack1AnimationState, BlackGoldStepperAnimation.ATTACK1, f3);
		this.animate(t.attack2AnimationState, BlackGoldStepperAnimation.ATTACK2, f3);
		this.animate(t.attack3AnimationState, BlackGoldStepperAnimation.ATTACK3, f3);
		this.animate(t.attack4AnimationState, BlackGoldStepperAnimation.ATTACK4, f3);
		this.animate(t.sitAnimationState, BlackGoldStepperAnimation.SIT, f3);
		this.animate(t.toSitAnimationState, BlackGoldStepperAnimation.TOSIT, f3);
		this.animate(t.stopSitAnimationState, BlackGoldStepperAnimation.STOPSIT, f3);
		this.wood.visible = t.isSaddled();
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
