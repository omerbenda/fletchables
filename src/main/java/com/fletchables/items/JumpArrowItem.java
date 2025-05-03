package com.fletchables.items;

import com.fletchables.entities.JumpArrowEntity;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class JumpArrowItem extends ArrowItem {
  public JumpArrowItem(Item.Settings settings) {
    super(settings);
  }

  @Override
  public PersistentProjectileEntity createArrow(
      World world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
    return new JumpArrowEntity(shooter, world, stack.copyWithCount(1), shotFrom);
  }

  @Override
  public ProjectileEntity createEntity(
      World world, Position pos, ItemStack stack, Direction direction) {
    JumpArrowEntity jumpArrowEntity =
        new JumpArrowEntity(
            pos.getX(), pos.getY(), pos.getZ(), world, stack.copyWithCount(1), (ItemStack) null);
    jumpArrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
    return jumpArrowEntity;
  }

  @Override
  public void appendTooltip(
      ItemStack stack,
      TooltipContext context,
      TooltipDisplayComponent displayComponent,
      Consumer<Text> textConsumer,
      TooltipType type) {
    textConsumer.accept(Text.translatable("tooltip.item.jump_arrow").withColor(Colors.GRAY));

    super.appendTooltip(stack, context, displayComponent, textConsumer, type);
  }
}
