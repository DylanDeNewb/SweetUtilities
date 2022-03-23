package fun.sweetsmp.sweetutilities.inspects.commands;

import cc.newbs.commandapi.annotation.Command;
import cc.newbs.commandapi.annotation.Require;
import cc.newbs.commandapi.annotation.Sender;
import fun.sweetsmp.sweetutilities.SweetUtilities;
import fun.sweetsmp.sweetutilities.inspects.menus.InspectMinersMenu;
import fun.sweetsmp.sweetutilities.inspects.menus.InspectPlayersMenu;
import fun.sweetsmp.sweetutilities.utils.ChatUtils;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.entity.Player;

public class InspectMenuCommand {

    private SweetUtilities core = SweetUtilities.getInstance();

    @Command(name="players", aliases = {}, desc="Open Players Menu")
    @Require("sweetutilities.inspect")
    public void players(@Sender Player player) throws MenuManagerNotSetupException, MenuManagerException {
        MenuManager.openMenu(InspectPlayersMenu.class, player);
    }

    @Command(name="miners", aliases = {}, desc="Open Miners Menu")
    @Require("sweetutilities.inspect")
    public void miners(@Sender Player player) throws MenuManagerNotSetupException, MenuManagerException {
        MenuManager.openMenu(InspectMinersMenu.class, player);
    }
}
