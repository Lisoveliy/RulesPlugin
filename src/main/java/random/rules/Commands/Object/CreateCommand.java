package random.rules.Commands.Object;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import random.rules.Storage;

import java.util.ArrayList;
import java.util.List;

public class CreateCommand extends ObjectCommand{
    public CreateCommand(Storage storage) {
        super(storage);
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public boolean execute(List<String> arguments, CommandSender entity) {
        return storage.createObject(arguments.get(0));
    }

    @Override
    public List<String> autoComplete(List<String> arguments) {
        return new ArrayList<>();
    }
}
