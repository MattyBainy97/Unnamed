package com.unnamed.listeners.entity.player;

import com.unnamed.Unnamed;
import com.unnamed.listeners.UListener;
import com.unnamed.utils.ChatUtilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChat extends UListener {
    
    public OnChat(Unnamed pl){
        
        super(pl);
        
    }
    
    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent pc) {

        pc.setCancelled(true);
        ChatUtilities.chat(pc.getMessage(), pc.getPlayer());
        
    }
    
}
