package bg.tu_varna.sit.automat_data;

import java.util.ArrayList;
import java.util.List;

public class Transitions {
    private State startState;
    private State endState;
    private Symbol symbol;

    public Transitions(State startState, State endState, Symbol symbol) {
        this.startState = startState;
        this.endState = endState;
        this.symbol = symbol;
    }

    public void setStartState(State startState) {
        this.startState = startState;
    }

    public void setEndState(State endState) {
        this.endState = endState;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public State getStartState() {
        return startState;
    }

    public State getEndState() {
        return endState;
    }

    public Symbol getSymbol() {
        return symbol;
    }
}
