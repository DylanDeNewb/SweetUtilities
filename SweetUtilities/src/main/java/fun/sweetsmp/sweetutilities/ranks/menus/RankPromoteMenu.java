package fun.sweetsmp.sweetutilities.ranks.menus;

import fun.sweetsmp.sweetutilities.SweetUtilities;
import fun.sweetsmp.sweetutilities.utils.ChatUtils;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.group.GroupManager;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class RankPromoteMenu extends Menu {

    private final List<Integer> slots = new ArrayList<>();
    private final int[] placeholders = {0,1,2,3,4,5,6,7,8,9,17,18,19,20,21,22,23,24,25,26};

    private final SweetUtilities core = SweetUtilities.getInstance();
    List<Group> groupsPromote = (List<Group>)playerMenuUtility.getData("groupsPromote");
    private User targetUser;

    public RankPromoteMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        IntStream.range(10, 17).forEachOrdered(slots::add);
    }

    @Override
    public String getMenuName() {
        return "Promote - " + playerMenuUtility.getData("target");
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent inventoryClickEvent) throws MenuManagerNotSetupException, MenuManagerException {
        ItemStack item = inventoryClickEvent.getCurrentItem();
        if(!NBTEditor.contains(item, "action")){
            return;
        }

        switch(NBTEditor.getString(item, "action")){
            case "return":
                MenuManager.openMenu(RankMainMenu.class, p);
                break;
            case "promote":
                promote(NBTEditor.getString(item, "group"));
                break;
        }
    }

    private void promote(String groupString){
        Group group = null;
        for(Group groups : groupsPromote){
            if(groups.getName().equalsIgnoreCase(groupString)) {
                group = groups;
                break;
            }
        }

        User user = (User) playerMenuUtility.getData("targetUser");
        GroupManager groupManager = core.getRankManager().getLuckPerms().getGroupManager();
        Group maingroup = groupManager.getGroup(user.getPrimaryGroup());
        if(maingroup == null){
            return;
        }

        if(core.getRankManager().getTrack().containsGroup(maingroup)){
            //If users primary group is on staff track

            DataMutateResult result = user.data().remove(Node.builder("group." + maingroup.getName()).build());
            if(!result.wasSuccessful()){
                p.sendMessage(ChatUtils.translate("&c&l( ! ) &8► &7Failed to &c&npromote&7 target"));
                return;
            }
        }


        DataMutateResult result = user.data().add(Node.builder("group." + group.getName()).build());
        if(!result.wasSuccessful()){
            p.sendMessage(ChatUtils.translate("&c&l( ! ) &8► &7Failed to &c&npromote&7 target to &c&n" + group.getName()));
            return;
        }

        Group finalGroup = group;
        core.getRankManager().getLuckPerms().getUserManager().saveUser(user).thenAcceptAsync(unused -> {
            p.sendMessage(ChatUtils.translate("&a&l( ! ) &8► &7Successfully &a&npromoted&7 target to &a&n" + finalGroup.getName()));
        });
        p.closeInventory();
    }

    @Override
    public void setMenuItems() {

        ItemStack placeholder = makeItem(Material.GRAY_STAINED_GLASS_PANE, ChatUtils.translate("&7"));
        ItemStack back = NBTEditor.set(makeItem(Material.RED_STAINED_GLASS_PANE, ChatUtils.translate("&cReturn")), "return", "action");

        for(int i : placeholders){
            inventory.setItem(i,placeholder);
        }

        inventory.setItem(9, back);

        int index = slots.get(0);

        for(Group group : groupsPromote){
            if(index > slots.get(slots.size()-1)){
                break;
            }

            ItemStack item = getPromotionItem(group);
            inventory.setItem(index, item);

            index++;
        }
    }

    private ItemStack getPromotionItem(Group group){
        //Plush +
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(ChatUtils.translate("&7Choose this option to &6promote&6,"));
        if(group.getCachedData().getMetaData().getPrefix() != null){
            lore.add(ChatUtils.translate("&7to &f" + group.getCachedData().getMetaData().getPrefix() + " &frank!"));
        }else{
            lore.add(ChatUtils.translate("&7to &f" + group.getName() + " &frank!"));
        }

        lore.add(ChatUtils.translate(""));
        lore.add(ChatUtils.translate(" &d» Click to promote..."));

        meta.setLore(lore);
        meta.setDisplayName(ChatUtils.translate("&d" + StringUtils.capitalize(group.getName())));
        item.setItemMeta(meta);

        item = NBTEditor.set(item, group.getName(), "group");
        item = NBTEditor.set(item, "promote", "action");
        return item;
    }

}
