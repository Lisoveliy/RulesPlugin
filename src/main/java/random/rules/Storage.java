package random.rules;

import it.unimi.dsi.fastutil.Hash;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class Storage implements ConfigurationSerializable {
    private final HashMap<String, HashSet<Rule>> objectStorage = new HashMap<>();
    private final FileConfiguration configuration;
    private final File file;

    public Storage(FileConfiguration fileConfiguration, File file) {
        this.file = file;
        this.configuration = fileConfiguration;
        var objects = (HashMap<String, HashSet<String>>) fileConfiguration.get("objects");
        if (objects != null) {
            objects.forEach((key, value) -> {
                        objectStorage.put(key, new HashSet<>(value.stream().map(Rule::valueOf).toList()));
                    }
            );
        }
    }

    @Override
    @NotNull
    public Map<String, Object> serialize() {
        var fileStorageMap = new HashMap<String, HashSet<String>>();
        objectStorage.forEach((key, value) -> {
            fileStorageMap.put(key, new HashSet<>(value.stream().map(Enum::name).toList()));
        });
        return new HashMap<>(fileStorageMap);
    }

    @Nullable
    public List<Rule> getRules(String name) {
        var hashset = objectStorage.get(name);
        if (hashset != null)
            return hashset.stream().toList();
        return null;
    }


    public List<String> getObjects() {
        return objectStorage.keySet().stream().toList();
    }

    public boolean createObject(String objectName) {
        if (objectStorage.get(objectName) == null) {
            objectStorage.put(objectName, new HashSet<>());
            saveFile();
            return true;
        }
        return false;
    }

    public boolean deleteObject(String objectName) {
        if (objectStorage.get(objectName) == null)
            return false;
        objectStorage.remove(objectName);
        saveFile();
        return true;
    }

    public boolean setRule(String objectName, Rule rule) {
        var hashset = objectStorage.get(objectName);
        if (hashset != null && !hashset.contains(rule)) {
            hashset.add(rule);
            saveFile();
            return true;
        }
        return false;
    }

    public boolean deleteRule(String objectName, Rule rule) {
        var hashset = objectStorage.get(objectName);
        if (hashset == null)
            return false;
        hashset.remove(rule);
        saveFile();
        return true;
    }

    private void saveFile() {
        configuration.set("objects", this.serialize());
        System.out.println(this.serialize());
        System.out.println(configuration.get("objects"));
        try {
            configuration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
