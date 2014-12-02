package edu.sjsu.cs185c.plugin;

import net.minecraft.server.v1_7_R3.AchievementList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import org.bukkit.event.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.BookMeta;

public class Plugin extends JavaPlugin {

	// private final String HELLO_MESSAGE =
	// "Hello world... (this is the example bukkit plugin.)";
	// private final String GOODBYE_MESSAGE =
	// "Goodbye world... (this is the example bukkit plugin.)";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		getLogger().info("Inside onCommand");

		if (cmd.getName().equalsIgnoreCase("foo")) {
			getLogger().info("Bruno Rules!");
			sender.sendMessage("Bruno Rules!");
			return true;
		} else if (cmd.getName().equalsIgnoreCase("getthetime")) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			sender.sendMessage("The time is now " + dateFormat.format(date));
		}

		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (cmd.getLabel().equalsIgnoreCase("vanish")) {
				player.hidePlayer(player);
			} else if (cmd.getLabel().equalsIgnoreCase("unvanish")) {
				player.showPlayer(player);
			} else if (cmd.getLabel().equals("newbook")) {
				ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
				BookMeta meta = (BookMeta) book.getItemMeta();
				meta.setTitle("Simulacra and Simulation");
				meta.setAuthor("Jean Baudrillard");
				meta.setPages(Arrays
						.asList(ChatColor.GREEN
								+ "The simulacrum is never what hids the truth -- it is truth that hides the fact that there is none. The simulacrum is true. -- Ecclesiastes."));

				book.setItemMeta(meta);

				player.getInventory().addItem(book);

			}
		}
		return true;
	}

	/*
	 * onEnable and onDisable get invoked when the server is started up, shut
	 * down, restarted... In the console try doing a /reload
	 */
	@Override
	public void onEnable() {
		new MoneyManager(this);
		ServerPersistence.loadData();
		getLogger().info("Server starting");
		this.saveDefaultConfig();
		ShoppingManager.init();
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					String name = p.getName();
					Profession prof = ProfessionManager
							.getProfessionByName(name);
					double wage = prof.getWage();
					if (MoneyManager.hasAccount(name))
						MoneyManager.addBalance(name, wage);
					p.sendMessage(ChatColor.YELLOW+"Your wage was just paid! +"+prof.getWage()+"$");
				}
			}
		}, 0L, 6000L);
		// Register a new listener
		getServer().getPluginManager().registerEvents(new Listener() {

			@EventHandler
			public void playerJoin(PlayerJoinEvent event) {
				// On player join send them the message from config.yml
				MyEventHandler.onPlayerJoin(event);
				event.getPlayer().sendMessage(
						Plugin.this.getConfig().getString("message"));

			}

			@EventHandler
			public void playerDeath(PlayerDeathEvent e) {
				MyEventHandler.onPlayerDeath(e);
			}

			@EventHandler
			public void onKillEntity(EntityDeathEvent event) {
				MyEventHandler.onEntityDeath(event);
			}

			@EventHandler
			public void onMove(PlayerMoveEvent e) {
				MyEventHandler.onPlayerMove(e);
			}

			@EventHandler
			public void onBlockDestroy(BlockBreakEvent e) {
				MyEventHandler.onBlockDestroy(e);
			}

			@EventHandler
			public void onCraft(CraftItemEvent e) {
				
				MyEventHandler.onCraftItem(e);
			}

			@EventHandler(priority = EventPriority.HIGH)
			public void customCrafting(PrepareItemCraftEvent e) {
				//MyEventHandler.onCustomCraft(e);
			}
			@EventHandler
			public void onBlockPlace(BlockPlaceEvent event) {
		        MyEventHandler.onBlockPlace(event);
			}

		}, this);

		getCommand("money").setExecutor(new MoneyCommand());
		getCommand("karma").setExecutor(new KarmaCommand());
		getCommand("ach").setExecutor(new AchievementCommand());
		getCommand("prof").setExecutor(new ProfessionCommand());
		getCommand("shop").setExecutor(new ShoppingCommand());

	}

	@Override
	public void onDisable() {
		getLogger().info("Server turning off");
		ServerPersistence.saveData();
	}

	public void logToFile(String message) {
		try {
			File dataFolder = getDataFolder();
			if (!dataFolder.exists()) {
				dataFolder.mkdir();
			}

			File saveTo = new File(getDataFolder(), "myplugin.log");
			if (!saveTo.exists()) {
				saveTo.createNewFile();
			}

			FileWriter fw = new FileWriter(saveTo, true);

			PrintWriter pw = new PrintWriter(fw);

			pw.println(message);
			pw.flush();
			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
