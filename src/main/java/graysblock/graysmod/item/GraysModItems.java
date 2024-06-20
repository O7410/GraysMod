package graysblock.graysmod.item;

import graysblock.graysmod.GraysMod;
import graysblock.graysmod.component.type.GraysModFoodComponents;
import graysblock.graysmod.entity.GraysModEntityTypes;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class GraysModItems {

    public static final Item BALL_OF_REPULSION_GEL = registerItem("ball_of_repulsion_gel", new BallOfRepulsionGelItem(new Item.Settings()));
    public static final Item MAKESHIFT_WINGS = registerItem("makeshift_wings", new MakeshiftWingsItem(new Item.Settings().maxDamage(60)));
    public static final Item HOT_POCKET = registerItem("hot_pocket", new HotPocketItem(new Item.Settings().food(GraysModFoodComponents.HOT_POCKET)));
    public static final Item PIGLIN_WAR_AXE = registerItem("piglin_war_axe", new GuildedSwordItem(GraysModToolMaterials.GUILDED, (new Item.Settings()).fireproof().attributeModifiers(GuildedSwordItem.createAttributeModifiers(GraysModToolMaterials.GUILDED, 4, -2.75f))));
    public static final Item PRISMARINE_AXE = registerItem("prismarine_axe", new AxeItem(GraysModToolMaterials.PRISMARINE, (new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(GraysModToolMaterials.PRISMARINE, 6.0F, -3.0F)))));
    public static final Item PRISMARINE_HOE = registerItem("prismarine_hoe", new HoeItem(GraysModToolMaterials.PRISMARINE, (new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(GraysModToolMaterials.PRISMARINE, -2.0F, -1.0F)))));
    public static final Item PRISMARINE_PICKAXE = registerItem("prismarine_pickaxe", new PickaxeItem(GraysModToolMaterials.PRISMARINE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(GraysModToolMaterials.PRISMARINE, 1.0F, -2.8F))));
    public static final Item PRISMARINE_SHOVEL = registerItem("prismarine_shovel", new ShovelItem(GraysModToolMaterials.PRISMARINE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(GraysModToolMaterials.PRISMARINE, 1.5F, -3.0F))));
    public static final Item PRISMARINE_SWORD = registerItem("prismarine_sword", new SwordItem(GraysModToolMaterials.PRISMARINE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(GraysModToolMaterials.PRISMARINE, 3, -2.4F))));
    public static final Item STAFF_OF_REGROWTH = registerItem("staff_of_regrowth", new StaffOfRegrowthItem(new Item.Settings()));
    public static final Item STRIDER_BOOTS = registerItem("strider_boots", new ArmorItem(GraysModArmorMaterials.STRIDER, ArmorItem.Type.BOOTS, new Item.Settings().fireproof().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(12))));
    public static final Item STRIDER_SCALE = registerItem("strider_scale", new Item(new Item.Settings().fireproof()));
    public static final Item TURTLE_SHELL = registerItem("turtle_shell", new ArmorItem(GraysModArmorMaterials.TURTLE, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(25))));
    public static final Item TURTLE_SHOES = registerItem("turtle_shoes", new ArmorItem(GraysModArmorMaterials.TURTLE, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(25))));
    public static final Item TURTLE_TROUSERS = registerItem("turtle_trousers", new ArmorItem(GraysModArmorMaterials.TURTLE, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(25))));
    public static final Item WIND_BOLT = registerItem("wind_bolt", new WindBoltItem(new Item.Settings().rarity(Rarity.UNCOMMON)));

    public static final Item BOULDERING_ZOMBIE_SPAWN_EGG = registerItem("bouldering_zombie_spawn_egg", new SpawnEggItem(GraysModEntityTypes.BOULDERING_ZOMBIE, 0x4d575a, 0x492320, new Item.Settings()));
    public static final Item CLUCKSHROOM_SPAWN_EGG = registerItem("cluckshroom_spawn_egg", new SpawnEggItem(GraysModEntityTypes.CLUCKSHROOM, 0xd31e22, 0xf6c39a, new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(GraysMod.MOD_ID, name), item);
    }

    public static void registerModdedItems() {
        GraysMod.LOGGER.info("Registering GraysMod Items...");
    }
}
