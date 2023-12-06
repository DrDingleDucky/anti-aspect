package me.drdingleducky.antiaspect;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class AntiAspect extends JavaPlugin implements Listener {

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
                offer.setEnchantmentLevel(1);
            }
        }
    }

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        getServer().getScheduler().runTaskLater(this, () -> {
            if (event.getItem().getEnchantments().containsKey(Enchantment.FIRE_ASPECT)) {
                event.getItem().removeEnchantment(Enchantment.FIRE_ASPECT);
                if (!event.getItem().getEnchantments().containsKey(Enchantment.DAMAGE_ALL)) {
                    event.getItem().addEnchantment(Enchantment.DAMAGE_ALL, 1);
                }
            }
        }, 1);
    }
}
