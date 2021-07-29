package com.greazi.addonbase;

import me.TechsCode.UltraRegions.UltraRegions;
import me.TechsCode.UltraRegions.base.item.XMaterial;
import me.TechsCode.UltraRegions.flags.Flag;
import me.TechsCode.UltraRegions.flags.calculator.Result;
import me.TechsCode.UltraRegions.storage.FlagValue;
import me.TechsCode.UltraRegions.storage.ManagedWorld;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Optional;

public class FlagName extends Flag {

	/**
	 * This is the main name of your flag all in caps
	 * Spaces are allowed but needed to be given with a; _
	 *
	 *
	 * @param plugin This will be registered inside Ultra Regions
	 */
	public FlagName(UltraRegions plugin) {
		super(plugin, "FLAG_NAME");
	}

	/**
	 * The name of the flag it self
	 * The value can only be a string
	 *
	 * @return The name of the flag
	 */
	public String getName() {
		return "Name of the flag";
	}

	/**
	 * This will hold the description of the flag you are creating
	 * The value can only be a string
	 *
	 * @return The description of the flag
	 */
	public String getDescription() {
		return "Description";
	}

	/**
	 * The input for the icon that will shown in the flags list
	 * Possible icon's can be found here: https://helpch.at/docs/1.7.10/org/bukkit/Material.html
	 * !!-WARNING-!! ONLY USE ICON'S THAT ARE AVAILABLE ON 1.8 (if wanted you can add a check for the server version)
	 *
	 * @return The material of the icon that is shown in the flags list
	 */
	public XMaterial getIcon() {
		return XMaterial.CHEST;
	}

	/**
	 * What is the default falgs value?
	 * This means when you did not add the flag it will be allowed or not.
	 * !!-WARNING-!! WHEN SET TO "DISALLOW" THINGS CAN BREAK!
	 *
	 * @return The default flag value
	 * 	Possible values are:
	 * 	 FlagValue.ALLOW or FlagValue.ALLOW
	 */
	public FlagValue getDefaultValue() {
		return FlagValue.ALLOW;
	}

	/** Is this flag connected to players?
	 * Possible values are:
	 * 		true / false
	 * @return If the flag is player specific
	 */
	public boolean isPlayerSpecificFlag() {
		return true;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {

		/*
		 * This is an example of a check. In this case we are blocking the open Container event.
		 * So our end result will be when disallowed you are unable to open a container.
		 */
		if (e.getPlayer() instanceof Player) {
			Player player = e.getPlayer();
			Optional<ManagedWorld> optional = this.plugin.getWorlds().find(player.getWorld());
			if (optional.isPresent()) {
				Block block = e.getClickedBlock();
				if (block != null) {
					Result result = calculate(block.getLocation(), player);
					if (result != null && result.isSetToDisallowed() &&
							block.getState() instanceof org.bukkit.block.Container && (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)) {
						e.setCancelled(true);
						player.closeInventory();
					}
				}
			}
		}
	}
}
