package bg.tu_varna.sit.commands;

public enum CommandsEnum {
        OPEN("opens <file> "),
        CLOSE("closes currently opened file"),
        SAVEAS("saves the currently open file"),
        SAVE("saves the currently open file in <file>"),
        HELP("prints the information"),
        EXIT("exists the program"),
        LIST("list all ids"),
        PRINT("prints information for all transactions for an automation"),
        SAVEID("saves an automation in file"),
        EMPTY("check if the alphabet is empty"),
        DETERMINISTIC("check if the automation is determined"),
        RECOGNIZE("check if the word is from automation"),
        UNION("union of two automations"),
        CONCAT("concatenation of two automations"),
        UN("positive envelope of automation"),
        REG("Klini`s theorem"),
        MUTATOR("make automation deterministic"),
        FINITE("check if the automation is closed-loop");

        private final String commandEnum;

        CommandsEnum(String commandEnum) {
            this.commandEnum = commandEnum;
        }

        public String getCommandEnum() {
            return commandEnum;
        }
    }

