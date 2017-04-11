package com.unnamed.utils;

import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class ChatUtilities {

    public static void onePlayer(String msg, Player player) {

        player.sendMessage(starter() + msg);

    }

    private static String starter() {

        return GRAY + "" + BOLD + "|" + RESET + "" + RED + " UNNAMED " + GRAY + "| " + GOLD;

    }

}
