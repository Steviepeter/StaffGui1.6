package fr.stevie.utils;

import org.bukkit.BanList;
import org.bukkit.Bukkit;

import java.util.Date;

public class TargetPlayer {

    @SuppressWarnings("Deprecated")

    public static void ban(String target, String reason, Date expired){
        Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(target, reason, expired, null);
    }

    public static String banReason(String reason, Date time){
        String bumper = org.apache.commons.lang3.StringUtils.repeat("\n", 35);

        return bumper + Message.getMessage("ban") + Message.getMessage(reason) + "\n" + Message.getMessage("ban_time").replace("{years}", String.valueOf(time.getYear() + 1900)).replace("{months}", String.valueOf(time.getMonth() + 1)).replace("{days}", String.valueOf(time.getDate())).replace("{hours}", String.valueOf(time.getHours())).replace("{minutes}", String.valueOf(time.getMinutes())).replace("{seconds}", String.valueOf(time.getSeconds())) + bumper;
    }

}