package com.jerotes.blackgoldalliance.client.renderer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.block.NetherSiphonLinkStation;
import com.jerotes.blackgoldalliance.block.NetherSiphonLinkStationEntity;
import com.jerotes.blackgoldalliance.init.BGAItems;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class NetherSiphonLinkStationRenderer implements BlockEntityRenderer<NetherSiphonLinkStationEntity> {
    public NetherSiphonLinkStationRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(NetherSiphonLinkStationEntity t, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int n, int n2) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        BlockState blockState = t.getBlockState();
        boolean show1 = blockState.getValue(NetherSiphonLinkStation.TYPE) == 2 || blockState.getValue(NetherSiphonLinkStation.TYPE) == 3;
        boolean show2 = t.line;
        if (player == null) return;
        if (!show1 || !show2) return;
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(BGAItems.GOLDEN_VEINED_BLACKSTONE_TABLET.get()) || player.getItemInHand(InteractionHand.OFF_HAND).is(BGAItems.GOLDEN_VEINED_BLACKSTONE_TABLET.get())) {
            float startX = t.getBlockPos().getX() + 0.5f;
            float startY = t.getBlockPos().getY() + 0.65625f + 0.1f;
            float startZ = t.getBlockPos().getZ() + 0.5f;

            float endX = t.lineX + 0.5f;
            float endY = t.lineY + 0.5f;
            float endZ = t.lineZ + 0.5f;

            float dx = endX - startX;
            float dy = endY - startY;
            float dz = endZ - startZ;
            renderLightLock(dx, dy, dz, f, t.tickCount, poseStack, multiBufferSource, n);
        }
    }

    public static final ResourceLocation LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/beam/nether_siphon_link_station_beam.png");
    private static final RenderType BEAM2 = JerotesRenderType.translucent();
    public ResourceLocation getBeamLocation() {
        return LOCATION;
    }

    public void renderLightLock(float f, float f2, float f3, float f4, int n, PoseStack poseStack, MultiBufferSource multiBufferSource, int n2) {
        float horizontalDistance = Mth.sqrt(f * f + f3 * f3);
        float totalDistance = Mth.sqrt(f * f + f2 * f2 + f3 * f3);

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.65625f + 0.1f, 0.5f);

        float yaw = (float) Math.atan2(f, f3);
        float pitch = (float) -Math.atan2(f2, horizontalDistance);
        poseStack.mulPose(Axis.YP.rotation(yaw));
        poseStack.mulPose(Axis.XP.rotation(pitch));

        float uvBottom = 0.0f;
        float uvTop = 1.0f;

        {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(BEAM2);
            float beamWidth = 0.05f;
            float halfWidth = beamWidth / 2.0f;
            PoseStack.Pose pose = poseStack.last();
            Matrix4f matrix4f = pose.pose();
            Matrix3f matrix3f = pose.normal();

            vertexConsumer.vertex(matrix4f, -halfWidth, -halfWidth, totalDistance)
                    .color(255, 255, 255, 20).uv(0.0f, 0.0f)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 0.0f, 1.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, -halfWidth, totalDistance)
                    .color(255, 255, 255, 20).uv(1.0f, 0.0f)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 0.0f, 1.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, halfWidth, totalDistance)
                    .color(255, 255, 255, 20).uv(1.0f, 1.0f)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 0.0f, 1.0f).endVertex();
            vertexConsumer.vertex(matrix4f, -halfWidth, halfWidth, totalDistance)
                    .color(255, 255, 255, 20).uv(0.0f, 1.0f)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 0.0f, 1.0f).endVertex();

            vertexConsumer.vertex(matrix4f, -halfWidth, halfWidth, 0.0f)
                    .color(255, 255, 255, 20).uv(0.0f, 1.0f)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 0.0f, -1.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, halfWidth, 0.0f)
                    .color(255, 255, 255, 20).uv(1.0f, 1.0f)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 0.0f, -1.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, -halfWidth, 0.0f)
                    .color(255, 255, 255, 20).uv(1.0f, 0.0f)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 0.0f, -1.0f).endVertex();
            vertexConsumer.vertex(matrix4f, -halfWidth, -halfWidth, 0.0f)
                    .color(255, 255, 255, 20).uv(0.0f, 0.0f)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 0.0f, -1.0f).endVertex();

            vertexConsumer.vertex(matrix4f, -halfWidth, halfWidth, 0.0f)
                    .color(200, 200, 255, 20).uv(0.0f, uvBottom)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 1.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, halfWidth, 0.0f)
                    .color(200, 200, 255, 20).uv(1.0f, uvBottom)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 1.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, halfWidth, totalDistance)
                    .color(200, 200, 255, 20).uv(1.0f, uvTop)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 1.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, -halfWidth, halfWidth, totalDistance)
                    .color(200, 200, 255, 20).uv(0.0f, uvTop)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 1.0f, 0.0f).endVertex();

            vertexConsumer.vertex(matrix4f, -halfWidth, -halfWidth, totalDistance)
                    .color(200, 200, 255, 20).uv(0.0f, uvTop)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, -1.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, -halfWidth, totalDistance)
                    .color(200, 200, 255, 20).uv(1.0f, uvTop)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, -1.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, -halfWidth, 0.0f)
                    .color(200, 200, 255, 20).uv(1.0f, uvBottom)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, -1.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, -halfWidth, -halfWidth, 0.0f)
                    .color(200, 200, 255, 20).uv(0.0f, uvBottom)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, -1.0f, 0.0f).endVertex();

            vertexConsumer.vertex(matrix4f, -halfWidth, -halfWidth, totalDistance)
                    .color(180, 180, 255, 20).uv(0.0f, uvTop)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, -1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, -halfWidth, -halfWidth, 0.0f)
                    .color(180, 180, 255, 20).uv(0.0f, uvBottom)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, -1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, -halfWidth, halfWidth, 0.0f)
                    .color(180, 180, 255, 20).uv(1.0f, uvBottom)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, -1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, -halfWidth, halfWidth, totalDistance)
                    .color(180, 180, 255, 20).uv(1.0f, uvTop)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, -1.0f, 0.0f, 0.0f).endVertex();

            vertexConsumer.vertex(matrix4f, halfWidth, -halfWidth, 0.0f)
                    .color(180, 180, 255, 20).uv(0.0f, uvBottom)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, -halfWidth, totalDistance)
                    .color(180, 180, 255, 20).uv(0.0f, uvTop)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, halfWidth, totalDistance)
                    .color(180, 180, 255, 20).uv(1.0f, uvTop)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, halfWidth, 0.0f)
                    .color(180, 180, 255, 20).uv(1.0f, uvBottom)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 1.0f, 0.0f, 0.0f).endVertex();
        }

        {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(JerotesRenderType.glowDoubleSided(getBeamLocation()));
            float beamWidth = 0.05f;
            float halfWidth = beamWidth / 2.0f;
            PoseStack.Pose pose = poseStack.last();
            Matrix4f matrix4f = pose.pose();
            Matrix3f matrix3f = pose.normal();

            vertexConsumer.vertex(matrix4f, -halfWidth, -halfWidth, totalDistance)
                    .color(255, 255, 255, 255).uv(0.0f, 0.0f)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 0.0f, 1.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, -halfWidth, totalDistance)
                    .color(255, 255, 255, 255).uv(1.0f, 0.0f)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 0.0f, 1.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, halfWidth, totalDistance)
                    .color(255, 255, 255, 255).uv(1.0f, 1.0f)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 0.0f, 1.0f).endVertex();
            vertexConsumer.vertex(matrix4f, -halfWidth, halfWidth, totalDistance)
                    .color(255, 255, 255, 255).uv(0.0f, 1.0f)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 0.0f, 1.0f).endVertex();

            vertexConsumer.vertex(matrix4f, -halfWidth, halfWidth, 0.0f)
                    .color(255, 255, 255, 255).uv(0.0f, 1.0f)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 0.0f, -1.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, halfWidth, 0.0f)
                    .color(255, 255, 255, 255).uv(1.0f, 1.0f)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 0.0f, -1.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, -halfWidth, 0.0f)
                    .color(255, 255, 255, 255).uv(1.0f, 0.0f)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 0.0f, -1.0f).endVertex();
            vertexConsumer.vertex(matrix4f, -halfWidth, -halfWidth, 0.0f)
                    .color(255, 255, 255, 255).uv(0.0f, 0.0f)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 0.0f, -1.0f).endVertex();

            vertexConsumer.vertex(matrix4f, -halfWidth, halfWidth, 0.0f)
                    .color(200, 200, 255, 255).uv(0.0f, uvBottom)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 1.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, halfWidth, 0.0f)
                    .color(200, 200, 255, 255).uv(1.0f, uvBottom)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 1.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, halfWidth, totalDistance)
                    .color(200, 200, 255, 255).uv(1.0f, uvTop)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 1.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, -halfWidth, halfWidth, totalDistance)
                    .color(200, 200, 255, 255).uv(0.0f, uvTop)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, 1.0f, 0.0f).endVertex();

            vertexConsumer.vertex(matrix4f, -halfWidth, -halfWidth, totalDistance)
                    .color(200, 200, 255, 255).uv(0.0f, uvTop)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, -1.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, -halfWidth, totalDistance)
                    .color(200, 200, 255, 255).uv(1.0f, uvTop)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, -1.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, -halfWidth, 0.0f)
                    .color(200, 200, 255, 255).uv(1.0f, uvBottom)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, -1.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, -halfWidth, -halfWidth, 0.0f)
                    .color(200, 200, 255, 255).uv(0.0f, uvBottom)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 0.0f, -1.0f, 0.0f).endVertex();

            vertexConsumer.vertex(matrix4f, -halfWidth, -halfWidth, totalDistance)
                    .color(180, 180, 255, 255).uv(0.0f, uvTop)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, -1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, -halfWidth, -halfWidth, 0.0f)
                    .color(180, 180, 255, 255).uv(0.0f, uvBottom)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, -1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, -halfWidth, halfWidth, 0.0f)
                    .color(180, 180, 255, 255).uv(1.0f, uvBottom)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, -1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, -halfWidth, halfWidth, totalDistance)
                    .color(180, 180, 255, 255).uv(1.0f, uvTop)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, -1.0f, 0.0f, 0.0f).endVertex();

            // 右侧面
            vertexConsumer.vertex(matrix4f, halfWidth, -halfWidth, 0.0f)
                    .color(180, 180, 255, 255).uv(0.0f, uvBottom)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, -halfWidth, totalDistance)
                    .color(180, 180, 255, 255).uv(0.0f, uvTop)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, halfWidth, totalDistance)
                    .color(180, 180, 255, 255).uv(1.0f, uvTop)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, halfWidth, halfWidth, 0.0f)
                    .color(180, 180, 255, 255).uv(1.0f, uvBottom)
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n2)
                    .normal(matrix3f, 1.0f, 0.0f, 0.0f).endVertex();
        }

        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(NetherSiphonLinkStationEntity blockEntity) {
        return true;
    }
}

