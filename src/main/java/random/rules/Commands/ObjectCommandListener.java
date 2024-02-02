package random.rules.Commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import random.rules.Commands.Object.ObjectCommand;
import random.rules.Rules;

import java.util.*;

public class ObjectCommandListener implements CommandExecutor, TabCompleter {
    private final HashMap<String, ObjectCommand> commands = new HashMap<>();

    public ObjectCommandListener(List<ObjectCommand> commands, Rules plugin) {
        Objects.requireNonNull(plugin.getCommand("object")).setExecutor(this);
        commands.forEach(x -> {
            this.commands.put(x.getName(), x);
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if (args.length < 1)
            return false;
        var executableCommand = this.commands.get(args[0]);
        if (executableCommand != null) {
            var data = Arrays.stream(args).toList().subList(1, args.length);
            var result = executableCommand.execute(data, sender);
            if (result) {
                if (executableCommand.isReturnSuccessMessage())
                    sender.sendMessage(Component.text("§bВыполнено успешно!"));

                return true;
            }
        }
        return false;
    }

    @Override
    public List<String>
    onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length){
            case 0:
                return commands.keySet().stream().toList();
            case 1:
                return commands.keySet().stream().filter(x -> x.startsWith(args[0])).toList();
        }
        var executableCommand = this.commands.get(args[0]);
        if (executableCommand != null) {
            var data = Arrays.stream(args).toList().subList(1, args.length);
            return executableCommand.autoComplete(data);
        }
        return new ArrayList<>();
    }
}

