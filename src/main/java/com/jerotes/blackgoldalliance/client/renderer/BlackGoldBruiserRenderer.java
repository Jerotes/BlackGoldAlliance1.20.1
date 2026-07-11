package com.jerotes.blackgoldalliance.client.renderer;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.client.layer.GlowOtherBodyLayer;
import com.jerotes.blackgoldalliance.client.model.Modelblack_gold_bruiser;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldBruiserEntity;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class BlackGoldBruiserRenderer extends MobRenderer<BlackGoldBruiserEntity, Modelblack_gold_bruiser<BlackGoldBruiserEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/black_gold_bruiser/black_gold_bruiser.png");
	private static final ResourceLocation DEAD_0_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/black_gold_bruiser/black_gold_bruiser_dead_0.png");
	private static final ResourceLocation DEAD_1_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/black_gold_bruiser/black_gold_bruiser_dead_1.png");
	private static final ResourceLocation DEAD_2_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/black_gold_bruiser/black_gold_bruiser_dead_2.png");
	private static final ResourceLocation DEAD_3_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/black_gold_bruiser/black_gold_bruiser_dead_3.png");
	private static final ResourceLocation DEAD_4_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/black_gold_bruiser/black_gold_bruiser_dead_4.png");
	private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/black_gold_bruiser/black_gold_bruiser_glow.png");

	public BlackGoldBruiserRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelblack_gold_bruiser<>(context.bakeLayer(Modelblack_gold_bruiser.LAYER_LOCATION)), 1.25f);
		this.addLayer(new GlowOtherBodyLayer<>(this, new Modelblack_gold_bruiser(context.bakeLayer(Modelblack_gold_bruiser.LAYER_LOCATION)), GLOW_LOCATION));
	}
	@Override
	public void render(BlackGoldBruiserEntity entity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		entity.thisTickRenderTime += 1;
		float attackAnim = entity.getAttackAnim();
		float fs = entity.lastTickRenderTime + 1;
		float smoothFactor = 1f / fs;
		entity.attackAnimProgress = Mth.lerp(smoothFactor, entity.attackAnimProgress, attackAnim);
		if (entity.isLoss()) {
			shadowRadius = 1.25f + Math.min(1, Math.max(0, (entity.deathTime - 15f) / 15f));
			if (entity.deathTime > 15) {
				poseStack.pushPose();
				float f7 = (float) entity.getX();
				float f8 = (float) (entity.getY() + 12f);
				float f9 = (float) entity.getZ();
				float f10 = (float) (f7 - Mth.lerp((double) f2, entity.xo, entity.getX()));
				float f11 = (float) (f8 - Mth.lerp((double) f2, entity.yo, entity.getY()));
				float f12 = (float) (f9 - Mth.lerp((double) f2, entity.zo, entity.getZ()));
				renderLightLock(f10, f11, f12, f2, entity.tickCount, poseStack, multiBufferSource, n);
				poseStack.popPose();
			}
		}
		else {
			shadowRadius = 1.25f;
		}
		super.render(entity, f, f2, poseStack, multiBufferSource, n);
	}
	@Override
	public ResourceLocation getTextureLocation(BlackGoldBruiserEntity entity) {
		if (entity.isLoss()) {
			if (entity.deathTime > 30) {
				return DEAD_4_LOCATION;
			}
			if (entity.deathTime > 24) {
				return DEAD_3_LOCATION;
			}
			if (entity.deathTime > 21) {
				return DEAD_2_LOCATION;
			}
			if (entity.deathTime > 18) {
				return DEAD_1_LOCATION;
			}
			if (entity.deathTime > 15) {
				return DEAD_0_LOCATION;
			}
		}
		return LOCATION;
	}

	@Override
	protected float getFlipDegrees(BlackGoldBruiserEntity entity) {
		return 0.0f;
	}

	@Override
	protected boolean isShaking(BlackGoldBruiserEntity entity) {
		return super.isShaking(entity) || entity.isConverting();
	}

	private static final ResourceLocation BEAM_LOCATION = new ResourceLocation(BGA.MODID, "textures/entity/piglin/black_gold_bruiser/black_gold_bruiser_dead_beam.png");
	private static final RenderType BEAM = JerotesRenderType.glowDoubleSided(BEAM_LOCATION);

	public static void renderLightLock(float f, float f2, float f3, float f4, int n, PoseStack poseStack, MultiBufferSource multiBufferSource, int n2) {
		float f5 = Mth.sqrt(f * f + f3 * f3);
		float f6 = Mth.sqrt(f * f + f2 * f2 + f3 * f3);

		poseStack.pushPose();
		poseStack.translate(0.0f, 2.0f, 0.0f);
		poseStack.mulPose(Axis.YP.rotation((float)(-Math.atan2(f3, f)) - 1.5707964f));
		poseStack.mulPose(Axis.XP.rotation((float)(-Math.atan2(f5, f2)) - 1.5707964f));

		VertexConsumer vertexConsumer = multiBufferSource.getBuffer(BEAM);
		float f7 = 0.0f - ((float)n + f4) * 0.01f;
		float f8 = f6 / 32.0f - ((float)n + f4) * 0.01f;

		int segments = 16;
		float startRadius = 0.75f * 1.5f;
		float endRadius = 0.75f;

		PoseStack.Pose pose = poseStack.last();
		Matrix4f matrix4f = pose.pose();
		Matrix3f matrix3f = pose.normal();

		float lastStartX = Mth.sin(0) * startRadius;
		float lastStartY = Mth.cos(0) * startRadius;
		float lastEndX = Mth.sin(0) * endRadius;
		float lastEndY = Mth.cos(0) * endRadius;
		float lastU = 0.0f;

		for (int i = 1; i <= segments; ++i) {
			float angle = (float)i / (float)segments * Mth.TWO_PI;
			float currentStartX = Mth.sin(angle) * startRadius;
			float currentStartY = Mth.cos(angle) * startRadius;
			float currentEndX = Mth.sin(angle) * endRadius;
			float currentEndY = Mth.cos(angle) * endRadius;
			float currentU = (float)i / (float)segments;

			vertexConsumer.vertex(matrix4f, lastStartX, lastStartY, 0.0f)
					.color(0, 0, 0, 255)
					.uv(lastU, f7)
					.overlayCoords(OverlayTexture.NO_OVERLAY)
					.uv2(n2)
					.normal(matrix3f, lastStartX/startRadius, lastStartY/startRadius, 0.0f)
					.endVertex();

			vertexConsumer.vertex(matrix4f, currentStartX, currentStartY, 0.0f)
					.color(0, 0, 0, 255)
					.uv(currentU, f7)
					.overlayCoords(OverlayTexture.NO_OVERLAY)
					.uv2(n2)
					.normal(matrix3f, currentStartX/startRadius, currentStartY/startRadius, 0.0f)
					.endVertex();

			vertexConsumer.vertex(matrix4f, currentEndX, currentEndY, f6)
					.color(255, 255, 255, 255)
					.uv(currentU, f8)
					.overlayCoords(OverlayTexture.NO_OVERLAY)
					.uv2(n2)
					.normal(matrix3f, currentEndX/endRadius, currentEndY/endRadius, 0.0f)
					.endVertex();

			vertexConsumer.vertex(matrix4f, lastEndX, lastEndY, f6)
					.color(255, 255, 255, 255)
					.uv(lastU, f8)
					.overlayCoords(OverlayTexture.NO_OVERLAY)
					.uv2(n2)
					.normal(matrix3f, lastEndX/endRadius, lastEndY/endRadius, 0.0f)
					.endVertex();

			lastStartX = currentStartX;
			lastStartY = currentStartY;
			lastEndX = currentEndX;
			lastEndY = currentEndY;
			lastU = currentU;
		}
		poseStack.popPose();
	}
}
