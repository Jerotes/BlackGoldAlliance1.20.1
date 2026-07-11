package com.jerotes.blackgoldalliance.client.model;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.animation.NetherSiphonCoreForceAnimation;
import com.jerotes.blackgoldalliance.entity.Other.NetherSiphonCoreForceEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelnether_siphon_core_force<T extends NetherSiphonCoreForceEntity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BGA.MODID, "nether_siphon_core_force"), "main");
    private final ModelPart root;
    private final ModelPart body;

    public Modelnether_siphon_core_force(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-32.0F, -32.0F, -32.0F, 64.0F, 64.0F, 64.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(t.startAnimationState, NetherSiphonCoreForceAnimation.START, f3);
        this.animate(t.stopAnimationState, NetherSiphonCoreForceAnimation.STOP, f3);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}

