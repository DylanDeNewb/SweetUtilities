package fun.sweetsmp.sweetutilities.quotes;

public class Quote {

    private String group;
    private String quote;

    public Quote(String group, String quote){
        this.group = group;
        this.quote = quote;
    }

    public String getGroup() {
        return group;
    }

    public String getQuote() {
        return quote;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
