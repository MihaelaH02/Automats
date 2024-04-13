package bg.tu_varna.sit.commands.operations;

import bg.tu_varna.sit.automat_data.OneAutomation;
import bg.tu_varna.sit.automat_data.State;
import bg.tu_varna.sit.automat_data.Symbol;
import bg.tu_varna.sit.automat_data.Transitions;
import bg.tu_varna.sit.commands.ExecuteCommand;

public class Deterministic implements ExecuteCommand {
    private final OneAutomation oneAutomation;

    public Deterministic(OneAutomation oneAutomation) {
        this.oneAutomation = oneAutomation;
    }

    @Override
    public void execute() throws Exception {
        int deterministic;
        for (State state : this.oneAutomation.getStates()) {
            for (Symbol symbol : this.oneAutomation.getAlphabet()) {
                deterministic = 0;
                for (Transitions transitions : this.oneAutomation.getTransitions()) {
                    if (transitions.getStartState().equals(state) && transitions.getSymbol().equals(symbol))
                        deterministic++;
                    if (deterministic > 1)
                        System.out.println("The automation is nondeterministic");
                }
            }
        }
        System.out.println("The automation is deterministic");
    }
}
