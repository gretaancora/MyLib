package it.uniroma2.dicii.ispw.mylib.view.cli.librarian;

import it.uniroma2.dicii.ispw.mylib.controller.ManageReservationsController;
import it.uniroma2.dicii.ispw.mylib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.BorrowNotFoundException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.mylib.model.Librarian;
import it.uniroma2.dicii.ispw.mylib.other.Printer;
import it.uniroma2.dicii.ispw.mylib.view.cli.State;
import it.uniroma2.dicii.ispw.mylib.view.cli.StateMachine;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;

public class ManagePendingReservationsState extends State {

    private static final Logger log = Logger.getLogger(Configurations.LOGGER_NAME);

    private Librarian librarian;

    public ManagePendingReservationsState(Librarian librarian) {
        this.librarian = librarian;
    }

    @Override
    public void execute(StateMachine stateMachine) {
        var in = new Scanner(System.in);
        var manageReservationController = new ManageReservationsController();

        List<BorrowBean> borrows = null;

        try {
            borrows = manageReservationController.getPendingReservations();
        } catch (DAOException e) {
            log.severe(e.getMessage());
            Printer.errorPrint("Error while getting pending reservations");

            stateMachine.goBack();
        }

        BorrowBean borrowBean = null;

        try {
            borrowBean = selectBorrow(borrows, in);
        } catch (BorrowNotFoundException e) {
            stateMachine.goBack();
        }

        if(borrowBean==null){
            stateMachine.goBack();
        }else {
            try {
                borrowBean = manageReservationController.activateReservation(borrowBean);
            } catch (DAOException e) {
                log.severe(e.getMessage());
                Printer.errorPrint("Error while getting pending reservations");

                stateMachine.goBack();
            }
            Printer.println("Activation succeeded!");
            Printer.println("Initial date: " + borrowBean.getInDate() + "\t" + "Ending date: " + borrowBean.getEndDate());
            stateMachine.goBack();
        }

    }


    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------MANAGE PENDING RESERVATIONS--------------");
    }


    private BorrowBean selectBorrow(List<BorrowBean> borrows, Scanner in) throws BorrowNotFoundException {
        int i = 1;
        int choice;

        if(borrows.isEmpty()) {
            Printer.println("No pending reservations found.");
            throw new BorrowNotFoundException();

        }else {

            Printer.println("0) go back");

            for (BorrowBean borrow : borrows) {
                Printer.println(i++ + ") " + borrow.toString() + " -> activate");
            }

            while (true) {
                try {
                    choice = in.nextInt();
                    in.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    Printer.println("Please enter a valid option!");
                    in.nextLine();
                } catch (NoSuchElementException e) {
                    Printer.println("Please enter a valid option!");
                }
            }
        }

        if(choice==0){
            return null;
        }else {
            return borrows.get(--choice);
        }
    }
}
