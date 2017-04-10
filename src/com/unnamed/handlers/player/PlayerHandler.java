package com.unnamed.handlers.player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.unnamed.Unnamed;
import com.unnamed.utils.ChatUtilities;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_11_R1.PacketPlayOutRespawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.UUID;

public class PlayerHandler {

    private static HashMap<UUID, Property> oldSkins = new HashMap<UUID, Property>();

    public static void setPlayerName(Player p, String newName){

        EntityPlayer ep = ((CraftPlayer) p).getHandle();
        GameProfile gp = ep.getProfile();

        try {

            Field nameField = GameProfile.class.getDeclaredField("name");
            nameField.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(nameField, nameField.getModifiers() & ~Modifier.FINAL);

            nameField.set(gp, newName);

        } catch (IllegalAccessException | NoSuchFieldException ex) {

            throw new IllegalStateException(ex);

        }

        reloadPlayer(p);

    }

    public static void setPlayerSkin(Player p, String newSkin){

        EntityPlayer ep = ((CraftPlayer) p).getHandle();
        GameProfile gp = ep.getProfile();
        PropertyMap pm = gp.getProperties();

        Property property = pm.get("textures").iterator().next();

        if(!oldSkins.containsKey(p.getUniqueId())){

            oldSkins.put(p.getUniqueId(), property);

        }

        pm.remove("textures", property);
        if(newSkin.equalsIgnoreCase("matty")) {

            pm.put("textures", new Property("textures", "eyJ0aW1lc3RhbXAiOjE0OTE3ODUzNzcxMTYsInByb2ZpbGVJZCI6IjQzYTgzNzNkNjQyOTQ1MTBhOWFhYjMwZjViM2NlYmIzIiwicHJvZmlsZU5hbWUiOiJTa3VsbENsaWVudFNraW42Iiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsibWV0YWRhdGEiOnsibW9kZWwiOiJzbGltIn0sInVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQ0ZGE4NzBkZmE0MWY1MGZjZTYxNTcxYzEzNWNlOWFmZGU3OWEyMWMxNGVkNDVjNzhkYTQxZDVlOWVlZTk5In19fQ==", "KF8WZl+GXiPCWvHPS2Oz389gJUnVfypvavtIgzCJFYcwyQYci9fwxg6jYQjCpEBWS0lVsx88vBkZr2l2Hf/Z0ZJvqX6OU7rkACqLjYXdBxwBTg4pGn77mwdNLXupGJ/ns4HVvFBvQED8Tv9WYuHAKWAoZUQ5vrSGX/2z78p+1y7p7mckwBcPe1QO1JEifTCejdtC7GtUQwa03cH7odc9ujA5n4WQIhNzYkF56qchBLW0l97vsXfzuu1iNRXuHYXjmLJgc/via4XEPYrJ9MPHmNh3BxhbRud+ewWITzhDEluvSEjwawtDnxZNzqNWId/2/5Jj+aeb4mh1yDoR0CubeAAFPjvFb9uo7DKr7buB0Hw+RLfA7tZDAEq0ZnVtS8UHWsZFWDwBye+wMLHuX8TjGwnnG7KsfH3aDQte/iAzUOaq9GPzAvkCwg/ONbrVBH61toP0ucdIt2XPLO9rMnO4baGphY9urPM+wx1I1/+WNFSlXeSwdHPS7/Ulov+LbqhsCw10mWw3LVNPKFa2tBrG66VWnqnzeaO5p7PfTb+UAoIEKL/ht/h3fBpXNvRkH1h7+9toCC7Mx9tSHCkYTviXHZXvEuIYZVl/D9i1d99S6Mc/AbvpwcorGqpSbtFNAI8xLYF9nBtsFUawV6WqN2JftUPtX+DUNPRuzycCnUmKyEE="));

        }else if(newSkin.equalsIgnoreCase("generic")){

            pm.put("textures", new Property("textures", "eyJ0aW1lc3RhbXAiOjE0OTEyNDMxNzQ0MDQsInByb2ZpbGVJZCI6ImVmOWM3ZDY4NmNkYzQyNjZiOGQ0N2I2OTM0YzJiYzc0IiwicHJvZmlsZU5hbWUiOiJJdHNFdGhhbk1DIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS84ZTYwODkxZmY3NDljNjYyZDc4MWU0MjZhMzE5Nzk5OWJjZTZkYjZkYmUyM2E3NWY0NWZiOWE1NDIyNjE3OCJ9fX0=", "oewFL+sr7cu/3OyPLdZt7x6jvnaOEIEsJ9WHQXbeBZAdlFuow68prOAVJHI4hvUD17zaGtluoQt89hjI273NH+GdeOUaK9HEbuZQ+eSR2876aTR4qagocMhwyehHAxsKKEiJo2OnSQYkSxkSzK1R6QexAZqovzQuD/LhHqZD1MLTjFZBilEMg3Fhn0kTM5TYJOTtgAfpJ+yZRS4xg2ITyAtrW97BB959H3qSuyz7MD8YKsW6SxwBwNjP1AZ2Pq5mMaCu+WJzK0rsfZFDmEs5F7Ov5xil5Kw0mCnapw0Fx4cTiiq9I73JgYxs6DU14Lz/p+uwgi0lyKH5YnoS8ZIvB4nW7vBI2ohEUBgH28klGfAWr63PE/YMTuyAsWh6guTHyQngbTQRt49OVkuRl9HEWCQImFJknHttQywzneiE7FfRcJ+QiSBZuwjNNHZZ7PKe4XS5PcqvrhyctIWs5Ip74xFk/eqp1U8KgFcwRHtwOSfJzogV/htvui9RTg4wglN3K8oo/ogDJNp/cfL0LmkVJqLNgcWYYFkte49p7bKDr3Xbj7xe/UzMOsV0csMeBkJv7IqbMaPpmL3ovRUsovxmLizF0MizW1fKnaMmfUE7fTAWQkoVb1AgLaZsoVitc1cBSS/ZLwPzmUl2I++Ba5ANBFj/ccJgputcLvwvLNtzOSw="));

        }

        reloadPlayer(p);

    }

    public static void revertPlayerSkin(Player p) {

        if(!oldSkins.containsKey(p.getUniqueId())){

            ChatUtilities.onePlayer("You're using your default skin", p);

        }else {

            EntityPlayer ep = ((CraftPlayer) p).getHandle();
            GameProfile gp = ep.getProfile();
            PropertyMap pm = gp.getProperties();

            Property property = pm.get("textures").iterator().next();

            pm.remove("textures", property);
            pm.put("textures", oldSkins.get(p.getUniqueId()));

            oldSkins.remove(p.getUniqueId());

            reloadPlayer(p);

            ChatUtilities.onePlayer("Player skin reverted", p);

        }

    }

    private static final void reloadPlayer(Player player) {

        final EntityPlayer ep = ((CraftPlayer) player).getHandle();
        final PacketPlayOutPlayerInfo removeInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ep);
        final PacketPlayOutPlayerInfo addInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ep);
        final Location loc = player.getLocation();
        ep.playerConnection.sendPacket(removeInfo);
        ep.playerConnection.sendPacket(addInfo);
        player.teleport(Bukkit.getWorld("ProjectUnnamed_nether").getSpawnLocation());
        new BukkitRunnable() {

            @Override
            public void run() {

                player.teleport(loc);
                ep.playerConnection.sendPacket(new PacketPlayOutRespawn(ep.dimension, ep.getWorld().getDifficulty(), ep.getWorld().getWorldData().getType(), ep.playerInteractManager.getGameMode()));

            }

        }.runTaskLater(Unnamed.getPlugin(), 2L);

        for (Player p : Bukkit.getOnlinePlayers()) {

            p.hidePlayer(player);
            p.showPlayer(player);

        }

    }

}
