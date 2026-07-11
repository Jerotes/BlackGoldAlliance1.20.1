package com.jerotes.blackgoldalliance.client.model;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.animation.BlackGoldPiglinAnimation;
import com.jerotes.blackgoldalliance.client.animation.BlackGoldPiglinHeavyAnimation;
import com.jerotes.blackgoldalliance.client.animation.BlackGoldPiglinHunterAnimation;
import com.jerotes.blackgoldalliance.entity.Piglin.PiglinRaiderEntity;
import com.jerotes.jerotes.client.model.Modelspecial_action;
import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.item.Interface.ItemTwoHanded;
import com.jerotes.jerotes.item.Interface.JerotesItemThrownJavelinUse;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.*;
import org.joml.Vector3f;

public class Modelpiglin_raider<T extends PiglinRaiderEntity> extends Modelspecial_action<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BGA.MODID, "piglin_raider"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart left_ear;
	private final ModelPart right_ear;
	private final ModelPart body;
	private final ModelPart jacket;
	private final ModelPart left_arm;
	private final ModelPart left_weapon;
	private final ModelPart left_sleeve;
	private final ModelPart right_arm;
	private final ModelPart right_weapon;
	private final ModelPart right_sleeve;
	private final ModelPart left_leg;
	private final ModelPart left_pants;
	private final ModelPart right_leg;
	private final ModelPart right_pants;

	public Modelpiglin_raider(ModelPart root) {
		super(root);
		this.root = root;
		this.head = root.getChild("head");
		this.left_ear = this.head.getChild("left_ear");
		this.right_ear = this.head.getChild("right_ear");
		this.body = root.getChild("body");
		this.jacket = root.getChild("jacket");
		this.left_arm = root.getChild("left_arm");
		this.left_weapon = this.left_arm.getChild("left_weapon");
		this.left_sleeve = root.getChild("left_sleeve");
		this.right_arm = root.getChild("right_arm");
		this.right_weapon = this.right_arm.getChild("right_weapon");
		this.right_sleeve = root.getChild("right_sleeve");
		this.left_leg = root.getChild("left_leg");
		this.left_pants = root.getChild("left_pants");
		this.right_leg = root.getChild("right_leg");
		this.right_pants = root.getChild("right_pants");
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

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -8.0F, -4.0F, 10.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(31, 1).addBox(-2.0F, -4.0F, -5.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(2, 4).addBox(2.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(2, 0).addBox(-3.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(51, 6).addBox(0.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -6.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(39, 6).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -6.0F, 0.0F, 0.0F, 0.0F, 0.6109F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition jacket = partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition left_weapon = left_arm.addOrReplaceChild("left_weapon", CubeListBuilder.create(), PartPose.offset(1.0F, 8.0F, 0.0F));

		PartDefinition left_sleeve = partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition right_weapon = right_arm.addOrReplaceChild("right_weapon", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 0.0F));

		PartDefinition right_sleeve = partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition left_pants = partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition right_pants = partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = f4 * ((float) Math.PI / 180F);
		this.head.xRot = f5 * ((float) Math.PI / 180F);
//		if (t.isBaby()) {
//			float headY = -3f;
//			this.head.y += headY;
//		}

		//行走
		if (t.getMainHandItem().getItem() instanceof ItemTwoHanded && t.getMainHandItem().is(ItemTags.create(new ResourceLocation(BGA.MODID, "piglin_weapon"))) &&
				t.getMainHandItem().getItem().canDisableShield(t.getMainHandItem(), new ItemStack(Items.SHIELD), t, t)) {
			if (!t.isAggressive()) {
				if (!t.isLeftHanded()) {
					this.animateWalk(BlackGoldPiglinHeavyAnimation.WALK_HAMMER_RIGHT, f, f2, 2.0f, 2.0f);
				} else {
					this.animateWalk(BlackGoldPiglinHeavyAnimation.WALK_HAMMER_LEFT, f, f2, 2.0f, 2.0f);
				}
			}
			else {
				if (!t.isLeftHanded()) {
					this.animateWalk(BlackGoldPiglinHeavyAnimation.RUN_HAMMER_RIGHT, f, f2, 2.0f, 2.0f);
				} else {
					this.animateWalk(BlackGoldPiglinHeavyAnimation.RUN_HAMMER_LEFT, f, f2, 2.0f, 2.0f);
				}
			}
		}
		else if (t.getMainHandItem().getItem() instanceof CrossbowItem) {
			this.animateWalk(BlackGoldPiglinHunterAnimation.RUN, f, f2, 2.0f, 2.0f);
		}
		else if (t.getOffhandItem().getItem() instanceof CrossbowItem && t.getMainHandItem().isEmpty()) {
			this.animateWalk(BlackGoldPiglinHunterAnimation.RUN, f, f2, 2.0f, 2.0f);
		}
		else if (t.isAggressive()) {
			if (t.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
				this.animateWalk(BlackGoldPiglinAnimation.RUN_NO_ARMOR, f, f2, 2.0f, 2.0f);
			}
			else {
				this.animateWalk(BlackGoldPiglinAnimation.RUN, f, f2, 2.0f, 2.0f);
			}
		}
		else {
			this.animateWalk(BlackGoldPiglinAnimation.WALK, f, f2, 2.0f, 2.0f);
		}


		if (t.getUseItem().getItem() instanceof BowItem) {
			this.rightArm.yRot = 0f;
			this.leftArm.yRot = 0f;
			this.rightArm.xRot = 0f;
			this.leftArm.xRot = 0f;
		}


		//默认
		this.animate(t.idleAnimationState, BlackGoldPiglinAnimation.IDLE_RAIDER, f3);

		if (t.getMainHandItem().getItem() instanceof CrossbowItem) {
			if (!t.isLeftHanded()) {
				this.animateIdle2(t, t.idleAnimationState, BlackGoldPiglinHunterAnimation.IDLEPOSE_CROSSBOW_RIGHT, f3);
			} else {
				this.animateIdle2(t, t.idleAnimationState, BlackGoldPiglinHunterAnimation.IDLEPOSE_CROSSBOW_LEFT, f3);
			}
		}
		else {
			if (!t.isLeftHanded()) {
				this.animateIdle2(t, t.idleAnimationState, BlackGoldPiglinAnimation.IDLEPOSE_RAIDER_RIGHT, f3);
				this.animateIdle3(t, t.idleAnimationState, BlackGoldPiglinAnimation.IDLEPOSE_RAIDER_ATTACK_RIGHT, f3);
			} else {
				this.animateIdle2(t, t.idleAnimationState, BlackGoldPiglinAnimation.IDLEPOSE_RAIDER_LEFT, f3);
				this.animateIdle3(t, t.idleAnimationState, BlackGoldPiglinAnimation.IDLEPOSE_RAIDER_ATTACK_LEFT, f3);
			}
		}

		this.animate(t.danceAnimationState, BlackGoldPiglinAnimation.DANCE, f3);
		if (t.isShiftKeyDown())
			this.animate(t.idleAnimationState, BlackGoldPiglinAnimation.SHIFT, f3);

		if (!t.isLeftHanded()) {
			this.animate(t.throw1AnimationState, BlackGoldPiglinAnimation.THROW1, f3);
			this.animate(t.throw2AnimationState, BlackGoldPiglinAnimation.THROW2, f3);
			this.animate(t.shoot1AnimationState, BlackGoldPiglinHunterAnimation.CROSSBOW_SHOOT_RIGHT, f3);
			this.animate(t.shoot2AnimationState, BlackGoldPiglinHunterAnimation.CROSSBOW_SHOOT_LEFT, f3);
		} else {
			this.animate(t.throw1AnimationState, BlackGoldPiglinAnimation.THROW2, f3);
			this.animate(t.throw2AnimationState, BlackGoldPiglinAnimation.THROW1, f3);
			this.animate(t.shoot1AnimationState, BlackGoldPiglinHunterAnimation.CROSSBOW_SHOOT_LEFT, f3);
			this.animate(t.shoot2AnimationState, BlackGoldPiglinHunterAnimation.CROSSBOW_SHOOT_RIGHT, f3);
		}
		if (!t.isLeftHanded()) {
			this.animate(t.attackAnimationState, BlackGoldPiglinAnimation.RAIDER_ITEMATTACK_RIGHT, f3);
		}
		else {
			this.animate(t.attackAnimationState, BlackGoldPiglinAnimation.RAIDER_ITEMATTACK_LEFT, f3);
		}

		armSpecial(t, f, f2, f3, f4, f5);
		this.hat.copyFrom(this.head);
		this.jacket.copyFrom(this.body);
		this.left_sleeve.copyFrom(this.left_arm);
		this.right_sleeve.copyFrom(this.right_arm);
		this.left_pants.copyFrom(this.left_leg);
		this.right_pants.copyFrom(this.right_leg);
	}

	protected void animateIdle2(PiglinRaiderEntity piglinRaiderEntity, AnimationState animationState,
								AnimationDefinition animation, float ageInTicks) {
		animationState.updateTime(ageInTicks, 1.0F);
		animationState.ifStarted((state) -> {
			Modelspecial_action.animate(this, animation,
					animationState.getAccumulatedTime(),
					Math.min(1, piglinRaiderEntity.attackAnimProgress / 10f),
					new Vector3f());
		});
	}
	protected void animateIdle3(PiglinRaiderEntity piglinRaiderEntity, AnimationState animationState,
								AnimationDefinition animation, float ageInTicks) {
		animationState.updateTime(ageInTicks, 1.0F);
		animationState.ifStarted((state) -> {
			Modelspecial_action.animate(this, animation,
					animationState.getAccumulatedTime(),
					Math.min(1, 1 - piglinRaiderEntity.attackAnimProgress / 10f),
					new Vector3f());
		});
	}


	public void armSpecial(T t, float f, float f2, float f3, float f4, float f5) {
		{
			ItemStack itemStack = t.getMainHandItem();
			ItemStack itemStacks = t.getOffhandItem();
			ModelPart mainHand;
			ModelPart offHand;
			if (!(t.isLeftHanded())) {
				mainHand = rightArm;
				offHand = leftArm;
			} else {
				mainHand = leftArm;
				offHand = rightArm;
			}
			ItemStack handItem = t.getMainHandItem();
			if ((t.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(t.getMainHandItem())) && !t.getOffhandItem().isEmpty()) {
				handItem = t.getOffhandItem();
			}
			if (t.isAggressive()) {
				if (itemStack.getItem() instanceof BowItem) {
					if (mainHand == rightArm) {
						this.rightArm.yRot = -0.1f + this.head.yRot;
						this.leftArm.yRot = 0.1f + this.head.yRot + 0.4f;
					} else {
						this.rightArm.yRot = -0.1f + this.head.yRot - 0.4f;
						this.leftArm.yRot = 0.1f + this.head.yRot;
					}
					this.rightArm.xRot = -1.5707964f + this.head.xRot;
					this.leftArm.xRot = -1.5707964f + this.head.xRot;
				}
				else if (itemStacks.getItem() instanceof BowItem && handItem != t.getMainHandItem() && t.getUsedItemHand() == InteractionHand.OFF_HAND) {
					if (mainHand == leftArm) {
						this.rightArm.yRot = -0.1f + this.head.yRot;
						this.leftArm.yRot = 0.1f + this.head.yRot + 0.4f;
					} else {
						this.rightArm.yRot = -0.1f + this.head.yRot - 0.4f;
						this.leftArm.yRot = 0.1f + this.head.yRot;
					}
					this.rightArm.xRot = -1.5707964f + this.head.xRot;
					this.leftArm.xRot = -1.5707964f + this.head.xRot;
				}
				//主手弩
				if (t.isAggressive()) {
					if (itemStack.getItem() instanceof CrossbowItem) {
						if (t.isChargingCrossbow()) {
							AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, t, !t.isLeftHanded());
						} else {
							AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, !t.isLeftHanded());
						}
					}
					else if (itemStacks.getItem() instanceof CrossbowItem && handItem != t.getMainHandItem()) {
						if (t.isChargingCrossbow()) {
							AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, t, t.isLeftHanded());
						} else {
							AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, t.isLeftHanded());
						}
					}
				}
			}


			if (InventoryEntity.isRangeJavelin(itemStack) || itemStack.getItem() instanceof TridentItem) {
				if (t.isUsingItem() && t.getUseItem().getItem() instanceof JerotesItemThrownJavelinUse jerotesItemThrownJavelinUse && jerotesItemThrownJavelinUse.isJerotesThrownJavelin()) {
					jerotesItemThrownJavelinUse.JerotesNormalThrownJavelinAnim(mainHand, offHand, head, true);
				}
				//三叉戟
				else if (t.isUsingItem() && t.getUseItem().getItem() instanceof TridentItem) {
					mainHand.xRot = mainHand.xRot * 0.5f - 3.1415927f;
					mainHand.yRot = 0.0f;
				}
			}
			else if ((InventoryEntity.isRangeJavelin(itemStacks) || itemStacks.getItem() instanceof TridentItem) && handItem != t.getMainHandItem()) {
				if (t.isUsingItem() && t.getUseItem().getItem() instanceof JerotesItemThrownJavelinUse jerotesItemThrownJavelinUse && jerotesItemThrownJavelinUse.isJerotesThrownJavelin()) {
					jerotesItemThrownJavelinUse.JerotesNormalThrownJavelinAnim(offHand, mainHand, head, true);
				}
				//三叉戟
				else if (t.isUsingItem() && t.getUseItem().getItem() instanceof TridentItem) {
					offHand.xRot = offHand.xRot * 0.5f - 3.1415927f;
					offHand.yRot = 0.0f;
				}
			}
			if (t.isAggressive()) {
				if (t.shieldCanUse() && t.notBowCrossbow(t, InteractionHand.MAIN_HAND) && t.getOffhandItem().getItem() instanceof ShieldItem && t.isUsingItem() && t.getUseItem().getItem() instanceof ShieldItem) {
					this.poseBlockingArm(offHand, false);
				}
				//主手盾牌
				else if (t.shieldCanUse() && t.notBowCrossbow(t, InteractionHand.OFF_HAND) && t.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ShieldItem && t.isUsingItem() && t.getUseItem().getItem() instanceof ShieldItem) {
					this.poseBlockingArm(mainHand, false);
				}
				//主手双手武器
				else if (t.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ItemTwoHanded itemTwoHanded &&
						itemTwoHanded.canBlock() &&
						t.getOffhandItem().isEmpty() &&
						t.isUsingItem() &&
						t.getUseItem().getItem() instanceof ItemTwoHanded &&
						t.attackAnim <= 0.0F
						&& 	!(t.getMainHandItem().getItem() instanceof ItemTwoHanded && t.getMainHandItem().is(ItemTags.create(new ResourceLocation(BGA.MODID, "piglin_weapon"))) &&
						t.getMainHandItem().getItem().canDisableShield(t.getMainHandItem(), new ItemStack(Items.SHIELD), t, t))) {
					this.poseBlockingArm(mainHand, false);
				}
				//副手双手武器
				else if (t.getOffhandItem().getItem() instanceof ItemTwoHanded itemTwoHanded && itemTwoHanded.canBlock() && t.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && t.isUsingItem() && t.getUseItem().getItem() instanceof ItemTwoHanded && t.attackAnim <= 0.0F) {
					this.poseBlockingArm(offHand, false);
				}
			}
			specialAnim(t, this, f, f2, f3, f4, f5);
		}
	}

	public void poseBlockingArm(ModelPart modelPart, boolean bl) {
		if (modelPart == leftArm) {
			modelPart.xRot = modelPart.xRot * 0.5f - 0.9424779f + Mth.clamp(this.head.xRot, -1.3962634f, 0.43633232f);
			modelPart.yRot = (bl ? -30.0f : 30.0f) * 0.017453292f + Mth.clamp(this.head.yRot, -0.5235988f, 0.5235988f);
		}
		else {
			modelPart.xRot = modelPart.xRot * 0.5f - 0.9424779f + Mth.clamp(this.head.xRot, -1.3962634f, 0.43633232f);
			modelPart.yRot = (bl ? 30.0f : -30.0f) * 0.017453292f + Mth.clamp(this.head.yRot, -0.5235988f, 0.5235988f);
		}
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