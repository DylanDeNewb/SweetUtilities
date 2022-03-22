package fun.sweetsmp.sweetutilities;

import cc.newbs.commandapi.CommandService;
import fun.sweetsmp.sweetutilities.greetings.GreetingManager;
import fun.sweetsmp.sweetutilities.utils.ChatUtils;
import fun.sweetsmp.sweetutilities.utils.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "SweetUtilities", version = "0.1.0")
@Description("Utility commands for SweetSMP")
@Author("DylanDeNewb")
@ApiVersion(ApiVersion.Target.v1_18)
public final class SweetUtilities extends JavaPlugin {

    private static SweetUtilities instance;
    private CommandService commandService;

    public GreetingManager greetingManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        this.greetingManager = new GreetingManager(this);
        this.greetingManager.load();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SweetUtilities getInstance() {
        return instance;
    }

    public void log(String message){
        Bukkit.getConsoleSender().sendMessage(ChatUtils.translate(message));
    }
}
