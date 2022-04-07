package net.adriantodt.speedometer

import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.ConfigHolder
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer
import net.adriantodt.speedometer.data.SpeedometerConfig
import net.adriantodt.speedometer.item.SpeedometerItem
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Rarity
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("MemberVisibilityCanBePrivate")
object Speedometer : ModInitializer {
    val logger: Logger = LogManager.getLogger(Speedometer.javaClass)

    val configHolder: ConfigHolder<SpeedometerConfig> =
        AutoConfig.register(SpeedometerConfig::class.java, ::JanksonConfigSerializer)

    val speedometer = SpeedometerItem(itemSettings().maxCount(1).rarity(Rarity.UNCOMMON))

    override fun onInitialize() {
        identifier("speedometer").item(speedometer)
    }
}
