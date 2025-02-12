package it.uniroma2.dicii.ispw.mylib.view.cli;

import java.util.ArrayDeque;
import java.util.Deque;

public class StateMachineImpl implements StateMachine {
    /*utilizzo una deque come pila di stati visitati sulla quale effettuare operazioni di push e pop*/
    private Deque<State> stateHistory = new ArrayDeque<>();
    private State currentState;

    //alla creazione della macchina setto il current state
    public StateMachineImpl() {
        this.currentState = new InitialState();
    }

    @Override
    public void start() {transitionTo(currentState);}

    //goBack e goNext settano il current state e la stateHistory prima di eseguire la transizione
    @Override
    public void goBack() {
        currentState = stateHistory.pop();
        transitionTo(currentState);
    }

    @Override
    public void goNext(State state) {
        stateHistory.push(currentState);
        currentState = state;
        transitionTo(currentState);
    }

    private void transitionTo(State state) {
        state.entry(this);
    }
}
