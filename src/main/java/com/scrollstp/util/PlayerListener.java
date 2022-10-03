package com.scrollstp.util;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;
import java.util.Random;

public class PlayerListener implements Listener {

    public void showMyTitle(final @NonNull Audience target) {
        final Component mainTitle = Component.text("Телепортация", NamedTextColor.YELLOW);
        final Component subtitle = Component.text("Ожидайте...", NamedTextColor.WHITE);

        final Title title = Title.title(mainTitle, subtitle);

        target.showTitle(title);
    }

    @EventHandler
    public void RightClickScrolls(PlayerInteractEvent e) {
        String player_name = e.getPlayer().getName();
        Player player = e.getPlayer();
        EquipmentSlot getHand = e.getHand();
        Action entity_action = e.getAction();
        Material item_material = Objects.requireNonNull(e.getItem()).getType();

        Random random = new Random();
        int x = random.nextInt(10);
        int y = 10;
        int z = random.nextInt(10);

        Location RandomScrollsLocation = new Location(player.getWorld(), x, y, z);

        y = RandomScrollsLocation.getWorld().getHighestBlockYAt(RandomScrollsLocation);
        RandomScrollsLocation.setY(y + 1);


        player.sendMessage("Ник: " + player_name +
                " Рука: " + getHand + " " +
                " Действие: " + entity_action + " " +
                " Предмет: " + item_material);

        if (item_material.equals(Material.ARROW)) {
            player.sendMessage("Все верно!");
            showMyTitle(player);
            player.teleport(RandomScrollsLocation);
            int countscrolls = e.getItem().getAmount();

            e.getItem().setAmount(countscrolls - 1);
            Bukkit.getConsoleSender().sendMessage(String.valueOf(countscrolls));

        }
    }
}