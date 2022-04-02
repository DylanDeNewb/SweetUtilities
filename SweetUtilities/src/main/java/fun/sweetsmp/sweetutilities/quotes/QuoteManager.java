package fun.sweetsmp.sweetutilities.quotes;

import fun.sweetsmp.sweetutilities.SweetUtilities;
import fun.sweetsmp.sweetutilities.api.Manager;
import fun.sweetsmp.sweetutilities.quotes.commands.QuoteCommand;
import fun.sweetsmp.sweetutilities.utils.ChatUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuoteManager extends Manager {

    private boolean enabled = false;
    private String format;
    private List<Quote> quotes;

    public QuoteManager(SweetUtilities core) {
        super(core);
    }

    @Override
    public void loadCommands() {
        getCore().getCommandService().register(new QuoteCommand(getCore()), "quote");
    }

    public void load(){
        YamlConfiguration config = createFile("greetings").getAsYaml();
        enabled = config.getBoolean("quotes.enabled");
        if(!enabled){
            return;
        }

        getCore().log(" ---> Loading Quotes");
        getCore().log("Format:");
        format = ChatUtils.translate(config.getString("quotes.format"));
        getCore().log(" · " + format);

        for(String group : config.getConfigurationSection("quotes.options").getKeys(false)){
            getCore().log(group + ":");
            for(String quote : config.getConfigurationSection("quotes.options." + group).getKeys(false)){
                Quote q = new Quote(group, ChatUtils.translate(quote));
                quotes.add(q);
                getCore().log(" · " + q.getQuote());
            }
        }
    }

    public String getRandomQuote(List<Quote> quotes){
        Random random = new Random();
        List<String> squotes = new ArrayList<>();
        for(Quote sq : quotes){
            squotes.add(sq.getQuote());
        }

        return squotes.get(random.nextInt(squotes.size()));
    }

    public List<Quote> getQuotesByGroup(String group){
        List<Quote> quotes = new ArrayList<>();
        for(Quote quote : getQuotes()){
            if(quote.getGroup().equalsIgnoreCase(group)){
                quotes.add(quote);
            }
        }
        return quotes;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public String getFormat() {
        return format;
    }
}
