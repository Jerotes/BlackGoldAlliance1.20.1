package com.jerotes.blackgoldalliance.mixin;

import com.jerotes.blackgoldalliance.entity.Interface.ChangeAbstractPiglin;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.IBlackGoldPiglin;
import com.jerotes.blackgoldalliance.init.BGAMobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractPiglin.class)
public abstract class AbstractPiglinMixin extends Monster implements ChangeAbstractPiglin {
    @Shadow
    protected int timeInOverworld;

    protected AbstractPiglinMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "getTarget", at = @At("HEAD"), cancellable = true)
    public void getTarget(CallbackInfoReturnable<LivingEntity> cir) {
        if (this instanceof IBlackGoldPiglin) {
            cir.setReturnValue(super.getTarget());
            cir.cancel();
        }
    }

    public void blackGoldAlliance1_20_1$piglinBlessing() {
        this.timeInOverworld = 0;
    }

    @Inject(method = "isImmuneToZombification", at = @At("HEAD"), cancellable = true)
    private void isImmuneToZombification(CallbackInfoReturnable<Boolean> cir) {
        if (this.hasEffect(BGAMobEffects.PIGLIN_BLESSING.get())) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
    @Inject(method = "isConverting", at = @At("HEAD"), cancellable = true)
    public void isConverting(CallbackInfoReturnable<Boolean> cir) {
        if (this.hasEffect(BGAMobEffects.PIGLIN_BLESSING.get())) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}