package random.rules;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Storage {
    private final HashMap<String, HashSet<Rule>> objectStorage = new HashMap<>();

    private final HashMap<String, List<String>> yamlConfig;
    private final Yaml yaml;
    private final File file;
    public Storage(HashMap<String, List<String>> data, Yaml yaml, File file) {
        this.file = file;
        this.yaml = yaml;
        yamlConfig = data;
        if (yamlConfig!= null) {
            yamlConfig.forEach((key, value) -> {
                        objectStorage.put(key, new HashSet<>(value.stream().map(Rule::valueOf).toList()));
                    }
            );
        }
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
        var serobject = new HashMap<String, List<String>>();
        objectStorage.forEach((key, value) -> {
            serobject.put(key, value.stream().map(Enum::name).toList());
        });
        try {
            var writer = new FileWriter(file);
            yaml.dump(serobject, writer);
            writer.close();
            System.out.println(serobject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
