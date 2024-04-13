package bg.tu_varna.sit.commands.operations.file;

import bg.tu_varna.sit.automat_data.*;
import bg.tu_varna.sit.commands.ExecuteCommand;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class Open implements ExecuteCommand {
    private final String pathFile;
    private final AllAutomations allAutomations;


    public Open(String pathFile, AllAutomations allAutomations) {
        this.pathFile = pathFile;
        this.allAutomations = allAutomations;
    }

    @Override
    public void execute() throws Exception {
        File file = new File(pathFile);
        if (file.exists()) {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList automation = doc.getElementsByTagName("automation");
            for (int i = 0; i < automation.getLength(); i++) {
                Element currentReadAutomation = (Element) automation.item(i);

                Element automationData = (Element) currentReadAutomation.getElementsByTagName("automationData").item(0);

                Element statesData = (Element) automationData.getElementsByTagName("statesList").item(0);
                NodeList statesList = statesData.getElementsByTagName("state");
                ArrayList<State> states = new ArrayList<>();
                for (int j = 0; j < statesList.getLength(); j++) {
                    Element currentReadState = (Element) statesList.item(j);
                    states.add(new State(currentReadState.getTextContent()));
                }

                Element symbolData = (Element) automationData.getElementsByTagName("symbolsList").item(0);
                NodeList symbolsList = symbolData.getElementsByTagName("symbol");
                ArrayList<Symbol> symbols = new ArrayList<>();
                for (int j = 0; j < symbolsList.getLength(); j++) {
                    Element currentReadSymbol = (Element) symbolsList.item(j);
                    symbols.add(new Symbol(currentReadSymbol.getTextContent()));
                }

                Element transactionData = (Element) automationData.getElementsByTagName("transitionsList").item(0);
                NodeList transitionList = transactionData.getElementsByTagName("transition");
                ArrayList<Transitions> transitions = new ArrayList<>();
                for (int j = 0; j < transitionList.getLength(); j++) {
                    Element currentReadTransition = (Element) transitionList.item(j);
                    transitions.add(new Transitions(new State(currentReadTransition.getElementsByTagName("startStateTransition").item(0).getTextContent()),
                            new State(currentReadTransition.getElementsByTagName("endStateTransition").item(0).getTextContent()),
                            new Symbol(currentReadTransition.getElementsByTagName("symbolTransition").item(0).getTextContent())));
                }

                State startState = new State(automationData.getElementsByTagName("startState").item(0).getTextContent());

                Element endStatesData = (Element) automationData.getElementsByTagName("endStatesList").item(0);
                NodeList endStatesList = endStatesData.getElementsByTagName("endStates");
                ArrayList<State> endStates = new ArrayList<>();
                for (int j = 0; j < endStatesList.getLength(); j++) {
                    Element currentReadEndState = (Element) endStatesList.item(j);
                    endStates.add(new State(currentReadEndState.getTextContent()));
                }

                OneAutomation readAutomation = new OneAutomation(states, symbols, transitions, startState, endStates);
                this.allAutomations.putElement(readAutomation);
            }
        } else if (!file.createNewFile()) throw new Exception();
    }
}