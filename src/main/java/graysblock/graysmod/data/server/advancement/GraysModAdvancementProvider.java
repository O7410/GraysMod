package graysblock.graysmod.data.server.advancement;

import graysblock.graysmod.GraysMod;
import graysblock.graysmod.item.GraysModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.ImpossibleCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class GraysModAdvancementProvider extends FabricAdvancementProvider {

    public GraysModAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> exporter) {


        AdvancementEntry rootAdvancement = Advancement.Builder.create()
                .display(
                        GraysModItems.HOT_POCKET,
                        Text.translatable("advancements.graysmod.root.title"),
                        Text.translatable("advancements.graysmod.root.description"),
                        Identifier.ofVanilla("textures/block/bricks.png"),
                        AdvancementFrame.TASK,
                        false,
                        false,
                        false
                )
                .criterion("hot_pocket", InventoryChangedCriterion.Conditions.items(GraysModItems.HOT_POCKET))
                .build(exporter, Identifier.of(GraysMod.MOD_ID, "graysmod/root").toString());

        AdvancementEntry pureAbsurdityAdvancement = Advancement.Builder.create()
                .parent(rootAdvancement)
                .display(
                        Items.ENCHANTED_GOLDEN_APPLE,
                        Text.translatable("advancements.graysmod.pure_absurdity.title"),
                        Text.translatable("advancements.graysmod.pure_absurdity.description"),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        true
                )
                .rewards(AdvancementRewards.Builder.experience(100))
                .criterion(
                        "impossible",
                        Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions())
                )
                .build(exporter, Identifier.of(GraysMod.MOD_ID, "graysmod/pure_absurdity").toString());

        AdvancementEntry icarusAdvancement = Advancement.Builder.create()
                .parent(rootAdvancement)
                .display(
                        GraysModItems.MAKESHIFT_WINGS,
                        Text.translatable("advancements.graysmod.icarus.title"),
                        Text.translatable("advancements.graysmod.icarus.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion(
                        "impossible",
                        Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions())
                )
                .build(exporter, Identifier.of(GraysMod.MOD_ID, "graysmod/icarus").toString());

        AdvancementEntry soScuteAdvancement = Advancement.Builder.create()
                .parent(icarusAdvancement)
                .display(
                        GraysModItems.TURTLE_SHELL,
                        Text.translatable("advancements.graysmod.so_scute.title"),
                        Text.translatable("advancements.graysmod.so_scute.description"),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        false
                )
                .rewards(AdvancementRewards.Builder.experience(45))
                .criterion(
                        "turtle_armor",
                        InventoryChangedCriterion.Conditions.items(Items.TURTLE_HELMET, GraysModItems.TURTLE_SHELL, GraysModItems.TURTLE_TROUSERS, GraysModItems.TURTLE_SHOES)
                )
                .build(exporter, Identifier.of(GraysMod.MOD_ID, "graysmod/so_scute").toString());

        AdvancementEntry hotPocketsAdvancement = Advancement.Builder.create()
                .parent(rootAdvancement)
                .display(
                        GraysModItems.HOT_POCKET,
                        Text.translatable("advancements.graysmod.hot_pockets.title"),
                        Text.translatable("advancements.graysmod.hot_pockets.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion(
                        "hot_pocket",
                        ConsumeItemCriterion.Conditions.item(GraysModItems.HOT_POCKET)
                )
                .build(exporter, Identifier.of(GraysMod.MOD_ID, "graysmod/hot_pockets").toString());

        AdvancementEntry irresistiblyHotAdvancement = Advancement.Builder.create()
                .parent(hotPocketsAdvancement)
                .display(
                        GraysModItems.HOT_POCKET,
                        Text.translatable("advancements.graysmod.irresistibly_hot.title"),
                        Text.translatable("advancements.graysmod.irresistibly_hot.description"),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        true
                )
                .rewards(AdvancementRewards.Builder.experience(100))
                .criterion(
                        "impossible",
                        Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions())
                )
                .build(exporter, Identifier.of(GraysMod.MOD_ID, "graysmod/irresistibly_hot").toString());
    }
}
