package com.jerotes.blackgoldalliance.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.animation.BlackGoldPiglinHunterAnimation;
import com.jerotes.blackgoldalliance.client.animation.TheBlackGoldMarshalAnimation;
import com.jerotes.blackgoldalliance.entity.Boss.TheBlackGoldMarshalEntity;
import com.jerotes.blackgoldalliance.init.BGAItems;
import com.jerotes.jerotes.client.model.Modelspecial_action;
import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class Modelthe_black_gold_marshal<T extends TheBlackGoldMarshalEntity> extends Modelspecial_action<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BGA.MODID, "the_black_gold_marshal"), "main");
	private final ModelPart root;
	private final ModelPart slash;
	private final ModelPart head;
    private final ModelPart right_ear_helmet;
    private final ModelPart left_ear_helmet;
	private final ModelPart helmet;
    private final ModelPart body;
	private final ModelPart chestplate;
    private final ModelPart leggings;
	private final ModelPart left_arm;
	private final ModelPart left_armor;
	private final ModelPart left_weapon;
	private final ModelPart right_arm;
	private final ModelPart right_armor;
	private final ModelPart right_weapon;
	private final ModelPart sword;
    private final ModelPart left_leg;
	private final ModelPart left_boots;
	private final ModelPart left_leggings;
	private final ModelPart right_leg;
	private final ModelPart right_boots;
	private final ModelPart right_leggings;

	public Modelthe_black_gold_marshal(ModelPart root) {
		super(root);
		this.root = root;
		this.slash = root.getChild("slash");
		this.head = root.getChild("head");
        ModelPart right_ear = this.head.getChild("right_ear");
		this.right_ear_helmet = right_ear.getChild("right_ear_helmet");
        ModelPart left_ear = this.head.getChild("left_ear");
		this.left_ear_helmet = left_ear.getChild("left_ear_helmet");
		this.helmet = this.head.getChild("helmet");
        this.helmet.getChild("mouth2");
        this.helmet.getChild("mouth");
        this.helmet.getChild("horn");
        this.body = root.getChild("body");
		this.chestplate = this.body.getChild("chestplate");
        this.chestplate.getChild("cloak");
        this.chestplate.getChild("jacket");
        this.leggings = this.body.getChild("leggings");
		this.left_arm = root.getChild("left_arm");
		this.left_armor = this.left_arm.getChild("left_armor");
		this.left_weapon = this.left_arm.getChild("left_weapon");
		this.right_arm = root.getChild("right_arm");
		this.right_armor = this.right_arm.getChild("right_armor");
		this.right_weapon = this.right_arm.getChild("right_weapon");
		this.sword = this.right_weapon.getChild("sword");
        this.sword.getChild("blade");
        this.sword.getChild("rod");
        this.left_leg = root.getChild("left_leg");
		this.left_boots = this.left_leg.getChild("left_boots");
		this.left_leggings = this.left_leg.getChild("left_leggings");
		this.right_leg = root.getChild("right_leg");
		this.right_boots = this.right_leg.getChild("right_boots");
		this.right_leggings = this.right_leg.getChild("right_leggings");
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

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(2, 64).addBox(-3.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 64).addBox(-5.0F, -8.0F, -4.0F, 10.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(64, 64).addBox(-5.0F, -8.0F, -4.0F, 10.0F, 8.0F, 8.0F, new CubeDeformation(0.25F))
				.texOffs(31, 65).addBox(-2.0F, -4.0F, -5.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(100, 76).addBox(-3.0F, -3.0F, -5.0F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.255F))
				.texOffs(2, 68).addBox(2.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(39, 70).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -6.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition right_ear_helmet = right_ear.addOrReplaceChild("right_ear_helmet", CubeListBuilder.create().texOffs(144, 0).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(51, 70).addBox(0.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -6.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

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

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 80).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(16, 80).addBox(-4.0F, -2.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(-0.25F))
				.texOffs(16, 96).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.255F))
				.texOffs(80, 80).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.275F))
				.texOffs(80, 80).addBox(-4.0F, -2.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(-0.075F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition chestplate = body.addOrReplaceChild("chestplate", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(128, 15).addBox(-5.0F, 8.0F, -4.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.15F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cloak = chestplate.addOrReplaceChild("cloak", CubeListBuilder.create().texOffs(184, 0).addBox(-4.9F, -0.2867F, -2.3745F, 12.0F, 25.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(112, 8).addBox(2.5F, 0.139F, -1.9829F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(3.0F, -2.0F, 2.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition jacket = chestplate.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition jacket_r1 = jacket.addOrReplaceChild("jacket_r1", CubeListBuilder.create().texOffs(64, 20).addBox(-4.0F, -3.5F, -2.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(1.105F)), PartPose.offsetAndRotation(0.0F, 2.7524F, -0.859F, 0.0873F, 0.0F, 0.0F));

		PartDefinition leggings = body.addOrReplaceChild("leggings", CubeListBuilder.create().texOffs(112, 16).addBox(-4.0F, -0.1F, -3.4F, 8.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(112, 16).addBox(-4.0F, -0.1F, 3.1F, 8.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition jacket_r2 = leggings.addOrReplaceChild("jacket_r2", CubeListBuilder.create().texOffs(96, 16).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(1.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, 3.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

		PartDefinition jacket_r3 = leggings.addOrReplaceChild("jacket_r3", CubeListBuilder.create().texOffs(96, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(-3.5F, 3.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(48, 112).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(96, 112).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.35F))
				.texOffs(32, 112).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition left_armor = left_arm.addOrReplaceChild("left_armor", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.775F))
				.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm_r1 = left_armor.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(128, 0).addBox(-0.4272F, -4.0F, -3.5F, 1.0F, 8.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.9272F, -0.4395F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition left_arm_r2 = left_armor.addOrReplaceChild("left_arm_r2", CubeListBuilder.create().texOffs(112, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.25F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition left_arm_r3 = left_armor.addOrReplaceChild("left_arm_r3", CubeListBuilder.create().texOffs(112, 8).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.15F)), PartPose.offsetAndRotation(1.4848F, -1.4236F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition left_weapon = left_arm.addOrReplaceChild("left_weapon", CubeListBuilder.create(), PartPose.offset(2.0F, 8.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 80).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(40, 96).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(104, 80).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.35F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition right_armor = right_arm.addOrReplaceChild("right_armor", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.775F))
				.texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_arm_r1 = right_armor.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(128, 0).addBox(-0.6772F, -4.0F, -3.5F, 1.0F, 8.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.8228F, -0.4395F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition right_arm_r2 = right_armor.addOrReplaceChild("right_arm_r2", CubeListBuilder.create().texOffs(96, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.25F)), PartPose.offsetAndRotation(-1.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition right_arm_r3 = right_armor.addOrReplaceChild("right_arm_r3", CubeListBuilder.create().texOffs(96, 8).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.15F)), PartPose.offsetAndRotation(-1.5F, -1.25F, 0.0F, 0.0F, 0.0F, -0.2182F));

		PartDefinition right_weapon = right_arm.addOrReplaceChild("right_weapon", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 0.0F));

		PartDefinition sword = right_weapon.addOrReplaceChild("sword", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.25F, -0.5F, -14.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition blade = sword.addOrReplaceChild("blade", CubeListBuilder.create().texOffs(160, 32).addBox(0.0F, -21.0F, -0.5F, 1.0F, 29.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(184, 61).addBox(0.0F, -5.0F, -1.5F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(174, 44).addBox(0.0F, -21.0F, -2.0F, 1.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(184, 44).addBox(0.5F, -5.0F, -2.0F, 0.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(164, 44).addBox(0.5F, -21.0F, -2.5F, 0.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.25F, -3.5F, 0.0F));

		PartDefinition sword_r1 = blade.addOrReplaceChild("sword_r1", CubeListBuilder.create().texOffs(186, 32).addBox(0.59F, -2.5F, -3.5F, 0.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(186, 32).addBox(0.61F, -2.5F, -3.5F, 0.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(160, 71).addBox(0.1F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(-0.1F, -21.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition sword_r2 = blade.addOrReplaceChild("sword_r2", CubeListBuilder.create().texOffs(202, 60).addBox(0.51F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(202, 60).addBox(0.49F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition sword_r3 = blade.addOrReplaceChild("sword_r3", CubeListBuilder.create().texOffs(192, 59).addBox(0.51F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(192, 59).addBox(0.49F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.5F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition rod = sword.addOrReplaceChild("rod", CubeListBuilder.create().texOffs(164, 32).addBox(-1.5F, 1.0F, -4.0F, 3.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(192, 44).addBox(-1.5F, 5.0F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(174, 64).addBox(-1.0F, 5.5F, -1.5F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(160, 65).addBox(-1.5F, 13.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.25F, 3.5F, 0.0F));

		PartDefinition sword_r4 = rod.addOrReplaceChild("sword_r4", CubeListBuilder.create().texOffs(202, 59).addBox(-0.505F, -1.505F, -1.505F, 1.01F, 3.01F, 3.01F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition sword_r5 = rod.addOrReplaceChild("sword_r5", CubeListBuilder.create().texOffs(192, 50).addBox(-1.5F, -3.5F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 2.5F, -4.5F, 0.3927F, 0.0F, 0.0F));

		PartDefinition sword_r6 = rod.addOrReplaceChild("sword_r6", CubeListBuilder.create().texOffs(192, 50).addBox(-1.5F, -3.5F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 2.5F, 4.5F, -0.3927F, 0.0F, 0.0F));

		PartDefinition sword_r7 = rod.addOrReplaceChild("sword_r7", CubeListBuilder.create().texOffs(192, 69).addBox(-0.5F, -1.5F, -2.5F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.5F, 2.5F, -4.5F, -0.3927F, 0.0F, 0.0F));

		PartDefinition sword_r8 = rod.addOrReplaceChild("sword_r8", CubeListBuilder.create().texOffs(198, 32).addBox(-1.0F, -2.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, 4.5F, 4.5F, 0.3927F, 0.0F, 0.0F));

		PartDefinition sword_r9 = rod.addOrReplaceChild("sword_r9", CubeListBuilder.create().texOffs(198, 32).addBox(-1.0F, -2.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, 4.5F, -4.5F, -0.3927F, 0.0F, 0.0F));

		PartDefinition sword_r10 = rod.addOrReplaceChild("sword_r10", CubeListBuilder.create().texOffs(192, 69).addBox(-0.5F, -1.5F, -1.5F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.5F, 2.5F, 4.5F, 0.3927F, 0.0F, 0.0F));

		PartDefinition sword_r11 = rod.addOrReplaceChild("sword_r11", CubeListBuilder.create().texOffs(198, 40).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 112).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(80, 112).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.35F))
				.texOffs(16, 112).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.9F, 12.0F, 0.0F));

		PartDefinition left_boots = left_leg.addOrReplaceChild("left_boots", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_leggings = left_leg.addOrReplaceChild("left_leggings", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.505F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 96).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(64, 80).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.351F))
				.texOffs(0, 80).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition right_boots = right_leg.addOrReplaceChild("right_boots", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.751F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_leggings = right_leg.addOrReplaceChild("right_leggings", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.51F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition slash = partdefinition.addOrReplaceChild("slash", CubeListBuilder.create().texOffs(100, 96).addBox(-32.0F, 0.0F, 13.0F, 64.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 128);
	}

	protected @NotNull Iterable<ModelPart> bodyParts() {
		return Iterables.concat(super.bodyParts(), ImmutableList.of(this.slash));
	}

	@Override
	public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(t, f4, f5, f3);
		Modelspecial_action.animate(this, TheBlackGoldMarshalAnimation.BASE, 0L, 1f, new Vector3f());
		float attackingAnim = Mth.clamp((1 - (t.attackingAnimProgress / 10)), 0, 1);
		//行走
		float attack = 1 - t.attackAnimProgress / 10f;
		this.animateWalk(TheBlackGoldMarshalAnimation.RUN, f, f2 * attack * (attackingAnim * 0.5f + 0.5f), 1.5f, 2.0f);
		this.animateWalk(TheBlackGoldMarshalAnimation.WALK, f, f2 * (1 - attack) * (attackingAnim * 0.5f + 0.5f), 1.5f, 2.0f);
		//站立
		this.animate(t.idleAnimationState, TheBlackGoldMarshalAnimation.IDLE, f3);

		this.animateWalk(TheBlackGoldMarshalAnimation.IDLEPOSE_RUN, f, f2 * (attackingAnim * 0.5f + 0.5f), 2.0f, 2.0f);

		this.animateIdle2(t);
		this.animateIdle3(t);

		if (InventoryEntity.isPike(t, t.getMainHandItem()))
			Modelspecial_action.animate(this, TheBlackGoldMarshalAnimation.HANDLE_PIKE_RIGHT, 0L, 1f, new Vector3f());
		if (InventoryEntity.isPike(t, t.getOffhandItem()))
			Modelspecial_action.animate(this, TheBlackGoldMarshalAnimation.HANDLE_PIKE_LEFT, 0L, 1f, new Vector3f());
		//攻击
		this.animate(t.normalAttack1AnimationState, TheBlackGoldMarshalAnimation.NORMAL_ATTACK1, f3);
		this.animate(t.normalAttack2AnimationState, TheBlackGoldMarshalAnimation.NORMAL_ATTACK2, f3);
		this.animate(t.normalAttack3AnimationState, TheBlackGoldMarshalAnimation.NORMAL_ATTACK3, f3);
		this.animate(t.normalAttack4AnimationState, TheBlackGoldMarshalAnimation.NORMAL_ATTACK4, f3);
		this.animate(t.normalAttack5AnimationState, TheBlackGoldMarshalAnimation.NORMAL_ATTACK5, f3);
		this.animate(t.normalAttack6AnimationState, TheBlackGoldMarshalAnimation.NORMAL_ATTACK6, f3);
		this.animate(t.specialAttack1AnimationState, TheBlackGoldMarshalAnimation.SPECIAL_ATTACK1, f3);
		this.animate(t.specialAttack2AnimationState, TheBlackGoldMarshalAnimation.SPECIAL_ATTACK2, f3);
		this.animate(t.specialAttack3AnimationState, TheBlackGoldMarshalAnimation.SPECIAL_ATTACK3, f3);
		this.animate(t.specialAttack4AnimationState, TheBlackGoldMarshalAnimation.SPECIAL_ATTACK4, f3);
		this.animate(t.specialAttack5AnimationState, TheBlackGoldMarshalAnimation.SPECIAL_ATTACK5, f3);
		this.animate(t.specialAttack6AnimationState, TheBlackGoldMarshalAnimation.SPECIAL_ATTACK6, f3);
		this.animate(t.block1AnimationState, TheBlackGoldMarshalAnimation.BLOCK1, f3);
		this.animate(t.block2AnimationState, TheBlackGoldMarshalAnimation.BLOCK2, f3);
		this.animate(t.block3AnimationState, TheBlackGoldMarshalAnimation.BLOCK3, f3);
		this.animate(t.shootAnimationState, BlackGoldPiglinHunterAnimation.CROSSBOW_SHOOT_LEFT, f3);
		this.animate(t.deadAnimationState, TheBlackGoldMarshalAnimation.DEAD, f3);

		this.sword.visible = t.getItemBySlot(EquipmentSlot.MAINHAND).getItem() == BGAItems.THE_BLACK_GOLD_MARSHAL_GREATSWORD.get();
		if (t.getItemBySlot(EquipmentSlot.HEAD).getItem() != BGAItems.THE_BLACK_GOLD_MARSHAL_HELMET.get()) {
			this.helmet.visible = false;
			this.left_ear_helmet.visible = false;
			this.right_ear_helmet.visible = false;
		}
		else {
			this.helmet.visible = true;
			this.left_ear_helmet.visible = true;
			this.right_ear_helmet.visible = true;
		}
		if (t.getItemBySlot(EquipmentSlot.CHEST).getItem() != BGAItems.THE_BLACK_GOLD_MARSHAL_CHESTPLATE.get()) {
			this.chestplate.visible = false;
			this.left_armor.visible = false;
			this.right_armor.visible = false;
		}
		else {
			this.chestplate.visible = true;
			this.left_armor.visible = true;
			this.right_armor.visible = true;
		}
		if (t.getItemBySlot(EquipmentSlot.LEGS).getItem() != BGAItems.THE_BLACK_GOLD_MARSHAL_LEGGINGS.get()) {
			this.leggings.visible = false;
			this.left_leggings.visible = false;
			this.right_leggings.visible = false;
		}
		else {
			this.leggings.visible = true;
			this.left_leggings.visible = true;
			this.right_leggings.visible = true;
		}
		if (t.getItemBySlot(EquipmentSlot.FEET).getItem() != BGAItems.THE_BLACK_GOLD_MARSHAL_BOOTS.get()) {
			this.left_boots.visible = false;
			this.right_boots.visible = false;
		}
		else {
			this.left_boots.visible = true;
			this.right_boots.visible = true;
		}
		this.slash.visible = t.getAttackTick() > 0;
	}
	public void stopSlash(T t, float f, float f2, float f3, float f4, float f5) {
		this.root().getAllParts().forEach(modelPart -> modelPart.xScale *= 1.005f);
		this.root().getAllParts().forEach(modelPart -> modelPart.yScale *= 1.005f);
		this.root().getAllParts().forEach(modelPart -> modelPart.zScale *= 1.005f);
		this.slash.visible = false;
	}
	private void applyHeadRotation(T t, float f, float f2, float f3) {
		f = Mth.clamp(f, -60.0f, 60.0f);
		f2 = Mth.clamp(f2, -60.0f, 60.0f);
		this.head.yRot = f * ((float) Math.PI / 180F);
		this.head.xRot = f2 * ((float) Math.PI / 180F);
	}

	protected void animateIdle2(TheBlackGoldMarshalEntity blackGoldPiglinEntity) {
		float attackingAnim = Mth.clamp((1 - (blackGoldPiglinEntity.attackingAnimProgress / 10)), 0, 1);
		boolean bl = blackGoldPiglinEntity.attackAnimProgress < blackGoldPiglinEntity.getAttackAnim();
		if (bl) {
			Modelspecial_action.animate(this, TheBlackGoldMarshalAnimation.IDLEPOSE, (long) (Math.min(1, blackGoldPiglinEntity.attackAnimProgress / 10f) * attackingAnim * TheBlackGoldMarshalAnimation.IDLEPOSE.lengthInSeconds() * 999), 1.0f, new Vector3f());
		}
		else {
			Modelspecial_action.animate(this, TheBlackGoldMarshalAnimation.IDLEPOSE_USE, 0L, Math.min(1, blackGoldPiglinEntity.attackAnimProgress / 10f) * attackingAnim, new Vector3f());
		}
	}
	protected void animateIdle3(TheBlackGoldMarshalEntity blackGoldPiglinEntity) {
		float attackingAnim = Mth.clamp((1 - (blackGoldPiglinEntity.attackingAnimProgress / 10)), 0, 1);
		Modelspecial_action.animate(this, TheBlackGoldMarshalAnimation.IDLEPOSE_ATTACK, 0L, Math.min(1, 1 - blackGoldPiglinEntity.attackAnimProgress / 10f) * attackingAnim, new Vector3f());
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

	protected @NotNull ModelPart getArm(@NotNull HumanoidArm humanoidArm) {
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
	public @NotNull ModelPart getHead() {
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