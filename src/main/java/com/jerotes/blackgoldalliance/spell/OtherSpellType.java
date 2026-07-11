package com.jerotes.blackgoldalliance.spell;

import com.jerotes.jerotes.spell.MagicSpell;
import com.jerotes.jerotes.spell.SpellTypeInterface;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public enum OtherSpellType implements SpellTypeInterface {
	BLACKGOLDALLIANCE_CONJURE_SHAMANIC_ZOMBIE_PIGMAN("blackgoldalliance_conjure_shamanic_zombie_pigman"),
	BLACKGOLDALLIANCE_WARPED_BREATH("blackgoldalliance_warped_breath"),
	BLACKGOLDALLIANCE_RAY_OF_WARPED("blackgoldalliance_ray_of_warped"),
	BLACKGOLDALLIANCE_WARPED_BOMB("blackgoldalliance_warped_bomb");

	private final String id;

	private OtherSpellType(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public MagicSpell magicSpellGet(int level, LivingEntity caster, Entity target) {
		return switch (this) {
			case BLACKGOLDALLIANCE_CONJURE_SHAMANIC_ZOMBIE_PIGMAN -> OtherSpellList.ConjureShamanicZombiePigman(level, caster, target);
			case BLACKGOLDALLIANCE_WARPED_BREATH -> OtherSpellList.WarpedBreath(level, caster, target);
			case BLACKGOLDALLIANCE_RAY_OF_WARPED -> OtherSpellList.RayofWarped(level, caster, target);
			case BLACKGOLDALLIANCE_WARPED_BOMB -> OtherSpellList.WarpedBomb(level, caster, target);
		};
	}

	public void stop(LivingEntity caster, int level, boolean must) {
		stops(caster, level, must);
	}
	public static void stops(LivingEntity caster, int level, boolean must) {

	}
}