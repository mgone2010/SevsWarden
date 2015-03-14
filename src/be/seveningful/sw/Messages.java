package be.seveningful.sw;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messages {

	public static void sendMessage(Player p, String type ){
		switch (type){
		case "help":
			p.sendMessage(ChatColor.GRAY + "---------------" + ChatColor.YELLOW + " SevsWarden " + ChatColor.GRAY + "---------------"  );
			p.sendMessage(ChatColor.YELLOW + "/sw - " + ChatColor.GRAY + " Commande pricipale"  );
			p.sendMessage(ChatColor.YELLOW + "/sw ff <joueur> - " + ChatColor.GRAY + " Permet de forcer le check de forcefield sur un joueur"  );
			p.sendMessage(ChatColor.YELLOW + "/sw reset <joueur> - " + ChatColor.GRAY + " Permet de réinitialiser les avertissements d'un joueur"  );
			p.sendMessage(ChatColor.GRAY + "------------------------------------------"  );
			break;
		default:
			
		}
	}
	
}
