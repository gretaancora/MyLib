package it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.costumer;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.State;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.StateMachine;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Costumer;

public class BorrowState extends State {
    private Costumer costumer;

    public BorrowState(Costumer costumer) {
        super();
        this.costumer = costumer;
    }
    @Override
    public void execute(StateMachine stateMachine) {

    }
}
