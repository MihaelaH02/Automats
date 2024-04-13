package bg.tu_varna.sit.commands.operations;

import bg.tu_varna.sit.automat_data.*;
import bg.tu_varna.sit.commands.ExecuteCommand;

public class Union implements ExecuteCommand {
    private final AllAutomations allAutomations;
    private final OneAutomation automation1;
    private final OneAutomation automation2;

    public Union(AllAutomations allAutomations, OneAutomation automation1, OneAutomation automation2) {
        this.allAutomations = allAutomations;
        this.automation1 = automation1;
        this.automation2 = automation2;
    }

    @Override
    public void execute() throws Exception {
        OneAutomation newAutomation = new OneAutomation();
        newAutomation.setStartState(new State("1"));// new start state
        newAutomation.addTransaction(new Transitions(newAutomation.getStartState(), new State(String.valueOf(Integer.parseInt(this.automation1.getStartState().getStates())+1)), new Symbol("E"))); // connection with automation1 with new start state

        // copy NFA n's transitions to result
        for (Transitions t: this.automation1.getTransitions()){
            newAutomation.addTransaction(new Transitions(new State(String.valueOf(Integer.parseInt(t.getStartState().getStates())+1)),new State(String.valueOf(Integer.parseInt(t.getEndState().getStates())+1)), t.getSymbol())); //copy transitions from automation1 with new states numbers +1
        }

        // empty transition from final state of n to beginning state of m
        newAutomation.addTransaction(new Transitions(newAutomation.getStartState(), new State(String.valueOf(Integer.parseInt(this.automation2.getStartState().getStates()) + this.automation1.getStates().size())), new Symbol("E"))); // connection with automation2 with new start state

        // copy NFA m's transitions to result
        for (Transitions t: this.automation2.getTransitions()){
            newAutomation.addTransaction(new Transitions(new State(String.valueOf(Integer.parseInt(t.getStartState().getStates())+ this.automation1.getStates().size())), new State(String.valueOf(Integer.parseInt(t.getEndState().getStates())+ this.automation1.getStates().size())), t.getSymbol())); //copy transactions from automation2 with new states last of automation1 + current automation2
        }

        //add end states
        for (State s: this.automation1.getEndState()) {//adding end states from automation1
            newAutomation.getEndState().add(new State(String.valueOf(Integer.parseInt(s.getStates())+1)));
        }
        for (State s: this.automation2.getEndState()) {//adding end states from automation2
            newAutomation.getEndState().add(new State(String.valueOf(Integer.parseInt(s.getStates()) + this.automation1.getStates().size())));
        }

        for (int i=1; i < this.automation1.getStates().size() + this.automation2.getStates().size()+1; i++)//adding total states
            newAutomation.addStates(new State(String.valueOf(i)));

        newAutomation.addAlphabet(new Symbol("E"));
        for (Symbol s:this.automation1.getAlphabet())//adding alphabet
            newAutomation.addAlphabet(s);
        for (Symbol s:this.automation2.getAlphabet())
            if(!newAutomation.containSymbolInAlphabet(s)) newAutomation.addAlphabet(s);

        this.allAutomations.putElement(newAutomation);//adding new automation
    }
}
