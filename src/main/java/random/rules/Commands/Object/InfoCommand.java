package random.rules.Commands.Object;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import random.rules.Storage;

import java.util.ArrayList;
import java.util.List;

public class InfoCommand extends ObjectCommand {
    public InfoCommand(Storage storage) {
        super(storage);
        returnSuccessMessage = false;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public boolean execute(List<String> arguments, CommandSender entity) {
        if (arguments.isEmpty())
            return false;
        var rules = storage.getRules(arguments.get(0));
        if (rules == null)
            return false;
        var strRules = rules
                .stream()
                .map(Enum::name).toList();
        entity.sendMessage(Component.text(String.join(", ", strRules)));
        return true;
    }

    @Override
    public List<String> autoComplete(List<String> arguments) {
        if(arguments.size() > 1)
            return new ArrayList<>();
        return storage.getObjects().stream().filter(x -> x.startsWith(arguments.get(0))).toList();
    }
}
