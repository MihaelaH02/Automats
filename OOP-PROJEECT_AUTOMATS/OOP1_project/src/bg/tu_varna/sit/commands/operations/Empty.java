package bg.tu_varna.sit.commands.operations;

import bg.tu_varna.sit.automat_data.OneAutomation;
import bg.tu_varna.sit.commands.ExecuteCommand;

public class Empty implements ExecuteCommand {
    private final OneAutomation oneAutomation;

    public Empty(OneAutomation oneAutomation) {
        this.oneAutomation = oneAutomation;
    }

    @Override
    public void execute() throws Exception {
        if(this.oneAutomation.getAlphabet().isEmpty()) System.out.println("The alphabet is empty!");
        else System.out.println("The alphabet is not empty.\nIt contains this symbols:" + this.oneAutomation.getAlphabet());
    }
}
