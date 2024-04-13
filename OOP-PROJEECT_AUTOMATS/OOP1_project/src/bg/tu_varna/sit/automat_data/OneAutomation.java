package bg.tu_varna.sit.automat_data;

import java.util.ArrayList;
import java.util.List;

public class OneAutomation {
    private List<State> states;
    private List<Symbol> alphabet;
    private List<Transitions> transitions;
    private State startState;
    private List<State> endState;

    public OneAutomation() {
        this.states = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.startState = null;
        this.endState = new ArrayList<>();
    }

    public OneAutomation(List<State> states, List<Symbol> alphabet, List<Transitions> transitions, State startState, List<State> endState) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.startState = startState;
        this.endState = endState;
    }

    public List<State> getStates() {
        return states;
    }

    public List<Symbol> getAlphabet() {
        return alphabet;
    }

    public List<Transitions> getTransitions() {
        return transitions;
    }

    public State getStartState() {
        return startState;
    }

    public List<State> getEndState() {
        return endState;
    }

    public void addStates(State state) {
        this.states.add(state);
    }

    public void addAlphabet(Symbol symbol) {
        this.alphabet.add(symbol);
    }

    public void addTransaction(Transitions transition) {
        this.transitions.add(transition);
    }

    public void addEndState(State state) {
        this.endState.add(state);
    }

    public void setStartState(State startState) {
        this.startState = startState;
    }

    public boolean containEndState(State state){
        for (State currentState:this.getEndState())
            if(currentState.getStates().equals(state.getStates())) return true;
        return false;
    }

    public boolean containSymbolInAlphabet(Symbol symbol){
        for (Symbol currentSymbol:this.getAlphabet())
            if(currentSymbol.getSymbol().equals(symbol.getSymbol())) return true;
        return false;
    }

    public boolean containState(String state){
        for (State currentState:this.getStates())
            if(currentState.getStates().equals(state)) return true;
        return false;
    }
}
