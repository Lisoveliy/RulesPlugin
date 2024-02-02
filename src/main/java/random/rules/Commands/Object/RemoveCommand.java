package random.rules.Commands.Object;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import random.rules.Rule;
import random.rules.Storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoveCommand extends ObjectCommand{
    public RemoveCommand(Storage storage) {
        super(storage);
    }

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public boolean execute(List<String> arguments, CommandSender entity) {
        try {
            return storage.deleteRule(arguments.get(0), Rule.valueOf(arguments.get(1)));
        }catch(IllegalArgumentException ignored){
            return false;
        }
    }

    @Override
    public List<String> autoComplete(List<String> arguments) {
        switch (arguments.size()){
            case 1:
                return storage.getObjects().stream().filter(x -> x.startsWith(arguments.get(0))).toList();
            case 2:
                var storageRules = storage.getRules(arguments.get(0));
                if(storageRules == null)
                    break;
                return storageRules
                        .stream()
                        .map(Rule::name)
                        .filter(x -> x.startsWith(arguments.get(1))).toList();
        }
        return new ArrayList<>();
    }
}
