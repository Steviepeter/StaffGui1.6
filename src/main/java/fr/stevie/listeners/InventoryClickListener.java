package fr.stevie.listeners;

import fr.stevie.StaffGui;
import fr.stevie.ui.StaffUI;
import fr.stevie.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private final StaffGui staffGUI;
    private final StaffUI staffUI = new StaffUI();

    public InventoryClickListener(StaffGui plugin){
        staffGUI = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        String title = e.getView().getTitle();
        String player = e.getWhoClicked().getName();
        Player p = (Player) e.getWhoClicked();

        try{
            if(title.equals(Message.getMessage("inventory_main")) || title.equals(Message.getMessage("inventory_player").replace("{player}", player)) || title.equals(Message.getMessage("inventory_world")) || title.equals(Message.getMessage("inventory_players")) || title.equals(Message.getMessage("players_color").replace("{player}", staffUI.target_player.get(p).getName())) || title.equals(Message.getMessage("inventory_actions").replace("{player}", staffUI.target_player.get(p).getName())) || title.equals(Message.getMessage("inventory_kick").replace("{player}", staffUI.target_player.get(p).getName())) || title.equals(Message.getMessage("inventory_ban").replace("{player}", staffUI.target_player.get(p).getName())) || title.equals(Message.getMessage("inventory_inventory").replace("{player}", staffUI.target_player.get(p).getName()))) {
                e.setCancelled(true);

                if (title.equals(Message.getMessage("inventory_main"))) {

                    if (e.getCurrentItem() != null) {
                        staffUI.clicked_main(p, e.getSlot(), e.getCurrentItem(), e.getInventory());
                    }

                } else if (title.equals(Message.getMessage("inventory_player").replace("{player}", player))) {

                    if (e.getCurrentItem() != null) {
                        staffUI.clicked_player(p, e.getSlot(), e.getCurrentItem(), e.getInventory());
                    }

                } else if (title.equals(Message.getMessage("inventory_world"))) {

                    if (e.getCurrentItem() != null) {
                        staffUI.clicked_world(p, e.getSlot(), e.getCurrentItem(), e.getInventory());
                    }

                } else if (title.equals(Message.getMessage("inventory_players"))) {

                    if (e.getCurrentItem() != null) {
                        staffUI.clicked_players(p, e.getSlot(), e.getCurrentItem(), e.getInventory());
                    }

                } else if (title.equals(Message.getMessage("players_color").replace("{player}", staffUI.target_player.get(p).getName()))) {

                    if (e.getCurrentItem() != null) {
                        staffUI.clicked_players_settings(p, e.getSlot(), e.getCurrentItem(), e.getInventory(), staffUI.target_player.get(p));
                    }

                } else if (title.equals(Message.getMessage("inventory_actions").replace("{player}", staffUI.target_player.get(p).getName()))) {

                    if (e.getCurrentItem() != null) {
                        staffUI.clicked_actions(p, e.getSlot(), e.getCurrentItem(), e.getInventory(), staffUI.target_player.get(p));
                    }

                } else if (title.equals(Message.getMessage("inventory_kick").replace("{player}", staffUI.target_player.get(p).getName()))) {

                    if (e.getCurrentItem() != null) {
                        staffUI.clicked_kick(p, e.getSlot(), e.getCurrentItem(), e.getInventory(), staffUI.target_player.get(p));
                    }
                } else if (title.equals(Message.getMessage("inventory_ban").replace("{player}", staffUI.target_player.get(p).getName()))) {

                    if (e.getCurrentItem() != null) {
                        staffUI.clicked_ban(p, e.getSlot(), e.getCurrentItem(), e.getInventory(), staffUI.target_player.get(p));
                    }

                } else if (title.equals(Message.getMessage("inventory_inventory").replace("{player}", staffUI.target_player.get(p).getName()))){

                    if(e.getCurrentItem() != null){
                        if(e.isLeftClick()){
                            staffUI.clicked_inventory(p, e.getSlot(), e.getCurrentItem(), e.getInventory(), staffUI.target_player.get(p), true);
                        }else{
                            staffUI.clicked_inventory(p, e.getSlot(), e.getCurrentItem(), e.getInventory(), staffUI.target_player.get(p), false);
                        }
                    }
                }
            }
        }catch (Exception ignored){}
    }
}