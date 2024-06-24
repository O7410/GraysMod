package graysblock.graysmod.data.server.advancement;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class CustomAdvancementGranters {

    public static void grantAdvancementFromId(ServerPlayerEntity player, Identifier id) {
        AdvancementEntry advancement = player.server.getAdvancementLoader().get(id);
        if (advancement != null) {
            AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancement);
            if (!progress.isDone()) {
                for (String criteria : progress.getUnobtainedCriteria()) {
                    player.getAdvancementTracker().grantCriterion(advancement, criteria);
                }
            }
        }
    }
}
