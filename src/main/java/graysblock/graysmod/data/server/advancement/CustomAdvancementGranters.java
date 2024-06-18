package graysblock.graysmod.data.server.advancement;

import graysblock.graysmod.GraysMod;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class CustomAdvancementGranters {

    public static void grantPureAbsurdityAdvancement(ServerPlayerEntity player) {
        AdvancementEntry advancement = player.server.getAdvancementLoader().get(Identifier.of(GraysMod.MOD_ID, "graysmod/pure_absurdity"));
        if(advancement != null) {
            AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancement);
            if(!progress.isDone()) {
                for(String criteria : progress.getUnobtainedCriteria()) {
                    player.getAdvancementTracker().grantCriterion(advancement, criteria);
                }
            }
        }
    }

    public static void grantIcarusAdvancement(ServerPlayerEntity player) {
        AdvancementEntry advancement = player.server.getAdvancementLoader().get(Identifier.of(GraysMod.MOD_ID, "graysmod/icarus"));
        if(advancement != null) {
            AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancement);
            if(!progress.isDone()) {
                for(String criteria : progress.getUnobtainedCriteria()) {
                    player.getAdvancementTracker().grantCriterion(advancement, criteria);
                }
            }
        }
    }

    public static void grantIrresistiblyHotAdvancement(ServerPlayerEntity player) {
        AdvancementEntry advancement = player.server.getAdvancementLoader().get(Identifier.of(GraysMod.MOD_ID, "graysmod/irresistibly_hot"));
        if(advancement != null) {
            AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancement);
            if(!progress.isDone()) {
                for(String criteria : progress.getUnobtainedCriteria()) {
                    player.getAdvancementTracker().grantCriterion(advancement, criteria);
                }
            }
        }
    }
}
