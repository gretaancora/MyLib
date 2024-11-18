package it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.librarian;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.State;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.StateMachine;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Librarian;

public class ManagePendingReservationsState extends State {
    private Librarian librarian;
    public ManagePendingReservationsState(Librarian librarian) {
        super();
        this.librarian = librarian;
    }

    @Override
    public void execute(StateMachine stateMachine) {

    }
}
