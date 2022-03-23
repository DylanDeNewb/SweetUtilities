package fun.sweetsmp.sweetutilities.ranks;

import fun.sweetsmp.sweetutilities.SweetUtilities;
import fun.sweetsmp.sweetutilities.api.Manager;
import fun.sweetsmp.sweetutilities.ranks.commands.RankMenuCommand;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.track.Track;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class RankManager extends Manager {

    private LuckPerms luckPerms;
    private Track track;
    public RankManager(SweetUtilities core) {
        super(core);
    }

    public void load(){
        if(!Bukkit.getPluginManager().isPluginEnabled("LuckPerms")){
            Bukkit.getPluginManager().disablePlugin(getCore());
            return;
        }

        this.luckPerms = LuckPermsProvider.get();

        //START

        YamlConfiguration config = createFile("ranks").getAsYaml();
        getCore().log(" ---> Loading Track");

        this.luckPerms.getTrackManager().loadTrack(config.getString("track"))
                .thenAccept(track -> {
                    if(!track.isPresent()){
                        this.track = null;
                        return;
                    }
                    this.track = track.get();
                    getCore().log(" · Track: " + this.track.getName());
                    getCore().log("» Successfully loaded " + this.track.getName());
                })
                .exceptionally(throwable -> null);
    }

    @Override
    public void loadCommands() {
        getCore().getCommandService().register(new RankMenuCommand(), "rank", "promote", "demote");
    }

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }

    public Track getTrack() {
        return track;
    }
}
