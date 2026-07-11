package com.jerotes.blackgoldalliance.block;

import com.jerotes.blackgoldalliance.init.BGABlockEntityType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class NetherSiphonLinkStation extends BaseEntityBlock {
	public static final IntegerProperty TYPE = BlockStateProperties.AGE_3;
	protected static final VoxelShape BOUNDING_BOX= Shapes.or(
			AbstractCauldronBlock.box(6.5, 0.0, 6.5, 9.5, 4.0, 9.5),
			AbstractCauldronBlock.box(7.0, 4.0, 7.0, 9.0, 10.5, 9.0),
			AbstractCauldronBlock.box(6.0, 10.5, 6.0, 10.0, 14.5, 10.0));

	public NetherSiphonLinkStation() {
		super(Properties.of().lightLevel(blockState -> blockState.getValue(BlockStateProperties.AGE_3) != 3 ? 15 : 0).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(50.0F, 1200.0F).sound(SoundType.NETHERITE_BLOCK));
		this.registerDefaultState(((this.stateDefinition.any()).setValue(TYPE, 0)));
	}

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return BOUNDING_BOX;
	}
	@Override
	public VoxelShape getOcclusionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return BOUNDING_BOX;
	}
	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
		return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
	}
	@Override
	public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		BlockEntity blockentity = serverLevel.getBlockEntity(blockPos);
		if (blockentity instanceof NetherSiphonLinkStationEntity netherSiphonLinkStationEntity) {
			netherSiphonLinkStationEntity.recheckOpen();
		}
	}

	@Override
	public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
		if (itemStack.hasCustomHoverName()) {
			BlockEntity blockentity = level.getBlockEntity(blockPos);
			if (blockentity instanceof NetherSiphonLinkStationEntity netherSiphonLinkStationEntity) {
				netherSiphonLinkStationEntity.setCustomName(itemStack.getHoverName());
			}
		}
	}
	@Override
	public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
		if (!blockState.is(blockState2.getBlock())) {
			BlockEntity blockentity = level.getBlockEntity(blockPos);
			if (blockentity instanceof Container) {
				Containers.dropContents(level, blockPos, (Container)blockentity);
				level.updateNeighbourForOutputSignal(blockPos, this);
			}
			super.onRemove(blockState, level, blockPos, blockState2, bl);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(TYPE);
	}

	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new NetherSiphonLinkStationEntity(blockPos, blockState);
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return createTickerHelper(blockEntityType, BGABlockEntityType.NETHER_SIPHON_LINK_STATION.get(),
				level.isClientSide() ? NetherSiphonLinkStationEntity::clientTick : NetherSiphonLinkStationEntity::serverTick);
	}

	@Override
	public RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.MODEL;
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState blockState) {
		return true;
	}
	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos) {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(blockPos));
	}


	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
		super.appendHoverText(itemStack, blockGetter, list, tooltipFlag);
	}

	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}
}