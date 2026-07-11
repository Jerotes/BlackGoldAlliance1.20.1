package com.jerotes.blackgoldalliance.client.model;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.animation.PiglinRaidNetherPortalAnimation;
import com.jerotes.blackgoldalliance.entity.Boss.PiglinRaidNetherPortalEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class Modelpiglin_raid_nether_portal<T extends PiglinRaidNetherPortalEntity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BGA.MODID, "piglin_raid_nether_portal"), "main");
    private final ModelPart root;
    private final ModelPart main;

    public Modelpiglin_raid_nether_portal(ModelPart root) {
        this.root = root;
        this.main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, -24.0F, 0.0F));

        PartDefinition block_1 = main.addOrReplaceChild("block_1", CubeListBuilder.create().texOffs(0, 96).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(32.0F, 40.0F, 0.0F));

        PartDefinition block_2 = main.addOrReplaceChild("block_2", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(16.0F, 40.0F, 0.0F));

        PartDefinition block_3 = main.addOrReplaceChild("block_3", CubeListBuilder.create().texOffs(0, 64).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 40.0F, 0.0F));

        PartDefinition block_4 = main.addOrReplaceChild("block_4", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-16.0F, 40.0F, 0.0F));

        PartDefinition block_5 = main.addOrReplaceChild("block_5", CubeListBuilder.create().texOffs(0, 96).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-32.0F, 40.0F, 0.0F));

        PartDefinition block_6 = main.addOrReplaceChild("block_6", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-32.0F, 24.0F, 0.0F));

        PartDefinition block_7 = main.addOrReplaceChild("block_7", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-32.0F, 8.0F, 0.0F));

        PartDefinition block_8 = main.addOrReplaceChild("block_8", CubeListBuilder.create().texOffs(0, 64).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-32.0F, -8.0F, 0.0F));

        PartDefinition block_9 = main.addOrReplaceChild("block_9", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-32.0F, -24.0F, 0.0F));

        PartDefinition block_10 = main.addOrReplaceChild("block_10", CubeListBuilder.create().texOffs(0, 96).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-32.0F, -40.0F, 0.0F));

        PartDefinition block_11 = main.addOrReplaceChild("block_11", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-16.0F, -40.0F, 0.0F));

        PartDefinition block_12 = main.addOrReplaceChild("block_12", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -40.0F, 0.0F));

        PartDefinition block_13 = main.addOrReplaceChild("block_13", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(16.0F, -40.0F, 0.0F));

        PartDefinition block_14 = main.addOrReplaceChild("block_14", CubeListBuilder.create().texOffs(0, 96).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(32.0F, -40.0F, 0.0F));

        PartDefinition block_15 = main.addOrReplaceChild("block_15", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(32.0F, -24.0F, 0.0F));

        PartDefinition block_16 = main.addOrReplaceChild("block_16", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(32.0F, -8.0F, 0.0F));

        PartDefinition block_17 = main.addOrReplaceChild("block_17", CubeListBuilder.create().texOffs(0, 64).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(32.0F, 8.0F, 0.0F));

        PartDefinition block_18 = main.addOrReplaceChild("block_18", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(32.0F, 24.0F, 0.0F));

        PartDefinition portal = main.addOrReplaceChild("portal", CubeListBuilder.create().texOffs(160, 0).addBox(-24.0F, -32.0F, -1.0F, 48.0F, 64.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(160, 0).addBox(-24.0F, -32.0F, 1.0F, 48.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(T t, float f, float f2, float f3, float f4, float f5) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(t.startAnimationState, PiglinRaidNetherPortalAnimation.START, f3);
        this.animate(t.deadAnimationState, PiglinRaidNetherPortalAnimation.DEAD, f3);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}

