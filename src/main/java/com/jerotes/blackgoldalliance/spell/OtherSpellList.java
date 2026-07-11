package com.jerotes.blackgoldalliance.spell;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.entity.MagicSummoned.ZombiePigman.ZombiePigmanEntity;
import com.jerotes.blackgoldalliance.init.BGAParticleTypes;
import com.jerotes.blackgoldalliance.init.BGASoundEvents;
import com.jerotes.jerotes.spell.MagicSpell;
import com.jerotes.jerotes.spell.MagicType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class OtherSpellList {
	//召唤萨满制僵尸猪人
	public static MagicSpell ConjureShamanicZombiePigman(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SELF, MagicType.MAIN, "conjure_shamanic_zombie_pigman", BGAParticleTypes.CONJURE_SHAMANIC_ZOMBIE_PIGMAN_DISPLAY.get(), BGASoundEvents.MAGIC_CONJURE_SHAMANIC_ZOMBIE_PIGMAN){
			public boolean spellFindUse() {
				return OtherSpellFind.ConjureShamanicZombiePigman(getCaster(), (Math.max(3, getSpellLevel() / 2)), (Math.max(3, getSpellLevel())), 16);
			}
			public String getSpellModId() {
				return BGA.MODID;
			}
			public int baseSpellLevel() {
				return 4;
			}
			public float getSpellDistance() {
				return 16;
			}
			public boolean canUse() {
				if (getCaster() != null) {
					List<ZombiePigmanEntity> list = getCaster().level().getEntitiesOfClass(ZombiePigmanEntity.class, getCaster().getBoundingBox().inflate(32.0, 32.0, 32.0));
					list.removeIf(summon -> summon.getOwner() != getCaster());
					return super.canUse() && list.size() <= getSpellLevel() * 3;
				}
				return super.canUse();
			}
		};
	}
	//诡异吐息
	public static MagicSpell WarpedBreath(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "warped_breath", BGAParticleTypes.WARPED_BREATH_DISPLAY.get(), BGASoundEvents.MAGIC_WARPED_BREATH){
			public boolean spellFindUse() {
				return OtherSpellFind.WarpedBreath(getCaster(), getTarget(), getSpellLevel(), getSpellLevel() * 6, getSpellLevel() - 1, getSpellAccuracy(), getSpellLevel() + 1, 5, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return BGA.MODID;
			}
			public int baseSpellLevel() {
				return 3;
			}
		};
	}
	//诡异射线
	public static MagicSpell RayofWarped(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "ray_of_warped", BGAParticleTypes.RAY_OF_WARPED_DISPLAY.get(), BGASoundEvents.MAGIC_RAY_OF_WARPED){
			public boolean spellFindUse() {
				return OtherSpellFind.RayofWarped(getCaster(), getTarget(), getSpellLevel(), getSpellLevel() * 4, getSpellLevel(), getSpellAccuracy(), 1, 5, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return BGA.MODID;
			}
			public int baseSpellLevel() {
				return 3;
			}
			public float getSpellDistance() {
				return 18;
			}
		};
	}
	//诡异炸弹
	public static MagicSpell WarpedBomb(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "warped_bomb", BGAParticleTypes.WARPED_BOMB_DISPLAY.get(), BGASoundEvents.MAGIC_WARPED_BOMB){
			public boolean spellFindUse() {
				return OtherSpellFind.WarpedBomb(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(),getSpellLevel() * 2, getSpellLevel() * 4, 4, 4);
			}
			public String getSpellModId() {
				return BGA.MODID;
			}
			public int baseSpellLevel() {
				return 2;
			}
			public float getSpellDistance() {
				return 12;
			}
			public boolean canUseTargetNone() {
				return true;
			}
		};
	}
}