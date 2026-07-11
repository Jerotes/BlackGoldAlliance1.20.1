package com.jerotes.blackgoldalliance;

import com.jerotes.blackgoldalliance.client.CilentInit;
import com.jerotes.blackgoldalliance.config.OtherMainConfig;
import com.jerotes.blackgoldalliance.init.*;
import com.jerotes.blackgoldalliance.network.OtherPacketHandler;
import com.jerotes.blackgoldalliance.spell.OtherSpellType;
import com.jerotes.jerotes.spell.SpellRegistry;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.io.IOException;

@Mod(BGA.MODID)
public class BGA
{
    public static final String MODID = "blackgoldalliance";
    public static final Logger LOGGER = LogUtils.getLogger();
    public BGA() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BGAItems.REGISTRY.register(modEventBus);
        BGABlocks.REGISTRY.register(modEventBus);
        BGABlockEntityType.REGISTRY.register(modEventBus);
        BGATabs.REGISTRY.register(modEventBus);
        BGAMobEffects.REGISTRY.register(modEventBus);
        BGAEntityType.REGISTRY.register(modEventBus);
        BGAParticleTypes.REGISTRY.register(modEventBus);
        BGAMenus.REGISTRY.register(modEventBus);
        modEventBus.addListener(this::initClient);
        modEventBus.addListener(this::commonSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OtherMainConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, OtherMainConfig.CLIENT_SPEC);
        for (OtherSpellType type : OtherSpellType.values()) {
            SpellRegistry.register(type);
        }
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(OtherPacketHandler::register);
        event.enqueueWork(BGAItems::setup);
    }

    private void initClient(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        event.enqueueWork(CilentInit::clientInit);
    }

    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(
                new ShaderInstance(
                        event.getResourceProvider(),
                        new ResourceLocation("blackgoldalliance", "shockwave"),
                        DefaultVertexFormat.POSITION_TEX_COLOR
                ),
                shader -> {
                }
        );
    }
}
