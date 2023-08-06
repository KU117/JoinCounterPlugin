package me.imbestpumpkin.joincount.Listeners;

import me.imbestpumpkin.joincount.db.SQLite;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public static void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (SQLite.getPlayer(p.getUniqueId().toString())) {
            System.out.println("Gracz jest");
            int joinCount = SQLite.getJoinAmount(p.getUniqueId().toString()) + 1;
            p.sendMessage(ChatColor.LIGHT_PURPLE + "Twoja obecna liczba wejść: " + ChatColor.AQUA + joinCount);
            SQLite.updatePlayer(p.getUniqueId().toString(), SQLite.getJoinAmount(p.getUniqueId().toString()) + 1);
        } else {
            System.out.println("Gracz nie ma");
            SQLite.newPlayer(p.getUniqueId().toString(), 1);
        }
    }
}
