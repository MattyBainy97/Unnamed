package com.unnamed;

import com.unnamed.handlers.player.PlayerHandler;
import com.unnamed.utils.ChatUtilities;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Unnamed extends JavaPlugin {

    private static Plugin plugin;
    @Override
    public void onEnable() {

        plugin = this;

    }

    @Override
    public void onDisable() {

        plugin = null;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(sender instanceof Player) {

            Player player = (Player) sender;

            if (commandLabel.equalsIgnoreCase("name")) {

                if(args.length == 0){

                    ChatUtilities.onePlayer("Please enter a name", player);

                }else if(args.length >= 2){

                    ChatUtilities.onePlayer("Too many arguments supplied", player);

                }else{

                    if(args[0].length() > 16 || args[0].length() < 3){

                        ChatUtilities.onePlayer("Player name must be between 3 and 16 characters", player);

                    }else if(args[0].equalsIgnoreCase("reset")) {

                        Player found = null;

                        for(Player p : Bukkit.getOnlinePlayers()){

                            if(p.getName().equalsIgnoreCase(player.getDisplayName()) && !p.getUniqueId().equals(player.getUniqueId())){

                                found = p;

                            }

                        }

                        if(found != null){

                            ChatUtilities.onePlayer(ChatColor.DARK_AQUA + player.getDisplayName() + ChatColor.GOLD + " wants their name back", found);
                            PlayerHandler.setPlayerName(found, found.getDisplayName());
                            ChatUtilities.onePlayer("Player name reverted to " + ChatColor.DARK_AQUA + found.getDisplayName(), found);

                        }

                        PlayerHandler.setPlayerName(player, player.getDisplayName());
                        ChatUtilities.onePlayer("Player name reverted to " + ChatColor.DARK_AQUA + player.getDisplayName(), player);

                    }else{

                        boolean found = false;

                        for(Player p : Bukkit.getOnlinePlayers()){

                            if(p.getName().equalsIgnoreCase(args[0])){

                                found = true;

                            }

                        }

                        if(found == false) {

                            PlayerHandler.setPlayerName(player, args[0]);
                            ChatUtilities.onePlayer("Player name changed to " + ChatColor.DARK_AQUA + args[0], player);

                        }else{

                            ChatUtilities.onePlayer("This name is already taken by another player", player);

                        }

                    }

                }

            }

            if (commandLabel.equalsIgnoreCase("skin")){

                if(args.length == 0){

                    ChatUtilities.onePlayer("Please enter a skin name", player);
                    ChatUtilities.onePlayer("Possible skins are: " + ChatColor.DARK_AQUA + "matty" + ChatColor.GOLD + ", " + ChatColor.DARK_AQUA + "generic", player);

                }else if(args.length >= 2){

                    ChatUtilities.onePlayer("Too many arguments supplied", player);

                }else{

                    if(args[0].equalsIgnoreCase("matty") || args[0].equalsIgnoreCase("generic")){

                        PlayerHandler.setPlayerSkin(player, args[0]);
                        ChatUtilities.onePlayer("Skin changed to " + ChatColor.DARK_AQUA + args[0], player);

                    }else if(args[0].equalsIgnoreCase("reset")) {

                        PlayerHandler.revertPlayerSkin(player);

                    }else{

                        ChatUtilities.onePlayer("Please enter a valid skin", player);
                        ChatUtilities.onePlayer("Possible skins are: " + ChatColor.DARK_AQUA + "matty" + ChatColor.GOLD + ", " + ChatColor.DARK_AQUA + "generic", player);

                    }

                }

            }

        }

        return false;

    }

    public static Plugin getPlugin(){

        return plugin;

    }

}
