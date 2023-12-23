package com.indref.industrial_reforged.registries.items.armor;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;

public class HazmatSuiteItem extends ArmorItem {
    public HazmatSuiteItem(Type type, Properties properties) {
        super(ArmorMaterials.LEATHER, type, properties.stacksTo(1));
    }
}
