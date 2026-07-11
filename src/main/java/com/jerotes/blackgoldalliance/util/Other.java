package com.jerotes.blackgoldalliance.util;

import com.jerotes.blackgoldalliance.config.OtherMainConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class Other {
	//袭击Boss数量
	public static int getRaidBossCount(LivingEntity boss) {
		if (OtherMainConfig.RaidBossCanIncreaseSummonCountBasedOnPlayerCount
				&& (OtherMainConfig.RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainAllBoss
				|| OtherMainConfig.RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainBoss.contains(boss.getEncodeId()))) {
			double reach = OtherMainConfig.RaidBossCanIncreaseSummonCountBasedOnPlayerCountFindReach;
			List<Player> listTeam = boss.level().getEntitiesOfClass(Player.class, boss.getBoundingBox().inflate(reach, reach, reach));
			if (listTeam.isEmpty()) {
				return 1;
			}
			else return Math.min(listTeam.size(), OtherMainConfig.RaidBossCanIncreaseSummonCountBasedOnPlayerCountMaxCount);
		}
		return 1;
	}
}