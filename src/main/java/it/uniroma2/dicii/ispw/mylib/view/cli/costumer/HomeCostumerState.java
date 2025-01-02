package it.uniroma2.dicii.ispw.mylib.view.cli.costumer;

import it.uniroma2.dicii.ispw.mylib.other.Printer;
import it.uniroma2.dicii.ispw.mylib.view.cli.State;
import it.uniroma2.dicii.ispw.mylib.view.cli.StateMachine;
import it.uniroma2.dicii.ispw.mylib.view.cli.StateMachineImpl;
import it.uniroma2.dicii.ispw.mylib.model.Costumer;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HomeCostumerState extends State {
    private Costumer costumer;
    private static final String INVALID_CHOICE = "Invalid choice. Try again...";

    public HomeCostumerState(Costumer costumer) {
        super();
        this.costumer = costumer;
    }

    @Override
    public void execute(StateMachine stateMachine) {
        showMenu();

        Scanner scan = new Scanner(System.in);
        int choice;

        while(true) {
            Printer.print("Enter your choice: ");

            try{
                choice = scan.nextInt();
                scan.nextLine();

                switch (choice) {
                    case 1 -> Printer.println("Feature not implemented yet.");
                    case 2 -> stateMachine.goNext(new BorrowState(costumer));
                    case 3 -> new StateMachineImpl().start();
                    default -> Printer.println(INVALID_CHOICE);
                }

            } catch (InputMismatchException e){
                Printer.errorPrint(INVALID_CHOICE);
                scan.nextLine();
            }catch (NoSuchElementException e){
                Printer.errorPrint(INVALID_CHOICE);
            }
        }
    }

    @Override
    public void showMenu() {
        Printer.println("Choose one of the following options: ");
        Printer.println("1) show profile");
        Printer.println("2) borrow a book");
        Printer.println("3) logout");
    }
    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------HOME--------------");
    }
}
