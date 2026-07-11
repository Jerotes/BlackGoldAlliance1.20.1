package com.jerotes.blackgoldalliance.init;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.block.NetherSiphonCore;
import com.jerotes.blackgoldalliance.block.NetherSiphonLinkStation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BGABlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, BGA.MODID);
	public static final RegistryObject<Block> NETHER_SIPHON_CORE = REGISTRY.register("nether_siphon_core", NetherSiphonCore::new);
	public static final RegistryObject<Block> NETHER_SIPHON_LINK_STATION = REGISTRY.register("nether_siphon_link_station", NetherSiphonLinkStation::new);
}
