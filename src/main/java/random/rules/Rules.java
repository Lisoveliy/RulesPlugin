package random.rules;

import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;
import random.rules.Commands.Object.*;
import random.rules.Commands.ObjectCommandListener;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createStorage() throws IOException {
        File storageData = new File(getDataFolder(), Constants.YamlStorageName);
        if(!storageData.exists()){
            storageData.getParentFile().mkdirs();
            storageData.createNewFile();
        }
        storage = new Storage(storageData);
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