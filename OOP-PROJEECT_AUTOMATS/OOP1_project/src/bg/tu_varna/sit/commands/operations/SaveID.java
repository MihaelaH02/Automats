package bg.tu_varna.sit.commands.operations;

import bg.tu_varna.sit.automat_data.OneAutomation;
import bg.tu_varna.sit.commands.ExecuteCommand;
import bg.tu_varna.sit.commands.operations.file.Write;


public class SaveID implements ExecuteCommand {
    private final OneAutomation oneAutomation;
    private final String pathFile;

    public SaveID(OneAutomation oneAutomation, String pathFile) {
        this.oneAutomation = oneAutomation;
        this.pathFile = pathFile;
    }

    @Override
    public void execute() throws Exception {
        new Write(this.oneAutomation, this.pathFile).execute();
    }
}
