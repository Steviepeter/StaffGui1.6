package fr.stevie.utils;


import fr.stevie.StaffGui;
import org.bukkit.ChatColor;

public class Message {

    public static String chat(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getMessage(String config){
        if(StaffGui.getInstance().getLang().getString(config) != null){
            return chat(StaffGui.getInstance().getLang().getString(config));
        }else{
            return chat("&cValue: " + config + " is missing in language.yml file! Please add it or delete language.yml file.");
        }
    }
}