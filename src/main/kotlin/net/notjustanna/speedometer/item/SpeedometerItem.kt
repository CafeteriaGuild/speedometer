package net.notjustanna.speedometer.item

import net.notjustanna.speedometer.ExpandedLivingEntity
import net.notjustanna.speedometer.Speedometer
import net.notjustanna.speedometer.active
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import net.notjustanna.speedometer.highestSpeed

class SpeedometerItem(settings: Settings) : Item(settings) {
    override fun use(world: World?, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)

        val copy = stack.copy()
        val nbt = copy.orCreateNbt
        nbt.active = !nbt.active
        if (!nbt.active) nbt.remove("HighestSpeed")
        return TypedActionResult.success(copy)
    }

    override fun inventoryTick(stack: ItemStack, world: World, user: Entity, slot: Int, selected: Boolean) {
        if (user is PlayerEntity && user is ExpandedLivingEntity && world.isClient && stack.nbt?.active == true) {
            val unit = Speedometer.configHolder.config.velocityUnit
            val lastPos = user.lastPos
            if (lastPos != null) {
                val nbt = stack.orCreateNbt
                val speed = lastPos.distanceTo(user.pos) * 20 * unit.multiplier
                var formatting = Formatting.WHITE
                if (nbt.highestSpeed < speed) {
                    nbt.highestSpeed = speed
                    formatting = Formatting.YELLOW
                }
                user.sendMessage(Text.literal("| %.4f %s |".format(speed, unit.suffix)).formatted(formatting), true)
            }
        }
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, ctx: TooltipContext) {
        tooltip += Text.translatable("$translationKey.description")
        tooltip += Text.translatable("tooltip.speedometer.configure_speedometer")
        if (stack.nbt?.active == true) {
            val unit = Speedometer.configHolder.config.velocityUnit
            tooltip += Text.translatable("tooltip.speedometer.highest_speed", Text.literal("%.4f %s".format(stack.nbt?.highestSpeed ?: 0.0, unit.suffix))).formatted(Formatting.GRAY)
            tooltip += Text.translatable("tooltip.speedometer.deactivate_speedometer")
        } else {
            tooltip += Text.translatable("tooltip.speedometer.activate_speedometer")
        }
    }
}