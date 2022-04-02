package fun.sweetsmp.sweetutilities.greetings.listeners;

import de.myzelyam.api.vanish.VanishAPI;
import fun.sweetsmp.sweetutilities.greetings.GreetingManager;
import fun.sweetsmp.sweetutilities.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    GreetingManager manager;
    public JoinListener(GreetingManager manager){
        this.manager = manager;
    }

    @EventHandler(priority= EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String string = manager.getRandomMessage(manager.getJoinMessages());
        string = manager.replacePlayer(string, player);
        string = manager.replaceDisplayName(string, player);

        if(manager.isVanishEnabled()){
            if(VanishAPI.isInvisible(player)){
                return;
            }
        }

        event.setJoinMessage(ChatUtils.translate("&a&l( ! ) &8► &r") + string);
    }
}
