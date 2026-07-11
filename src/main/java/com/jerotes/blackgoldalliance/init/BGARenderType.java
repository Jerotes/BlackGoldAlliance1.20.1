package com.jerotes.blackgoldalliance.init;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.jerotes.init.JerotesRenderType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BGARenderType extends JerotesRenderType {
	public BGARenderType(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
		super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
	}
	private static final RenderType MARSHAL_ARMOR_GLINT = create("marshal_armor_glint",
			DefaultVertexFormat.POSITION_TEX,
			VertexFormat.Mode.QUADS,
			256,
			false,
			false,
			CompositeState.builder()
					.setShaderState(RENDERTYPE_ARMOR_GLINT_SHADER)
					.setTextureState(new TextureStateShard(new ResourceLocation(BGA.MODID, "textures/entity/piglin/the_black_gold_marshal/marshal_armor_glint.png"), true, false))
					.setWriteMaskState(COLOR_WRITE)
					.setCullState(NO_CULL)
					.setDepthTestState(EQUAL_DEPTH_TEST)
					.setTransparencyState(GLINT_TRANSPARENCY)
					.setTexturingState(GLINT_TEXTURING)
					.setLayeringState(VIEW_OFFSET_Z_LAYERING)
					.createCompositeState(false));

	public static RenderType marshalArmorGlint() {
		return MARSHAL_ARMOR_GLINT;
	}
}

