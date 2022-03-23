package fun.sweetsmp.sweetutilities.staff.menus;

import fun.sweetsmp.sweetutilities.SweetUtilities;
import fun.sweetsmp.sweetutilities.utils.ChatUtils;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.heads.SkullCreator;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RankMainMenu extends Menu {

//    private final int[] slots = {10,11,12,13,14,15,16};
    private final int[] placeholders = {0,1,2,3,4,5,6,7,8,9,17,18,19,20,21,22,23,24,25,26};

    private final SweetUtilities core = SweetUtilities.getInstance();
    private User targetUser;

    public RankMainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Rank Manager - " + playerMenuUtility.getData("target");
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
            case "promote":
                MenuManager.openMenu(RankPromoteMenu.class, p);
                break;
            case "demote":
                MenuManager.openMenu(RankDemoteMenu.class, p);
                break;
        }
    }

    @Override
    public void setMenuItems() {

        ItemStack placeholder = makeItem(Material.GRAY_STAINED_GLASS_PANE, ChatUtils.translate("&7"));

        for(int i : placeholders){
            inventory.setItem(i,placeholder);
        }

        ItemStack plus = getPromotionItem();
        ItemStack minus = getDemotionItem();

        inventory.setItem(12, plus);
        inventory.setItem(14, minus);
    }

    private ItemStack getPromotionItem(){
        //Plush +
        ItemStack item = SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/32332b770a4874698862855da5b3fe47f19ab291df766b6083b5f9a0c3c6847e");
        ItemMeta meta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(ChatUtils.translate("&7Choose this option to &6promote&6,"));
        lore.add(ChatUtils.translate("&7up the staff track..."));
        lore.add(ChatUtils.translate(""));

        List<Group> groupsPromote = (List<Group>)playerMenuUtility.getData("groupsPromote");

        if(groupsPromote.isEmpty()){
            lore.add(ChatUtils.translate("&d➡ &fMax Rank Achieved"));
        }else{
            for(Group group : groupsPromote){
                lore.add(ChatUtils.translate("&d➡ &f" + group.getName()));
            }
        }

        lore.add(ChatUtils.translate(""));
        lore.add(ChatUtils.translate(" &d» Click to promote..."));

        meta.setLore(lore);
        meta.setDisplayName(ChatUtils.translate("&dPromote"));
        item.setItemMeta(meta);

        return NBTEditor.set(item, "promote", "action");
    }

    private ItemStack getDemotionItem(){
        //Plush -
        ItemStack item = SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/32cbdc9d4c590eac285a4544f2b1e068bd27fd52173ac8d7679013823cbab95a");
        ItemMeta meta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(ChatUtils.translate("&7Choose this option to &6demote&6,"));
        lore.add(ChatUtils.translate("&7down the staff track..."));
        lore.add(ChatUtils.translate(""));

        List<Group> groupsDemote = (List<Group>)playerMenuUtility.getData("groupsDemote");

        if(groupsDemote.isEmpty()){
            lore.add(ChatUtils.translate("&d➡ &fMember"));
        }else{
            for(Group group : groupsDemote){
                lore.add(ChatUtils.translate("&d➡ &f" + group.getName()));
            }
        }

        lore.add(ChatUtils.translate(""));
        lore.add(ChatUtils.translate(" &d» Click to promote..."));

        meta.setLore(lore);
        meta.setDisplayName(ChatUtils.translate("&dDemote"));
        item.setItemMeta(meta);

        return NBTEditor.set(item, "demote", "action");
    }
}
