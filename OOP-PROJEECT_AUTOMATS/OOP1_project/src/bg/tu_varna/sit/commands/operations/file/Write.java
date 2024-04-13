package bg.tu_varna.sit.commands.operations.file;

import bg.tu_varna.sit.automat_data.*;
import bg.tu_varna.sit.commands.ExecuteCommand;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedHashMap;

public class Write implements ExecuteCommand {
    private final String pathFile;
    private final AllAutomations allAutomations;

    public Write(AllAutomations allAutomations, String pathFile) {//save the whole data
        this.allAutomations = allAutomations;
        this.pathFile = pathFile;
    }

    public Write(OneAutomation oneAutomation, String pathFile) {//save only one automation
        this.allAutomations = new AllAutomations();
        this.allAutomations.getMap().put(1, oneAutomation);
        this.pathFile = pathFile;
    }

    private AllAutomations getAllAutomations() {
        return allAutomations;
    }

    @Override
    public void execute() throws Exception {
        File file = new File(pathFile);
        if (file.length() > 0) {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.close();
        }

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element root = doc.createElement("allAutomations");
        doc.appendChild(root);

        for (OneAutomation oneAutomation : this.getAllAutomations().getMap().values()) {

            Element automation = doc.createElement("automation");
            root.appendChild(automation);

            Element automationData = doc.createElement("automationData");
            automation.appendChild(automationData);

            Element statesList = doc.createElement("statesList");
            automationData.appendChild(statesList);
            for (State currentState : oneAutomation.getStates()) {
                Element states = doc.createElement("state");
                states.appendChild(doc.createTextNode(currentState.getStates()));
                statesList.appendChild(states);
            }

            Element symbolsList = doc.createElement("symbolsList");
            automationData.appendChild(symbolsList);
            for (Symbol currentSymbol : oneAutomation.getAlphabet()) {
                Element symbol = doc.createElement("symbol");
                symbol.appendChild(doc.createTextNode(currentSymbol.getSymbol()));
                symbolsList.appendChild(symbol);
            }

            Element transitionsList = doc.createElement("transitionsList");
            automationData.appendChild(transitionsList);
            for (Transitions currentTransition: oneAutomation.getTransitions()) {
                Element transition = doc.createElement("transition");
                transitionsList.appendChild(transition);

                Element startStateTransition = doc.createElement("startStateTransition");
                startStateTransition.appendChild(doc.createTextNode(currentTransition.getStartState().getStates()));
                transition.appendChild(startStateTransition);

                Element endStateTransition = doc.createElement("endStateTransition");
                endStateTransition.appendChild(doc.createTextNode(currentTransition.getEndState().getStates()));
                transition.appendChild(endStateTransition);

                Element symbolTransition = doc.createElement("symbolTransition");
                symbolTransition.appendChild(doc.createTextNode(currentTransition.getSymbol().getSymbol()));
                transition.appendChild(symbolTransition);
            }

            Element startState = doc.createElement("startState");
            startState.appendChild(doc.createTextNode(oneAutomation.getStartState().getStates()));
            automationData.appendChild(startState);

            Element endStatesList = doc.createElement("endStatesList");
            automationData.appendChild(endStatesList);
            for (State currentEndState: oneAutomation.getEndState()) {
                Element endStates = doc.createElement("endStates");
                endStates.appendChild(doc.createTextNode(currentEndState.getStates()));
                endStatesList.appendChild(endStates);
            }
        }
        doc.getDocumentElement().normalize();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(file);
        transformer.transform(domSource, streamResult);
    }
}
