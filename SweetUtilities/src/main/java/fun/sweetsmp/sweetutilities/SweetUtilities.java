package fun.sweetsmp.sweetutilities;

import cc.newbs.commandapi.CommandAPI;
import cc.newbs.commandapi.CommandService;
import fun.sweetsmp.sweetutilities.greetings.GreetingManager;
import fun.sweetsmp.sweetutilities.staff.RankManager;
import fun.sweetsmp.sweetutilities.utils.ChatUtils;
import fun.sweetsmp.sweetutilities.utils.FileUtils;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "SweetUtilities", version = "0.1.1")
@Description("Utility commands for SweetSMP")
@SoftDependency("LuckPerms")
@Author("DylanDeNewb")
@ApiVersion(ApiVersion.Target.v1_18)
public final class SweetUtilities extends JavaPlugin {

    private static SweetUtilities instance;
    private CommandService commandService;

    public GreetingManager greetingManager;
    public RankManager rankManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        this.commandService = CommandAPI.get(this);

        this.greetingManager = new GreetingManager(this);
        this.greetingManager.load();

        this.rankManager = new RankManager(this);
        this.rankManager.load();

        this.commandService.registerCommands();

        MenuManager.setup(getServer(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SweetUtilities getInstance() {
        return instance;
    }

    public CommandService getCommandService() {
        return commandService;
    }

    public GreetingManager getGreetingManager() {
        return greetingManager;
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public void log(String message){
        Bukkit.getConsoleSender().sendMessage(ChatUtils.translate(message));
    }
}
