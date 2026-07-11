package com.jerotes.blackgoldalliance.init;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.world.inventory.NetherSiphonCoreMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BGAMenus {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, BGA.MODID);
    public static final RegistryObject<MenuType<NetherSiphonCoreMenu>> NETHER_SIPHON_CORE = REGISTRY.register("nether_siphon_core", () -> new MenuType<>(NetherSiphonCoreMenu::new, FeatureFlags.VANILLA_SET));
 }