package be.seveningful.sw;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class EventsClass implements Listener {
	public static SevsWarden plugin;
	ArrayList<Player> immunite = new ArrayList<Player>();
	public EventsClass(SevsWarden sevsWarden) {
	this.plugin = sevsWarden;
	}
	public void kickplayerfly(final Player p){
BukkitTask task1 = new BukkitRunnable() {
			
			@Override
			public void run() {
				if(plugin.loadavert.getInt(p.getUniqueId() + ".avertfly") < plugin.getConfig().getInt("Nombremaxfly") ){
					String Ban = plugin.getConfig().getString("FlyKickMessage");
					String BanMessage = ChatColor.translateAlternateColorCodes('&', Ban);
					Bukkit.broadcastMessage(plugin.SevsWarden + ChatColor.RED + ChatColor.BOLD.toString() + p.getName() + ChatColor.GRAY + " a été éjecté pour : " + ChatColor.YELLOW + "Fly");
					for(Player online : Bukkit.getOnlinePlayers()){
						online.playSound(p.getLocation(), Sound.WITHER_SHOOT, 10, -10);
					}
					p.kickPlayer(BanMessage);
				plugin.loadavert.set(p.getUniqueId() + ".avertfly", (plugin.loadavert.getInt(p.getUniqueId() +".avertfly") + 1));
				try {
					plugin.loadavert.save(plugin.avert);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
			}else{
				String Ban = plugin.getConfig().getString("FlyBanMessage");
				String BanMessage = ChatColor.translateAlternateColorCodes('&', Ban);
				Bukkit.broadcastMessage(plugin.SevsWarden + ChatColor.RED + ChatColor.BOLD.toString() + p.getName() + ChatColor.GRAY + " a été banni pour : " + ChatColor.YELLOW + "Fly");
				for(Player online : Bukkit.getOnlinePlayers()){
					online.playSound(p.getLocation(), Sound.WITHER_DEATH, 10, -10);
				}
				p.kickPlayer(BanMessage);
				p.setBanned(true);
			}
			}
		}.runTaskLater(plugin, 1);
	}
	

	@EventHandler
	public void AttackFakeplayer(PlayerMoveEvent e){
		final Player p = e.getPlayer();
		if( !plugin.getConfig().getStringList("disbaledworlds").contains(p.getWorld().getName()))
		if(!p.hasPermission("sw.immune")){
		Location to = e.getTo();
		Location from = e.getFrom();
		
		 Vector vec = new Vector(to.getX(), to.getY(), to.getZ());
		 
		 double i = vec.distance(new Vector(from.getX(), from.getY(), from.getZ()));
         if(p.getGameMode().equals(GameMode.SURVIVAL) ){

           if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE){
        	   immunite.add(p);
        	   BukkitTask task = new BukkitRunnable() {
				
				@Override
				public void run() {
					immunite.remove(p);
					
				}
			}.runTaskLater(plugin, 20*10);
           }else
            if(p.getFallDistance() == 0 && !immunite.contains(p)){
             if(i > 2.1){
            	 kickplayerfly(p);
                 e.setCancelled(true);
            
             }
            }
         }
		
		/*if(p.getGameMode().equals(GameMode.SURVIVAL) && p.getVelocity().getEpsilon() > 3.9 ){
			
			
		}else{
			Bukkit.broadcastMessage("" + p.getWalkSpeed());
		}*/
		
	}
	}

}
