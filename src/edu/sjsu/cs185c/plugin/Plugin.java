package edu.sjsu.cs185c.plugin;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import org.bukkit.event.*;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class Plugin extends JavaPlugin{

	//private final String HELLO_MESSAGE = "Hello world... (this is the example bukkit plugin.)";
	//private final String GOODBYE_MESSAGE = "Goodbye world... (this is the example bukkit plugin.)";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
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
				meta.setPages(Arrays.asList(ChatColor.GREEN + "The simulacrum is never what hids the truth -- it is truth that hides the fact that there is none. The simulacrum is true. -- Ecclesiastes."));

				book.setItemMeta(meta);
				
				player.getInventory().addItem(book);
				
			}
		}
		return true;
	}
	
	/* onEnable and onDisable get invoked when the server is started up, shut down, restarted... 
	 * In the console try doing a /reload 
	 */
	@Override
	public void onEnable ()
	{
		new MoneyManager(this);
		ServerPersistence.loadData();
		getLogger().info ("Server starting");
		this.saveDefaultConfig();
		 
        // Register a new listener
        getServer().getPluginManager().registerEvents(new Listener() {
 
            @EventHandler
            public void playerJoin(PlayerJoinEvent event) {
                // On player join send them the message from config.yml
                event.getPlayer().sendMessage(Plugin.this.getConfig().getString("message"));
                if(!MoneyManager.hasAccount(event.getPlayer().getName())){
                	MoneyManager.setBalance(event.getPlayer().getName(), 1000.0);
         
                }else if(!KarmaManager.hasKarma(event.getPlayer().getName())){
                	KarmaManager.setKarma(event.getPlayer().getName(), 0);
                }
            	
            }
            
            @EventHandler
            public void Playerdeath(PlayerDeathEvent e) {
            String killers_name = e.getEntity().getKiller().getName();
            String dead_name = e.getEntity().getName();
            if (KarmaManager.hasKarma(killers_name) && KarmaManager.hasKarma(dead_name)){
            	int killers_karma = KarmaManager.getKarma(killers_name);
            	int dead_karma = KarmaManager.getKarma(dead_name);
            	if(dead_karma<0){
            		if(dead_karma < killers_karma)
            		killers_karma++;
            		else
            		killers_karma--;
            	}else{
            		if(dead_karma < killers_karma)
                		killers_karma--;
                		else
                		killers_karma-=3;
            	}
                
                }
            }
        }, this);

        getCommand("econ").setExecutor(new MoneyCommand());
        getCommand("karma").setExecutor(new KarmaCommand());
        
	}
	
	@Override
	public void onDisable ()
	{
		getLogger().info ("Server turning off");
		ServerPersistence.saveData();
	}
	
    public void logToFile(String message)
    {
    	try {
    		File dataFolder = getDataFolder();
    		if(!dataFolder.exists()) {
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


