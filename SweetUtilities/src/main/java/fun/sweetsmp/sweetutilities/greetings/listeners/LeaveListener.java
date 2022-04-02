package fun.sweetsmp.sweetutilities.greetings.listeners;

import de.myzelyam.api.vanish.VanishAPI;
import fun.sweetsmp.sweetutilities.greetings.GreetingManager;
import fun.sweetsmp.sweetutilities.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {

    GreetingManager manager;
    public LeaveListener(GreetingManager manager){
        this.manager = manager;
    }

    @EventHandler(priority= EventPriority.HIGHEST)
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        String string = manager.getRandomMessage(manager.getLeaveMessages());
        string = manager.replacePlayer(string, player);
        string = manager.replaceDisplayName(string, player);

        if(manager.isVanishEnabled()){
            if(VanishAPI.isInvisibleOffline(player.getUniqueId())){
                return;
            }
        }

        event.setQuitMessage(ChatUtils.translate("&c&l( ! ) &8► &r") + string);
    }

}
