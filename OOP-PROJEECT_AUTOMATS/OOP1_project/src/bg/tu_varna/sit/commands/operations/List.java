package bg.tu_varna.sit.commands.operations;

import bg.tu_varna.sit.automat_data.AllAutomations;
import bg.tu_varna.sit.commands.ExecuteCommand;

public class List implements ExecuteCommand {
    private final AllAutomations allAutomations;

    public List(AllAutomations allAutomations) {
        this.allAutomations = allAutomations;
    }

    @Override
    public void execute() throws Exception {
        for (Integer id: this.allAutomations.getMap().keySet()) {
            System.out.println("ID:" + id);
        }
    }
}
