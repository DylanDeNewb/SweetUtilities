package fun.sweetsmp.sweetutilities.inspects.menus;

import fun.sweetsmp.sweetutilities.SweetUtilities;
import fun.sweetsmp.sweetutilities.utils.ChatUtils;
import fun.sweetsmp.sweetutilities.utils.Pagination;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.heads.SkullCreator;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InspectPlayersMenu extends Menu {

    private final int[] placeholders = {1,2,3,5,6,7};
    List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
    Pagination<ItemStack> pagination;
    List<ItemStack> playerItems = new ArrayList<>();

    private final SweetUtilities core = SweetUtilities.getInstance();

    private int start = 9;
    private int end = 35;

    private int index = start;

    private int page = 0;

    public InspectPlayersMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);


        for(Player player : players){
            ItemStack item = getHeadItem(player);
            playerItems.add(item);
        }

        pagination = new Pagination<>(end-start, playerItems);
    }

    @Override
    public String getMenuName() {
        return "Inspect - Players";
    }

    @Override
    public int getSlots() {
        return 36;
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
            case "previous":
                if(page == 0){ return; }
                else {
                    page--;
                    reloadItems();
                }
                break;
            case "next":
                if(!pagination.exists(page+1)) { return; }
                else{
                    page++;
                    reloadItems();
                }
                break;
            case "random":
                core.getInspectManager().teleport(p, getRandomItem());
                break;
            case "head":
                core.getInspectManager().teleport(p, item);
                break;
        }
    }

    private ItemStack getRandomItem() {
        Random random = new Random();
        return playerItems.get(random.nextInt(playerItems.size()));
    }

    @Override
    public void setMenuItems() {
        ItemStack placeholder = makeItem(Material.GRAY_STAINED_GLASS_PANE, ChatUtils.translate("&7"));

        for(int i : placeholders){
            inventory.setItem(i,placeholder);
        }

        ItemStack prev = NBTEditor.set(makeItem(Material.LEVER, ChatUtils.translate("&cPrevious")), "previous", "action");
        ItemStack next = NBTEditor.set(makeItem(Material.LEVER, ChatUtils.translate("&cNext")), "next", "action");
        ItemStack random = NBTEditor.set(makeItem(Material.PAPER, ChatUtils.translate("&dRandom Player")), "random", "action");

        inventory.setItem(0, prev);
        inventory.setItem(8, next);
        inventory.setItem(4, random);

        if(!pagination.exists(page)){ return; }
        List<ItemStack> items = pagination.getPage(page);

        for(ItemStack item : items){
            inventory.setItem(index, item);
            index++;
        }

        index = start;
    }

    private ItemStack getHeadItem(Player player){
        ItemStack item = SkullCreator.itemFromUuid(player.getUniqueId());
        ItemMeta meta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        Location location = player.getLocation();
        lore.add(ChatUtils.translate("&7Location: (&f" + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ() + "&7)"));
        lore.add(ChatUtils.translate(""));
        lore.add(ChatUtils.translate(" &d» Click to teleport..."));

        meta.setLore(lore);
        meta.setDisplayName(ChatUtils.translate("&d" + player.getName()));

        item.setItemMeta(meta);

        item = NBTEditor.set(item, player.getName(), "player");
        item = NBTEditor.set(item, location.getBlockX(), "locX");
        item = NBTEditor.set(item, location.getBlockY(), "locY");
        item = NBTEditor.set(item, location.getBlockZ(), "locZ");
        item = NBTEditor.set(item, location.getWorld().getName(), "locW");
        item = NBTEditor.set(item, "head", "action");

        return item;
    }
}
