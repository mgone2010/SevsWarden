package be.seveningful.sw;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;






import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;


public class SevsWarden extends JavaPlugin{
	
	public File avert = new File(getDataFolder(), "avert.yml");
	
	public YamlConfiguration loadavert = YamlConfiguration.loadConfiguration(avert);
	
	String SevsWarden = ChatColor.GRAY + "[§4SevsWarden" + ChatColor.GRAY + "] " + ChatColor.RESET;
	HashMap<Player, Integer> warning = new HashMap<Player, Integer>();
	HashMap<Player, Integer> npca = new HashMap<Player, Integer>();
	public void kickplayerff(final Player p){
		BukkitTask task1 = new BukkitRunnable() {
			
			@Override
			public void run() {
				if(loadavert.getInt(p.getUniqueId() + ".avertff") < getConfig().getInt("Nombremaxforcefield") ){
					String Ban = getConfig().getString("ForceFieldKickMessage");
					String BanMessage = ChatColor.translateAlternateColorCodes('&', Ban);
					Bukkit.broadcastMessage(SevsWarden + ChatColor.RED + ChatColor.BOLD.toString() + p.getName() + ChatColor.GRAY + " a été éjecté pour : " + ChatColor.YELLOW + "ForceField");
					for(Player online : Bukkit.getOnlinePlayers()){
						online.playSound(p.getLocation(), Sound.WITHER_SHOOT, 10, -10);
					}
					p.kickPlayer(BanMessage);
				loadavert.set(p.getUniqueId() + ".avertff", (loadavert.getInt(p.getUniqueId() + ".avertff") + 1));
				try {
					loadavert.save(avert);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
			}else{
				String Ban = getConfig().getString("ForceFieldBanMessage");
				String BanMessage = ChatColor.translateAlternateColorCodes('&', Ban);
				Bukkit.broadcastMessage(SevsWarden + ChatColor.RED + ChatColor.BOLD.toString() + p.getName() + ChatColor.GRAY + " a été banni pour : " + ChatColor.YELLOW + "ForceField");
				for(Player online : Bukkit.getOnlinePlayers()){
					online.playSound(p.getLocation(), Sound.WITHER_DEATH, 10, -10);
				}
				p.kickPlayer(BanMessage);
				
				p.setBanned(true);
			}
			}
		}.runTaskLater(this, 1);
	}
	public void RemoveNPC(final NPC npc, final Player p){
		BukkitTask task2 = new BukkitRunnable() {
			
			@Override
			public void run() {
				npca.remove(p);
				npc.remove();
				
			}
		}.runTaskLater(this, (long) (20*0.3));
	}
	public void RemoveNPC2(final NPC npc, final Player p){
		BukkitTask task2 = new BukkitRunnable() {
			
			@Override
			public void run() {
				npca.remove(p);
				npc.remove();
				
			}
		}.runTaskLater(this, (long) (20*0.6));
	}
	private ProtocolManager protocolManager;
	  
	public void onLoad() {
	    protocolManager = ProtocolLibrary.getProtocolManager();
	}
	@Override
	public void onEnable() {
		if (!avert.exists()) {
			try {
				
				avert.createNewFile();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		 String path = "Nombremaxforcefield";
			     getConfig().addDefault(path, 5);
			     String path2 = "Nombremaxfly";
			    
				    getConfig().addDefault(path2, 3);
				    String path3 = "ForceFieldKickMessage";
				    getConfig().addDefault(path3, "&4Tu as ete kick pour Forcefield");
				    String path4 = "ForceFieldBanMessage";
				    getConfig().addDefault(path4, "&4Tu as ete banni pour Forcefield");
				    String path5 = "FlyKickMessage";
				    getConfig().addDefault(path5, "&4Tu as ete kick pour Fly");
				    String path6 = "FlyBanMessage";
				    getConfig().addDefault(path6, "&4Tu as ete banni pour Fly");
				    String path7 = "disbaledworlds";
				    getConfig().addDefault(path7, Arrays.asList("Worldtest"));
				    
			   getConfig().options().copyDefaults(true);
			    saveConfig();
	BukkitTask task1 = new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Player online : Bukkit.getOnlinePlayers()){
					
				
				World world = online.getWorld();
				Location loc = online.getLocation();
				
				int direction = (int)loc.getYaw();
				 
				if(direction < 0) {
				    direction += 360;
				    direction = (direction + 45) / 90;
				}else {
				    direction = (direction + 45) / 90;
				}
				 
				switch (direction) {
				    case 1:
				        loc = new Location(world, loc.getX() - 1,loc.getY()-0.75, loc.getZ() );
				        break;
				    case 2:
				    	loc = new Location(world, loc.getX() ,loc.getY()-0.75, loc.getZ() - 1);
				        break;
				    case 3:
				    	loc = new Location(world, loc.getX()- 1,loc.getY()-0.75, loc.getZ() );
				        break;
				    case 4:
				    	loc = new Location(world, loc.getX(),loc.getY()-0.75, loc.getZ() - 1);
				        break;
				    case 0:
				    	loc = new Location(world, loc.getX() ,loc.getY()-0.75, loc.getZ()- 1 );
				        break;
				    default:
				        break;
				}
				
				
				String name = "";
				int id = new Random().nextInt(5000-1000)+1000;
				
				int itemInHandId = online.getItemInHand().getTypeId();
				final NPC npc = new NPC(world, name, id, loc, itemInHandId);
				npca.put(online, npc.id);
				RemoveNPC(npc, online);
				
			}
			
			}
		}.runTaskTimer(this, 0, 20*60*10);
	
		Bukkit.getPluginManager().registerEvents(new EventsClass(this), this);
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY){
            public void onPacketReceiving(PacketEvent event){
                if(event.getPacketType() == PacketType.Play.Client.USE_ENTITY){
                    try {
                    	int entityID = event.getPacket().getIntegers().read(0);
                    	final Player p = event.getPlayer();
   
                    	if(!warning.containsKey(event.getPlayer())){
                    		
                    		PacketContainer packet = event.getPacket();
                    		 
                    		if(entityID == npca.get(p) ){
                    			
                    			
                    			World world = p.getWorld();
                				String name = "";
                				int tmp = (int) ( Math.random() * 2 + 1);
                				int id = new Random().nextInt(5000-1000)+1000;
                				Location location = new Location(p.getWorld(), p.getLocation().getX() + tmp, p.getLocation().getY() + tmp, p.getLocation().getZ() + tmp);
                				int itemInHandId = p.getItemInHand().getTypeId();
                    			 NPC npc = new NPC(world, name, id, location, itemInHandId);;
                    			 
                    			 RemoveNPC2(npc, p);
                            warning.put(p, 1);
                    		}
                    	}else if(warning.get(p) < 5){
                    		
                    		warning.put(p, warning.get(p) + 1);
                    		
                    		World world = p.getWorld();
            				String name = "";
            				int tmp = (int) ( Math.random() * 2 + 1);
            				int id = new Random().nextInt(5000-1000)+1000;
            				Location location = new Location(p.getWorld(), p.getLocation().getX() + tmp, p.getLocation().getY() + tmp, p.getLocation().getZ() + tmp);
            				int itemInHandId = p.getItemInHand().getTypeId();
            				 NPC npc = new NPC(world, name, id, location, itemInHandId);;
            				 RemoveNPC2(npc, p);
                    		
                    	}else if(warning.get(p) == 5){
                    		warning.remove(p);
                    		
                    		kickplayerff(p);
                    	}
                    } catch (Exception e){
                    	
                    }
                }
            }
        });
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		final Player p = (Player) sender;
		
		if(command.getName().equalsIgnoreCase("sw")){
			if(args[0].equalsIgnoreCase("ff")){
				if(args.length == 2){
					try {
						
						
					
					final Player pl = Bukkit.getPlayerExact(args[1]);
					final int id = new Random().nextInt(5000-1000)+1000;
					
					final int itemInHandId = pl.getItemInHand().getTypeId();
					BukkitTask task2 = new BukkitRunnable() {
						
						@Override
						public void run() {
							
								World world = pl.getWorld();
								Location loc = pl.getLocation();
								
								
								int direction = (int)loc.getYaw();
								 
								if(direction < 0) {
								    direction += 360;
								    direction = (direction + 45) / 90;
								}else {
								    direction = (direction + 45) / 90;
								}
								 
									switch (direction) {
							    case 1:
							        loc = new Location(world, loc.getX() - 1,loc.getY()-0.50, loc.getZ() );
							        break;
							    case 2:
							    	loc = new Location(world, loc.getX() ,loc.getY()-0.50, loc.getZ() - 1);
							        break;
							    case 3:
							    	loc = new Location(world, loc.getX()- 1,loc.getY()-0.50, loc.getZ() );
							        break;
							    case 4:
							    	loc = new Location(world, loc.getX(),loc.getY()-0.50, loc.getZ() - 1);
							        break;
							    case 0:
							    	loc = new Location(world, loc.getX() ,loc.getY()-0.5, loc.getZ()- 1 );
							        break;
							    default:
							        break;
							}
								try {
									final NPC npc =  new NPC(world, "", id, loc, itemInHandId);;
									p.sendMessage(ChatColor.GREEN + "Le Bot a spawné");
									npca.put(p, npc.id);
									RemoveNPC(npc, pl);
							} catch (Exception e) {
									
									p.sendMessage(ChatColor.RED + "Une erreur s'est produite ! : " + e.getStackTrace());
								}
							
						}
					}.runTaskLater(this, 20*5);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}else if(args.length == 1){
				
				final String name = "";
				final int id = new Random().nextInt(5000-1000)+1000;
				final Location location = p.getLocation();
				final int itemInHandId = p.getItemInHand().getTypeId();
				
				
			BukkitTask task = new BukkitRunnable() {
				
				@Override
				public void run() {
					for(Player pl : Bukkit.getOnlinePlayers()){
						World world = pl.getWorld();
						Location loc = p.getLocation();
						
						int direction = (int)loc.getYaw();
						 
						if(direction < 0) {
						    direction += 360;
						    direction = (direction + 45) / 90;
						}else {
						    direction = (direction + 45) / 90;
						}
						 
							switch (direction) {
					    case 1:
					        loc = new Location(world, loc.getX() - 1,loc.getY()-0.75, loc.getZ() );
					        break;
					    case 2:
					    	loc = new Location(world, loc.getX() ,loc.getY()-0.75, loc.getZ() - 1);
					        break;
					    case 3:
					    	loc = new Location(world, loc.getX()- 1,loc.getY()-0.75, loc.getZ() );
					        break;
					    case 4:
					    	loc = new Location(world, loc.getX(),loc.getY()-0.75, loc.getZ() - 1);
					        break;
					    case 0:
					    	loc = new Location(world, loc.getX() ,loc.getY()-0.75, loc.getZ()- 1 );
					        break;
					    default:
					        break;
					}
						try {
							final NPC npc =  new NPC(world, "", id, loc, itemInHandId);;
							p.sendMessage(ChatColor.GREEN + "Le Bot a spawné");
							npca.put(p, npc.id);
							RemoveNPC(npc, pl);
					} catch (Exception e) {
							
							p.sendMessage(ChatColor.RED + "Une erreur s'est produite ! : " + e.getStackTrace());
						}
						
						
						
					}
					
					
					
				}
			}.runTaskLater(this, 20*5);
				}else{
					p.sendMessage(ChatColor.RED + "Il vous manque des arguments !");
				}
			return true;
				
			}else if(args[0].equalsIgnoreCase("reset")){
				if(args.length >=2){
					
					Player pl = Bukkit.getPlayerExact(args[1]);
					OfflinePlayer disconnected = Bukkit.getOfflinePlayer(args[1]);
					if(pl != null){
					if(loadavert.get(pl.getUniqueId() + "") != null){
						loadavert.set(pl.getUniqueId() + ".avertff", 0);
						loadavert.set(pl.getUniqueId() + ".avertfly", 0);
						try {
							loadavert.save(avert);
							p.sendMessage(ChatColor.GREEN + "Les avertissements de " + ChatColor.GOLD + pl.getName()+ ChatColor.GREEN + " ont été remis à zéro !");
					
						} catch (IOException e) {
							// TODO Auto-generated catch block
							p.sendRawMessage("Error :" + e.getStackTrace()) ;
						}
					}
					}else{
						if(loadavert.get(disconnected.getUniqueId() + "") != null){
							loadavert.set(disconnected.getUniqueId() + ".avertff", 0);
							loadavert.set(disconnected.getUniqueId() + ".avertfly", 0);
							try {
								loadavert.save(avert);
								p.sendMessage(ChatColor.GREEN + "Les avertissements de " + ChatColor.GOLD + disconnected.getName()+ ChatColor.GREEN + " ont été remis à zéro !");
						
							} catch (IOException e) {
								// TODO Auto-generated catch block
								p.sendRawMessage("Error :" + e.getStackTrace()) ;
							}
						}
					}
					}
				
			}else
				p.sendMessage(ChatColor.RED + "Il vous manque des arguments !");
				return true;
			
		}
		
		return false;
}
	}

