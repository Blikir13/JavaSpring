package console;

public enum Commands {

    HELP("Enter the 'help' to see all commands"),
    CREATE("To create record"),
    READ("Read all data"),
    EXIT ("Exit from application"),
    DELETE("Delete record"),
    UPDATE("Update record"),
    ;

    private final String description;

    Commands(String description) {
        this.description = description;
    }

    public void printHelp() {
        for (Commands command : Commands.values()) {
            System.out.println(command.name() + "," + command.description);
        }
    }
}