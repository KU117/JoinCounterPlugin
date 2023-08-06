package me.imbestpumpkin.joincount.main;

import me.imbestpumpkin.joincount.Listeners.JoinListener;
import me.imbestpumpkin.joincount.db.SQLite;
import org.bukkit.plugin.java.JavaPlugin;

public final class JoinCount extends JavaPlugin {

    @Override
    public void onEnable() {
        SQLite.connect();
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    @Override
    public void onDisable() {
        SQLite.disconnect();
    }
}
