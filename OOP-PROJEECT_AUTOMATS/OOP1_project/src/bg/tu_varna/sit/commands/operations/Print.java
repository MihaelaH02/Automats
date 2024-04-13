package bg.tu_varna.sit.commands.operations;

import bg.tu_varna.sit.automat_data.*;
import bg.tu_varna.sit.commands.ExecuteCommand;

public class Print implements ExecuteCommand {
    private final OneAutomation oneAutomation;

    public Print(OneAutomation oneAutomation) {
        this.oneAutomation = oneAutomation;
    }

    private String printTransaction(State currentState, Symbol currentSymbol){
        StringBuilder string = new StringBuilder();
        boolean flagFindTransaction = false;
        boolean flagMoreThenOne = false;

        string.append("{");
        for (Transitions transaction : this.oneAutomation.getTransitions()) {
            if (transaction.getStartState().getStates().equals(currentState.getStates()) && transaction.getSymbol().getSymbol().equals(currentSymbol.getSymbol())) {
                if (flagMoreThenOne) string.append(", ");
                string.append(transaction.getEndState().getStates());
                flagMoreThenOne = true;
                flagFindTransaction = true;
            }
        }
        string.append("}");
        if(!flagFindTransaction) string = new StringBuilder("-");
        return string.toString();
    }

    @Override
    public void execute() throws Exception {
        StringBuilder string = new StringBuilder("\t\t\t\t");

        for (Symbol symbol : this.oneAutomation.getAlphabet()) {
            string.append(symbol.getSymbol()).append("\t\t\t");
        }//първи ред - азбуката

        string.append("\nSTART->\t").append(this.oneAutomation.getStartState().getStates());
        for (Symbol currentSymbol : this.oneAutomation.getAlphabet())
            string.append("\t\t").append(this.printTransaction(this.oneAutomation.getStartState(),currentSymbol)).append("\t");

        for (State currentState : this.oneAutomation.getStates()) {
            if(this.oneAutomation.getStartState().getStates().equals(currentState.getStates())) continue;//ako e nachalno sustoyane se preskacha
            if(this.oneAutomation.containEndState(currentState)) continue; //ako e kraino susto

            string.append("\n\t\t").append(currentState.getStates());
            for (Symbol currentSymbol : this.oneAutomation.getAlphabet()) {
                string.append("\t\t").append(this.printTransaction(currentState,currentSymbol)).append("\t");
            }
        }

        for (State currentEndState : this.oneAutomation.getEndState()) {
            string.append("\nEND->\t").append(currentEndState.getStates());
            for (Symbol currentSymbol : this.oneAutomation.getAlphabet())
                string.append("\t\t").append(this.printTransaction(currentEndState, currentSymbol)).append("\t");
        }
        System.out.println(string);
    }
}
