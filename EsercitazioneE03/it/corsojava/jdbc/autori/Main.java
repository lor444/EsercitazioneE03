package it.corsojava.jdbc.autori;

import it.corsojava.ui.terminal.TerminalUi;

public class Main {

    public static void main(String[] args) {
        TerminalUi tui = TerminalUi.getBuilder().build();
        UserInterface ui = new UserInterface(tui);
        ui.setConnectionString("jdbc:postgresql://localhost:5432/corsosql");
        ui.setUserName("corsosql");
        ui.setUserPass("corsosql");

        ui.run();
    }
}
