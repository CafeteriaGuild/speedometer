package net.adriantodt.speedometer.item

import net.adriantodt.speedometer.ExpandedLivingEntity
import net.adriantodt.speedometer.Speedometer
import net.adriantodt.speedometer.active
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class SpeedometerItem(settings: Settings) : Item(settings) {
    override fun use(world: World?, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)

        val copy = stack.copy()
        val tag = copy.orCreateTag
        tag.active = !tag.active
        return TypedActionResult.success(copy)
    }

    override fun inventoryTick(stack: ItemStack, world: World, user: Entity, slot: Int, selected: Boolean) {
        if (user is PlayerEntity && user is ExpandedLivingEntity && world.isClient && stack.tag?.active == true) {
            val unit = Speedometer.configHolder.config.velocityUnit
            val lastPos = user.lastPos
            if (lastPos != null) {
                val speed = lastPos.distanceTo(user.pos) * 20 * unit.multiplier
                user.sendMessage(LiteralText("| %.4f %s |".format(speed, unit.suffix)), true)
            }
        }
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, ctx: TooltipContext) {
        tooltip += TranslatableText("$translationKey.description")
        tooltip += TranslatableText("tooltip.speedometer.configure_speedometer")
        tooltip += if (stack.tag?.active == true) {
            TranslatableText("tooltip.speedometer.deactivate_speedometer")
        } else {
            TranslatableText("tooltip.speedometer.activate_speedometer")
        }
    }
}