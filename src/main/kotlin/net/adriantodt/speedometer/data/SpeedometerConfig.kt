package net.adriantodt.speedometer.data

import me.sargunvohra.mcmods.autoconfig1u.ConfigData
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry

@Config(name = "speedometer")
class SpeedometerConfig : ConfigData {
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    var velocityUnit = VelocityUnit.METERS_PER_SECOND

    enum class VelocityUnit(val multiplier: Double, val suffix: String) : SelectionListEntry.Translatable {
        METERS_PER_SECOND(1.0, "m/s"),
        KILOMETERS_PER_HOUR(3.6, "km/h"),
        MILES_PER_HOUR(2.23694, "mph");

        override fun getKey(): String = "text.speedometer.velocityUnit.${name.toLowerCase()}"
    }

}