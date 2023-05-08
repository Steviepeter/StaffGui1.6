package fr.stevie.utils;


import fr.stevie.StaffGui;
import org.bukkit.ChatColor;

public class Message {

    @SuppressWarnings("deprecation")

    public static String chat(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getMessage(String config){
        if(StaffGui.getInstance().getLang().getString(config) != null){
            return chat(StaffGui.getInstance().getLang().getString(config));
        }else{
            return chat(Message.getMessage("prefix") + "&cIl manque la valeur" + config + " dans le fichier language.yml.");
        }
    }
}