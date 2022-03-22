package fun.sweetsmp.sweetutilities.api;

import fun.sweetsmp.sweetutilities.SweetUtilities;
import fun.sweetsmp.sweetutilities.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class Manager implements IManager{

    private SweetUtilities core;
    private List<FileUtils> files = new ArrayList<>();
    public Manager(SweetUtilities core){
        this.core = core;
        loadCommands();
        loadListeners();
    }

    public FileUtils createFile(String name){
        FileUtils file = new FileUtils(getCore(), name + ".yml");
        this.files.add(file);
        return file;
    }

    public FileUtils getFile(String name){
        for(FileUtils fileUtils : this.files){
            if(fileUtils.getName().equalsIgnoreCase(name + ".yml")){
                return fileUtils;
            }
        }
        return null;
    }

    public SweetUtilities getCore() {
        return core;
    }
}
