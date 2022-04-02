package fun.sweetsmp.sweetutilities.quotes.commands;

import cc.newbs.commandapi.annotation.Command;
import cc.newbs.commandapi.annotation.Require;
import cc.newbs.commandapi.annotation.Sender;
import fun.sweetsmp.sweetutilities.SweetUtilities;
import fun.sweetsmp.sweetutilities.quotes.Quote;
import fun.sweetsmp.sweetutilities.utils.ChatUtils;
import org.bukkit.entity.Player;

import java.util.List;

public class QuoteCommand {

    private SweetUtilities core;
    public QuoteCommand(SweetUtilities core){
        this.core = core;
    }

    @Command(name = "", aliases = {"",""}, desc = "Send a quote for a staff rank")
    @Require("sweetutilities.quote")
    public void root(@Sender Player player, String group){
        List<Quote> quotes = core.getQuoteManager().getQuotesByGroup(group);
        if(quotes == null || quotes.isEmpty()){
            player.sendMessage(ChatUtils.translate("&cThere are no quotes for this group."));
            return;
        }

        String format = core.getQuoteManager().getFormat();
        format = format.replace("%quote%", core.getQuoteManager().getRandomQuote(quotes));

        player.sendMessage(format);
    }
}
