package bg.tu_varna.sit.validate;

import bg.tu_varna.sit.automat_data.AllAutomations;

public class ValidateID {
    private final AllAutomations allAutomations;

    public ValidateID(AllAutomations allAutomations) {
        this.allAutomations = allAutomations;
    }

    public Integer validateID(Integer id) throws Exception {
        if(this.allAutomations.getMap().containsKey(id)) return id;
        throw new Exception("There is no automation with this ID!");
    }
}
