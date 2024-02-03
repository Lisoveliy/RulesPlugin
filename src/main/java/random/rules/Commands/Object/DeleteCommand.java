package random.rules.Commands.Object;

import org.bukkit.command.CommandSender;
import random.rules.Storage;

import java.util.ArrayList;
import java.util.List;

public class DeleteCommand extends ObjectCommand{
    public DeleteCommand(Storage storage) {
        super(storage);
    }

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public boolean execute(List<String> arguments, CommandSender entity) {
        return storage.deleteObject(arguments.get(0));
    }

    @Override
    public List<String> autoComplete(List<String> arguments) {
        if(arguments.size() > 1)
            return new ArrayList<>();
        return storage.getObjects().stream().filter(x -> x.startsWith(arguments.get(0))).toList();
    }
}
