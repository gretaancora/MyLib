package it.uniroma2.dicii.ispw.mylib.view.cli;

public interface StateMachine {
    void start();
    void goBack();
    void goNext(State state);
    void transitionTo(State state);
}
