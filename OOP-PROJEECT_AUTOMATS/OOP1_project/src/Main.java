import bg.tu_varna.sit.commands.SwitchCommands;
import bg.tu_varna.sit.commands.operations.Reg;



public class Main {
    public static void main(String[] args) throws Exception {
        SwitchCommands commands = new SwitchCommands();
        commands.runCommand();

        //new Reg(null,"(a+b)*(abb+ab)(a+b)*").execute();

    }
}