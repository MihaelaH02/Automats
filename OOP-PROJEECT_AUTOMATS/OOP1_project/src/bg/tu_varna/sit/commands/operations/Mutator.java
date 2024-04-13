package bg.tu_varna.sit.commands.operations;

import bg.tu_varna.sit.automat_data.*;
import bg.tu_varna.sit.commands.ExecuteCommand;
import bg.tu_varna.sit.validate.ValidateID;

import java.util.ArrayList;

public class Mutator implements ExecuteCommand {
    private final AllAutomations allAutomations;
    private final OneAutomation automation;
    private OneAutomation newAutomation;
    private final Integer key;

    public Mutator(AllAutomations allAutomations, String key) throws Exception {
        this.allAutomations = allAutomations;
        this.key=Integer.valueOf(key);
        this.automation = this.allAutomations.getMap().get(new ValidateID(this.allAutomations).validateID(this.key));
    }

    private boolean isNFA() {
        boolean flagFindTransaction;
        for (State currentState : this.automation.getStates())
            for (Symbol currentSymbol : this.automation.getAlphabet()) {
                flagFindTransaction = false;
                for (Transitions transition : this.automation.getTransitions()) {
                    if (currentState.getStates().equals(transition.getStartState().getStates()) && currentSymbol.getSymbol().equals(transition.getSymbol().getSymbol())) {
                        if (flagFindTransaction) return true;
                        flagFindTransaction = true;
                    }
                }
            }
        return false;
    }

    @Override
    public void execute() throws Exception {
        if (!this.isNFA()) System.out.println("The automat is determinic!");
        else {
            this.newAutomation = new OneAutomation(new ArrayList<>(), this.automation.getAlphabet(), new ArrayList<>(), this.automation.getStartState(), this.automation.getEndState());
            this.newAutomation.addStates(this.automation.getStartState());
            addNewState(this.automation.getStartState());
            this.allAutomations.getMap().put(this.key,this.newAutomation);
        }

    }

    private void addNewState(State currentState) {
        char[] newStates = currentState.getStates().toCharArray();
        String newState;
        for (Symbol currentSymbol : this.automation.getAlphabet()) {
            newState = "";
            for (char state : newStates) {
                for (Transitions currentTransition : this.automation.getTransitions()) {
                    if (String.valueOf(state).equals(currentTransition.getStartState().getStates()) && currentSymbol.getSymbol().equals(currentTransition.getSymbol().getSymbol())) {
                        if (newState.equals("")) {//ако има отрито първо състояние
                            this.newAutomation.addTransaction(new Transitions(currentState,currentTransition.getEndState(),currentSymbol));//добави тази транзакция
                            newState += currentTransition.getEndState().getStates();//флага с името на новото състояние се променя
                        } else {
                            if (!newState.contains(currentTransition.getEndState().getStates())) {
                                newState += currentTransition.getEndState().getStates();//ако се открие втора транзакция се добавя към предходната
                                this.newAutomation.getTransitions().get(this.newAutomation.getTransitions().size() - 1).setEndState(new State(newState));//редактираме предходно записаната транзакция като към крайното състояние добавяме новото
                            }
                        }
                    }
                }
            }
            if (newState.equals("")) continue;
            if(!this.newAutomation.containState(newState)){
                this.newAutomation.addStates(new State(newState));//ако има открито състояние го добави
                addNewState(this.newAutomation.getStates().get(this.newAutomation.getStates().size() - 1));//нова итерация с това състояние
            }
        }
    }

}
