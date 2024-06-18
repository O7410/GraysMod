package graysblock.graysmod.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class GuildedItem extends Item {

    public GuildedItem(Settings settings) {
        super(settings);
    }

    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey(stack)).withColor(0xDEB12D);
    }

    public Text getName() {
        return Text.translatable(this.getTranslationKey()).withColor(0xDEB12D);
    }
}
