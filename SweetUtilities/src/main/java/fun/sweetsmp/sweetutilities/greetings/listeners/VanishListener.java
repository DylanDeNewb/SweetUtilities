package fun.sweetsmp.sweetutilities.greetings.listeners;

import de.myzelyam.api.vanish.PlayerVanishStateChangeEvent;
import de.myzelyam.api.vanish.VanishAPI;
import fun.sweetsmp.sweetutilities.greetings.GreetingManager;
import fun.sweetsmp.sweetutilities.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class VanishListener implements Listener {

    GreetingManager manager;
    public VanishListener(GreetingManager manager){
        this.manager = manager;
    }

    @EventHandler(priority= EventPriority.HIGHEST)
    public void onVanish(PlayerVanishStateChangeEvent event){
        Player player = Bukkit.getPlayer(event.getUUID());
        boolean state = event.isVanishing();

        String string = null;
        if(state){
            string = manager.getRandomMessage(manager.getLeaveMessages());
        }else{
            string = manager.getRandomMessage(manager.getJoinMessages());
        }

        string = manager.replacePlayer(string, player);
        string = manager.replaceDisplayName(string, player);

        if(event.isCancelled()){
            return;
        }

        if(state){
            broadcast(ChatUtils.translate("&c&l( ! ) &8► &r") + string);
        }else{
            broadcast(ChatUtils.translate("&c&l( ! ) &8► &r") + string);
        }
    }

    private void broadcast(String msg){
        for(Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(msg);
        }
    }
}
