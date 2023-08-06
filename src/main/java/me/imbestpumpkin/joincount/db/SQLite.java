package me.imbestpumpkin.joincount.db;

import me.imbestpumpkin.joincount.main.JoinCount;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.sql.*;
import java.util.UUID;

public class SQLite {
    public static Connection connection;

    public static void connect() {
        File file = new File(JoinCount.getPlugin(JoinCount.class).getDataFolder(), "mydatabase.db");
        if (!file.exists()) {
            new File(JoinCount.getPlugin(JoinCount.class).getDataFolder().getPath()).mkdir();
        }
        String URL = "jdbc:sqlite:" + file;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
            createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTable() {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS mydatabase(Id INTEGER PRIMARY KEY AUTOINCREMENT, uuid TEXT, JoinCount INT)");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void newPlayer(String uuid, int JoinAmount) {
        PreparedStatement ps = null;
        try {
            ps = SQLite.connection.prepareStatement("INSERT INTO mydatabase(uuid, JoinCount) VALUES (?, ?)");
            ps.setString(1, uuid);
            ps.setInt(2, JoinAmount);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePlayer(String uuid, int joinAmount) {
        PreparedStatement ps = null;
        try {
            ps = SQLite.connection.prepareStatement("UPDATE mydatabase SET JoinCount = ? WHERE UUID = ?");
            ps.setInt(1, joinAmount);
            ps.setString(2, uuid);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int getJoinAmount(String uuid) {
        int joinAmount = 0;
        PreparedStatement ps = null;
        try {
            ps = SQLite.connection.prepareStatement("SELECT JoinCount FROM mydatabase WHERE UUID = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                joinAmount = rs.getInt("JoinCount");
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return joinAmount;
    }

    public static boolean getPlayer(String uuid) {
        PreparedStatement ps = null;
        try {
            ps = SQLite.connection.prepareStatement("SELECT UUID FROM mydatabase WHERE UUID = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

