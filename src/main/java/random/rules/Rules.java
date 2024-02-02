package random.rules;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import random.rules.Commands.Object.*;
import random.rules.Commands.ObjectCommandListener;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class Rules extends JavaPlugin {
    private Storage storage;
    @Override
    public void onEnable() {
        try {
            createStorage();
            createCommands();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void createStorage() throws IOException {
        ConfigurationSerialization.registerClass(Storage.class);
        File storageData = new File(getDataFolder(), Constants.YamlStorageName);
        if(!storageData.exists()){
            storageData.getParentFile().mkdirs();
            storageData.createNewFile();
        }
        FileConfiguration storageConfig = new YamlConfiguration();
        try {
            storageConfig.load(storageData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        storage = new Storage(storageConfig, storageData);
    }
    private void createCommands(){
        new ObjectCommandListener(List.of(
                new CreateCommand(storage),
                new AddCommand(storage),
                new DeleteCommand(storage),
                new RemoveCommand(storage),
                new InfoCommand(storage)), this);
    }
}