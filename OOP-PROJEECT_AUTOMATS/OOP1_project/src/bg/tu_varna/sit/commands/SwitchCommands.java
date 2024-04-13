package bg.tu_varna.sit.commands;

import bg.tu_varna.sit.automat_data.*;
import bg.tu_varna.sit.commands.operations.*;
import bg.tu_varna.sit.commands.operations.file.Close;
import bg.tu_varna.sit.commands.operations.file.Open;
import bg.tu_varna.sit.commands.operations.file.Write;
import bg.tu_varna.sit.validate.ValidateID;

import java.util.Scanner;

public class SwitchCommands implements RunCLI{

    private static boolean openFile = false;
    private static String pathFile;

    public void runCommand() throws Exception {
        AllAutomations data = new AllAutomations();
      /* data.putElement(new OneAutomation(
                new ArrayList<>(asList(new State("0"), new State("1"), new State("2"), new State("3"), new State("4"))),
                new ArrayList<>(asList(new Symbol("a"), new Symbol("b"))),
                new ArrayList<>(asList(
                        new Transitions(new State("0"), new State("1"), new Symbol("a")),
                        new Transitions(new State("0"), new State("2"), new Symbol("a")),
                        new Transitions(new State("0"), new State("4"), new Symbol("b")),
                        new Transitions(new State("1"), new State("0"), new Symbol("a")),
                        new Transitions(new State("2"), new State("3"), new Symbol("a")),
                        new Transitions(new State("3"), new State("0"), new Symbol("b"))
                )),
                new State("0"),
                new ArrayList<>(java.util.List.of(new State("4"))))
        );
        data.putElement(new OneAutomation(
                new ArrayList<>(asList(new State("1"), new State("2"), new State("3"), new State("4"), new State("5"))),
                new ArrayList<>(asList(new Symbol("a"), new Symbol("b"))),
                new ArrayList<>(asList(
                        new Transitions(new State("1"), new State("2"), new Symbol("a")),
                        new Transitions(new State("1"), new State("5"), new Symbol("b")),
                        new Transitions(new State("2"), new State("2"), new Symbol("b")),
                        new Transitions(new State("2"), new State("3"), new Symbol("a")),
                        new Transitions(new State("3"), new State("4"), new Symbol("a")),
                        new Transitions(new State("4"), new State("4"), new Symbol("b")),
                        new Transitions(new State("4"), new State("3"), new Symbol("b")),
                        new Transitions(new State("5"), new State("4"), new Symbol("a")),
                        new Transitions(new State("5"), new State("3"), new Symbol("a"))
                )),
                new State("1"),
                new ArrayList<>(java.util.List.of(new State("7"))))
        );*/

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            String[] params = input.split(" ");

            CommandsEnum command = CommandsEnum.valueOf(params[0].toUpperCase());
            System.out.println("> " + command.name().toLowerCase());

            if (command == CommandsEnum.HELP) {
                String[] paramsToTake = new String[]{"<file>", "", "", "<file>", "", "", "" , "<id>", "<id><file>", "<id>", "<id>", "<id><word>", "<id1><id2>", "<id1><id2>", "<id>", "<regex>", "<id>", "<id>"};
                int i = 0;
                System.out.println("The following commands are supported:");
                for (CommandsEnum cmd : CommandsEnum.values())
                    System.out.printf("%-35s %s%n", cmd.name().toLowerCase() + " " + paramsToTake[i++], cmd.getCommandEnum());

            } else if (command == CommandsEnum.EXIT) {
                System.out.println("Exit the program...");
                System.exit(1);

            } else if (command == CommandsEnum.OPEN) {
                pathFile = params[1];
                new Open(pathFile,data).execute();
                openFile = true;

            } else if (openFile) {
                switch (command) {
                    case SAVE -> new Write(data, pathFile).execute();
                    case SAVEAS -> new Write(data, params[1]).execute();
                    case CLOSE -> {
                        new Close(data).execute();
                        openFile = false;
                    }
                    case LIST -> new List(data).execute();
                    case PRINT -> new Print(data.getMap().get(new ValidateID(data).validateID(Integer.valueOf(params[1])))).execute();
                    case SAVEID -> new SaveID(data.getMap().get(new ValidateID(data).validateID(Integer.valueOf(params[1]))),pathFile).execute();
                    case EMPTY -> new Empty(data.getMap().get(new ValidateID(data).validateID(Integer.valueOf(params[1])))).execute();
                    case DETERMINISTIC -> new Deterministic(data.getMap().get(new ValidateID(data).validateID(Integer.valueOf(params[1])))).execute();
                    case RECOGNIZE -> new Recognize(data.getMap().get(new ValidateID(data).validateID(Integer.valueOf(params[1]))),params[2]).execute();
                    case UNION -> new Union(data, data.getMap().get(new ValidateID(data).validateID(Integer.valueOf(params[1]))), data.getMap().get(new ValidateID(data).validateID(Integer.valueOf(params[2])))).execute();
                    //case CONCAT ->
                    //case UN ->
                    //case REG -> new Reg(data,params[1]).execute();
                    //case FINITE ->
                    case MUTATOR -> new Mutator(data, params[1]).execute();
                }
            } else throw new Exception("Operation error, file not open!");
            System.out.println("Successfully do operation " + command.name().toLowerCase());
        }
    }
}
