package com.jerotes.blackgoldalliance.entity.Shoot.Arrow;

import com.jerotes.blackgoldalliance.init.BGAEntityType;
import com.jerotes.blackgoldalliance.init.BGAItems;
import com.jerotes.jerotes.entity.Shoot.Arrow.BaseArrowEntity;
import com.jerotes.jerotes.util.AttackFind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BlackGoldSpectralArrowEntity extends BaseArrowEntity {
    public static final ItemStack item = new ItemStack(BGAItems.BLACK_GOLD_SPECTRAL_ARROW.get());
    private static final EntityType<BlackGoldSpectralArrowEntity> type = BGAEntityType.BLACK_GOLD_SPECTRAL_ARROW.get();

    public BlackGoldSpectralArrowEntity(EntityType<? extends BlackGoldSpectralArrowEntity> entityType, Level level) {
        super(entityType, level, item, 3.5);
    }
    public BlackGoldSpectralArrowEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(level, livingEntity, itemStack, type, 3.5);
    }
    public BlackGoldSpectralArrowEntity(Level level, double d, double d2, double d3, ItemStack itemStack) {
        super(level, d, d2, d3, itemStack, type, 3.5);
    }



    protected boolean canHitEntity(Entity entity) {
        if (this.getOwner() != null && entity instanceof LivingEntity livingEntity && AttackFind.SameFactionAvoidDamage(this.getOwner(), livingEntity)) {
            return false;
        } else {
            return super.canHitEntity(entity);
        }
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround) {
            this.level().addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    protected void doPostHurtEffects(LivingEntity p_37422_) {
        super.doPostHurtEffects(p_37422_);
        MobEffectInstance $$1 = new MobEffectInstance(MobEffects.GLOWING, this.duration, 0);
        p_37422_.addEffect($$1, this.getEffectSource());
    }

    private int duration = 200;
    public void readAdditionalSaveData(CompoundTag p_37424_) {
        super.readAdditionalSaveData(p_37424_);
        if (p_37424_.contains("Duration")) {
            this.duration = p_37424_.getInt("Duration");
        }
    }
    public void addAdditionalSaveData(CompoundTag p_37426_) {
        super.addAdditionalSaveData(p_37426_);
        p_37426_.putInt("Duration", this.duration);
    }

    @Override
    public int basePierce() {
        return 3;
    }
    @Override
    protected float getWaterInertia() {
        return 0.95f;
    }
}


