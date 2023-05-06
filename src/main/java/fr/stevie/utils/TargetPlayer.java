package fr.stevie.utils;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class TargetPlayer {

    public static void ban(String target, String reason, Date expired){
        Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(target, reason, expired, null);
    }

    public static String banReason(String reason, Date time){
        String bumper = org.apache.commons.lang3.StringUtils.repeat("\n", 35);

        return bumper + Message.getMessage("ban") + Message.getMessage(reason) + "\n" + Message.getMessage("ban_time").replace("{years}", ""+(time.getYear()+1900)).replace("{months}", ""+(time.getMonth()+1)).replace("{days}", ""+time.getDate()).replace("{hours}", ""+time.getHours()).replace("{minutes}", ""+time.getMinutes()).replace("{seconds}", ""+time.getSeconds()) + bumper;
    }

}