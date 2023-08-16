package net.notjustanna.speedometer

import net.minecraft.item.Item
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

fun identifier(path: String) = Identifier("speedometer", path)

fun Identifier.item(item: Item) = apply {
    Registry.register(Registries.ITEM, this, item)
}

var NbtCompound.active
    get() = getBoolean("Active")
    set(value) = putBoolean("Active", value)

var NbtCompound.highestSpeed
    get() = getDouble("HighestSpeed")
    set(value) = putDouble("HighestSpeed", value)

fun itemSettings(): Item.Settings = Item.Settings()//.group(ItemGroup.TRANSPORTATION)
