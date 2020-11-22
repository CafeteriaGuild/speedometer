package net.adriantodt.speedometer

import net.adriantodt.speedometer.Speedometer.speedometer
import net.adriantodt.speedometer.item.SpeedometerModelPredicateProvider
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.client.model.FabricModelPredicateProviderRegistry

object SpeedometerClient : ClientModInitializer {
    override fun onInitializeClient() {
        FabricModelPredicateProviderRegistry.register(
            speedometer, identifier("speed"), SpeedometerModelPredicateProvider
        )
    }
}