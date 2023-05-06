package fr.stevie.ui;


import fr.stevie.StaffGui;
import fr.stevie.utils.*;
//import de.myzelyam.api.vanish.VanishAPI;
//import de.myzelyam.api.vanish.PlayerHideEvent;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;

public class StaffUI {


    public static HashMap<Player, Player> target_player = new HashMap<>();

    //Ban
    private final HashMap<Player, Integer> ban_years = new HashMap<>();
    private final HashMap<Player, Integer> ban_months = new HashMap<>();
    private final HashMap<Player, Integer> ban_days = new HashMap<>();
    private final HashMap<Player, Integer> ban_hours = new HashMap<>();
    private final HashMap<Player, Integer> ban_minutes = new HashMap<>();

    //Page
    private final HashMap<Player, Integer> page = new HashMap<>();
    private final HashMap<Player, Integer> pages = new HashMap<>();

    //God
    public static HashMap<Player, Boolean> god = new HashMap<>();

    public Inventory GUI_Main(Player p) {

        Inventory inv_main = Bukkit.createInventory(null, 27, Message.getMessage("inventory_main"));

        Player random_player = Bukkit.getOnlinePlayers().stream().findAny().get();

        for (int i = 1; i < 27; i++) {
            Item.create(inv_main, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, " ");
        }

        Item.createPlayerHead(inv_main, p.getName(), 1, 12, Message.getMessage("main_player").replace("{player}", p.getName()));
        Item.create(inv_main, "GRASS_BLOCK", 1, 14, Message.getMessage("main_world"));
        Item.createPlayerHead(inv_main, random_player.getName(), 1, 16, Message.getMessage("main_players"));
        Item.create(inv_main, "REDSTONE_BLOCK", 1, 27, Message.getMessage("main_quit"));

        return inv_main;
    }

    public Inventory GUI_Player(Player p) {

        String inventory_player_name = Message.getMessage("inventory_player").replace("{player}", p.getName());

        Inventory inv_player = Bukkit.createInventory(null, 45, inventory_player_name);

        for (int i = 1; i < 45; i++) {
            Item.create(inv_player, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, " ");
        }

        if (p.hasPermission("admingui.info")) {
            Item.createPlayerHead(inv_player, p.getName(), 1, 5, Message.getMessage("player_info").replace("{player}", p.getName()), Message.chat("&eHeal: " + Math.round(p.getHealth())), Message.chat("&7Feed: " + Math.round(p.getFoodLevel())), Message.chat("&aGamemode: " + p.getGameMode().toString()), Message.chat("&5IP: " + p.getAddress().getAddress()));
        } else {
            Item.createPlayerHead(inv_player, p.getName(), 1, 5, Message.getMessage("player_info").replace("{player}", p.getName()));
        }

        if (p.hasPermission("admingui.heal")) {
            Item.create(inv_player, "GOLDEN_APPLE", 1, 11, Message.getMessage("player_heal"));
        } else {
            Item.create(inv_player, "RED_STAINED_GLASS_PANE", 1, 11, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.feed")) {
            Item.create(inv_player, "COOKED_BEEF", 1, 13, Message.getMessage("player_feed"));
        } else {
            Item.create(inv_player, "RED_STAINED_GLASS_PANE", 1, 13, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.gamemode")) {
            if (p.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                Item.create(inv_player, "DIRT", 1, 15, Message.getMessage("player_survival"));
            } else if (p.getPlayer().getGameMode() == GameMode.ADVENTURE) {
                Item.create(inv_player, "GRASS_BLOCK", 1, 15, Message.getMessage("player_adventure"));
            } else if (p.getPlayer().getGameMode() == GameMode.CREATIVE) {
                Item.create(inv_player, "BRICKS", 1, 15, Message.getMessage("player_creative"));
            } else if (p.getPlayer().getGameMode() == GameMode.SPECTATOR) {
                if (Bukkit.getVersion().contains("1.8")) {
                    Item.create(inv_player, "POTION", 1, 15, Message.getMessage("player_spectator"));
                } else {
                    Item.create(inv_player, "SPLASH_POTION", 1, 15, Message.getMessage("player_spectator"));
                }
            }
        } else {
            Item.create(inv_player, "RED_STAINED_GLASS_PANE", 1, 15, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.god")) {
            if (!Bukkit.getVersion().contains("1.8")) {
                if (p.isInvulnerable()) {
                    Item.create(inv_player, "RED_TERRACOTTA", 1, 17, Message.getMessage("player_god_disabled"));
                } else {
                    Item.create(inv_player, "LIME_TERRACOTTA", 1, 17, Message.getMessage("player_god_enabled"));
                }
            } else {
                if (god.getOrDefault(p, false)) {
                    Item.create(inv_player, "RED_TERRACOTTA", 1, 17, Message.getMessage("player_god_disabled"));
                } else {
                    Item.create(inv_player, "LIME_TERRACOTTA", 1, 17, Message.getMessage("player_god_enabled"));
                }
            }
        } else {
            Item.create(inv_player, "RED_STAINED_GLASS_PANE", 1, 17, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.potions")) {
            Item.create(inv_player, "POTION", 1, 19, Message.getMessage("player_potions"));
        } else {
            Item.create(inv_player, "RED_STAINED_GLASS_PANE", 1, 19, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.kill")) {
            Item.create(inv_player, "DIAMOND_SWORD", 1, 23, Message.getMessage("player_kill"));
        } else {
            Item.create(inv_player, "RED_STAINED_GLASS_PANE", 1, 23, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.burn")) {
            Item.create(inv_player, "FLINT_AND_STEEL", 1, 25, Message.getMessage("player_burn"));
        } else {
            Item.create(inv_player, "RED_STAINED_GLASS_PANE", 1, 25, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.lightning")) {
            if (Bukkit.getVersion().contains("1.8") || Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12")) {
                Item.create(inv_player, "STICK", 1, 27, Message.getMessage("player_lightning"));
            } else {
                Item.create(inv_player, "TRIDENT", 1, 27, Message.getMessage("player_lightning"));
            }
        } else {
            Item.create(inv_player, "RED_STAINED_GLASS_PANE", 1, 27, Message.getMessage("permission"));
        }

        //if (p.hasPermission("admingui.vanish")) {
        //    if (Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish")) {
        //        if (VanishAPI.isInvisible(p)) {
        //            Item.create(inv_player, "FEATHER", 1, 33, Message.getMessage("player_vanish_disabled"));
        //        } else {
        //            Item.create(inv_player, "FEATHER", 1, 33, Message.getMessage("player_vanish_enabled"));
        //        }
        //    } else {
        //        Item.create(inv_player, "FEATHER", 1, 33, Message.getMessage("player_vanish_enabled"));
        //    }
        //} else {
        //    Item.create(inv_player, "RED_STAINED_GLASS_PANE", 1, 33, Message.getMessage("permission"));
        //}

        Item.create(inv_player, "REDSTONE_BLOCK", 1, 45, Message.getMessage("player_back"));

        return inv_player;
    }

    private Inventory GUI_World(Player p) {

        Inventory inv_world = Bukkit.createInventory(null, 27, Message.getMessage("inventory_world"));

        for (int i = 1; i < 27; i++) {
            Item.create(inv_world, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, " ");
        }

        if (p.hasPermission("admingui.time")) {
            if (p.getPlayer().getWorld().getTime() < 13000) {
                Item.create(inv_world, "GOLD_BLOCK", 1, 11, Message.getMessage("world_day"));
            } else {
                Item.create(inv_world, "COAL_BLOCK", 1, 11, Message.getMessage("world_night"));
            }
        } else {
            Item.create(inv_world, "RED_STAINED_GLASS_PANE", 1, 11, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.weather")) {
            if (p.getPlayer().getWorld().isThundering()) {
                Item.create(inv_world, " BLUE_TERRACOTTA", 1, 13, Message.getMessage("world_thunder"));
            } else if (p.getPlayer().getWorld().hasStorm()) {
                Item.create(inv_world, "CYAN_TERRACOTTA", 1, 13, Message.getMessage("world_rain"));
            } else {
                Item.create(inv_world, "LIGHT_BLUE_TERRACOTTA", 1, 13, Message.getMessage("world_clear"));
            }
        } else {
            Item.create(inv_world, "RED_STAINED_GLASS_PANE", 1, 13, Message.getMessage("permission"));
        }

        Item.create(inv_world, "REDSTONE_BLOCK", 1, 27, Message.getMessage("world_back"));

        return inv_world;
    }

    private Inventory GUI_Players(Player p) {

        ArrayList<String> pl = new ArrayList<String>();

        Inventory inv_players = Bukkit.createInventory(null, 54, Message.getMessage("inventory_players"));

        for (Player all : Bukkit.getServer().getOnlinePlayers()) {
            pl.add(all.getName());
        }

        pl.remove(p.getName());

        Collections.sort(pl);

        int online = pl.size();

        pages.put(p, (int) Math.ceil((float) online / 45));

        for (int i = 46; i <= 53; i++) {
            Item.create(inv_players, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, " ");
        }

        int player_slot = (page.getOrDefault(p, 1) - 1) * 45;

        for (int i = 0; i < 45; i++) {
            if (player_slot < online) {
                Item.createPlayerHead(inv_players, pl.get(player_slot), 1, i + 1, Message.getMessage("players_color").replace("{player}", pl.get(player_slot)), Message.getMessage("players_lore"));
                player_slot++;
            } else {
                Item.create(inv_players, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i + 1, " ");
            }
        }

        if (page.getOrDefault(p, 1) > 1) {
            Item.create(inv_players, "PAPER", 1, 49, Message.getMessage("players_previous"));
        }

        if (pages.getOrDefault(p, 1) > 1) {
            Item.create(inv_players, "BOOK", page.getOrDefault(p, 1), 50, Message.getMessage("players_page") + " " + page.getOrDefault(p, 1));
        }

        if (pages.get(p) > page.getOrDefault(p, 1)) {
            Item.create(inv_players, "PAPER", 1, 51, Message.getMessage("players_next"));
        }

        Item.create(inv_players, "REDSTONE_BLOCK", 1, 54, Message.getMessage("players_back"));

        return inv_players;
    }

    public Inventory GUI_Players_Settings(Player p, Player target_player) {

        String inventory_players_settings_name = Message.getMessage("players_color").replace("{player}", target_player.getName());
        Inventory inv_players_settings = Bukkit.createInventory(null, 27, inventory_players_settings_name);

        for (int i = 1; i < 27; i++) {
            Item.create(inv_players_settings, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, " ");
        }

        if (p.hasPermission("admingui.info")) {
            Item.createPlayerHead(inv_players_settings, target_player.getName(), 1, 5, Message.getMessage("players_settings_info").replace("{player}", target_player.getName()), Message.chat("&eHeal: " + Math.round(target_player.getHealth())), Message.chat("&7Feed: " + Math.round(target_player.getFoodLevel())), Message.chat("&aGamemode: " + target_player.getGameMode().toString()), Message.chat("&5IP: " + target_player.getAddress().getAddress()));
        } else {
            Item.createPlayerHead(inv_players_settings, target_player.getName(), 1, 5, Message.getMessage("players_settings_info").replace("{player}", target_player.getName()));
        }

        Item.create(inv_players_settings, "DIAMOND_SWORD", 1, 11, Message.getMessage("players_settings_actions"));

        if (p.hasPermission("admingui.kick.other")) {
            Item.create(inv_players_settings, "BLACK_TERRACOTTA", 1, 15, Message.getMessage("players_settings_kick_player"));
        } else {
            Item.create(inv_players_settings, "RED_STAINED_GLASS_PANE", 1, 15, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.ban")) {
            Item.create(inv_players_settings, "BEDROCK", 1, 17, Message.getMessage("players_settings_ban_player"));
        } else {
            Item.create(inv_players_settings, "RED_STAINED_GLASS_PANE", 1, 17, Message.getMessage("permission"));
        }

        Item.create(inv_players_settings, "REDSTONE_BLOCK", 1, 27, Message.getMessage("players_settings_back"));

        return inv_players_settings;
    }

    public Inventory GUI_Actions(Player p, Player target) {

        String inventory_actions_name = Message.getMessage("inventory_actions").replace("{player}", target.getName());
        target_player.put(p, target);

        Inventory inv_actions = Bukkit.createInventory(null, 54, inventory_actions_name);

        for (int i = 1; i < 54; i++) {
            Item.create(inv_actions, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, " ");
        }

        if (p.hasPermission("admingui.info")) {
            Item.createPlayerHead(inv_actions, target.getName(), 1, 5, Message.getMessage("players_settings_info").replace("{player}", target.getName()), Message.chat("&eHeal: " + Math.round(target.getHealth())), Message.chat("&7Feed: " + Math.round(target.getFoodLevel())), Message.chat("&aGamemode: " + target.getGameMode().toString()), Message.chat("&5IP: " + target.getAddress().getAddress()));
        } else {
            Item.createPlayerHead(inv_actions, target.getName(), 1, 5, Message.getMessage("actions_info").replace("{player}", target.getName()));
        }

        if (p.hasPermission("admingui.heal.other")) {
            Item.create(inv_actions, "GOLDEN_APPLE", 1, 11, Message.getMessage("actions_heal"));
        } else {
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 11, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.feed.other")) {
            Item.create(inv_actions, "COOKED_BEEF", 1, 13, Message.getMessage("actions_feed"));
        } else {
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 13, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.gamemode.other")) {
            if (target.getGameMode() == GameMode.SURVIVAL) {
                Item.create(inv_actions, "DIRT", 1, 15, Message.getMessage("actions_survival"));
            } else if (target.getGameMode() == GameMode.ADVENTURE) {
                Item.create(inv_actions, "GRASS_BLOCK", 1, 15, Message.getMessage("actions_adventure"));
            } else if (target.getGameMode() == GameMode.CREATIVE) {
                Item.create(inv_actions, "BRICKS", 1, 15, Message.getMessage("actions_creative"));
            } else if (target.getGameMode() == GameMode.SPECTATOR) {
                if (Bukkit.getVersion().contains("1.8")) {
                    Item.create(inv_actions, "POTION", 1, 15, Message.getMessage("actions_spectator"));
                } else {
                    Item.create(inv_actions, "SPLASH_POTION", 1, 15, Message.getMessage("actions_spectator"));
                }
            }
        } else {
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 15, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.god.other")) {
            if (!Bukkit.getVersion().contains("1.8")) {
                if (target.isInvulnerable()) {
                    Item.create(inv_actions, "RED_TERRACOTTA", 1, 17, Message.getMessage("actions_god_disabled"));
                } else {
                    Item.create(inv_actions, "LIME_TERRACOTTA", 1, 17, Message.getMessage("actions_god_enabled"));
                }
            } else {
                if (god.getOrDefault(target, false)) {
                    Item.create(inv_actions, "RED_TERRACOTTA", 1, 17, Message.getMessage("actions_god_disabled"));
                } else {
                    Item.create(inv_actions, "LIME_TERRACOTTA", 1, 17, Message.getMessage("actions_god_enabled"));
                }
            }
        } else {
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 17, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.teleport")) {
            Item.create(inv_actions, "ENDER_PEARL", 1, 19, Message.getMessage("actions_teleport_to_player"));
        } else {
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 19, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.potions.other")) {
            Item.create(inv_actions, "POTION", 1, 21, Message.getMessage("actions_potions"));
        } else {
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 21, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.kill.other")) {
            Item.create(inv_actions, "DIAMOND_SWORD", 1, 23, Message.getMessage("actions_kill_player"));
        } else {
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 23, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.spawner.other")) {
            Item.create(inv_actions, "SPAWNER", 1, 25, Message.getMessage("actions_spawner"));
        } else {
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 25, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.teleport.other")) {
            if (Bukkit.getVersion().contains("1.8")) {
                Item.create(inv_actions, "ENDER_PEARL", 1, 27, Message.getMessage("actions_teleport_player_to_you"));
            } else {
                Item.create(inv_actions, "END_CRYSTAL", 1, 27, Message.getMessage("actions_teleport_player_to_you"));
            }
        } else {
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 27, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.inventory")) {
            Item.create(inv_actions, "BOOK", 1, 29, Message.getMessage("actions_inventory"));
        } else {
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 29, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.burn.other")) {
            Item.create(inv_actions, "FLINT_AND_STEEL", 1, 31, Message.getMessage("actions_burn_player"));
        } else {
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 31, Message.getMessage("permission"));
        }

//        if (p.hasPermission("admingui.vanish.other")) {
//            if (Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish")) {
//                if (VanishAPI.isInvisible(target)) {
//                    Item.create(inv_actions, "FEATHER", 1, 33, Message.getMessage("actions_vanish_disabled"));
//                } else {
//                    Item.create(inv_actions, "FEATHER", 1, 33, Message.getMessage("actions_vanish_enabled"));
//                }
//            } else {
//                Item.create(inv_actions, "FEATHER", 1, 33, Message.getMessage("actions_vanish_enabled"));
//            }
//        } else {
//            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 33, Message.getMessage("permission"));
//        }

        if (p.hasPermission("admingui.lightning.other")) {
            if (Bukkit.getVersion().contains("1.8") || Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12")) {
                Item.create(inv_actions, "STICK", 1, 35, Message.getMessage("actions_lightning"));
            } else {
                Item.create(inv_actions, "TRIDENT", 1, 35, Message.getMessage("actions_lightning"));
            }
        } else {
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 35, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.firework.other")) {
            Item.create(inv_actions, "FIREWORK_ROCKET", 1, 37, Message.getMessage("actions_firework"));
        } else {
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 37, Message.getMessage("permission"));
        }

        if (p.hasPermission("admingui.fakeop")) {
            Item.create(inv_actions, "PAPER", 1, 39, Message.getMessage("actions_fakeop"));
        } else {
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 39, Message.getMessage("permission"));
        }

        Item.create(inv_actions, "REDSTONE_BLOCK", 1, 54, Message.getMessage("actions_back"));

        return inv_actions;
    }

    public Inventory GUI_Kick(Player p, Player target) {

        String inventory_kick_name = Message.getMessage("inventory_kick").replace("{player}", target.getName());
        target_player.put(p, target);

        Inventory inv_kick = Bukkit.createInventory(null, 27, inventory_kick_name);

        for (int i = 1; i < 27; i++) {
            Item.create(inv_kick, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, " ");
        }

        Item.create(inv_kick, "WHITE_TERRACOTTA", 1, 10, Message.getMessage("kick_hacking"));
        Item.create(inv_kick, "ORANGE_TERRACOTTA", 1, 12, Message.getMessage("kick_griefing"));
        Item.create(inv_kick, "MAGENTA_TERRACOTTA", 1, 14, Message.getMessage("kick_spamming"));
        Item.create(inv_kick, "LIGHT_BLUE_TERRACOTTA", 1, 16, Message.getMessage("kick_advertising"));
        Item.create(inv_kick, "YELLOW_TERRACOTTA", 1, 18, Message.getMessage("kick_swearing"));

        Item.create(inv_kick, "REDSTONE_BLOCK", 1, 27, Message.getMessage("kick_back"));

        return inv_kick;
    }

    public Inventory GUI_Ban(Player p, Player target) {

        String inventory_ban_name = Message.getMessage("inventory_ban").replace("{player}", target.getName());
        target_player.put(p, target);

        Inventory inv_ban = Bukkit.createInventory(null, 36, inventory_ban_name);

        for (int i = 1; i < 36; i++) {
            Item.create(inv_ban, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, " ");
        }

        if (ban_years.getOrDefault(p, 0) == 0) {
            Item.create(inv_ban, "RED_STAINED_GLASS_PANE", 1, 12, Message.getMessage("ban_years"));
        } else {
            Item.create(inv_ban, "CLOCK", ban_years.getOrDefault(p, 0), 12, Message.getMessage("ban_years"));
        }

        if (ban_months.getOrDefault(p, 0) == 0) {
            Item.create(inv_ban, "RED_STAINED_GLASS_PANE", 1, 13, Message.getMessage("ban_months"));
        } else {
            Item.create(inv_ban, "CLOCK", ban_months.getOrDefault(p, 0), 13, Message.getMessage("ban_months"));
        }

        if (ban_days.getOrDefault(p, 0) == 0) {
            Item.create(inv_ban, "RED_STAINED_GLASS_PANE", 1, 14, Message.getMessage("ban_days"));
        } else {
            Item.create(inv_ban, "CLOCK", ban_days.getOrDefault(p, 0), 14, Message.getMessage("ban_days"));
        }

        if (ban_hours.getOrDefault(p, 0) == 0) {
            Item.create(inv_ban, "RED_STAINED_GLASS_PANE", 1, 15, Message.getMessage("ban_hours"));
        } else {
            Item.create(inv_ban, "CLOCK", ban_hours.getOrDefault(p, 0), 15, Message.getMessage("ban_hours"));
        }

        if (ban_minutes.getOrDefault(p, 0) == 0) {
            Item.create(inv_ban, "RED_STAINED_GLASS_PANE", 1, 16, Message.getMessage("ban_minutes"));
        } else {
            Item.create(inv_ban, "CLOCK", ban_minutes.getOrDefault(p, 0), 16, Message.getMessage("ban_minutes"));
        }

        Item.create(inv_ban, "WHITE_TERRACOTTA", 1, 30, Message.getMessage("ban_hacking"));
        Item.create(inv_ban, "ORANGE_TERRACOTTA", 1, 31, Message.getMessage("ban_griefing"));
        Item.create(inv_ban, "MAGENTA_TERRACOTTA", 1, 32, Message.getMessage("ban_spamming"));
        Item.create(inv_ban, "LIGHT_BLUE_TERRACOTTA", 1, 33, Message.getMessage("ban_advertising"));
        Item.create(inv_ban, "YELLOW_TERRACOTTA", 1, 34, Message.getMessage("ban_swearing"));

        Item.create(inv_ban, "REDSTONE_BLOCK", 1, 36, Message.getMessage("ban_back"));

        return inv_ban;
    }

    public Inventory GUI_Inventory(Player p, Player target) {

        String inventory_inventory_name = Message.getMessage("inventory_inventory").replace("{player}", target.getName());
        target_player.put(p, target);

        Inventory inv_inventory = Bukkit.createInventory(null, 54, inventory_inventory_name);

        if (target.isOnline()) {

            ItemStack[] items = target.getInventory().getContents();
            ItemStack[] armor = target.getInventory().getArmorContents();

            for (int i = 0; i < items.length; i++) {
                if (items[i] != null) {
                    inv_inventory.setItem(i, items[i]);
                } else {
                    inv_inventory.setItem(i, null);
                }
            }

            for (int i = 0, j = 36; i < armor.length; i++, j++) {
                if (armor[i] != null) {
                    inv_inventory.setItem(j, armor[i]);
                } else {
                    inv_inventory.setItem(j, null);
                }
            }
        } else {
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_not_found"));
            p.closeInventory();
        }

        for (int i = 42; i < 54; i++) {
            Item.create(inv_inventory, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, " ");
        }

        Item.create(inv_inventory, "GREEN_TERRACOTTA", 1, 46, Message.getMessage("inventory_refresh"));

        Item.create(inv_inventory, "BLUE_TERRACOTTA", 1, 50, Message.getMessage("inventory_clear"));

        Item.create(inv_inventory, "REDSTONE_BLOCK", 1, 54, Message.getMessage("inventory_back"));

        return inv_inventory;
    }

    public void clicked_main(Player p, int slot, ItemStack clicked, Inventory inv) {

        if (InventoryGUI.getClickedItem(clicked, Message.getMessage("main_quit"))) {
            p.closeInventory();
        } else if (InventoryGUI.getClickedItem(clicked, Message.getMessage("main_player").replace("{player}", p.getName()))) {
            p.openInventory(GUI_Player(p));
        } else if (InventoryGUI.getClickedItem(clicked, Message.getMessage("main_world"))) {
            p.openInventory(GUI_World(p));
        } else if (InventoryGUI.getClickedItem(clicked, Message.getMessage("main_players"))) {
            p.openInventory(GUI_Players(p));
        } else {
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("permission"));
            p.closeInventory();
        }
    }



    public void clicked_player(Player p, int slot, ItemStack clicked, Inventory inv) {

        if (InventoryGUI.getClickedItem(clicked, Message.getMessage("player_back"))) {
            p.openInventory(GUI_Main(p));
        } else if (InventoryGUI.getClickedItem(clicked, Message.getMessage("player_info").replace("{player}", p.getName()))) {
            p.openInventory(GUI_Player(p));
        } else if (InventoryGUI.getClickedItem(clicked, Message.getMessage("player_heal"))) {
            p.setHealth(20);
            p.setFireTicks(0);
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_heal"));
            p.closeInventory();
        } else if (InventoryGUI.getClickedItem(clicked, Message.getMessage("player_feed"))) {
            p.setFoodLevel(20);
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_feed"));
            p.closeInventory();
        } else if (InventoryGUI.getClickedItem(clicked, Message.getMessage("player_survival"))) {
            p.setGameMode(GameMode.ADVENTURE);
            p.openInventory(GUI_Player(p));
        } else if (InventoryGUI.getClickedItem(clicked, Message.getMessage("player_adventure"))) {
            p.setGameMode(GameMode.CREATIVE);
            p.openInventory(GUI_Player(p));
        } else if (InventoryGUI.getClickedItem(clicked, Message.getMessage("player_creative"))) {
            p.setGameMode(GameMode.SPECTATOR);
            p.openInventory(GUI_Player(p));
        } else if (InventoryGUI.getClickedItem(clicked, Message.getMessage("player_spectator"))) {
            p.setGameMode(GameMode.SURVIVAL);
            p.openInventory(GUI_Player(p));
        } else if (InventoryGUI.getClickedItem(clicked, Message.getMessage("player_god_enabled"))) {
            if (Bukkit.getVersion().contains("1.8")) {
                god.put(p, true);
            } else {
                p.setInvulnerable(true);
            }
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_god_enabled"));
            p.openInventory(GUI_Player(p));
        } else if (InventoryGUI.getClickedItem(clicked, Message.getMessage("player_god_disabled"))) {
            if (Bukkit.getVersion().contains("1.8")) {
                god.put(p, false);
            } else {
                p.setInvulnerable(false);
            }
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_god_disabled"));
            p.openInventory(GUI_Player(p));
        } else if (InventoryGUI.getClickedItem(clicked, Message.getMessage("player_kill"))) {
            p.setHealth(0);
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_kill"));
        } else if (InventoryGUI.getClickedItem(clicked, Message.getMessage("player_burn"))) {
            p.setFireTicks(500);
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_burn"));
        } else if (InventoryGUI.getClickedItem(clicked, Message.getMessage("player_lightning"))) {
            p.getWorld().strikeLightning(p.getLocation());
//        }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("player_vanish_enabled"))){
//            if (Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish")) {
//                VanishAPI.hidePlayer(p);
//                p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_hide"));
//            }else{
//                p.sendMessage(Message.getMessage("prefix") + Message.getMessage("vanish_required"));
//            }
//            p.closeInventory();
//        }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("player_vanish_disabled"))){
//            if (Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish")) {
//                VanishAPI.showPlayer(p);
//                p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_visible"));
//            }else{
//                p.sendMessage(Message.getMessage("prefix") + Message.getMessage("vanish_required"));
//            }
//            p.closeInventory();
//        }
//    }
        }
    }
    public void clicked_world(Player p, int slot, ItemStack clicked, Inventory inv){

        if(InventoryGUI.getClickedItem(clicked, Message.getMessage("world_back"))) {
            p.openInventory(GUI_Main(p));
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("world_day"))){
            p.getPlayer().getWorld().setTime(13000);
            p.openInventory(GUI_World(p));
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("world_night"))){
            p.getPlayer().getWorld().setTime(0);
            p.openInventory(GUI_World(p));
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("world_clear"))){
            World world = p.getWorld();
            world.setThundering(false);
            world.setStorm(true);
            p.openInventory(GUI_World(p));
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("world_rain"))){
            World world = p.getWorld();
            world.setStorm(true);
            world.setThundering(true);
            p.openInventory(GUI_World(p));
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("world_thunder"))){
            World world = p.getWorld();
            world.setStorm(false);
            world.setThundering(false);
            p.openInventory(GUI_World(p));
        }

    }

    public void clicked_players(Player p, int slot, ItemStack clicked, Inventory inv){

        if(clicked.getItemMeta().getLore() != null){
            if(clicked.getItemMeta().getLore().get(0).equals(Message.getMessage("players_lore"))){
                Player target_p = Bukkit.getServer().getPlayer(ChatColor.stripColor(clicked.getItemMeta().getDisplayName()));
                if(target_p != null){
                    target_player.put(p, target_p);
                    p.openInventory(GUI_Players_Settings(p,target_p));
                }else{
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_not_found"));
                    p.closeInventory();
                }
            }
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("players_back"))){
            p.openInventory(GUI_Main(p));
        }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("players_previous"))){
            page.put(p, page.get(p)-1);
            p.openInventory(GUI_Players(p));
        }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("players_next"))){
            page.put(p, page.get(p)+1);
            p.openInventory(GUI_Players(p));
        }

    }

    public void clicked_players_settings(Player p, int slot, ItemStack clicked, Inventory inv, Player target_player){

        if(target_player.isOnline()){
            if(InventoryGUI.getClickedItem(clicked,Message.getMessage("players_settings_back"))){
                p.openInventory(GUI_Players(p));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("players_settings_info").replace("{player}", target_player.getName()))){
                p.openInventory(GUI_Players_Settings(p, target_player));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("players_settings_actions"))){
                p.openInventory(GUI_Actions(p, target_player));
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("players_settings_kick_player"))){
                p.openInventory(GUI_Kick(p, target_player));
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("players_settings_ban_player"))){
                p.openInventory(GUI_Ban(p, target_player));
            }
        }else{
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_not_found"));
            p.closeInventory();
        }

    }

    public void clicked_actions(Player p, int slot, ItemStack clicked, Inventory inv, Player target_player){

        if(target_player.isOnline()){
            if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_back"))){
                p.openInventory(GUI_Players_Settings(p, target_player));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_info").replace("{player}", target_player.getName()))){
                p.openInventory(GUI_Actions(p,target_player));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_survival"))){
                target_player.setGameMode(GameMode.ADVENTURE);
                p.openInventory(GUI_Actions(p,target_player));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_adventure"))){
                target_player.setGameMode(GameMode.CREATIVE);
                p.openInventory(GUI_Actions(p,target_player));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_creative"))){
                target_player.setGameMode(GameMode.SPECTATOR);
                p.openInventory(GUI_Actions(p,target_player));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_spectator"))){
                target_player.setGameMode(GameMode.SURVIVAL);
                p.openInventory(GUI_Actions(p,target_player));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_teleport_to_player"))){
                p.closeInventory();
                p.teleport(target_player.getLocation());
                p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_target_player_teleport").replace("{player}", target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_kill_player"))){
                target_player.setHealth(0);
                p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_kill").replace("{player}", target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_burn_player"))){
                target_player.setFireTicks(500);
                p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_burn").replace("{player}", target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_teleport_player_to_you"))){
                p.closeInventory();
                target_player.teleport(p.getLocation());
                target_player.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_target_player_teleport").replace("{player}", p.getName()));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_heal"))){
                target_player.setHealth(20);
                target_player.setFireTicks(0);
                target_player.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_target_player_heal").replace("{player}", p.getName()));
                p.sendMessage(Message.chat(Message.getMessage("prefix") + Message.getMessage("message_player_heal").replace("{player}", target_player.getName())));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_feed"))){
                target_player.setFoodLevel(20);
                target_player.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_target_player_feed").replace("{player}", p.getName()));
                p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_feed").replace("{player}", target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("actions_god_enabled"))){
                if(Bukkit.getVersion().contains("1.8")){
                    god.put(target_player, true);
                }else{
                    target_player.setInvulnerable(true);
                }
                p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_god_enabled").replace("{player}", target_player.getName()));
                target_player.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_target_player_god_enabled").replace("{player}", p.getName()));
                p.openInventory(GUI_Actions(p,target_player));
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("actions_god_disabled"))){
                if(Bukkit.getVersion().contains("1.8")){
                    god.put(target_player, false);
                }else{
                    target_player.setInvulnerable(false);
                }
                p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_god_disabled").replace("{player}", target_player.getName()));
                target_player.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_target_player_god_disabled").replace("{player}", p.getName()));
                p.openInventory(GUI_Actions(p,target_player));
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("actions_inventory"))){
                p.openInventory(GUI_Inventory(p, target_player));
//            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("actions_vanish_enabled"))){
//                if (Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish")) {
//                    VanishAPI.hidePlayer(target_player);
//                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_hide").replace("{player}", target_player.getName()));
//                    target_player.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_hide"));
//                }else{
//                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("vanish_required"));
//                }
//                p.closeInventory();
//            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("actions_vanish_disabled"))){
//                if (Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish")) {
//                    VanishAPI.showPlayer(target_player);
//                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_visible").replace("{player}", target_player.getName()));
//                    target_player.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_visible"));
//                }else{
//                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("vanish_required"));
//                }
                p.closeInventory();
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("actions_lightning"))){
                target_player.getWorld().strikeLightning(target_player.getLocation());
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("actions_fakeop"))){
                Bukkit.broadcastMessage(Message.chat("&7&o[Server: Made " + target_player.getName() +" a server operator]"));
            }
        }else{
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_not_found"));
            p.closeInventory();
        }

    }

    public void clicked_kick(Player p, int slot, ItemStack clicked, Inventory inv, Player target_player){

        if(target_player.isOnline()){
            if(InventoryGUI.getClickedItem(clicked,Message.getMessage("kick_back"))){
                p.openInventory(GUI_Players_Settings(p, target_player));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("kick_hacking"))){
                if(target_player.hasPermission("admingui.kick.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_kick_bypass"));
                    p.closeInventory();
                }else{
                    target_player.kickPlayer(Message.getMessage("prefix") + Message.getMessage("kick") + Message.getMessage("kick_hacking"));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_kick").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("kick_griefing"))){
                if(target_player.hasPermission("admingui.kick.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_kick_bypass"));
                    p.closeInventory();
                }else{
                    target_player.kickPlayer(Message.getMessage("prefix") + Message.getMessage("kick") + Message.getMessage("kick_griefing"));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_kick").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("kick_spamming"))){
                if(target_player.hasPermission("admingui.kick.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_kick_bypass"));
                    p.closeInventory();
                }else{
                    target_player.kickPlayer(Message.getMessage("prefix") + Message.getMessage("kick") + Message.getMessage("kick_spamming"));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_kick").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("kick_advertising"))){
                if(target_player.hasPermission("admingui.kick.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_kick_bypass"));
                    p.closeInventory();
                }else{
                    target_player.kickPlayer(Message.getMessage("prefix") + Message.getMessage("kick") + Message.getMessage("kick_advertising"));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_kick").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("kick_swearing"))){
                if(target_player.hasPermission("admingui.kick.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_kick_bypass"));
                    p.closeInventory();
                }else{
                    target_player.kickPlayer(Message.getMessage("prefix") + Message.getMessage("kick") + Message.getMessage("kick_swearing"));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_kick").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }
        }else{
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_not_found"));
            p.closeInventory();
        }

    }

    public void clicked_ban(Player p, int slot, ItemStack clicked, Inventory inv, Player target_player){

        long mil_year = 31556952000L;
        long mil_month = 2592000000L;
        long mil_day = 86400000L;
        long mil_hour = 3600000L;
        long mil_minute = 60000L;

        Date time = new Date(System.currentTimeMillis()+(mil_minute*ban_minutes.getOrDefault(p,0))+(mil_hour*ban_hours.getOrDefault(p,0))+(mil_day*ban_days.getOrDefault(p,0))+(mil_month*ban_months.getOrDefault(p,0))+(mil_year*ban_years.getOrDefault(p,0)));

        if(target_player.isOnline()){
            if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_back"))){
                p.openInventory(GUI_Players_Settings(p, target_player));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_hacking"))){
                if(target_player.hasPermission("admingui.ban.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_ban_bypass"));
                    p.closeInventory();
                }else{
                    TargetPlayer.ban(target_player.getName(),  TargetPlayer.banReason("ban_hacking", time), time);
                    target_player.kickPlayer(Message.getMessage("prefix") + TargetPlayer.banReason("ban_hacking", time));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_ban").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_griefing"))){
                if(target_player.hasPermission("admingui.ban.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_ban_bypass"));
                    p.closeInventory();
                }else{
                    TargetPlayer.ban(target_player.getName(), TargetPlayer.banReason("ban_griefing", time), time);
                    target_player.kickPlayer(Message.getMessage("prefix") + TargetPlayer.banReason("ban_griefing", time));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_ban").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_spamming"))){
                if(target_player.hasPermission("admingui.ban.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_ban_bypass"));
                    p.closeInventory();
                }else{
                    TargetPlayer.ban(target_player.getName(), TargetPlayer.banReason("ban_spamming", time), time);
                    target_player.kickPlayer(Message.getMessage("prefix") + TargetPlayer.banReason("ban_spamming", time));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_ban").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_advertising"))){
                if(target_player.hasPermission("admingui.ban.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_ban_bypass"));
                    p.closeInventory();
                }else{
                    TargetPlayer.ban(target_player.getName(), TargetPlayer.banReason("ban_advertising", time), time);
                    target_player.kickPlayer(Message.getMessage("prefix") + TargetPlayer.banReason("ban_advertising", time));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_ban").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_swearing"))){
                if(target_player.hasPermission("admingui.ban.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_ban_bypass"));
                    p.closeInventory();
                }else{
                    TargetPlayer.ban(target_player.getName(), TargetPlayer.banReason("ban_swearing", time), time);
                    target_player.kickPlayer(Message.getMessage("prefix") + TargetPlayer.banReason("ban_swearing", time));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_ban").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_years"))){
                switch (ban_years.getOrDefault(p,0)){
                    case 0:
                        ban_years.put(p, 1);
                        break;
                    case 1:
                        ban_years.put(p, 2);
                        break;
                    case 2:
                        ban_years.put(p, 3);
                        break;
                    case 3:
                        ban_years.put(p, 4);
                        break;
                    case 4:
                        ban_years.put(p, 5);
                        break;
                    case 5:
                        ban_years.put(p, 6);
                        break;
                    case 6:
                        ban_years.put(p, 7);
                        break;
                    case 7:
                        ban_years.put(p, 8);
                        break;
                    case 8:
                        ban_years.put(p, 9);
                        break;
                    case 9:
                        ban_years.put(p, 10);
                        break;
                    case 10:
                        ban_years.put(p, 15);
                        break;
                    case 15:
                        ban_years.put(p, 20);
                        break;
                    case 20:
                        ban_years.put(p, 30);
                        break;
                    case 30:
                        ban_years.put(p, 0);
                        break;
                }
                p.openInventory(GUI_Ban(p, target_player));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_months"))){
                switch (ban_months.getOrDefault(p,0)){
                    case 0:
                        ban_months.put(p, 1);
                        break;
                    case 1:
                        ban_months.put(p, 2);
                        break;
                    case 2:
                        ban_months.put(p, 3);
                        break;
                    case 3:
                        ban_months.put(p, 4);
                        break;
                    case 4:
                        ban_months.put(p, 5);
                        break;
                    case 5:
                        ban_months.put(p, 6);
                        break;
                    case 6:
                        ban_months.put(p, 7);
                        break;
                    case 7:
                        ban_months.put(p, 8);
                        break;
                    case 8:
                        ban_months.put(p, 9);
                        break;
                    case 9:
                        ban_months.put(p, 10);
                        break;
                    case 10:
                        ban_months.put(p, 11);
                        break;
                    case 11:
                        ban_months.put(p, 12);
                        break;
                    case 12:
                        ban_months.put(p, 0);
                        break;
                }
                p.openInventory(GUI_Ban(p, target_player));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_days"))){
                switch (ban_days.getOrDefault(p,0)){
                    case 0:
                        ban_days.put(p, 1);
                        break;
                    case 1:
                        ban_days.put(p, 2);
                        break;
                    case 2:
                        ban_days.put(p, 3);
                        break;
                    case 3:
                        ban_days.put(p, 4);
                        break;
                    case 4:
                        ban_days.put(p, 5);
                        break;
                    case 5:
                        ban_days.put(p, 6);
                        break;
                    case 6:
                        ban_days.put(p, 7);
                        break;
                    case 7:
                        ban_days.put(p, 8);
                        break;
                    case 8:
                        ban_days.put(p, 9);
                        break;
                    case 9:
                        ban_days.put(p, 10);
                        break;
                    case 10:
                        ban_days.put(p, 15);
                        break;
                    case 15:
                        ban_days.put(p, 20);
                        break;
                    case 20:
                        ban_days.put(p, 30);
                        break;
                    case 30:
                        ban_days.put(p, 0);
                        break;
                }
                p.openInventory(GUI_Ban(p, target_player));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_hours"))){
                switch (ban_hours.getOrDefault(p, 0)){
                    case 0:
                        ban_hours.put(p, 1);
                        break;
                    case 1:
                        ban_hours.put(p, 2);
                        break;
                    case 2:
                        ban_hours.put(p, 3);
                        break;
                    case 3:
                        ban_hours.put(p, 4);
                        break;
                    case 4:
                        ban_hours.put(p, 5);
                        break;
                    case 5:
                        ban_hours.put(p, 6);
                        break;
                    case 6:
                        ban_hours.put(p, 7);
                        break;
                    case 7:
                        ban_hours.put(p, 8);
                        break;
                    case 8:
                        ban_hours.put(p, 9);
                        break;
                    case 9:
                        ban_hours.put(p, 10);
                        break;
                    case 10:
                        ban_hours.put(p, 15);
                        break;
                    case 15:
                        ban_hours.put(p, 20);
                        break;
                    case 20:
                        ban_hours.put(p, 0);
                        break;
                }
                p.openInventory(GUI_Ban(p, target_player));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_minutes"))){
                switch (ban_minutes.getOrDefault(p,0)){
                    case 0:
                        ban_minutes.put(p, 5);
                        break;
                    case 5:
                        ban_minutes.put(p, 10);
                        break;
                    case 10:
                        ban_minutes.put(p, 15);
                        break;
                    case 15:
                        ban_minutes.put(p, 20);
                        break;
                    case 20:
                        ban_minutes.put(p, 25);
                        break;
                    case 25:
                        ban_minutes.put(p, 30);
                        break;
                    case 30:
                        ban_minutes.put(p, 35);
                        break;
                    case 35:
                        ban_minutes.put(p, 40);
                        break;
                    case 40:
                        ban_minutes.put(p, 45);
                        break;
                    case 45:
                        ban_minutes.put(p, 50);
                        break;
                    case 50:
                        ban_minutes.put(p, 55);
                        break;
                    case 55:
                        ban_minutes.put(p, 0);
                        break;
                }
                p.openInventory(GUI_Ban(p, target_player));
            }
        }else{
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_not_found"));
            p.closeInventory();
        }

    }
    public void clicked_inventory(Player p, int slot, ItemStack clicked, Inventory inv, Player target_player, boolean left_click){

        if(target_player.isOnline()){
            if(InventoryGUI.getClickedItem(clicked, Message.getMessage("inventory_back"))){
                p.openInventory(GUI_Actions(p, target_player));
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("inventory_clear"))){
                target_player.getInventory().clear();
                p.openInventory(GUI_Inventory(p, target_player));
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("inventory_refresh"))){
                p.openInventory(GUI_Inventory(p, target_player));
            }else{
                if(p.hasPermission("staffgui.inventory.edit")){
                    if(left_click){
                        target_player.getInventory().addItem(clicked);
                    }else{
                        if(clicked.getType() == target_player.getInventory().getItem(slot).getType() && clicked.getAmount() == target_player.getInventory().getItem(slot).getAmount()){
                            target_player.getInventory().setItem(slot, null);
                        }
                    }
                    target_player.updateInventory();
                    p.openInventory(GUI_Inventory(p, target_player));
                }
            }
        }else{
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_not_found"));
            p.closeInventory();
        }
    }

    public static String stripNonDigits(final CharSequence input){
        final StringBuilder sb = new StringBuilder(input.length());
        for(int i = 0; i < input.length(); i++){
            final char c = input.charAt(i);
            if(c > 47 && c < 58){
                sb.append(c);
            }
        }
        return sb.toString();
    }
}