package com.scrollstp.util;

import com.scrollstp.master;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public class PlayerListener implements Listener {

    Plugin plugin = master.getPlugin(master.class);
    Map<String, Long> cooldowns =
            new HashMap<>();

    public void showMyTitle(final @NonNull Audience target) {

        String mainTitleConfig = plugin.getConfig().getString("Title.MainTitle");
        String subTitleConfig = plugin.getConfig().getString("Title.SubTitle");

        final Component mainTitle = LegacyComponentSerializer.legacyAmpersand().deserialize(mainTitleConfig);
        final Component subtitle = LegacyComponentSerializer.legacyAmpersand().deserialize(subTitleConfig);

        final Title title = Title.title(mainTitle, subtitle);

        if (plugin.getConfig().getBoolean("Title.Enabled")) {
            target.showTitle(title);
        }
    }

    @EventHandler
    public void RightClickScrolls(PlayerInteractEvent e) {

        String player_name = e.getPlayer().getName();
        // Collection<? extends Player> execute_player = e.getPlayer().getServer().getOnlinePlayers();
        Player player = e.getPlayer();
        EquipmentSlot getHand = e.getHand();
        Action entity_action = e.getAction();
        Material item_material = e.getMaterial();

        Bukkit.getConsoleSender().sendMessage(String.valueOf(plugin.getConfig().getBoolean("Title.Enabled")));
        // Bukkit.getConsoleSender().sendMessage(execute_player.toString());

        if (entity_action == Action.RIGHT_CLICK_AIR && item_material.equals(Material.ARROW)) {
            if (cooldowns.containsKey(player.getName())) {
                if (cooldowns.get(player.getName()) > System.currentTimeMillis()) {
                    long timeleft = (cooldowns.get(player.getName()) - System.currentTimeMillis()) / 1000;
                    player.sendMessage("У Вас кулдаун еще: " + timeleft + " секунд");
                    return;
                }
            }

            cooldowns.put(player.getName(), System.currentTimeMillis() + (5 * 1000));

            int x_config = plugin.getConfig().getInt("x");
            int z_config = plugin.getConfig().getInt("z");
            int y_config = plugin.getConfig().getInt("y");

            Random random = new Random();
            int x = random.nextInt(x_config);
            int y = 1000;
            int z = random.nextInt(z_config);

            Location RandomScrollsLocation = new Location(player.getWorld(), x, y, z);
            if (y_config != 0) {
                y = y_config;
                RandomScrollsLocation.setY(y + 1);
            } else {
                y = RandomScrollsLocation.getWorld().getHighestBlockYAt(RandomScrollsLocation);
                RandomScrollsLocation.setY(y + 1);
            }

            player.sendMessage("Ник: " + player_name +
                    " Рука: " + getHand + " " +
                    " Действие: " + entity_action + " " +
                    " Предмет: " + item_material);

            player.sendMessage("Все верно!");
            showMyTitle(player);
            player.teleport(RandomScrollsLocation);
            int countscrolls = e.getItem().getAmount();

            e.getItem().setAmount(countscrolls - 1);
            Bukkit.getConsoleSender().sendMessage(String.valueOf(countscrolls));
            cooldowns.put(player.getName(), System.currentTimeMillis() + (5 * 1000));
        }

    }

}