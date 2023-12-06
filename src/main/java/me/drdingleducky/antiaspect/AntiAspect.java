package me.drdingleducky.antiaspect;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class AntiAspect extends JavaPlugin implements Listener {

    int enchantmentLevel = 1;

    @Override
    public void onEnable() {
        getLogger().info("Hello From AntiAspect!");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Bye From AntiAspect!");
    }

    @EventHandler
    public void onPrepareItemEnchant(PrepareItemEnchantEvent event) {
        for (EnchantmentOffer offer : event.getOffers()) {
            if (offer != null && offer.getEnchantment().equals(Enchantment.FIRE_ASPECT)) {
                offer.setEnchantment(Enchantment.DAMAGE_ALL);
                if (event.getEnchantmentBonus() <= 4) {
                    offer.setEnchantmentLevel(1);
                    enchantmentLevel = 1;
                } else if (event.getEnchantmentBonus() <= 8) {
                    offer.setEnchantmentLevel(2);
                    enchantmentLevel = 2;
                } else if (event.getEnchantmentBonus() <= 11) {
                    offer.setEnchantmentLevel(3);
                    enchantmentLevel = 3;
                } else if (event.getEnchantmentBonus() <= 15) {
                    offer.setEnchantmentLevel(4);
                    enchantmentLevel = 4;
                }
            }
        }
    }

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        getServer().getScheduler().runTaskLater(this, () -> {
            if (event.getItem().getEnchantments().containsKey(Enchantment.FIRE_ASPECT)) {
                event.getItem().removeEnchantment(Enchantment.FIRE_ASPECT);
                event.getItem().removeEnchantment(Enchantment.DAMAGE_ALL);
                event.getItem().addEnchantment(Enchantment.DAMAGE_ALL, enchantmentLevel);
            }
        }, 1);
    }
}
