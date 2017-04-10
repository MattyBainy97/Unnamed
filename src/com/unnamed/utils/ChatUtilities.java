package com.unnamed.utils;

import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.bukkit.ChatColor.*;

public class ChatUtilities {

    public static void broadcastNoStarter(String msg) {

        for (Player player : Bukkit.getOnlinePlayers()) {

            player.sendMessage(msg);

        }

    }

    public static void onePlayer(String msg, Player player) {

        player.sendMessage(starter() + msg);

    }

    public static void chat(String msg, Player player) {

        broadcastNoStarter(chatStarter(player) + DARK_AQUA + player.getName() + GRAY + " » " + WHITE + msg);

    }

    private static String chatStarter(Player p) {

        if(p.getUniqueId().equals(UUID.fromString("bc61d2bf-3bcc-4058-a610-60eaa62c14e0"))){

            return YELLOW + "DEV " + GRAY + "| ";

        }

        return "";

    }

    private static String starter() {

        return GRAY + "" + BOLD + "|" + RESET + "" + RED + " UNNAMED " + GRAY + "| " + GOLD;

    }

}
