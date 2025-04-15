package com.envygames.equipmentexpanded.player;

import com.envygames.equipmentexpanded.EquipmentExpanded;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class EquipmentTags {
    private static TagKey<Item> tag(String path) {
        return TagKey.create(BuiltInRegistries.ITEM.key(),
                ResourceLocation.fromNamespaceAndPath(EquipmentExpanded.MOD_ID, path));
    }

    public static final TagKey<Item> RELICS           = tag("relics");
    public static final TagKey<Item> RINGS            = tag("rings");
    public static final TagKey<Item> AMULETS          = tag("amulets");
    public static final TagKey<Item> BELTS            = tag("belts");
    public static final TagKey<Item> QUIVERS          = tag("quivers");
    public static final TagKey<Item> BACKPACKS        = tag("backpacks");
    public static final TagKey<Item> MAGNET_ITEMS     = tag("magnet_items");
    public static final TagKey<Item> STORAGE_DEVICES  = tag("storage_devices");
    public static final TagKey<Item> CRAFTING_DEVICES = tag("crafting_devices");

    private EquipmentTags() {}
}
