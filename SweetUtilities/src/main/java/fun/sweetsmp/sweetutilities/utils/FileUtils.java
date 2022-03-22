package fun.sweetsmp.sweetutilities.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtils {

    private final File configFile;
    private YamlConfiguration config;
    private String name;

    public FileUtils(JavaPlugin plugin, String fileName) {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        this.configFile = new File(plugin.getDataFolder(), fileName);
        if(!this.configFile.exists()) {
            plugin.saveResource(fileName, false);
        }
        this.config = YamlConfiguration.loadConfiguration(this.configFile);
        this.name = fileName;
    }

    public String getName() {
        return name;
    }

    public String getString(String path) {
        if(this.config.contains(path))
            return this.config.getString(path);
        return "Invalid file path specified";
    }

    public List<String> getStringList (String path) {
        if(this.config.contains(path)) {
            return new ArrayList<>(this.config.getStringList(path));
        }
        return Collections.singletonList("Invalid string list path specified");
    }

    public boolean getBoolean (String path) {
        if(this.config.contains(path))
            return this.config.getBoolean(path);
        return false;
    }

    public int getInt (String path) {
        if(this.config.contains(path))
            return this.config.getInt(path);
        return 0;
    }

    public Double getDouble (String path) {
        if(this.config.contains(path))
            return this.config.getDouble(path);
        return 0D;
    }

    public void save() {
        try {
            this.config.save(this.configFile);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("Failed to save file " + this.config);
        }
    }

    public void reload(boolean save) {
        if (save) this.save();
        this.config = YamlConfiguration.loadConfiguration(this.configFile);
    }

    public void set (String path, Object value, boolean save) {
        this.config.set(path, value);
        save();
    }

    public YamlConfiguration getAsYaml() {
        return this.config;
    }


}
