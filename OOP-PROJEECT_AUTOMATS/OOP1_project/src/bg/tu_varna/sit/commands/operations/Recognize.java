package bg.tu_varna.sit.commands.operations;

import bg.tu_varna.sit.automat_data.OneAutomation;
import bg.tu_varna.sit.automat_data.State;
import bg.tu_varna.sit.automat_data.Symbol;
import bg.tu_varna.sit.automat_data.Transitions;
import bg.tu_varna.sit.commands.ExecuteCommand;

import java.util.ArrayList;
import java.util.List;

public class Recognize implements ExecuteCommand {
    private final OneAutomation oneAutomation;
    private final char[] word;
    private List<State> followingState;
    private boolean find;
    private int correctSymbolFromWord;

    public Recognize(OneAutomation oneAutomation, String word) {
        this.oneAutomation = oneAutomation;
        this.word = word.toCharArray();
        this.followingState = new ArrayList<>();
        this.followingState.add(this.oneAutomation.getStartState());
        this.find = false;
        this.correctSymbolFromWord = 0;
    }

    @Override
    public void execute() throws Exception {
        for (char symbol : this.word) {//проверка дали всички символи от думата са част от азбуката на автомата
            if (!this.oneAutomation.containSymbolInAlphabet(new Symbol(String.valueOf(symbol)))) {
                System.out.println("The word is not from the automation.");
                return;
            }
        }
        findTransition(this.word[this.correctSymbolFromWord]);
        System.out.println(this.find ? "The word is from the automation." : "The word is not from the automation.");
    }

    private void findTransition(char symbol) {
        this.find=false;
        for (Transitions transitions : this.oneAutomation.getTransitions()) {//итерираме всички преходи
            if (this.followingState.get(this.followingState.size() - 1).getStates().equals(transitions.getStartState().getStates()) && transitions.getSymbol().getSymbol().equals(String.valueOf(symbol))) {//ако последното състояние има преход по настоящия символ
                if(this.correctSymbolFromWord == this.word.length-1){//проверка дали не сме на последния символ
                    if(this.oneAutomation.containEndState(transitions.getEndState())) { //ако да проверка дали намереното настоящето състояние от преходите е крайно за автомата
                        this.followingState.add(transitions.getEndState());//добавяме го
                        this.find=true;//намерено
                        return;//връщане към предходно ниво от рекурсията
                    }
                    else continue;//продължава търсене на правилен преход
                }
                find = true;//намерен преход
                this.followingState.add(transitions.getEndState());//добавяме
                findTransition(this.word[++this.correctSymbolFromWord]);//нова рекурсия за следващ символ
            }
            if(this.find) return;//ако сме стигнали края на думата излизаме от рекурсията
        }
        if (!this.find) {//ако сме обходили всички преходи и няма подходящ
            this.followingState.remove(this.followingState.size() - 1);//изтрии последното състояние
            this.correctSymbolFromWord--;//върни се с един символ назад и търси нов преход връщане от рекурсията към предно ниво
        }
    }
}