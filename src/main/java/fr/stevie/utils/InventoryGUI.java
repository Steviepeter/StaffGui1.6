package fr.stevie.utils;

import org.bukkit.inventory.ItemStack;

public class InventoryGUI {
    @SuppressWarnings("deprecation")

    public static boolean getClickedItem(ItemStack clicked, String message){
        return clicked.getItemMeta().getDisplayName().equalsIgnoreCase(message);
    }

}