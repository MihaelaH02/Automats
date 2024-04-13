 package bg.tu_varna.sit.commands.operations.file;

import bg.tu_varna.sit.automat_data.AllAutomations;
import bg.tu_varna.sit.commands.ExecuteCommand;

public class Close implements ExecuteCommand {
    private AllAutomations allAutomations;

    public Close(AllAutomations allAutomations) {
        this.allAutomations = allAutomations;
    }

    public void execute() {
        this.allAutomations =new AllAutomations();
    }
}
