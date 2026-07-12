package com.jerotes.blackgoldalliance.event;

import com.jerotes.blackgoldalliance.block.NetherSiphonCoreEntity;
import com.jerotes.blackgoldalliance.entity.Other.PortalPointEntity;
import com.jerotes.blackgoldalliance.init.BGAEntityType;
import com.jerotes.jerotes.util.Main;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommandEvent {

	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("jerotes").requires(s -> s.hasPermission(4))
				.then(Commands.literal("other")
						.then(Commands.literal("blackgoldalliance")
								.then(Commands.literal("PortalPoint").then(Commands.argument("player", EntityArgument.player()).executes(arguments ->
														{
															Player playerTo = EntityArgument.getPlayer(arguments, "player");
															PlayerTeam teams = (PlayerTeam) playerTo.getTeam();
															PortalPointEntity portalPointEntity = BGAEntityType.PORTAL_POINT.get().spawn(arguments.getSource().getLevel(), BlockPos.containing(arguments.getSource().getPosition().x,arguments.getSource().getPosition().y, arguments.getSource().getPosition().z), MobSpawnType.MOB_SUMMONED);
															if (portalPointEntity != null) {
																portalPointEntity.setSelfX((float) playerTo.getX());
																portalPointEntity.setSelfY((float) playerTo.getY());
																portalPointEntity.setSelfZ((float) playerTo.getZ());
																BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(playerTo, 20);
																float targetPosX = summonPos.getX();
																float targetPosY = summonPos.getY();
																float targetPosZ = summonPos.getZ();

																portalPointEntity.setTargetAddX(targetPosX - (float) playerTo.getX());
																portalPointEntity.setTargetAddY(targetPosY - (float) playerTo.getY());
																portalPointEntity.setTargetAddZ(targetPosZ - (float) playerTo.getZ());
																if (playerTo.level() instanceof ServerLevel serverLevel) {
																	if (teams != null) {
																		serverLevel.getScoreboard().addPlayerToTeam(portalPointEntity.getStringUUID(), teams);
																	}
																}
															}
															return 0;
														}
												)
										)
								)
						)
				)
				.then(Commands.literal("event")
						.then(Commands.literal("piglin_raid")
								.then(Commands.argument("player", EntityArgument.player())
										.then(Commands.argument("number", IntegerArgumentType.integer(1))
												.executes(arguments ->
														{
															int n = IntegerArgumentType.getInteger(arguments, "number");
															Player player = EntityArgument.getPlayer(arguments, "player");
															EntityAboutEvent.findNearestNetherSiphonCore(player, 64).ifPresent(core -> {
																core.startChallenge(n, player);
															});
															return 0;
														}
												)
										)
								)
						)
				)
				.then(Commands.literal("player_change").then(Commands.argument("player", EntityArgument.player())
								.then(Commands.literal("add")
										.then(Commands.literal("defeat_th_highest_level_piglin_raid")
												.then(Commands.argument("number", DoubleArgumentType.doubleArg(0)).executes(arguments -> {
																	Entity entity = arguments.getSource().getEntity();
																	Player playerTo = EntityArgument.getPlayer(arguments, "player");
																	if (entity instanceof Player player) {
																		double n = DoubleArgumentType.getDouble(arguments, "number");
																		NetherSiphonCoreEntity.SetDefeatTheHighestLevelPiglinRaid(playerTo, (int) n);
																		player.sendSystemMessage(Component.literal(String.valueOf((NetherSiphonCoreEntity.GetDefeatTheHighestLevelPiglinRaid(playerTo)))).withStyle(ChatFormatting.GOLD));
																	}
																	return 0;
																}
														)
												)
										)
								)
								.then(Commands.literal("set")
										.then(Commands.literal("defeat_th_highest_level_piglin_raid")
												.then(Commands.argument("number", DoubleArgumentType.doubleArg(0)).executes(arguments -> {
																	Entity entity = arguments.getSource().getEntity();
																	Player playerTo = EntityArgument.getPlayer(arguments, "player");
																	if (entity instanceof Player player) {
																		double n = DoubleArgumentType.getDouble(arguments, "number");
																		NetherSiphonCoreEntity.SetDefeatTheHighestLevelPiglinRaid(playerTo, (int) n);
																		player.sendSystemMessage(Component.literal(String.valueOf((NetherSiphonCoreEntity.GetDefeatTheHighestLevelPiglinRaid(playerTo)))).withStyle(ChatFormatting.GOLD));
																	}
																	return 0;
																}
														)
												)
										)
								)
								.then(Commands.literal("get")
										.then(Commands.literal("defeat_th_highest_level_piglin_raid").executes(arguments -> {
															Entity entity = arguments.getSource().getEntity();
															Player playerTo = EntityArgument.getPlayer(arguments, "player");
															if (entity instanceof Player player) {
																player.sendSystemMessage(Component.literal(String.valueOf((NetherSiphonCoreEntity.GetDefeatTheHighestLevelPiglinRaid(playerTo)))).withStyle(ChatFormatting.GOLD));
															}
															return 0;
														}
												)
										)
								)
						)
				)
		);
	}
}