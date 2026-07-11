package com.jerotes.blackgoldalliance.mixin;

import com.jerotes.blackgoldalliance.entity.Other.NetherSiphonCoreForceEntity;
import com.jerotes.blackgoldalliance.init.BGAMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Level.class)
public class LevelMixin extends CapabilityProvider<Level> {
    protected LevelMixin(Class<Level> baseClass) {
        super(baseClass);
    }

    @Inject(method = "destroyBlock(Lnet/minecraft/core/BlockPos;ZLnet/minecraft/world/entity/Entity;I)Z", at = @At("HEAD"), cancellable = true)
    private void destroyBlock(BlockPos pos, boolean dropBlock, Entity entity, int recursion, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(BGAMobEffects.PIGLIN_DETERRENT.get())) {
            AABB area = new AABB(pos).inflate(5);
            List<NetherSiphonCoreForceEntity> list = entity.level().getEntitiesOfClass(NetherSiphonCoreForceEntity.class, area);
            if (!list.isEmpty()) {
                cir.setReturnValue(false);
            }
        }
    }
}