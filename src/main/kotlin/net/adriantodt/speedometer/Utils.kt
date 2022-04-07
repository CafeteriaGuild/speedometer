package net.adriantodt.speedometer

import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

fun identifier(path: String) = Identifier("speedometer", path)

fun Identifier.item(item: Item) = apply {
    Registry.register(Registry.ITEM, this, item)
}

var NbtCompound.active
    get() = getBoolean("Active")
    set(value) = putBoolean("Active", value)

fun itemSettings(): Item.Settings = Item.Settings().group(ItemGroup.TRANSPORTATION)
