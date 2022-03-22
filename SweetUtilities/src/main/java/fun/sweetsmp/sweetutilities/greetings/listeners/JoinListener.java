package fun.sweetsmp.sweetutilities.greetings.listeners;

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

    @EventHandler()
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String string = manager.getRandomMessage(manager.getJoinMessages());
        string = manager.replace(string, player);

        event.setJoinMessage(ChatUtils.translate("&a&l( ! ) &8► &r") + string);
    }
}
