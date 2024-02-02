package random.rules.Commands.Object;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import random.rules.Storage;

import java.util.List;

public abstract class ObjectCommand{
    protected Storage storage;
    protected boolean returnSuccessMessage = true;

    public boolean isReturnSuccessMessage() {
        return returnSuccessMessage;
    }

    public ObjectCommand(Storage storage) {
        this.storage = storage;
    }
    public abstract String getName();
    public abstract boolean execute(List<String> arguments, CommandSender sender);
    public abstract List<String> autoComplete(List<String> arguments);
}
