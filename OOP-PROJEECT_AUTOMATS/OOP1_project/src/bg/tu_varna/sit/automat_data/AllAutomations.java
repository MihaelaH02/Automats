package bg.tu_varna.sit.automat_data;

import java.util.LinkedHashMap;
import java.util.Map;

public class AllAutomations {
    private Map<Integer, OneAutomation> automats;

    public AllAutomations() {
        this.automats = new LinkedHashMap<>();
    }

    public Map<Integer, OneAutomation> getMap() {
        return automats;
    }

    public void putElement(OneAutomation value) {
        this.automats.put(this.getLastKey()+1,value);
    }

    private Integer getLastKey() {
        Integer lastEntry = null;
        for (Integer entry : this.automats.keySet())
            lastEntry = entry;

        if (lastEntry != null)
            return lastEntry;
        else
            return 0;
    }
}
