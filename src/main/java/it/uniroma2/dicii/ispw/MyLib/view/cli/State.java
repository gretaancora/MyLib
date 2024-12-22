package it.uniroma2.dicii.ispw.MyLib.view.cli;


public abstract class State {
    protected State() {}

    //di base se non sono necessarie azioni all'entrata di uno stato passo subito all'esecuzione della logica interna
    public void entry(StateMachine stateMachine){
        showHeadline();
        this.execute(stateMachine);
    }

    /*exit viene utilizzato solo quando necessaria azione all'uscita di uno stato, solitamente nella logica interna
    execute troviamo il cambio di stato della macchina*/
    public void exit(StateMachine stateMachine) {}
    public abstract void execute(StateMachine stateMachine);
    public void showMenu() {}
    public void showHeadline() {}
}
