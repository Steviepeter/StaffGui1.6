package fr.stevie.listeners;

import fr.stevie.StaffGui;
import fr.stevie.ui.StaffUI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {

    public PlayerDamageListener(StaffGui plugin){

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event){

        if(Bukkit.getVersion().contains("1.8")){
            if(event.getEntity() instanceof Player p){
                if(StaffUI.god.getOrDefault(p, false)){
                    event.setCancelled(true);
                }
            }
        }
    }
}