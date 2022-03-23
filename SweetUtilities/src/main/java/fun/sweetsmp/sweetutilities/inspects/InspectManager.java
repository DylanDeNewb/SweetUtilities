package fun.sweetsmp.sweetutilities.inspects;

import fun.sweetsmp.sweetutilities.SweetUtilities;
import fun.sweetsmp.sweetutilities.api.Manager;
import fun.sweetsmp.sweetutilities.inspects.commands.InspectMenuCommand;
import fun.sweetsmp.sweetutilities.ranks.commands.RankMenuCommand;
import fun.sweetsmp.sweetutilities.utils.ChatUtils;
import fun.sweetsmp.sweetutilities.utils.FileUtils;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InspectManager extends Manager {

    YamlConfiguration config;

    public InspectManager(SweetUtilities core) {
        super(core);
    }

    public void load(){
        config = createFile("inspect").getAsYaml();
    }

    @Override
    public void loadCommands() {
        getCore().getCommandService().register(new InspectMenuCommand(), "inspect");
    }

    public void teleport(Player player, ItemStack item){
        int x = NBTEditor.getInt(item, "locX");
        int y = NBTEditor.getInt(item, "locY");
        int z = NBTEditor.getInt(item, "locZ");
        World world = Bukkit.getWorld(NBTEditor.getString(item, "locW"));
        String name = NBTEditor.getString(item, "player");

        Location loc = new Location(world, x, y, z);
        player.teleport(loc);

        player.sendMessage(ChatUtils.translate("&a&l( ! ) &8► &7Successfully &a&nteleported&7 to &a&n" + name));
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
