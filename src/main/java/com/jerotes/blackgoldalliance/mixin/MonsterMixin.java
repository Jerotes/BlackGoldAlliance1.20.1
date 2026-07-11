package com.jerotes.blackgoldalliance.mixin;

import com.jerotes.blackgoldalliance.init.BGAMobEffects;
import com.jerotes.jerotes.util.EntityFactionFind;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Monster.class)
public abstract class MonsterMixin extends PathfinderMob {
    protected MonsterMixin(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }
    @Inject(method = "shouldDespawnInPeaceful", at = @At("HEAD"), cancellable = true)
    private void shouldDespawnInPeaceful(CallbackInfoReturnable<Boolean> cir) {
        boolean bl = (((Monster)(Object)this) instanceof ZombifiedPiglin);
        if (this.hasEffect(BGAMobEffects.PIGLIN_BLESSING.get()) && (EntityFactionFind.isPiglin(this) || this instanceof HoglinBase || bl)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}