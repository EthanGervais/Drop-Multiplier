package com.ethangervais.plugin;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MultiplyDrops extends JavaPlugin implements Listener {
	private int dropAmount = 1;

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {

	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		Location loc = block.getLocation();

		Collection<ItemStack> drops = block.getDrops();

		loc.getBlock().setType(Material.AIR);

		for (ItemStack drop : drops) {
			for (int i = 0; i < dropAmount; i++) {
				loc.getWorld().dropItemNaturally(loc, new ItemStack(drop));
			}
		}

		event.setCancelled(true);
		dropAmount++;
	}

	@EventHandler
	public void onMobDeath(EntityDeathEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			Location loc = event.getEntity().getLocation();

			Collection<ItemStack> drops = event.getDrops();
			for (ItemStack drop : drops) {
				for (int i = 0; i < dropAmount; i++) {
					loc.getWorld().dropItemNaturally(loc, new ItemStack(drop));
				}
			}
			
			dropAmount++;
		} else {
			return;
		}
	}
}
