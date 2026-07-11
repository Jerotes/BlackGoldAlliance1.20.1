package com.jerotes.blackgoldalliance.entity.Shoot.Magic.Breath;

import com.jerotes.blackgoldalliance.init.BGAEntityType;
import com.jerotes.blackgoldalliance.init.BGAItems;
import com.jerotes.blackgoldalliance.init.BGAParticleTypes;
import com.jerotes.jerotes.entity.Shoot.Magic.Breath.BaseBreathEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class WarpedBreathEntity extends BaseBreathEntity {
    public WarpedBreathEntity(EntityType<? extends WarpedBreathEntity> entityType, Level level) {
        super(entityType, level);
    }

    public WarpedBreathEntity(EntityType<? extends WarpedBreathEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
        this(entityType, level);
        this.moveTo(d, d2, d3, this.getYRot(), this.getXRot());
        this.reapplyPosition();
        double d7 = Math.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
        if (d7 != 0.0) {
            this.xPower = d4 / d7 * 0.1;
            this.yPower = d5 / d7 * 0.1;
            this.zPower = d6 / d7 * 0.1;
        }
    }

    public WarpedBreathEntity(int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(BGAEntityType.WARPED_BREATH.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelMainEffectTime = spellLevelMainEffectTime;
        this.spellLevelMainEffectLevel = spellLevelMainEffectLevel;
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            DamageSource damageSource = AttackFind.findDamageType(livingEntity, JerotesDamageTypes.POISON, this, this.getOwner());
            boolean bl = livingEntity.hurt(damageSource, spellLevelDamage * Main.randomReach(RandomSource.create(), 1, 6) + 6);
            if (bl) {
                livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.DEADLY_POISON.get(), 20 * spellLevelMainEffectTime, spellLevelMainEffectLevel - 1), this.getEffectSource());
            }
            else {
                livingEntity.hurt(this.damageSources().indirectMagic(this, this.getOwner()), spellLevelDamage * Main.randomReach(RandomSource.create(), 1, 6) + 6);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (this.level().getBlockState(BlockPos.containing(blockHitResult.getLocation().x, blockHitResult.getLocation().y, blockHitResult.getLocation().z)).getBlock() == Blocks.WATER) {
            return;
        }
        if (!this.level().isClientSide) {
            this.Cloud(blockHitResult.getLocation().x, blockHitResult.getLocation().y, blockHitResult.getLocation().z);
            this.discard();
        }
    }

    @Override
    public float getCloudRadius() {
        return 1f;
    }
    @Override
    public float getCloudEffectTimeMultiple() {
        return 6f;
    }
    @Override
    public ParticleOptions getCloudParticle() {
        return BGAParticleTypes.WARPED_BREATH_FOG.get();
    }
    @Override
    public MobEffect getCloudEffect() {
        return JerotesMobEffects.DEADLY_POISON.get();
    }

    @Override
    public int getMaxLife() {
        return 30;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return BGAParticleTypes.WARPED_BREATH.get();
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(BGAItems.WARPED_BREATH.get()) : itemStack;
    }

    @Override
    protected float getInertia() {
        return 0.95f;
    }

    //@Override
    protected float getLiquidInertia() {
        return 0.6f;
    }
}