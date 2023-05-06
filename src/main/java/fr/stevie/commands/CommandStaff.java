package fr.stevie.commands;

import fr.stevie.ui.StaffUI;
import fr.stevie.utils.Message;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandStaff implements CommandExecutor {

    private StaffUI staffUI = new StaffUI();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if(player.hasPermission("staffgui.use")) {
        if (args.length == 0) {
            StaffUI.target_player.put(player, player);
            player.openInventory(this.staffUI.GUI_Main(player));
        }
        else if (args.length == 1) {
            final Player target_player = Bukkit.getServer().getPlayer(args[0]);
            if (target_player != null) {
                StaffUI.target_player.put(player, target_player);
                if (player.getName().equals(target_player.getName())) {
                    player.openInventory(this.staffUI.GUI_Player(player));
                }
                else {
                    player.openInventory(this.staffUI.GUI_Players_Settings(player, target_player));
                }
            }
            else {
                player.sendMessage(Message.getMessage("prefix") + Message.getMessage("is_not_a_player").replace("{player}", args[0]));
            }
        }
        else {
            player.sendMessage(Message.getMessage("prefix") + Message.getMessage("wrong_arguments"));
        }
    }
        else {
        player.sendMessage(Message.getMessage("prefix") + Message.getMessage("permission"));
    }
        return true;
}
}


