package net.notjustanna.speedometer.item

import net.notjustanna.speedometer.ExpandedLivingEntity
import net.minecraft.client.item.ClampedModelPredicateProvider
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack

object SpeedometerModelPredicateProvider : ClampedModelPredicateProvider {
    override fun unclampedCall(stack: ItemStack?, world: ClientWorld?, entity: LivingEntity?, seed: Int): Float {
        if (entity == null) {
            return 0f
        }
        val lastPos = (entity as? ExpandedLivingEntity)?.lastPos
        if (lastPos != null) {
            // Basically, velocity * 20 / 12 * 2.5
            // ... but rounded up a bit
            return lastPos.distanceTo(entity.pos).toFloat() * 4.16f
        }
        // Fallback
        return entity.velocity.length().toFloat() * 4.16f
    }
}