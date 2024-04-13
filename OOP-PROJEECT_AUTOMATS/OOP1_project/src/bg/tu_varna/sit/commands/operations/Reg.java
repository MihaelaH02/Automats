package bg.tu_varna.sit.commands.operations;

import bg.tu_varna.sit.automat_data.*;
import bg.tu_varna.sit.commands.ExecuteCommand;

import java.util.*;
import java.util.List;

public class Reg implements ExecuteCommand {
    private AllAutomations allAutomations;
    private final String regex;

    public Reg(AllAutomations allAutomations, String regex) {
        this.allAutomations = allAutomations;
        this.regex = regex;
    }

    @Override
    public void execute() throws Exception {
        ArrayList<String> regexSplit = new ArrayList<>(List.of(this.regex.split("\\*")));
        System.out.println(regexSplit);

        OneAutomation oneAutomation = new OneAutomation();
        //oneAutomation.addStates();
    }
}
