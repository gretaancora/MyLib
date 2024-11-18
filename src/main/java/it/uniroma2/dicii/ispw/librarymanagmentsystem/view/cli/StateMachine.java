package it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.State;

public interface StateMachine {
    void start();
    void goBack();
    void goNext(State state);
    void transitionTo(State state);
}
