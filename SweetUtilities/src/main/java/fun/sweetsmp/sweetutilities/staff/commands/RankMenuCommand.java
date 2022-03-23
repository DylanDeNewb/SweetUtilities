package fun.sweetsmp.sweetutilities.staff.commands;

import cc.newbs.commandapi.annotation.Command;
import cc.newbs.commandapi.annotation.Require;
import cc.newbs.commandapi.annotation.Sender;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fun.sweetsmp.sweetutilities.SweetUtilities;
import fun.sweetsmp.sweetutilities.staff.menus.RankMainMenu;
import fun.sweetsmp.sweetutilities.staff.misc.UUIDTypeAdapter;
import fun.sweetsmp.sweetutilities.utils.ChatUtils;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

public class RankMenuCommand {

    private SweetUtilities core = SweetUtilities.getInstance();

    @Command(name="", aliases = {}, desc="Open Rank Management")
    @Require("sweetutilities.rank")
    public void root(@Sender Player player, String target) throws MenuManagerNotSetupException, MenuManagerException {
        if(target == null){
            player.sendMessage(ChatUtils.translate("&c&l( ! ) &8► &7You must provide a &c&ntarget"));
            return;
        }

//        PlayerMenuUtility pmu = MenuManager.getPlayerMenuUtility(player);
//        pmu.setData("target", target);

        mojangRequest(target, uuid -> {
            if(uuid == null || !Bukkit.getOfflinePlayer(uuid).hasPlayedBefore()){
                player.sendMessage(ChatUtils.translate("&c&l( ! ) &8► &7You must provide a &c&nvalid&7 target"));
                return;
            }
            User targetUser = core.getRankManager().getLuckPerms().getUserManager().getUser(uuid);

            try {
                PlayerMenuUtility pmu = MenuManager.getPlayerMenuUtility(player);
                pmu.setData("target", targetUser.getUsername());
                pmu.setData("targetUser", targetUser);

                List<String> groupStrings = core.getRankManager().getTrack().getGroups();
                List<Group> groupsPromote = new ArrayList<>();
                List<Group> groupsDemote = new ArrayList<>();
                for(String string : groupStrings){
                    core.getRankManager().getLuckPerms().getGroupManager().loadGroup(string)
                            .thenAccept(groupOpt -> {
                                if(!groupOpt.isPresent()){
                                    return;
                                }

                                Group group = groupOpt.get();
                                if(!group.getWeight().isPresent()){
                                    return;
                                }

                                String mainString = targetUser.getPrimaryGroup();

                                Group main = core.getRankManager().getLuckPerms().getGroupManager().getGroup(mainString);
                                if(!main.getWeight().isPresent()){
                                    return;
                                }

                                if(main.getWeight().getAsInt() < group.getWeight().getAsInt() && !player.hasPermission("sweetutilities.rank.admin")){
                                    return;
                                }

                                if(group.getWeight().getAsInt() > main.getWeight().getAsInt()){
                                    groupsPromote.add(group);
                                }else if(group.getWeight().getAsInt() < main.getWeight().getAsInt()){
                                    groupsDemote.add(group);
                                }
                            })
                            .exceptionally(throwable -> null);
                }

                Bukkit.getScheduler().runTaskLater(core, () -> {
                    Collections.sort(groupsPromote, Comparator.comparingInt(o -> o.getWeight().getAsInt()));
                    Collections.sort(groupsDemote, Comparator.comparingInt(o -> o.getWeight().getAsInt()));

                    pmu.setData("groupsPromote", groupsPromote);
                    pmu.setData("groupsDemote", groupsDemote);

                    try {
                        MenuManager.openMenu(RankMainMenu.class, player);
                    } catch (MenuManagerException e) {
                        e.printStackTrace();
                    } catch (MenuManagerNotSetupException e) {
                        e.printStackTrace();
                    }
                }, 20);
            } catch (MenuManagerException e) {
                e.printStackTrace();
            } catch (MenuManagerNotSetupException e) {
                e.printStackTrace();
            }
        });

    }

    private final String MOJANG_URL = "https://api.mojang.com/users/profiles/minecraft/%s";
    private Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();

    private void mojangRequest(String name, Consumer<UUID> onComplete) {

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(String.format(MOJANG_URL, name)).openConnection();
            connection.setReadTimeout(5000);
            MojangProfile profile = gson.fromJson(new BufferedReader(new InputStreamReader(connection.getInputStream())), MojangProfile.class);

            onComplete.accept(profile.id);

        } catch (Exception e) {
            e.printStackTrace();
            onComplete.accept(null);
        }

    }

    private class MojangProfile {
        String name;
        UUID id;
    }

}
