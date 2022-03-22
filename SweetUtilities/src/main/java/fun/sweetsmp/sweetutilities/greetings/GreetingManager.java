package fun.sweetsmp.sweetutilities.greetings;

import fun.sweetsmp.sweetutilities.SweetUtilities;
import fun.sweetsmp.sweetutilities.api.Manager;
import fun.sweetsmp.sweetutilities.greetings.listeners.JoinListener;
import fun.sweetsmp.sweetutilities.greetings.listeners.LeaveListener;
import fun.sweetsmp.sweetutilities.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GreetingManager extends Manager {

    List<String> joinMessages;
    List<String> leaveMessages;
    public GreetingManager(SweetUtilities core) {
        super(core);
    }

    public void load(){
        YamlConfiguration config = createFile("greetings").getAsYaml();
        this.joinMessages = new ArrayList<>();
        this.leaveMessages = new ArrayList<>();

        getCore().log(" ---> Loading Greetings");
        getCore().log("Join:");
        for(String string : config.getStringList("greetings.join")){
            getCore().log(" · " + ChatUtils.translate(string));
            joinMessages.add(ChatUtils.translate(string));
        }

        getCore().log("Leave:");
        for(String string : config.getStringList("greetings.leave")){
            getCore().log(" · " + ChatUtils.translate(string));
            leaveMessages.add(ChatUtils.translate(string));
        }

        getCore().log("» Successfully loaded Greetings");
    }

    @Override
    public void loadListeners() {
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), getCore());
        Bukkit.getPluginManager().registerEvents(new LeaveListener(this), getCore());
    }

    public String replacePlayer(String string, Player player){
        return string.replace("%player%", player.getName());
    }

    public String replaceDisplayName(String string, Player player) { return string.replace("%displayname%", player.getDisplayName()); }

    public String getRandomMessage(List<String> strings){
        Random random = new Random();
        return strings.get(random.nextInt(strings.size()));
    }

    public List<String> getJoinMessages() {
        return joinMessages;
    }

    public List<String> getLeaveMessages() {
        return leaveMessages;
    }
}
