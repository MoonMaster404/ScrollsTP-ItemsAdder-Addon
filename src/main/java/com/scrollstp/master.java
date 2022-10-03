package com.scrollstp;

import com.scrollstp.util.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class master extends JavaPlugin {

    @Override
    public void onEnable() {

        String check_version = getServer().getVersion();
        String versions = "1.19";
        String version = check_version.substring(check_version.lastIndexOf(':') + 1);

        if(version.contains(versions)) {
            String support_version = (ChatColor.GREEN + "(!)" + ChatColor.WHITE + " Supported version server!");
            Bukkit.getConsoleSender().sendMessage("\n" + ChatColor.AQUA + "   ScrollsTP ItemsAdder Addon " + " - version - " + getDescription().getVersion() +
                    "\n" + ChatColor.GRAY + " - Unique teleport extension" +
                    "\n" + ChatColor.WHITE+ " - Developer: " + ChatColor.YELLOW+ "https://github.com/MoonMaster404" +
                    "\n" +ChatColor.WHITE + " - Version server: " + support_version);
        } else {
            String support_version = (ChatColor.RED + " (!) Unsupported version server!");
            Bukkit.getConsoleSender().sendMessage("\n" + ChatColor.AQUA + "   ScrollsTP ItemsAdder Addon " + " - version - " + getDescription().getVersion() +
                    "\n" + ChatColor.GRAY + " - Unique teleport extension" +
                    "\n" + ChatColor.GRAY + " - Developer: " + ChatColor.AQUA + "https://github.com/MoonMaster404" +
                    "\n" +ChatColor.GRAY +  " - Version server: " + support_version);
            getServer().getPluginManager().disablePlugin(this);
        }

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }


    @Override
    public void onDisable() {

    }
}
